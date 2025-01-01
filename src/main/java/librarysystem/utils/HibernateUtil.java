package librarysystem.utils;

import librarysystem.models.Book;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import librarysystem.models.User;

public class HibernateUtil {

    private static HibernateUtil instance = null;

    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry serviceRegistry;

    private HibernateUtil() {
        try {
            System.out.println("Initializing HibernateUtil...");

            Configuration configuration = new Configuration();
            System.out.println("Loading annotated class...");
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Book.class);

            System.out.println("Loading configuration file...");
            configuration.configure("hibernate.cfg.xml");

            System.out.println("Building service registry...");
            serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            System.out.println("Building session factory...");
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            System.out.println("HibernateUtil initialized successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing HibernateUtil: " + e.getMessage());
        }
    }

    public static HibernateUtil getInstance() {
        if (instance == null) {
            instance = new HibernateUtil();
        }
        return instance;
    }

    public synchronized static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
