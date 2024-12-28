package DB;

import Models.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;
import java.util.logging.Logger;

public class BookDAOImpl implements DAO {

    private static final Logger logger = Logger.getLogger(BookDAOImpl.class.getName());

    @Override
    public List<Book> getBooksBySearch(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            logger.warning("Search text is null or empty");
            return null;
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Book> books = null;

        try {
            String hql = "FROM Book b WHERE LOWER(b.title) LIKE :searchText OR LOWER(b.author) LIKE :searchText OR LOWER(b.type) LIKE :searchText";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("searchText", "%" + searchText.toLowerCase() + "%");
            books = query.list();
        } catch (Exception e) {
            logger.severe("Error while fetching books by search: " + e.getMessage());
        } finally {
            session.close();
        }

        return books;
    }

    @Override
    public List<Book> getBooksByFilter(String filter, String filterValue) {
        if (filter == null || filterValue == null || filterValue.trim().isEmpty()) {
            logger.warning("Filter or filter value is null or empty");
            return null;
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Book> books = null;

        try {
            String hql = "FROM Book b WHERE 1=1";
            if (filter.equalsIgnoreCase("Title")) {
                hql += " AND LOWER(b.title) LIKE :filterValue";
            } else if (filter.equalsIgnoreCase("Author")) {
                hql += " AND LOWER(b.author) LIKE :filterValue";
            } else if (filter.equalsIgnoreCase("Type")) {
                hql += " AND LOWER(b.type) LIKE :filterValue";
            } else if (filter.equalsIgnoreCase("Year")) {
                hql += " AND b.publishDate = :filterValue";
            } else {
                logger.warning("Invalid filter: " + filter);
                return null;
            }

            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("filterValue", "%" + filterValue.toLowerCase() + "%");
            books = query.list();
        } catch (Exception e) {
            logger.severe("Error while fetching books by filter: " + e.getMessage());
        } finally {
            session.close();
        }

        return books;
    }

    @Override
    public List<Book> getAllBooks() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Book> books = null;

        try {
            String hql = "FROM Book";
            Query<Book> query = session.createQuery(hql, Book.class);
            books = query.list();
        } catch (Exception e) {
            logger.severe("Error while fetching all books: " + e.getMessage());
        } finally {
            session.close();
        }

        return books;
    }
}
