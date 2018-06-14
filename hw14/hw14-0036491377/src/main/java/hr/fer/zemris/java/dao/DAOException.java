package hr.fer.zemris.java.dao;

/**
 * Models a DAO Exception.
 *
 * @author matej
 */
public class DAOException extends RuntimeException {

    /**
     * UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for DAO exception.
     *
     * @param message message
     * @param cause cause
     * @param enableSuppression enable suppression
     * @param writableStackTrace writable stack trace
     */
    public DAOException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Constructor for DAO exception.
     *
     * @param message message
     * @param cause cause
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for DAO exception.
     *
     * @param message message
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructor for DAO exception.
     *
     * @param cause cause
     */
    public DAOException(Throwable cause) {
        super(cause);
    }
}
