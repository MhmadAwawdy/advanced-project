package librarysystem.models;

import javax.persistence.*;

@Entity
@Table(name = "Student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studentID")
    private int studentID;

    @Column(name = "studentName", nullable = false)
    private String studentName;

    @Column(name = "studentPhone", nullable = false, unique = true)
    private String studentPhone;

    public int getStudentID() {return studentID;}
    public void setStudentID(int studentID) {this.studentID = studentID;}
    public String getStudentName() {return studentName;}
    public void setStudentName(String studentName) {this.studentName = studentName;}
    public String getStudentPhone() {return studentPhone;}
    public void setStudentPhone(String studentPhone) {this.studentPhone = studentPhone;}

}
