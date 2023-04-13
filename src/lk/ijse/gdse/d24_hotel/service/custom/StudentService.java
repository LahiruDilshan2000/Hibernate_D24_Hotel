package lk.ijse.gdse.d24_hotel.service.custom;

import lk.ijse.gdse.d24_hotel.dto.StudentDTO;
import lk.ijse.gdse.d24_hotel.service.SuperService;
import lk.ijse.gdse.d24_hotel.service.exception.DuplicateException;

import java.util.List;

public interface StudentService extends SuperService {

    StudentDTO saveStudent(StudentDTO studentDTO) throws DuplicateException;

    StudentDTO updateStudent(StudentDTO studentDTO);

    void deleteStudent(StudentDTO studentDTO);

    StudentDTO getStudent(String ID);

    List<StudentDTO> findAllStudent();

    List<StudentDTO> searchStudentByText(String text);
}
