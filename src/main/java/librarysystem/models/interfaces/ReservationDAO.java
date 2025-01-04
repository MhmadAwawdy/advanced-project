package librarysystem.models.interfaces;

import librarysystem.models.Reservation;

import java.util.List;

public interface ReservationDAO {
    void save(Reservation reservation);
    void update(Reservation reservation);
    List<Reservation> getReservedBooks();  // Only one method to get reserved books
    List<Reservation> searchReservations(String query);
    Reservation getReservationById(int reservationId);
    Reservation getReservationByBookId(int bookId);
    Reservation getReservationByBookName(String bookName);

}
