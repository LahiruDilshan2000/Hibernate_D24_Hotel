package lk.ijse.gdse.d24_hotel.view.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomTm {
    private String roomId;
    private String type;
    private String keyMoney;
    private int qty;
    private int availableRooms;
    private JFXButton option;
}
