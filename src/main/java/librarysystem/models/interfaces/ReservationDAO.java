package librarysystem.models.interfaces;

import librarysystem.models.Reservation;

import java.util.List;

public interface ReservationDAO
{
    void save(Reservation reservation);
    void update(Reservation reservation);
    List<Reservation> getReservedBooks();
    List<Reservation> searchReservations(String query);
    Reservation getReservationByBookName(String bookName);
}
