package hr.fer.zemris.java.servlets.glasanje;

/**
 * This class represents a band and holds all information about one. It provides no useful methods and is used only to
 * store data.
 *
 * @author matej
 */
public class Band {
    /**
     * Band id.
     */
    private int id;
    /**
     * Band name.
     */
    private String name;
    /**
     * Link to a song.
     */
    private String link;
    /**
     * Vote count in the rankings.
     */
    private int voteCount;

    /**
     * Constructor for the class.
     *
     * @param id id of band
     * @param name name of band
     * @param link link to a song
     */
    public Band(int id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.voteCount = 0;
    }

    /**
     * Returns the id of the band.
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns name of band.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns link to song.
     *
     * @return link
     */
    public String getLink() {
        return link;
    }

    /**
     * Returns the current vote count in the polls.
     *
     * @return vote count
     */
    public int getVoteCount() {
        return voteCount;
    }

    /**
     * Sets the current vote count to the given value.
     *
     * @param voteCount value to set vote count to
     */
    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
