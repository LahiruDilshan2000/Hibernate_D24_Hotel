package lk.ijse.gdse.d24_hotel.dao.custom.impl;

import lk.ijse.gdse.d24_hotel.entity.Room;
import lk.ijse.gdse.d24_hotel.dao.custom.RoomDAO;
import lk.ijse.gdse.d24_hotel.dao.exception.ConstraintViolationException;
import org.hibernate.Session;

import java.util.List;

public class RoomDAOImpl implements RoomDAO {
    @Override
    public Room save(Session session, Room room) {

        try {
            session.save(room);
            return room;
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Room update(Session session, Room room) {

        try {
            session.update(room);
            return room;
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void delete(Session session, Room room) {

        try {
            session.delete(room);
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Room get(Session session, String pk) {

        try {
            return session.get(Room.class,pk);
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public boolean existByPk(Session session, String pk) {

        try {
            return session.createQuery("FROM Room r WHERE r.roomId= :id").setParameter("id", pk).list().isEmpty();
        } catch (Exception e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Room> findAll(Session session) {

        try{
            return session.createQuery("FROM Room").list();
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Room> searchByText(Session session, String text) {

        try {
            text="%"+text+"%";
            return session.createQuery("FROM Room r WHERE r.roomId LIKE :text OR r.type LIKE :text OR r.key_money LIKE :text OR r.qty LIKE :text").setParameter("text", text).list();
        } catch (Exception e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public boolean updateAvailableRooms(Session session, String roomId, boolean flag) {

        try {

            int count = flag == true ? (session.get(Room.class, roomId).getAvailableRooms()+1) : (session.get(Room.class, roomId).getAvailableRooms()-1);

            return session.createQuery("UPDATE Room SET availableRooms = :count WHERE room_type_id = :id").setParameter("count", count).setParameter("id", roomId).executeUpdate() > 0;

        } catch (Exception e) {
            throw new ConstraintViolationException(e);
        }
    }
}
