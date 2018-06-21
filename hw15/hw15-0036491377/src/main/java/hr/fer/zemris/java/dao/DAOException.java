package hr.fer.zemris.java.dao;

/**
 * Exception used to signify that something went wrong during the DAO operations.
 *
 * @author matej
 */
public class DAOException extends RuntimeException {

    /**
     * UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     *
     * @param message message
     * @param cause cause
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param message message
     */
    public DAOException(String message) {
        super(message);
    }
}