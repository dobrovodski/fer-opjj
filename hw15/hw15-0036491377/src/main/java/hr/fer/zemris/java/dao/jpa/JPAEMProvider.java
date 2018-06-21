package hr.fer.zemris.java.dao.jpa;

import hr.fer.zemris.java.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * JPA entity manager provider. Stores managers in a local thread and returns them when needed.
 *
 * @author mcupic
 */
public class JPAEMProvider {

    /**
     * Thread locals of entity managers.
     */
    private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

    /**
     * Returns the next available entity manager.
     *
     * @return entity manager
     */
    public static EntityManager getEntityManager() {
        EntityManager em = locals.get();
        if (em == null) {
            em = JPAEMFProvider.getEmf().createEntityManager();
            em.getTransaction().begin();
            locals.set(em);
        }
        return em;
    }

    /**
     * Commits any transactions and closes the entity manager.
     *
     * @throws DAOException if unable to commit transaction or close entity manager
     */
    public static void close() throws DAOException {
        EntityManager em = locals.get();
        if (em == null) {
            return;
        }
        DAOException dex = null;
        try {
            em.getTransaction().commit();
        } catch (Exception ex) {
            dex = new DAOException("Unable to commit transaction.", ex);
        }
        try {
            em.close();
        } catch (Exception ex) {
            if (dex != null) {
                dex = new DAOException("Unable to close entity manager.", ex);
            }
        }
        locals.remove();
        if (dex != null) {
            throw dex;
        }
    }

}