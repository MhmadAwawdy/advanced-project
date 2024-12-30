package com.example.libraryfinalproject.DB;

import com.example.libraryfinalproject.Models.Book;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class BookDAOImpl implements DAO {

    @Override
    public List<Book> getAllBooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<Book> query = session.createQuery("FROM Book", Book.class);
            List<Book> books = query.list();

            session.getTransaction().commit();
            return books;
        }
    }

    @Override
    public List<Book> getBooksBySearch(String searchText) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            String hql = "FROM Book b WHERE LOWER(b.title) LIKE :searchText OR LOWER(b.author) LIKE :searchText OR LOWER(b.type) LIKE :searchText";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("searchText", "%" + searchText + "%");

            List<Book> books = query.list();

            session.getTransaction().commit();
            return books;
        }
    }

    @Override
    public List<Book> getBooksByFilter(String filter, String filterValue) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            String hql = "FROM book b WHERE 1=1";
            if (filter.equals("Title")) {
                hql += " AND LOWER(b.title) LIKE :filterValue";
            } else if (filter.equals("Author")) {
                hql += " AND LOWER(b.author) LIKE :filterValue";
            } else if (filter.equals("Type")) {
                hql += " AND LOWER(b.type) LIKE :filterValue";
            } else if (filter.equals("Year")) {
                hql += " AND YEAR(b.publishDate) = :filterValue";
            }

            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("filterValue", "%" + filterValue + "%");

            List<Book> books = query.list();

            session.getTransaction().commit();
            return books;
        }
    }

    @Override
    public List<String> getAuthors() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<String> query = session.createQuery("SELECT DISTINCT b.author FROM Book b", String.class);
            List<String> authors = query.list();

            session.getTransaction().commit();
            return authors;
        }
    }

    @Override
    public List<String> getTitles() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<String> query = session.createQuery("SELECT DISTINCT b.title FROM Book b", String.class);
            List<String> titles = query.list();

            session.getTransaction().commit();
            return titles;
        }
    }

    @Override
    public List<String> getTypes() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<String> query = session.createQuery("SELECT DISTINCT b.type FROM Book b", String.class);
            List<String> types = query.list();

            session.getTransaction().commit();
            return types;
        }
    }

    @Override
    public Book getBookById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Book book = session.get(Book.class, id);

            session.getTransaction().commit();
            return book;
        }
    }

    @Override
    public List<Book> getBooksByYear(String year) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            String hql = "FROM Book b WHERE YEAR(b.publishDate) = :year";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("year", year);

            List<Book> books = query.list();

            session.getTransaction().commit();
            return books;
        }
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            String hql = "FROM Book b WHERE LOWER(b.author) LIKE :author";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("author", "%" + author + "%");

            List<Book> books = query.list();

            session.getTransaction().commit();
            return books;
        }
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            String hql = "FROM Book b WHERE LOWER(b.title) LIKE :title";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("title", "%" + title + "%");

            List<Book> books = query.list();

            session.getTransaction().commit();
            return books;
        }
    }

    @Override
    public List<Book> getBooksByType(String type) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            String hql = "FROM Book b WHERE LOWER(b.type) LIKE :type";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("type", "%" + type + "%");

            List<Book> books = query.list();

            session.getTransaction().commit();
            return books;
        }
    }
}
