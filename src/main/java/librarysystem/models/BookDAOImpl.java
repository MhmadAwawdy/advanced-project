package librarysystem.models;

import librarysystem.models.Book2;
import librarysystem.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import librarysystem.models.interfaces.DAO;
import java.util.List;

public class BookDAOImpl  {

    private SessionFactory sessionFactory;

    public BookDAOImpl() {

        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
}

