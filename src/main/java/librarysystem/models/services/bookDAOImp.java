package librarysystem.models.services;

import librarysystem.models.Book2;
import librarysystem.models.interfaces.DAO;
import librarysystem.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class bookDAOImp implements DAO {

    private SessionFactory sessionFactory;

    public bookDAOImp() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List<Book2> getAllBooks() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Book2", Book2.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Book2 getBookById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Book2.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Book2> getBooksBySearch(String searchText) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Book2 WHERE LOWER(title) LIKE :searchText OR LOWER(author) LIKE :searchText OR LOWER(type) LIKE :searchText";
            Query<Book2> query = session.createQuery(hql, Book2.class);
            query.setParameter("searchText", "%" + searchText.toLowerCase() + "%");
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Book2> getBooksByFilter(String filter, String filterValue) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Book2 WHERE " + filter + " = :filterValue";
            Query<Book2> query = session.createQuery(hql, Book2.class);
            query.setParameter("filterValue", filterValue);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Book2> getBooksByYear(String year) {
        return getBooksByFilter("publishDate", year);
    }

    @Override
    public List<Book2> getBooksByAuthor(String author) {
        return getBooksByFilter("author", author);
    }

    @Override
    public List<Book2> getBooksByTitle(String title) {
        return getBooksByFilter("title", title);
    }

    @Override
    public List<Book2> getBooksByGenre(String genre) {
        return getBooksByFilter("type", genre);
    }

    @Override
    public List<String> getAuthors() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT DISTINCT author FROM Book2";
            Query<String> query = session.createQuery(hql, String.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> getTitles() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT DISTINCT title FROM Book2";
            Query<String> query = session.createQuery(hql, String.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> getGenres() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT DISTINCT type FROM Book2";
            Query<String> query = session.createQuery(hql, String.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> getYears() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT DISTINCT publishDate FROM Book2";
            Query<String> query = session.createQuery(hql, String.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
