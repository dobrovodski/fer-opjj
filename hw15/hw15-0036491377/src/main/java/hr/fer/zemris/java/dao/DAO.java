package hr.fer.zemris.java.dao;

import hr.fer.zemris.java.model.BlogComment;
import hr.fer.zemris.java.model.BlogEntry;
import hr.fer.zemris.java.model.BlogUser;

import java.util.List;

/**
 * DAO interface for adding blog entries, comments and users. It communicates with the database in order to retrieve or
 * store data.
 *
 * @author matej
 */
public interface DAO {

    /**
     * Gets {@link BlogEntry} with the given id or null if none exists.
     *
     * @param id id of entry
     *
     * @return entry with given id
     *
     * @throws DAOException exception
     */
    public BlogEntry getBlogEntry(Long id) throws DAOException;

    /**
     * Adds {@link BlogEntry} to database or updates already existing one.
     *
     * @param entry entry to add/update
     *
     * @throws DAOException exception
     */
    public void addBlogEntry(BlogEntry entry) throws DAOException;

    /**
     * Adds entry comment to the database.
     *
     * @param comment comment to add
     *
     * @throws DAOException exception
     */
    public void addEntryComment(BlogComment comment) throws DAOException;

    /**
     * Returns blog entries for given username.
     *
     * @param nick username of account
     *
     * @return list of blog entries for given username
     *
     * @throws DAOException exception
     */
    public List<BlogEntry> getBlogEntries(String nick) throws DAOException;

    /**
     * Returns the {@link BlogUser} for the given username.
     *
     * @param nick username of blog user
     *
     * @return blog user or null if there is no such user
     *
     * @throws DAOException exception
     */
    public BlogUser getBlogUser(String nick) throws DAOException;

    /**
     * Adds a blog user into the database.
     *
     * @param user blog user to add
     *
     * @throws DAOException exception
     */
    public void addBlogUser(BlogUser user) throws DAOException;

    /**
     * Returns a list of registered users.
     *
     * @return list of registered users
     *
     * @throws DAOException exception
     */
    public List<BlogUser> getRegisteredUsers() throws DAOException;

    /**
     * Checks if the user with the given username exists or not.
     *
     * @param nick username of account
     *
     * @return true if it exists, false otherwise
     *
     * @throws DAOException exception
     */
    public boolean userExists(String nick) throws DAOException;

    /**
     * Checks if the blog with the given id exists or not.
     *
     * @param id id of blog
     *
     * @return true if it exists, false otherwise
     *
     * @throws DAOException exception
     */
    public boolean blogExists(Long id) throws DAOException;
}