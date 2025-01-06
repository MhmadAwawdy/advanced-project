package librarysystem.models;

import librarysystem.utils.ValidationUtils;

import javax.persistence.*;

@Entity
@Table(name = "Student")
public class Student
{

    @Id
    @Column(name = "studentID")
    private int studentID;

    @Column(name = "studentName", nullable = false)
    private String studentName;

    @Column(name = "studentPhone", nullable = false, unique = true)
    private String studentPhone;

    @Override
    public String toString() {
        return "Student ID: " + studentID + ", Name: " + studentName;
    }

    public Student(String studentID, String phoneNumber) throws IllegalArgumentException {
        if (!ValidationUtils.isValidID(studentID)) {
            throw new IllegalArgumentException("Invalid student ID. It must be 8 digits.");
        }
        if (!ValidationUtils.isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number. It must start with 05 and contain 10 digits.");
        }

        this.studentID = Integer.parseInt(studentID);
        this.studentPhone = phoneNumber;
    }

    public Student() {

    }

    public int getStudentID() {return studentID;}
    public void setStudentID(int studentID) {this.studentID = studentID;}
    public String getStudentName() {return studentName;}
    public void setStudentName(String studentName) {this.studentName = studentName;}
    public String getStudentPhone() {return studentPhone;}
    public void setStudentPhone(String studentPhone) {this.studentPhone = studentPhone;}

}
