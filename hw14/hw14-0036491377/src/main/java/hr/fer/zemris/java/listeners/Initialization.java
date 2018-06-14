package hr.fer.zemris.java.listeners;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import hr.fer.zemris.java.model.PollOption;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

@WebListener
public class Initialization implements ServletContextListener {
    private static final String POLL_CREATE_SQL = "CREATE TABLE POLLS"
                                                  + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                                                  + " title VARCHAR(150) NOT NULL,"
                                                  + " message CLOB(2048) NOT NULL"
                                                  + ")";
    private static final String OPTIONS_CREATE_SQL = "CREATE TABLE POLLOPTIONS"
                                                     + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                                                     + " optionTitle VARCHAR(100) NOT NULL,"
                                                     + " optionLink VARCHAR(150) NOT NULL,"
                                                     + " pollID BIGINT,"
                                                     + " votesCount BIGINT,"
                                                     + " FOREIGN KEY (pollID) REFERENCES Polls(id)"
                                                     + ")";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String path = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            System.err.println("Couldn't find properties file.");
            return;
        }

        String dbName = properties.getProperty("name");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String host = properties.getProperty("host");
        String port = properties.getProperty("port");

        if (dbName == null || user == null || password == null || host == null || port == null) {
            throw new IllegalArgumentException("Missing properties. Required: name, user, password, host, port.");
        }

        String template = "jdbc:derby://%s:%s/%s;user=%s;password=%s";
        String connectionURL = String.format(template, host, port, dbName, user, password);

        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
        } catch (PropertyVetoException e1) {
            throw new RuntimeException("Could not initialize pooled data source.", e1);
        }

        cpds.setJdbcUrl(connectionURL);

        verifyTables(cpds);

        sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        ComboPooledDataSource cpds = (ComboPooledDataSource) ctx.getAttribute("hr.fer.zemris.dbpool");
        if (cpds != null) {
            try {
                DataSources.destroy(cpds);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void verifyTables(ComboPooledDataSource cpds) {
        Connection con;
        DatabaseMetaData dbmb;
        boolean populate = false;
        try {
            con = cpds.getConnection();
            dbmb = con.getMetaData();
        } catch (SQLException e) {
            System.err.println("Couldn't make connection.");
            return;
        }


        // Create table Polls if not exists
        try {
            ResultSet rs = dbmb.getTables(null, null, "POLLS", null);
            if (!rs.next()) {
                Statement st = con.createStatement();
                st.execute(POLL_CREATE_SQL);
                populate = true;
            } else {
                PreparedStatement pst = con.prepareStatement("select count(*) as cnt from polls");
                ResultSet rsc = pst.executeQuery();
                rsc.next();
                if (rsc.getInt("cnt") == 0) {
                    populate = true;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getErrorCode() + ' ' + e.getMessage());
        }

        try {
            ResultSet rs = dbmb.getTables(null, null, "POLLOPTIONS", null);
            if (!rs.next()) {
                Statement st = con.createStatement();
                st.execute(OPTIONS_CREATE_SQL);
                populate = true;
            }
        } catch (SQLException e) {
            System.err.println(e.getErrorCode() + ' ' + e.getMessage());
        }

        if (populate) {
            populatePolls(con);
        }
    }

    private void populatePolls(Connection con) {
        // Could also initialize through an external document, but it's not necessary
        populatePoll(con, "Glasanje za omiljeni bend:", "Koji vam je najdrazi bend? Kliknite link za glasanje!",
                new PollOption[]{
                        new PollOption("The Beatles", "https://www.youtube.com/watch?v=UM99VOP-TvI", 150),
                        new PollOption("The Beach Boys", "https://www.youtube.com/watch?v=w-0CS-T1HUQ", 149),
                        new PollOption("The Platters", "https://www.youtube.com/watch?v=H2di83WAOhU", 60),
                        new PollOption("The Four Seasons", "https://www.youtube.com/watch?v=y8yvnqHmFds", 20)
                });

        populatePoll(con, "Glasajte ovdje za najbolji predmet na FER-u:", "Klik na link!",
                new PollOption[]{
                        new PollOption("Matematika 2", "https://www.fer.unizg.hr/predmet/mat2", 64),
                        new PollOption("Signali i sustavi", "http://www.fer.unizg.hr/predmet/sis2", 111),
                        new PollOption("Oblikovanje programske potpore", "https://www.fer.unizg.hr/predmet/opp", 6),
                        new PollOption("Komunikacijske mreže", "https://www.fer.unizg.hr/predmet/kommre", 129),
                });
    }

    private void populatePoll(Connection con, String title, String message, PollOption[] pollOptions) {
        try {
            PreparedStatement pst = con.prepareStatement("insert into polls (title, message) values\n"
                                                         + "(?, ?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, title);
            pst.setString(2, message);
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs == null || !rs.next()) {
                return;
            }
            long id = rs.getLong(1);

            for (PollOption option : pollOptions) {
                PreparedStatement st = con.prepareStatement("insert into pollOptions (optionTitle, optionLink, pollID, "
                                                            + "votesCount) values (?, ?, ?, ?)");

                st.setString(1, option.getTitle());
                st.setString(2, option.getLink());
                st.setLong(3, id);
                st.setLong(4, option.getVoteCount());
                st.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Couldn't populate polls.");
        }
    }

}
