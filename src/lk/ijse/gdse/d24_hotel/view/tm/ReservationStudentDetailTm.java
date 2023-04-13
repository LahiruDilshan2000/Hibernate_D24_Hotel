package lk.ijse.gdse.d24_hotel.view.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationStudentDetailTm {
    private String reservationId;
    private String roomId;
    private String roomType;
    private String keyMoney;
    private String keyMoneyStatus;
    private String status;
    private Date date;
    private JFXButton option;
}
