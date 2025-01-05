package librarysystem.models;

import org.hibernate.Session;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(name = "student_id", nullable = false)
    private int studentId;

    @Column(name = "book_id", nullable = false)
    private int bookId;

    @Column(name = "reservation_date", nullable = false)
    private LocalDateTime reservationDate;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(50) default 'Pending'")
    private String status;

    @Column(name = "extended", nullable = true)
    private boolean extended;

    @Column(name = "Book_Name", nullable = false)
    private String bookName;

    public Book getBook(Session session) {
        // Use Hibernate to get the Book based on the bookId
        return session.get(Book.class, this.bookId);
    }

    // Getters and Setters
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getId() {
        return id; // Changed from getReservationId to getId
    }

    public void setId(int id) {
        this.id = id; // Changed from setReservationId to setId
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public int getReservationId() {
        return id;
    }

}
