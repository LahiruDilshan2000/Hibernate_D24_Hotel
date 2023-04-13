package lk.ijse.gdse.d24_hotel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Room implements SuperEntity{
    @Id
    @Column(name = "room_type_id")
    private String roomId;
    private String type;
    private String key_money;
    private int qty;
    private int availableRooms;

    @OneToMany(mappedBy = "room",targetEntity = Reservation.class)
    private List<Reservation> reservationList;
}
