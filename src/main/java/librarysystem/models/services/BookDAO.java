package librarysystem.models.services;

import librarysystem.models.Book;
import librarysystem.models.interfaces.DAO;
import librarysystem.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.util.List;

public class BookDAO implements DAO {

    private HibernateUtil hibernateUtil;
    private SessionFactory sessionFactory;

    public BookDAO() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }

    @Override
    public void save(Book book) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            if (book.getImageData() != null) {
                byte[] imageBytes = book.getImageData();
                book.setImage(imageBytes);
            }

            session.save(book);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Error saving the book to the database.", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Book> filterBooks(String searchText, String selectedTitle, String selectedAuthor, String selectedDate) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String queryStr = "FROM Book b WHERE 1=1";

            if (searchText != null && !searchText.trim().isEmpty()) {
                queryStr += " AND (b.title LIKE :searchText OR b.author LIKE :searchText)";
            }
            if (selectedTitle != null && !selectedTitle.equals("All Titles")) {
                queryStr += " AND b.title = :title";
            }
            if (selectedAuthor != null && !selectedAuthor.equals("All Authors")) {
                queryStr += " AND b.author = :author";
            }
            if (selectedDate != null && !selectedDate.trim().isEmpty()) {
                queryStr += " AND b.publishDate = :publishDate";
            }

            Query<Book> query = session.createQuery(queryStr, Book.class);

            if (searchText != null && !searchText.trim().isEmpty()) {
                query.setParameter("searchText", "%" + searchText + "%");
            }
            if (selectedTitle != null && !selectedTitle.equals("All Titles")) {
                query.setParameter("title", selectedTitle);
            }
            if (selectedAuthor != null && !selectedAuthor.equals("All Authors")) {
                query.setParameter("author", selectedAuthor);
            }
            if (selectedDate != null && !selectedDate.trim().isEmpty()) {
                query.setParameter("publishDate", Integer.parseInt(selectedDate));
            }

            List<Book> books = query.getResultList();
            transaction.commit();

            if (books.isEmpty()) {
                System.out.println("No books found matching your criteria.");
            }
            return books;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Error filtering books from the database.", e);
        } finally {
            session.close();
        }
    }

    @Override
    public Book getBookDetailsByTitle(String title) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Book> query = session.createQuery("FROM Book b WHERE b.title = :title", Book.class);
            query.setParameter("title", title);
            Book book = query.uniqueResult();
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Error fetching book details by title.", e);
        } finally {
            session.close();
        }
    }

    public Image getImageByBookTitle(String title) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Book WHERE title = :title";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("title", title);
            Book book = query.uniqueResult();

            if (book != null && book.getImage() != null) {
                byte[] imageBytes = book.getImage();
                return new Image(new ByteArrayInputStream(imageBytes));
            }
        }
        return null;
    }

    public List<Book> getBooksByTitle(String title) throws Exception {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Book WHERE title LIKE :title";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("title", "%" + title + "%");
            List<Book> books = query.getResultList();
            return books;
        } catch (Exception e) {
            throw new Exception("Error fetching books by title.", e);
        } finally {
            session.close();
        }
    }

    @Override
    public boolean isBookExists(String title, String author) throws Exception {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Book WHERE title = :title AND author = :author";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("title", title);
            query.setParameter("author", author);
            List<Book> books = query.getResultList();
            return !books.isEmpty();
        } catch (Exception e) {
            throw new Exception("Error checking if the book exists.", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Book> getAllBooks() throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Book> query = session.createQuery("FROM Book", Book.class);
            List<Book> books = query.getResultList();
            transaction.commit();
            return books;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Error fetching all books from the database.", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<String> getAvailableTitles() throws Exception {
        Session session = sessionFactory.openSession();
        try {
            String hql = "SELECT DISTINCT b.title FROM Book b";
            Query<String> query = session.createQuery(hql, String.class);
            List<String> titles = query.getResultList();
            return titles;
        } catch (Exception e) {
            throw new Exception("Error fetching available titles.", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<String> getAvailableAuthors() throws Exception {
        Session session = sessionFactory.openSession();
        try {
            String hql = "SELECT DISTINCT b.author FROM Book b";
            Query<String> query = session.createQuery(hql, String.class);
            List<String> authors = query.getResultList();
            return authors;
        } catch (Exception e) {
            throw new Exception("Error fetching available authors.", e);
        } finally {
            session.close();
        }
    }
}
