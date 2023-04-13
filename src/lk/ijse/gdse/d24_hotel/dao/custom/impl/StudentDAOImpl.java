package lk.ijse.gdse.d24_hotel.dao.custom.impl;

import lk.ijse.gdse.d24_hotel.entity.Student;
import lk.ijse.gdse.d24_hotel.dao.custom.StudentDAO;
import lk.ijse.gdse.d24_hotel.dao.exception.ConstraintViolationException;
import org.hibernate.Session;

import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public Student save(Session session, Student student) {

        try {
            session.save(student);
            return student;
        } catch (Exception e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Student update(Session session, Student student) {

        try {
            session.update(student);
            return student;
        } catch (Exception e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void delete(Session session, Student student) {

        try {
            session.delete(student);
        } catch (Exception e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Student get(Session session, String pk) {

        try {
            return session.get(Student.class, pk);
        } catch (Exception e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public boolean existByPk(Session session, String pk) {

        try {
            return session.createQuery("FROM Student s WHERE s.student_id = :id").setParameter("id", pk).list().isEmpty();
        } catch (Exception e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Student> findAll(Session session) {

        try {
            return session.createQuery("FROM Student").list();
        } catch (Exception e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Student> searchByText(Session session, String text) {

        try {
            text="%"+text+"%";
            return session.createQuery("FROM Student s WHERE s.student_id LIKE :text OR s.name LIKE :text OR s.address LIKE :text OR s.contact LIKE :text OR s.dob LIKE :text OR s.gender LIKE :text").setParameter("text", text).list();
        } catch (Exception e) {
            throw new ConstraintViolationException(e);
        }
    }
}
