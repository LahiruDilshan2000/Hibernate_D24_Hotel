package lk.ijse.gdse.d24_hotel.dao.custom.impl;

import lk.ijse.gdse.d24_hotel.entity.User;
import lk.ijse.gdse.d24_hotel.dao.custom.UserDAO;
import lk.ijse.gdse.d24_hotel.dao.exception.ConstraintViolationException;
import org.hibernate.Session;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public User save(Session session, User user) {

        try {
            session.save(user);
            return user;
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public User update(Session session, User user) {

        try {
            session.update(user);
            return user;
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void delete(Session session, User user) {

        try {
            session.save(user);
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public User get(Session session, String pk) {

        try {
            return session.get(User.class,pk);
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public boolean existByPk(Session session, String pk) {

        try {

            return session.createQuery("FROM User WHERE userId = :id").setParameter("id", pk).list().stream().findAny().isPresent();

        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<User> findAll(Session session) {

        try{
            return session.createQuery("FROM User").list();
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<User> searchByText(Session session, String text) {
        return null;
    }
}
