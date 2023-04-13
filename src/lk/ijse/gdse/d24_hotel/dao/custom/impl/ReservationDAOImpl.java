package lk.ijse.gdse.d24_hotel.dao.custom.impl;

import lk.ijse.gdse.d24_hotel.entity.Reservation;
import lk.ijse.gdse.d24_hotel.dao.custom.ReservationDAO;
import lk.ijse.gdse.d24_hotel.dao.exception.ConstraintViolationException;
import org.hibernate.Session;

import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {
    @Override
    public Reservation save(Session session, Reservation reservation) {

        try {
            session.save(reservation);
            return reservation;
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Reservation update(Session session, Reservation reservation) {

        try {
            session.createQuery("UPDATE Reservation SET status = :status, keyMoneyStatus = :keyMoneyStatus WHERE res_id = :res_id").setParameter("status", reservation.getStatus()).setParameter("keyMoneyStatus", reservation.getKeyMoneyStatus()).setParameter("res_id", reservation.getRes_id()).executeUpdate();
            return reservation;
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void delete(Session session, Reservation reservation) {

        try {
            session.detach(reservation);
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Reservation get(Session session, String pk) {

        try {
            return session.get(Reservation.class,pk);
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public boolean existByPk(Session session, String pk) {
        return false;
    }

    @Override
    public List<Reservation> findAll(Session session) {

        try{
            return session.createQuery("FROM Reservation").list();
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Reservation> searchByText(Session session, String text) {

        try {
            text="%"+text+"%";
            return session.createQuery("FROM Reservation r WHERE r.res_id LIKE :text OR r.date LIKE :text OR r.status LIKE :text OR r.room_id LIKE :text OR r.studnt_id LIKE :text").setParameter("text", text).list();
        } catch (Exception e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public String getNextReservationId(Session session) {

        try{
            List list = session.createQuery("FROM Reservation r ORDER BY r.res_id DESC").list();
            if(list.isEmpty()){
                return null;
            }
            Reservation reservation = (Reservation)list.get(0);
            return reservation.getRes_id();
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public boolean isExistBook(Session session, String pk) {

        try{
            return session.createQuery("FROM Reservation WHERE student_id = :id AND status = :status").setParameter("id", pk).setParameter("status", "Live").list().stream().findFirst().isPresent();
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public boolean updateReservationStatus(Session session, String studentId) {

        try{
            return session.createQuery("UPDATE Reservation r SET r.status = :status where student_id = :id").setParameter("status", "closed").setParameter("id", studentId).executeUpdate() > 0;
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Reservation getBeforeUpdateReservation(Session session, String studentId) {

        try{
            return (Reservation) session.createQuery("FROM Reservation  WHERE status = :status AND student_id = :id").setParameter("status", "Live").setParameter("id", studentId).list().stream().findAny().get();
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Reservation> getAllGroupByStudentId(Session session) {

        try{
            return session.createQuery("FROM Reservation GROUP BY student_id").list();
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Reservation> getReservationByStudentId(Session session, String studentId) {

        try{
            return session.createQuery("FROM Reservation WHERE student_id = :id").setParameter("id", studentId).list();
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Reservation> getReservationByText(Session session, String text) {

        try {
            text="%"+text+"%";
            return session.createQuery("FROM Reservation s WHERE student_id LIKE :text GROUP BY student_id").setParameter("text", text).list();
        } catch (Exception e) {
            throw new ConstraintViolationException(e);
        }
    }
}
