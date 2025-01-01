package librarysystem.models.interfaces;

import librarysystem.models.Student;

import java.util.List;

public interface StudentDAO
{
    void save(Student student);
    Student findByPhone(String phone);
    List<Student> searchStudentsByName(String name);

}
