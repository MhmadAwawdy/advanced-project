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

    @Override
    public Book getBookByName(String name) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            String hql = "FROM Book WHERE title = :name";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("name", name);
            return query.uniqueResult(); // Return the book if found, otherwise null
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch the book by name: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private final SessionFactory sessionFactory;

    public BookDAOImp() {
        HibernateUtil hibernateUtil = HibernateUtil.getInstance();
        this.sessionFactory = hibernateUtil.getSessionFactory();
    }

    @Override
    public void save(Book book) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            System.out.println("Status: " + book.getStatus());


            session.update(book);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to save the book", e);
        }
    }




        public boolean isBookExists(String title, String author) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Book WHERE title = :title AND author = :author";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("title", title);
            query.setParameter("author", author);

            return query.uniqueResult() != null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to check if book exists", e);
        }
    }

    @Override
    public List<Book> searchBooks(String keyword) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Book WHERE title LIKE :keyword OR author LIKE :keyword";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("keyword", "%" + keyword + "%");

            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to search books", e);
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
