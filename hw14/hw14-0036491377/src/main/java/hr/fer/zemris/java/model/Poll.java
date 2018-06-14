package hr.fer.zemris.java.model;

/**
 * Models a single poll entry. Only provides getters and setters.
 *
 * @author matej
 */
public class Poll {
    /**
     * Title of poll.
     */
    private String title;
    /**
     * Message of poll.
     */
    private String message;
    /**
     * Id of poll.
     */
    private int id;

    /**
     * Constructor.
     */
    public Poll() {
    }

    /**
     * Getter for poll title.
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title to given one.
     *
     * @param title title to set to
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for poll message.
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message to given one.
     *
     * @param message title to set to
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter for poll id.
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id to given one.
     *
     * @param id id to set to
     */
    public void setId(int id) {
        this.id = id;
    }
}
