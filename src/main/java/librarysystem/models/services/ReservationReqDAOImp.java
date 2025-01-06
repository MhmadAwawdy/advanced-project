package librarysystem.models.services;

import librarysystem.models.ReservationReq;
import librarysystem.models.interfaces.ReservationReqDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import librarysystem.utils.HibernateUtil;

import java.util.List;

public class ReservationReqDAOImp implements ReservationReqDAO
{
    public static List<ReservationReq> getAllReservationsWithDetails() {
        List<ReservationReq> reservations;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            reservations = session.createQuery(
                    "SELECT r FROM ReservationReq r JOIN FETCH r.student s JOIN FETCH r.book b",
                    ReservationReq.class
            ).getResultList();
        }
        return reservations;
    }


    @Override
    public void saveReservation(ReservationReq reservationReq)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try
        {
            transaction = session.beginTransaction();
            session.save(reservationReq);
            transaction.commit();
            System.out.println("Reservation saved successfully!");
        }
        catch (Exception e)
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        finally
        {
            session.close();
        }
    }
    @Override
    public List<ReservationReq> getAllReservations()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<ReservationReq> reservations = null;
        try
        {
            reservations = session.createQuery("FROM ReservationReq", ReservationReq.class).list();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            session.close();
        }
        return reservations;
    }
    @Override
    public ReservationReq getReservationById(int id)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ReservationReq reservationReq = null;

        try
        {
            reservationReq = session.get(ReservationReq.class, id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            session.close();
        }

        return reservationReq;
    }
    @Override
    public void updateReservation(ReservationReq reservationReq)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try
        {
            transaction = session.beginTransaction();
            session.update(reservationReq);
            transaction.commit();
            System.out.println("Reservation updated successfully!");
        }
        catch (Exception e)
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        finally
        {
            session.close();
        }
    }
    @Override
    public void deleteReservation(int id)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try
        {
            transaction = session.beginTransaction();
            ReservationReq reservationReq = session.get(ReservationReq.class, id);
            if (reservationReq != null)
            {
                session.delete(reservationReq);
                transaction.commit();
                System.out.println("Reservation deleted successfully!");
            }
            else
            {
                System.out.println("Reservation not found!");
            }
        }
        catch (Exception e)
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        finally
        {
            session.close();
        }
    }
}