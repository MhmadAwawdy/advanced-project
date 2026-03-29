package com.example.advanceproject.models.services;

import com.example.advanceproject.models.Book;
import com.example.advanceproject.models.interfaces.BookDAO;
import com.example.advanceproject.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class BookDAOImp implements BookDAO {

    private HibernateUtil hibernateUtil;
    private SessionFactory sessionFactory;

    public BookDAOImp() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }

    @Override
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
}
