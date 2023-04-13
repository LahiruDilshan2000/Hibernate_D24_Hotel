package lk.ijse.gdse.d24_hotel.service;

import lk.ijse.gdse.d24_hotel.service.custom.impl.ReservationServiceImpl;
import lk.ijse.gdse.d24_hotel.service.custom.impl.RoomServiceImpl;
import lk.ijse.gdse.d24_hotel.service.custom.impl.StudentServiceImpl;
import lk.ijse.gdse.d24_hotel.service.custom.impl.UserServiceImpl;

public class ServiceFactory {

    private static ServiceFactory serviceFactory;

    private ServiceFactory(){

    }

    public static ServiceFactory getInstances(){
        return serviceFactory==null ? (serviceFactory=new ServiceFactory()) : serviceFactory;
    }

    public  <T extends SuperService>T getService(ServiceType serviceType){
        switch (serviceType){
            case STUDENT:
                return (T) new StudentServiceImpl();
            case ROOM:
                return (T) new RoomServiceImpl();
            case RESERVATION:
                return (T) new ReservationServiceImpl();
            case USER:
                return (T) new UserServiceImpl();
            default:
                return null;
        }
    }
}
