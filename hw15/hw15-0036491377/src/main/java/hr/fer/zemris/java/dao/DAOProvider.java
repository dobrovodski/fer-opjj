package hr.fer.zemris.java.dao;

import hr.fer.zemris.java.dao.jpa.JPADAOImpl;

/**
 * Provider which returns an implementation of the {@link DAO}.
 *
 * @author mcupic
 */
public class DAOProvider {

    /**
     * DAO stored in the provider.
     */
    private static DAO dao = new JPADAOImpl();

    /**
     * Returns currently stored DAO.
     *
     * @return stored DAO
     */
    public static DAO getDAO() {
        return dao;
    }

}