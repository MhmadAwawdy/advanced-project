package librarysystem.models.services;

import librarysystem.models.Student;
import librarysystem.models.interfaces.StudentDAO;
import librarysystem.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

public class StudentDAOImp implements StudentDAO {

    private final SessionFactory sessionFactory;

    public StudentDAOImp() {
        this.sessionFactory = HibernateUtil.getInstance().getSessionFactory();
    }

    @Override
    public void save(Student student) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(student);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Error saving student", e);
        }
    }

    @Override
    public Student findByPhone(String phone) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Student WHERE studentPhone = :phone";
            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("phone", phone);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching student by phone", e);
        }
    }

    @Override
    public List<Student> getAllStudents() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Student"; // Select all students
            Query<Student> query = session.createQuery(hql, Student.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching all students", e);
        }
    }

    public List<Student> searchStudentsByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Student WHERE studentName LIKE :name";
            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("name", "%" + name + "%");
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error searching students by name", e);
        }
    }


}
