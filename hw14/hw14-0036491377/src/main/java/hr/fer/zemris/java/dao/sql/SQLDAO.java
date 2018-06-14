package hr.fer.zemris.java.dao.sql;

import hr.fer.zemris.java.dao.DAO;
import hr.fer.zemris.java.dao.DAOException;
import hr.fer.zemris.java.model.Poll;
import hr.fer.zemris.java.model.PollOption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova konkretna implementacija očekuje da joj veza stoji
 * na raspolaganju preko {@link SQLConnectionProvider} razreda, što znači da bi netko prije no što izvođenje dođe do ove
 * točke to trebao tamo postaviti. U web-aplikacijama tipično rješenje je konfigurirati jedan filter koji će presresti
 * pozive servleta i prije toga ovdje ubaciti jednu vezu iz connection-poola, a po zavrsetku obrade je maknuti.
 *
 * @author marcupic
 */
public class SQLDAO implements DAO {
    @Override
    public List<Poll> getPollsInfo() {
        List<Poll> pollList = new ArrayList<>();
        Connection con = SQLConnectionProvider.getConnection();
        PreparedStatement pst;
        try {
            pst = con.prepareStatement("select id, title, message from polls");
            try {
                ResultSet rs = pst.executeQuery();
                if (rs == null) {
                    return null;
                }
                try {
                    while (rs.next()) {
                        Poll poll = new Poll();
                        poll.setId(rs.getInt(1));
                        poll.setTitle(rs.getString(2));
                        poll.setMessage(rs.getString(3));
                        pollList.add(poll);
                    }
                } finally {
                    try {
                        rs.close();
                    } catch (Exception ignored) {
                    }
                }
            } finally {
                try {
                    pst.close();
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ex) {
            throw new DAOException("Error while retrieving polls.", ex);
        }

        return pollList;
    }

    @Override
    public Poll getPoll(long id) throws DAOException {
        Poll poll = new Poll();
        Connection con = SQLConnectionProvider.getConnection();
        PreparedStatement pst;
        try {
            pst = con.prepareStatement("select title, message from polls where id=?");
            pst.setLong(1, id);
            try {
                ResultSet rs = pst.executeQuery();
                if (rs == null) {
                    return null;
                }
                try {
                    while (rs.next()) {
                        poll.setTitle(rs.getString(1));
                        poll.setMessage(rs.getString(2));
                    }
                } finally {
                    try {
                        rs.close();
                    } catch (Exception ignored) {
                    }
                }
            } finally {
                try {
                    pst.close();
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ex) {
            throw new DAOException("Error while retrieving polls.", ex);
        }

        return poll;
    }

    @Override
    public List<PollOption> getPollOptionsInfo(long pollId) throws DAOException {
        List<PollOption> pollOptionsList = new ArrayList<>();
        Connection con = SQLConnectionProvider.getConnection();
        PreparedStatement pst;
        try {
            pst = con.prepareStatement("select id, optiontitle from polloptions where pollid=?");
            pst.setLong(1, pollId);
            try {
                ResultSet rs = pst.executeQuery();
                try {
                    if (rs == null) {
                        return null;
                    }
                    while (rs.next()) {
                        PollOption pollOption = new PollOption();
                        pollOption.setId(rs.getInt(1));
                        pollOption.setPollId(pollId);
                        pollOption.setTitle(rs.getString(2));
                        pollOptionsList.add(pollOption);
                    }
                } finally {
                    try {
                        rs.close();
                    } catch (Exception ignored) {
                    }
                }
            } finally {
                try {
                    pst.close();
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ex) {
            throw new DAOException("Error while retrieving poll options.", ex);
        }
        return pollOptionsList;
    }

    @Override
    public List<PollOption> getPollOptions(long pollId) throws DAOException {
        List<PollOption> pollOptionsList = new ArrayList<>();
        Connection con = SQLConnectionProvider.getConnection();
        PreparedStatement pst;
        try {
            pst = con.prepareStatement("select id, optiontitle, optionlink, votescount from polloptions where "
                                       + "pollid=?");
            pst.setLong(1, pollId);
            try {
                ResultSet rs = pst.executeQuery();
                try {
                    if (rs == null) {
                        return null;
                    }
                    while (rs.next()) {
                        PollOption pollOption = new PollOption();
                        pollOption.setId(rs.getInt(1));
                        pollOption.setPollId(pollId);
                        pollOption.setTitle(rs.getString(2));
                        pollOption.setLink(rs.getString(3));
                        pollOption.setVoteCount(rs.getInt(4));
                        pollOptionsList.add(pollOption);
                    }
                } finally {
                    try {
                        rs.close();
                    } catch (Exception ignored) {
                    }
                }
            } finally {
                try {
                    pst.close();
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ex) {
            throw new DAOException("Error while retrieving poll options.", ex);
        }
        return pollOptionsList;
    }
}
