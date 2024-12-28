package Models;
import DB.*;
import DB.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class BookService {


    public List<Book> getAllBooks() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();


        Query<Book> query = session.createQuery("FROM Book", Book.class);
        List<Book> books = query.list();

        session.getTransaction().commit();
        session.close();

        return books;
    }


    public List<Book> getBooksByFilter(String searchText, String filter, String filterValue) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        String hql = "FROM Book b WHERE 1=1";

        if (filter.equals("Title")) {
            hql += " AND LOWER(b.title) LIKE :searchText";
        } else if (filter.equals("Author")) {
            hql += " AND LOWER(b.author) LIKE :searchText";
        } else if (filter.equals("Type")) {
            hql += " AND LOWER(b.type) LIKE :searchText";
        } else if (filter.equals("Year")) {
            hql += " AND b.publishData = :searchText";
        }

        Query<Book> query = session.createQuery(hql, Book.class);
        query.setParameter("searchText", "%" + searchText + "%");

        List<Book> books = query.list();

        session.getTransaction().commit();
        session.close();

        return books;
    }


    public List<String> getAuthors() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query<String> query = session.createQuery("SELECT DISTINCT b.author FROM Book b", String.class);
        List<String> authors = query.list();

        session.getTransaction().commit();
        session.close();

        return authors;
    }

    public List<String> getTitles() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query<String> query = session.createQuery("SELECT DISTINCT b.title FROM Book b", String.class);
        List<String> titles = query.list();

        session.getTransaction().commit();
        session.close();

        return titles;
    }


    public List<String> getTypes() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query<String> query = session.createQuery("SELECT DISTINCT b.type FROM Book b", String.class);
        List<String> types = query.list();

        session.getTransaction().commit();
        session.close();

        return types;
    }
}
