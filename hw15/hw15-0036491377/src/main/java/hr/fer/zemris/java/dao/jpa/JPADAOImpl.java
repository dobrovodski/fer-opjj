package hr.fer.zemris.java.dao.jpa;

import hr.fer.zemris.java.dao.DAO;
import hr.fer.zemris.java.dao.DAOException;
import hr.fer.zemris.java.model.BlogComment;
import hr.fer.zemris.java.model.BlogEntry;
import hr.fer.zemris.java.model.BlogUser;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Implementation of {@link DAO} using JPA.
 *
 * @author matej
 */
public class JPADAOImpl implements DAO {

    @Override
    public BlogEntry getBlogEntry(Long id) throws DAOException {
        BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
        return blogEntry;
    }

    @Override
    public void addBlogEntry(BlogEntry entry) throws DAOException {
        EntityManager em = JPAEMFProvider.getEmf().createEntityManager();
        em.getTransaction().begin();
        em.merge(entry);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void addEntryComment(BlogComment comment) throws DAOException {
        EntityManager em = JPAEMFProvider.getEmf().createEntityManager();
        em.getTransaction().begin();
        em.merge(comment);
        em.getTransaction().commit();
        em.close();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BlogEntry> getBlogEntries(String nick) throws DAOException {
        List<BlogEntry> entries = (List<BlogEntry>) JPAEMProvider.getEntityManager()
                                                                 .createQuery("select u from BlogEntry as u where u"
                                                                              + ".creator.nick=:nick")
                                                                 .setParameter("nick", nick)
                                                                 .getResultList();

        return entries;
    }

    @SuppressWarnings("unchecked")
    @Override
    public BlogUser getBlogUser(String nick) throws DAOException {
        List<BlogUser> users = (List<BlogUser>) JPAEMProvider.getEntityManager()
                                                             .createQuery("select u from BlogUser as u where u"
                                                                          + ".nick=:nick")
                                                             .setParameter("nick", nick)
                                                             .getResultList();
        if (users.size() > 1) {
            throw new DAOException("Multiple users with same nick: " + nick);
        }

        if (users.size() == 0) {
            return null;
        }

        return users.get(0);
    }

    @Override
    public void addBlogUser(BlogUser user) throws DAOException {
        EntityManager em = JPAEMFProvider.getEmf().createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BlogUser> getRegisteredUsers() throws DAOException {
        return (List<BlogUser>) JPAEMProvider.getEntityManager().createQuery("from BlogUser").getResultList();
    }

    @Override
    public boolean userExists(String nick) throws DAOException {
        return JPAEMProvider.getEntityManager().createQuery("select u from BlogUser as u where u.nick=:nick")
                            .setParameter("nick", nick)
                            .getResultList().size() > 0;
    }

    @Override
    public boolean blogExists(Long id) throws DAOException {
        return JPAEMProvider.getEntityManager().createQuery("select c from BlogEntry as c where c.id=:id")
                            .setParameter("id", id)
                            .getResultList().size() > 0;
    }


}