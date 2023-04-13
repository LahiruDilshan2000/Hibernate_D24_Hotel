package lk.ijse.gdse.d24_hotel.service.custom;

import lk.ijse.gdse.d24_hotel.dto.ReservationDTO;
import lk.ijse.gdse.d24_hotel.dto.StudentDTO;
import lk.ijse.gdse.d24_hotel.service.SuperService;
import lk.ijse.gdse.d24_hotel.service.exception.AllReadyBookedException;

import java.util.List;

public interface ReservationService extends SuperService {

    ReservationDTO saveReservation(ReservationDTO reservationDTO) throws AllReadyBookedException;

    String getNextId();

    boolean updateReservationStatus(String studentId);

    List<ReservationDTO> findAll();

    List<ReservationDTO> getReservationList();

    List<ReservationDTO> getReservationListByStudentId(String studentId);

    ReservationDTO updateReservation(ReservationDTO reservationDTO);

    List<StudentDTO> getReservationByText(String text);
}
