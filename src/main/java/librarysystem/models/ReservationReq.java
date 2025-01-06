package librarysystem.models;

import javax.persistence.*;

@Entity
@Table(name = "reservationReq")
public class ReservationReq
{
    @Id
    private int reservation_id;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "reservation_date")
    private String reservationDate;

    public Student getStudent() {return student;}

    public void setStudent(Student student) {this.student = student;}

    public Book getBook() {return book;}

    public void setBook(Book book) {this.book = book;}

    public String getReservationDate() {return reservationDate;}
    public void setReservationDate(String reservationDate) {this.reservationDate = reservationDate;}
}
