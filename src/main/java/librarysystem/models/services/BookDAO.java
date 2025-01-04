package librarysystem.models.services;

import javafx.scene.image.Image;
import librarysystem.models.Book;
import librarysystem.models.interfaces.DAO;
import librarysystem.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

public class BookDAO implements DAO {

    private final HibernateUtil hibernateUtil;
    private final SessionFactory sessionFactory;

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
            throw new Exception("Error saving the book to the database: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    public void saveBookImage(String title, InputStream imageStream) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();


            String hql = "FROM Book b WHERE LOWER(b.title) = LOWER(:title)";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("title", title.toLowerCase());
            Book book = query.uniqueResult();

            if (book != null) {

                byte[] imageBytes = new byte[imageStream.available()];
                imageStream.read(imageBytes);


                book.setImage(imageBytes);


                session.saveOrUpdate(book);
                transaction.commit();
            } else {
                throw new Exception("Book not found with the given title.");
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Error saving book image: " + e.getMessage(), e);
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

            StringBuilder queryStr = new StringBuilder("FROM Book b WHERE 1=1");


            if (searchText != null && !searchText.trim().isEmpty()) {
                queryStr.append(" AND (LOWER(b.title) LIKE LOWER(:searchText) OR LOWER(b.author) LIKE LOWER(:searchText))");
            }
            if (selectedTitle != null && !selectedTitle.equals("All Titles")) {
                queryStr.append(" AND LOWER(b.title) = LOWER(:title)");
            }
            if (selectedAuthor != null && !selectedAuthor.equals("All Authors")) {
                queryStr.append(" AND LOWER(b.author) = LOWER(:author)");
            }
            if (selectedDate != null && !selectedDate.trim().isEmpty()) {
                queryStr.append(" AND b.publishDate = :publishDate");
            }

            Query<Book> query = session.createQuery(queryStr.toString(), Book.class);


            if (searchText != null && !searchText.trim().isEmpty()) {
                query.setParameter("searchText", "%" + searchText.toLowerCase() + "%");
            }
            if (selectedTitle != null && !selectedTitle.equals("All Titles")) {
                query.setParameter("title", selectedTitle.toLowerCase());
            }
            if (selectedAuthor != null && !selectedAuthor.equals("All Authors")) {
                query.setParameter("author", selectedAuthor.toLowerCase());
            }
            if (selectedDate != null && !selectedDate.trim().isEmpty()) {
                query.setParameter("publishDate", Integer.parseInt(selectedDate));
            }

            List<Book> books = query.getResultList();
            transaction.commit();
            return books;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Error filtering books: " + e.getMessage(), e);
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
            Query<Book> query = session.createQuery(
                    "FROM Book b WHERE LOWER(b.title) = LOWER(:title)",
                    Book.class
            );
            query.setParameter("title", title);
            Book book = query.uniqueResult();
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Error fetching book details: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    public Image getImageByBookTitle(String title) throws Exception {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Book WHERE LOWER(title) = LOWER(:title)";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("title", title);
            Book book = query.uniqueResult();

            if (book != null && book.getImage() != null) {
                byte[] imageBytes = book.getImage();
                try {
                    return new Image(new ByteArrayInputStream(imageBytes));
                } catch (Exception e) {
                    System.err.println("Error creating image from bytes: " + e.getMessage());
                    return getDefaultImage();
                }
            }
            return getDefaultImage();
        } catch (Exception e) {
            System.err.println("Error fetching book image: " + e.getMessage());
            return getDefaultImage();
        } finally {
            session.close();
        }
    }

    private Image getDefaultImage() {
        try {
            return new Image(getClass().getResourceAsStream("/images/default-book.png"));
        } catch (Exception e) {
            System.err.println("Error loading default image: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Book> getAllBooks() throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Book> query = session.createQuery("FROM Book ORDER BY title", Book.class);
            List<Book> books = query.getResultList();
            transaction.commit();
            return books;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Error fetching all books: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    @Override
    public boolean isBookExists(String title, String author) throws Exception {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Book WHERE LOWER(title) = LOWER(:title) AND LOWER(author) = LOWER(:author)";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("title", title.toLowerCase());
            query.setParameter("author", author.toLowerCase());
            List<Book> books = query.getResultList();
            return !books.isEmpty();
        } catch (Exception e) {
            throw new Exception("Error checking book existence: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<String> getAvailableTitles() throws Exception {
        Session session = sessionFactory.openSession();
        try {
            String hql = "SELECT DISTINCT b.title FROM Book b ORDER BY b.title";
            Query<String> query = session.createQuery(hql, String.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error fetching available titles: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<String> getAvailableAuthors() throws Exception {
        Session session = sessionFactory.openSession();
        try {
            String hql = "SELECT DISTINCT b.author FROM Book b ORDER BY b.author";
            Query<String> query = session.createQuery(hql, String.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error fetching available authors: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }


    public List<Book> getBooksByTitle(String title) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hql = "FROM Book b WHERE LOWER(b.title) = LOWER(:title)";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("title", title.toLowerCase());
            List<Book> books = query.getResultList();
            transaction.commit();
            return books;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Error fetching books by title: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    @Override
    public boolean hasImage(String title) {
        try {
            Book book = getBookDetailsByTitle(title);
            return book != null && book.getImage() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
