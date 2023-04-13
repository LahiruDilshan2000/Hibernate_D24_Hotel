package lk.ijse.gdse.d24_hotel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Date;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student implements SuperEntity{

    @Id
    private String student_id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String address;
    private String contact;
    private Date dob;
    private String gender;

    @OneToMany(mappedBy = "student",targetEntity = Reservation.class)
    private List<Reservation> reservationList;
}
