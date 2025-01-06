package librarysystem.models.services;

import librarysystem.models.Student;
import librarysystem.models.interfaces.StudentDAO;
import librarysystem.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class StudentDAOImp implements StudentDAO
{
    private final SessionFactory sessionFactory;
    public StudentDAOImp()
    {
        this.sessionFactory = HibernateUtil.getInstance().getSessionFactory();
    }
    @Override
    public void save(Student student)
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try
        {
            session.save(student);
            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving student", e);
        }
        finally
        {
            session.close();
        }
    }
    public Student findById(int id)
    {
        try (Session session = sessionFactory.openSession())
        {
            String hql = "FROM Student WHERE id = :id";
            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error fetching student by id", e);
        }
    }
    @Override
    public Student findByPhone(String phone)
    {
        try (Session session = sessionFactory.openSession())
        {
            String hql = "FROM Student WHERE studentPhone = :phone";
            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("phone", phone);
            return query.uniqueResult();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error fetching student by phone", e);
        }
    }
    @Override
    public List<Student> getAllStudents()
    {
        try (Session session = sessionFactory.openSession())
        {
            String hql = "FROM Student";
            Query<Student> query = session.createQuery(hql, Student.class);
            return query.list();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error fetching all students", e);
        }
    }
    public List<Student> searchStudentsByName(String name)
    {
        try (Session session = sessionFactory.openSession())
        {
            String hql = "FROM Student WHERE studentName LIKE :name";
            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("name", "%" + name + "%");
            return query.list();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error searching students by name", e);
        }
    }
    public Student getStudentById(int studentId)
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            return session.get(Student.class, studentId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
