package lk.ijse.gdse.d24_hotel.dao.custom;

import lk.ijse.gdse.d24_hotel.entity.Room;
import lk.ijse.gdse.d24_hotel.dao.CrudDAO;
import org.hibernate.Session;

public interface RoomDAO extends CrudDAO<Room, String > {

    boolean updateAvailableRooms(Session session, String roomId, boolean flag);
}
