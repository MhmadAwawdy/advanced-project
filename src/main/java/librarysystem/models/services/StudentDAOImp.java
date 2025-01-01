package librarysystem.models.services;


import librarysystem.models.Student;
import librarysystem.models.interfaces.StudentDAO;
import librarysystem.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class StudentDAOImp implements StudentDAO {

    private HibernateUtil hibernateUtil;
    private SessionFactory sessionFactory;

    public  StudentDAOImp()
    {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }

    @Override
    public void save(Student student)
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try
        {
            transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null)
            {
                transaction.rollback();
                e.printStackTrace();
            }
            throw new RuntimeException("Error saving librarian", e);
        }
        finally
        {
            session.close();
        }
    }
    @Override
    public Student findByPhone(String phone) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Student WHERE studentPhone = :phone";
            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("phone", phone);
            return query.uniqueResult();
        }
        catch (Exception e) {
            throw new RuntimeException("Error fetching user by username", e);
        } finally {
            session.close();
        }
    }
    @Override
    public List<Student> searchStudentsByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Student> students = null;

        try {
            String hql = "FROM Student WHERE name LIKE :name";
            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("name", "%" + name + "%");
            students = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return students;
    }
    public List<Student> getFirstTenStudents() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Student";
            return session.createQuery(hql, Student.class)
                    .setMaxResults(10) // Limit results to the first 10
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Return an empty list in case of an error
        }
    }
}

