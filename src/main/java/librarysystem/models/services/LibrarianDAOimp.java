package librarysystem.models.services;

import librarysystem.models.Librarian;
import librarysystem.models.interfaces.LibrarianDAO;
import librarysystem.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class LibrarianDAOimp implements LibrarianDAO {
    private HibernateUtil hibernateUtil;
    private SessionFactory sessionFactory;

    public LibrarianDAOimp() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }

    @Override
    public void save(Librarian librarian) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(librarian);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving librarian", e);
        } finally {
            session.close();
        }
    }
    public Librarian findByUsername(String username) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Librarian WHERE username = :username";
            Query<Librarian> query = session.createQuery(hql, Librarian.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching Librarian by username", e);
        } finally {
            session.close();
        }
    }
    public Librarian findByEmail(String email) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Librarian WHERE email = :email";
            Query<Librarian> query = session.createQuery(hql, Librarian.class);
            query.setParameter("email", email);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching Librarian by email", e);
        } finally {
            session.close();
        }
    }

}
