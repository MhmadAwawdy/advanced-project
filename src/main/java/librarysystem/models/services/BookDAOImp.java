package librarysystem.models.services;

import librarysystem.models.Book;
import librarysystem.models.interfaces.BookDAO;
import librarysystem.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BookDAOImp implements BookDAO {

    private HibernateUtil hibernateUtil;
    private SessionFactory sessionFactory;

    public BookDAOImp() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }


    public void save(Book book) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(book);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save the book", e);
        } finally {
            session.close();
        }
    }

    public boolean isBookExists(String title, String author) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            String hql = "FROM Book WHERE title = :title AND author = :author";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("title", title);
            query.setParameter("author", author);

            Book result = query.uniqueResult();
            return result != null;

        } catch (Exception e) {
            throw new RuntimeException("Failed to check if book exists: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    public List<Book> searchBooks(String keyword) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            String hql = "FROM Book WHERE title LIKE :keyword OR author LIKE :keyword";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to search books: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public boolean update(Book book) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            String hql = "UPDATE Book SET author = :author, type = :type, publishDate = :publishDate, status = :status WHERE title = :title";
            Query query = session.createQuery(hql);
            query.setParameter("title", book.getTitle());
            query.setParameter("author", book.getAuthor());
            query.setParameter("type", book.getType());
            query.setParameter("publishDate", book.getPublishDate());
            query.setParameter("status", book.getStatus());

            int result = query.executeUpdate();
            transaction.commit();
            return result > 0;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

}
