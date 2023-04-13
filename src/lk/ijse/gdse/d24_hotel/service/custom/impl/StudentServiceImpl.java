package lk.ijse.gdse.d24_hotel.service.custom.impl;

import lk.ijse.gdse.d24_hotel.dto.StudentDTO;
import lk.ijse.gdse.d24_hotel.dao.DaoFactory;
import lk.ijse.gdse.d24_hotel.dao.DaoType;
import lk.ijse.gdse.d24_hotel.dao.custom.StudentDAO;
import lk.ijse.gdse.d24_hotel.service.custom.StudentService;
import lk.ijse.gdse.d24_hotel.service.exception.DuplicateException;
import lk.ijse.gdse.d24_hotel.service.util.Convertor;
import lk.ijse.gdse.d24_hotel.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {

    private final StudentDAO studentDAO;
    private final Convertor convertor;

    public StudentServiceImpl() {

        studentDAO = DaoFactory.getInstance().getDAO(DaoType.STUDENT);
        convertor = new Convertor();
    }

    @Override
    public StudentDTO saveStudent(StudentDTO studentDTO) throws DuplicateException{

        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Transaction transaction = session.beginTransaction();

            System.out.println(studentDAO.existByPk(session, studentDTO.getStudentId()));
            if (!studentDAO.existByPk(session, studentDTO.getStudentId()))
                throw new DuplicateException("Student ID all ready exits !");

            if (studentDAO.save(session, convertor.toStudent(studentDTO)) == null)
                throw new RuntimeException("Failed to save student !");

            transaction.commit();
            return studentDTO;
        }finally {
            session.close();
        }
    }

    @Override
    public StudentDTO updateStudent(StudentDTO studentDTO) {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Transaction transaction = session.beginTransaction();

            if (studentDAO.update(session, convertor.toStudent(studentDTO)) == null)
                throw new RuntimeException("Failed to update student !");

            transaction.commit();
            return studentDTO;
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteStudent(StudentDTO studentDTO) {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {

            Transaction transaction = session.beginTransaction();

            studentDAO.delete(session, convertor.toStudent(studentDTO));

            transaction.commit();

        } finally {
            session.close();
        }
    }

    @Override
    public StudentDTO getStudent(String ID) {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {

            return convertor.fromStudent(studentDAO.get(session, ID));

        } finally {
            session.close();
        }
    }

    @Override
    public List<StudentDTO> findAllStudent() {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {

            return studentDAO.findAll(session).stream().map(student -> convertor.fromStudent(student)).collect(Collectors.toList());

        } finally {
            session.close();
        }
    }

    @Override
    public List<StudentDTO> searchStudentByText(String text) {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {

            return studentDAO.searchByText(session, text).stream().map(student -> convertor.fromStudent(student)).collect(Collectors.toList());

        } finally {
            session.close();
        }

    }
}
