package hr.fer.zemris.java.model;

/**
 * Models a single poll option table entry. Only provides getters and setters.
 *
 * @author matej
 */
public class PollOption {
    /**
     * Title of option.
     */
    private String title;
    /**
     * Link of the option.
     */
    private String link;
    /**
     * Id.
     */
    private int id;
    /**
     * Id of poll it belongs to.
     */
    private long pollId;
    /**
     * Number of votes.
     */
    private int voteCount;

    /**
     * Constructor.
     */
    public PollOption() {
    }

    /**
     * Constructor.
     *
     * @param title title
     * @param link link
     * @param voteCount vote count
     */
    public PollOption(String title, String link, int voteCount) {
        this.title = title;
        this.link = link;
        this.voteCount = voteCount;
    }

    /**
     * Getter for title of option.
     *
     * @return title of option
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of option to the given one.
     *
     * @param title title of option
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for link of option.
     *
     * @return link of option
     */

    public String getLink() {
        return link;
    }

    /**
     * Sets the link of option to the given one.
     *
     * @param link link of option
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Getter for id of option.
     *
     * @return id of option
     */

    public int getId() {
        return id;
    }

    /**
     * Sets the id of option to the given one.
     *
     * @param id id of option
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for poll id of option.
     *
     * @return poll id of option
     */

    public long getPollId() {
        return pollId;
    }

    /**
     * Sets the poll id of option to the given one.
     *
     * @param pollId poll id of option
     */
    public void setPollId(long pollId) {
        this.pollId = pollId;
    }

    /**
     * Getter for vote count of option.
     *
     * @return vote count of option
     */

    public int getVoteCount() {
        return voteCount;
    }

    /**
     * Sets the vote count of option to the given one.
     *
     * @param voteCount vote count of option
     */
    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
