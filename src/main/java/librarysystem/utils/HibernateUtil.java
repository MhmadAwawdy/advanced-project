package librarysystem.utils;

import librarysystem.models.Book;
import librarysystem.models.Librarian;
import librarysystem.models.Student;
import librarysystem.models.Reservation;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {

    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    private static HibernateUtil instance = null;

    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry serviceRegistry;

    private HibernateUtil() {
        try {
            logger.info("Initializing HibernateUtil...");

            Configuration configuration = new Configuration();
            logger.info("Loading annotated classes...");
            configuration.addAnnotatedClass(Librarian.class);
            configuration.addAnnotatedClass(Book.class);
            configuration.addAnnotatedClass(Student.class);
            configuration.addAnnotatedClass(Reservation.class); // Add the missing entity here

            logger.info("Loading configuration file...");
            configuration.configure("hibernate.cfg.xml");

            logger.info("Building service registry...");
            serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            logger.info("Building session factory...");
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            logger.info("HibernateUtil initialized successfully!");
        } catch (Exception e) {
            logger.error("Error initializing HibernateUtil", e);
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

    public static void shutdown() {
        if (serviceRegistry != null) {
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
            logger.info("Service registry destroyed.");
        }
    }
}
