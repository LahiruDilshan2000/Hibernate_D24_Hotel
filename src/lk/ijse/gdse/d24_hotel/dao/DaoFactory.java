package lk.ijse.gdse.d24_hotel.dao;

import lk.ijse.gdse.d24_hotel.dao.custom.impl.*;

public class DaoFactory {

    private static DaoFactory daoFactory;

    private DaoFactory(){

    }

    public static DaoFactory getInstance(){

        return daoFactory ==null ? (daoFactory =new DaoFactory()) : daoFactory;
    }

    public <T extends SuperDAO>T getDAO(DaoType daoType){
        switch (daoType){
            case STUDENT:
                return (T) new StudentDAOImpl();
            case ROOM:
                return (T) new RoomDAOImpl();
            case RESERVATION:
                return (T) new ReservationDAOImpl();
            case USER:
                return (T) new UserDAOImpl();
            case QUERY:
                return (T) new QueryDAOImpl();
            default:
                return null;
        }
    }
}
