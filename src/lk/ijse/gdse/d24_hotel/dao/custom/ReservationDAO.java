package lk.ijse.gdse.d24_hotel.dao.custom;

import lk.ijse.gdse.d24_hotel.entity.Reservation;
import lk.ijse.gdse.d24_hotel.dao.CrudDAO;
import org.hibernate.Session;

import java.util.List;

public interface ReservationDAO extends CrudDAO<Reservation, String> {

    String getNextReservationId(Session session);

    boolean isExistBook(Session session, String pk);

    boolean updateReservationStatus(Session session, String studentId);

    Reservation getBeforeUpdateReservation(Session session, String studentId);

    List<Reservation> getAllGroupByStudentId(Session session);

    List<Reservation> getReservationByStudentId(Session session, String studentId);

    List<Reservation> getReservationByText(Session session, String text);
}
