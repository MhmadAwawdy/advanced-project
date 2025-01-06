package librarysystem.models.interfaces;

import librarysystem.models.ReservationReq;

import java.util.List;

public interface ReservationReqDAO
{
    void saveReservation(ReservationReq reservationReq);
    List<ReservationReq> getAllReservations();
    ReservationReq getReservationById(int id);
    void updateReservation(ReservationReq reservationReq);
    void deleteReservation(int id);
}
