package lk.ijse.gdse.d24_hotel.service.custom;

import lk.ijse.gdse.d24_hotel.dto.RoomDTO;
import lk.ijse.gdse.d24_hotel.service.SuperService;
import lk.ijse.gdse.d24_hotel.service.exception.DuplicateException;

import java.util.List;

public interface RoomService extends SuperService {

    RoomDTO saveRoom(RoomDTO roomDTO) throws DuplicateException;

    RoomDTO updateRoom(RoomDTO roomDTO);

    void deleteRoom(RoomDTO roomDTO);

    RoomDTO getRoom(String ID);

    List<RoomDTO> findAllRoom();

    List<RoomDTO> searchRoomByText(String text);
}
