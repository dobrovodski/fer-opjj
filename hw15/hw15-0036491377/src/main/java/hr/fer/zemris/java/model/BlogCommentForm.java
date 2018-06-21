package hr.fer.zemris.java.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to validate the {@link BlogComment} before trying to submit to database.
 *
 * @author matej
 */
public class BlogCommentForm {
    /**
     * Comment length limit.
     */
    private static final int CHAR_LIM = 500;
    /**
     * Email of the blog comment's poster.
     */
    private String mail;
    /**
     * Content of the comment.
     */
    private String message;
    /**
     * Error message returned if validation failed.
     */
    private String errorMessage;

    /**
     * Constructor.
     *
     * @param usersEMail email of the blog comment's poster
     * @param message content of the comment
     */
    public BlogCommentForm(String usersEMail, String message) {
        this.mail = usersEMail;
        this.message = message;
    }

    /**
     * Returns true if the blog comment is valid and ready to be stored in the database.
     *
     * @return true if the comment is valid
     */
    public boolean validate() {
        if (mail.length() < 1) {
            errorMessage = "Email cannot be empty!";
            return false;
        }

        if (message.length() < 1) {
            errorMessage = "Message cannot be empty!";
            return false;
        }

        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(mail);
        if (!matcher.find()) {
            errorMessage = "Email is invalid!";
            return false;
        }

        if (message.length() > CHAR_LIM) {
            errorMessage = "Message is too long! Limit is: " + CHAR_LIM;
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
        return errorMessage;
    }
}
