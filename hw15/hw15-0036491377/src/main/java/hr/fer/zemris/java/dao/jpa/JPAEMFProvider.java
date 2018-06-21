package hr.fer.zemris.java.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * JPA entity manager factory provider.
 *
 * @author mcupic
 */
public class JPAEMFProvider {

    /**
     * Entity manager factory.
     */
    public static EntityManagerFactory emf;

    /**
     * Returns the entity manager factory.
     *
     * @return entity manager factory
     */
    public static EntityManagerFactory getEmf() {
        return emf;
    }

    /**
     * Sets the entity manager factory to the given one.
     *
     * @param emf entity manager factory
     */
    public static void setEmf(EntityManagerFactory emf) {
        JPAEMFProvider.emf = emf;
    }
}