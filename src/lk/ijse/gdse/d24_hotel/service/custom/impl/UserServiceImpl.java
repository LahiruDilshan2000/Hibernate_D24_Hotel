package lk.ijse.gdse.d24_hotel.service.custom.impl;

import lk.ijse.gdse.d24_hotel.dao.DaoFactory;
import lk.ijse.gdse.d24_hotel.dao.DaoType;
import lk.ijse.gdse.d24_hotel.dao.custom.UserDAO;
import lk.ijse.gdse.d24_hotel.dto.UserDTO;
import lk.ijse.gdse.d24_hotel.entity.User;
import lk.ijse.gdse.d24_hotel.service.custom.UserService;
import lk.ijse.gdse.d24_hotel.service.exception.DuplicateException;
import lk.ijse.gdse.d24_hotel.service.util.Convertor;
import lk.ijse.gdse.d24_hotel.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final Convertor convertor;

    public UserServiceImpl() {

        userDAO = DaoFactory.getInstance().getDAO(DaoType.USER);
        convertor = new Convertor();
    }

    @Override
    public UserDTO getUserDetails(String ID) {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {

            Optional<User> user = Optional.ofNullable(userDAO.get(session, ID));
            return user.isPresent() ? convertor.fromUser(user.get()) : null;

        } finally {
            session.close();
        }
    }

    @Override
    public List<UserDTO> findAll() {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {

            return userDAO.findAll(session).stream().map(user -> convertor.fromUser(user)).collect(Collectors.toList());

        } finally {
            session.close();
        }
    }

    @Override
    public boolean isExistById(String ID) throws DuplicateException {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {

            if (userDAO.existByPk(session, ID)) throw new DuplicateException("User ID is all ready exist !");

            return true;

        } finally {
            session.close();
        }
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Transaction transaction = session.beginTransaction();

            if (userDAO.save(session, convertor.toUser(userDTO)) == null)
                throw new RuntimeException("Failed to save user !");

            transaction.commit();
            return userDTO;

        } finally {
            session.close();
        }
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Transaction transaction = session.beginTransaction();

            if (userDAO.update(session, convertor.toUser(userDTO)) == null)
                throw new RuntimeException("Failed to update user !");

            transaction.commit();
            return userDTO;

        } finally {
            session.close();
        }
    }
}
