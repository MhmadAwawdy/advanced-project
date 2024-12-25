package librarysystem.models.services;

import librarysystem.models.User;
import librarysystem.models.interfaces.UserDAO;
import librarysystem.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UserDAOimp implements UserDAO {
    private HibernateUtil hibernateUtil;
    private SessionFactory sessionFactory;

    public UserDAOimp() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }

    @Override
    public void save(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving user", e);
        } finally {
            session.close();
        }
    }

    /**
     * البحث عن المستخدم بواسطة اسم المستخدم
     * @param username اسم المستخدم المراد البحث عنه
     * @return كائن المستخدم إذا تم العثور عليه، وإلا يعيد null
     */
    public User findByUsername(String username) {
        Session session = sessionFactory.openSession();
        try {
            // استعلام HQL للبحث عن المستخدم
            String hql = "FROM User WHERE username = :username";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);
            return query.uniqueResult(); // يعيد النتيجة أو null إذا لم توجد
        } catch (Exception e) {
            throw new RuntimeException("Error fetching user by username", e);
        } finally {
            session.close();
        }
    }
}
