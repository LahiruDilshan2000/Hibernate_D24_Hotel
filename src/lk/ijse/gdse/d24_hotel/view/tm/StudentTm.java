package lk.ijse.gdse.d24_hotel.view.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentTm {
    private String studentId;
    private String name;
    private String address;
    private String contact;
    private Date dob;
    private String gender;
    private JFXButton option;
}
