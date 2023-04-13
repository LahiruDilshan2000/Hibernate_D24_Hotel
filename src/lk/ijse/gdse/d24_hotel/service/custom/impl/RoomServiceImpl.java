package lk.ijse.gdse.d24_hotel.service.custom.impl;

import lk.ijse.gdse.d24_hotel.dto.RoomDTO;
import lk.ijse.gdse.d24_hotel.dao.DaoFactory;
import lk.ijse.gdse.d24_hotel.dao.DaoType;
import lk.ijse.gdse.d24_hotel.dao.custom.RoomDAO;
import lk.ijse.gdse.d24_hotel.service.custom.RoomService;
import lk.ijse.gdse.d24_hotel.service.exception.DuplicateException;
import lk.ijse.gdse.d24_hotel.service.util.Convertor;
import lk.ijse.gdse.d24_hotel.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class RoomServiceImpl implements RoomService {

    private final RoomDAO roomDAO;
    private final Convertor convertor;

    public RoomServiceImpl() {
        roomDAO = DaoFactory.getInstance().getDAO(DaoType.ROOM);
        convertor = new Convertor();
    }

    @Override
    public RoomDTO saveRoom(RoomDTO roomDTO) throws DuplicateException {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Transaction transaction = session.beginTransaction();

            if (!roomDAO.existByPk(session, roomDTO.getRoomId()))
                throw new DuplicateException("Room ID all ready exits !");

            if (roomDAO.save(session, convertor.toRoom(roomDTO)) == null)
                throw new RuntimeException("Failed to save room !");

            transaction.commit();
            return roomDTO;

        } finally {
            session.close();
        }
    }

    @Override
    public RoomDTO updateRoom(RoomDTO roomDTO) {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Transaction transaction = session.beginTransaction();

            if (roomDAO.update(session, convertor.toRoom(roomDTO)) == null)
                throw new RuntimeException("Failed to update room !");

            transaction.commit();
            return roomDTO;

        } finally {
            session.close();
        }
    }

    @Override
    public void deleteRoom(RoomDTO roomDTO) {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Transaction transaction = session.beginTransaction();

            roomDAO.delete(session, convertor.toRoom(roomDTO));

            transaction.commit();

        } finally {
            session.close();
        }
    }

    @Override
    public RoomDTO getRoom(String ID) {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {

            return convertor.fromRoom(roomDAO.get(session, ID));

        } finally {
            session.close();
        }
    }

    @Override
    public List<RoomDTO> findAllRoom() {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {

            return roomDAO.findAll(session).stream().map(room -> convertor.fromRoom(room)).collect(Collectors.toList());

        } finally {
            session.close();
        }
    }

    @Override
    public List<RoomDTO> searchRoomByText(String text) {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {

            return roomDAO.searchByText(session, text).stream().map(room -> convertor.fromRoom(room)).collect(Collectors.toList());

        } finally {
            session.close();
        }
    }
}
