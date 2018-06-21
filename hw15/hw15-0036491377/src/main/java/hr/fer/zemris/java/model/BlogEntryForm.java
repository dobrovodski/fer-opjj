package hr.fer.zemris.java.model;

/**
 * Class used to validate {@link BlogEntry} before trying to add it to the database.
 *
 * @author matej
 */
public class BlogEntryForm {
    /**
     * Limit of title.
     */
    private static final int CHAR_LIM_TITLE = 100;
    /**
     * Limit of contents of the entry.
     */
    private static final int CHAR_LIM_TEXT = 800;
    /**
     * Title.
     */
    private String title;
    /**
     * Text.
     */
    private String text;
    /**
     * Error message which might occur while validating.
     */
    private String message;

    /**
     * Constructor.
     *
     * @param title title of the entry
     * @param text text of the entry
     */
    public BlogEntryForm(String title, String text) {
        this.title = title;
        this.text = text;
    }

    /**
     * Returns true if the blog entry is valid and ready to be stored in the database.
     *
     * @return true if the comment is valid
     */
    public boolean validate() {
        if (title.length() < 1) {
            message = "Title cannot be empty!";
            return false;
        }

        if (text.length() < 1) {
            message = "Text cannot be empty!";
            return false;
        }

        if (title.length() > CHAR_LIM_TITLE) {
            message = "Title is too long! Limit is: " + CHAR_LIM_TITLE;
            return false;
        }

        if (text.length() > CHAR_LIM_TEXT) {
            message = "Message is too long! Limit is: " + CHAR_LIM_TEXT;
            return false;
        }

        return true;
    }

    /**
     * Returns the error message if the validation failed.
     *
     * @return error message
     */
    public String getMessage() {
        return message;
    }
}
