package lk.ijse.gdse.d24_hotel.dao;

import lk.ijse.gdse.d24_hotel.entity.SuperEntity;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

public interface CrudDAO<T extends SuperEntity, ID extends Serializable> extends SuperDAO {

    T save(Session session, T entity);

    T update(Session session, T entity);

    void delete(Session session, T entity);

    T get(Session session, ID pk);

    boolean existByPk(Session session, ID pk);

    List<T> findAll(Session session);

    List<T> searchByText(Session session, ID text);
}
