package librarysystem.models.services;

import librarysystem.models.Reservation;
import librarysystem.models.interfaces.ReservationDAO;
import librarysystem.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOImp implements ReservationDAO {
    private final SessionFactory sessionFactory;

    public ReservationDAOImp() {
        this.sessionFactory = HibernateUtil.getInstance().getSessionFactory();
    }

    public void save(Reservation reservation) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(reservation); // Save the reservation
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error saving reservation", e);
        }
    }

    public List<Reservation> searchReservations(String searchQuery) {
        List<Reservation> reservations = null;
        try (Session session = sessionFactory.openSession()) {
            // Modify the query to search by some criteria, e.g., bookId or reservationId
            reservations = session.createQuery(
                            "FROM Reservation WHERE bookId = :searchQuery OR id = :searchQuery",
                            Reservation.class
                    ).setParameter("searchQuery", Integer.parseInt(searchQuery))  // Assuming search query is a number
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservations;
    }


    public void update(Reservation reservation) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(reservation); // Update the existing reservation
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error updating reservation", e);
        }
    }

    // Implementing getReservedBooks() method
    @Override
    public List<Reservation> getReservedBooks() {
        List<Reservation> reservations = null;
        try (Session session = sessionFactory.openSession()) {
            reservations = session.createQuery(
                    "FROM Reservation WHERE status = 'Reserved'",  // Query for reserved books
                    Reservation.class
            ).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public Reservation getReservationById(int reservationId) {
        Reservation reservation = null;
        try (Session session = sessionFactory.openSession()) {
            reservation = session.get(Reservation.class, reservationId);
            if (reservation == null) {
                throw new ReservationNotFoundException("Reservation with ID " + reservationId + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservation;
    }

    public Reservation getReservationByBookName(String bookName) {
        Reservation reservation = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Create a CriteriaBuilder
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            // Create a CriteriaQuery for Reservation entity
            CriteriaQuery<Reservation> criteriaQuery = criteriaBuilder.createQuery(Reservation.class);

            // Set the root of the query to be the Reservation entity
            Root<Reservation> root = criteriaQuery.from(Reservation.class);

            // Add a condition (where clause) for bookName
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("bookName"), bookName));

            // Execute the query
            Query<Reservation> query = session.createQuery(criteriaQuery);
            reservation = query.uniqueResult(); // Get a single result (if any)

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reservation;
    }

    // Custom Exception
    public static class ReservationNotFoundException extends RuntimeException {
        public ReservationNotFoundException(String message) {
            super(message);
        }
    }


    // Get reservation by Book ID
    public Reservation getReservationByBookId(int bookId) {
        Reservation reservation = null;
        try (Session session = sessionFactory.openSession()) {
            reservation = session.createQuery(
                    "FROM Reservation WHERE bookId = :bookId AND status = 'Reserved'",
                    Reservation.class
            ).setParameter("bookId", bookId).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservation;
    }
}
