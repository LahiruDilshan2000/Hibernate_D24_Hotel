package lk.ijse.gdse.d24_hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomDTO {
    private String roomId;
    private String type;
    private String keyMoney;
    private int qty;
    private int availableRooms;
}
