package lk.ijse.gdse.d24_hotel.service.custom;

import lk.ijse.gdse.d24_hotel.dto.UserDTO;
import lk.ijse.gdse.d24_hotel.service.SuperService;
import lk.ijse.gdse.d24_hotel.service.exception.DuplicateException;

import java.util.List;

public interface UserService extends SuperService {

    UserDTO getUserDetails(String ID);

    List<UserDTO> findAll();

    boolean isExistById(String ID) throws DuplicateException;

    UserDTO saveUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO);

}
