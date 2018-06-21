package hr.fer.zemris.java.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used for validating {@link BlogUser}s before trying to add them to the database.
 *
 * @author matej
 */
public class BlogUserForm {
    /**
     * Password min length.
     */
    private static final int PWD_MIN_LENGTH = 5;
    /**
     * Password max length.
     */
    private static final int PWD_MAX_LENGTH = 30;
    /**
     * First name max length.
     */
    private static final int FIRSTNAME_LENGTH = 30;
    /**
     * Last name max length.
     */
    private static final int LASTNAME_LENGTH = 30;
    /**
     * Nickname max length.
     */
    private static final int NICK_LENGTH = 20;
    /**
     * Email max length.
     */
    private static final int EMAIL_LENGTH = 40;
    /**
     * First name of user.
     */
    private String firstName;
    /**
     * Last name of user.
     */
    private String lastName;
    /**
     * Username of user.
     */
    private String nick;
    /**
     * Email of user.
     */
    private String email;
    /**
     * Password of user.
     */
    private String password;
    /**
     * Error message returned if validation failed.
     */
    private String message;

    /**
     * Constructor.
     *
     * @param firstName first name
     * @param lastName last name
     * @param nick username
     * @param email email
     * @param password password
     */
    public BlogUserForm(String firstName, String lastName, String nick, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nick = nick;
        this.email = email;
        this.password = password;
    }

    /**
     * Returns true if the blog user is valid and ready to be stored in the database.
     *
     * @return true if the comment is valid
     */
    public boolean validate() {
        if (firstName.length() < 1) {
            message = "First name cannot be empty!";
            return false;
        }

        if (firstName.length() > FIRSTNAME_LENGTH) {
            message = "First name too long! Max is: " + FIRSTNAME_LENGTH;
            return false;
        }

        if (lastName.length() < 1) {
            message = "Last name cannot be empty!";
            return false;
        }

        if (lastName.length() > LASTNAME_LENGTH) {
            message = "Last name too long! Max is: " + LASTNAME_LENGTH;
            return false;
        }

        if (nick.length() < 1) {
            message = "Username cannot be empty!";
            return false;
        }

        if (nick.length() > NICK_LENGTH) {
            message = "Username too long! Max is: " + NICK_LENGTH;
            return false;
        }

        if (email.length() < 1) {
            message = "Email cannot be empty!";
            return false;
        }

        if (email.length() > EMAIL_LENGTH) {
            message = "Email too long! Max is: " + EMAIL_LENGTH;
            return false;
        }

        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(email);
        if (!matcher.find()) {
            message = "Email is invalid!";
            return false;
        }

        if (password.length() < PWD_MIN_LENGTH) {
            message = "Password must be at least" + PWD_MIN_LENGTH + " characters long!";
            return false;
        }

        if (password.length() > PWD_MAX_LENGTH) {
            message = "Password must be at most" + PWD_MAX_LENGTH + " characters long!";
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
