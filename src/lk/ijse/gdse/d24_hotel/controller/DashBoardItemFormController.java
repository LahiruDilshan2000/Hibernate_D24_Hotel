package lk.ijse.gdse.d24_hotel.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.gdse.d24_hotel.dto.ReservationDTO;
import lk.ijse.gdse.d24_hotel.dto.RoomDTO;
import lk.ijse.gdse.d24_hotel.service.ServiceFactory;
import lk.ijse.gdse.d24_hotel.service.ServiceType;
import lk.ijse.gdse.d24_hotel.service.custom.ReservationService;
import lk.ijse.gdse.d24_hotel.service.custom.RoomService;
import lk.ijse.gdse.d24_hotel.service.custom.StudentService;
import lk.ijse.gdse.d24_hotel.util.Navigation;
import lk.ijse.gdse.d24_hotel.util.Routes;
import lk.ijse.gdse.d24_hotel.view.tm.StudentTm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class DashBoardItemFormController {
    public AnchorPane pane;
    public Label lblDate;
    public Label lblTime;
    public TableView tblStudent;
    public TableColumn clmId;
    public TableColumn clmName;
    public TableColumn clmAddress;
    public TableColumn clmContact;
    public TableColumn clmOption;
    public Label lblStudentCount;
    public Label lblNonAc;
    public Label lblKeyMoneyCount;
    public Label lblNonAcFood;
    public Label lblAc;
    public Label lblAcFood;
    public LineChart lineChart;

    public StudentService studentService;
    public RoomService roomService;
    public ReservationService reservationService;
    int count=0;

    public void initialize(){

        this.studentService= ServiceFactory.getInstances().getService(ServiceType.STUDENT);
        this.roomService=ServiceFactory.getInstances().getService(ServiceType.ROOM);
        this.reservationService=ServiceFactory.getInstances().getService(ServiceType.RESERVATION);
        setDateAndTime();
        initializeTable();
        initializeLineChart();
    }

    private void initializeLineChart() {

        List<RoomDTO> tmList = roomService.findAllRoom();

        XYChart.Series series = new XYChart.Series();
        for (RoomDTO roomDTO : tmList) {
            String x = String.valueOf(roomDTO.getType());
            int y = (int) roomDTO.getAvailableRooms();
            series.getData().add(new XYChart.Data(x, y));
        }
        lineChart.getData().addAll(series);
        lineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");
        series.getNode().setStyle("-fx-stroke: #3742fa");
        setLabel(tmList);
    }

    private void setLabel(List<RoomDTO> tmList) {

        if(!tmList.isEmpty()) {
            lblAcFood.setText(String.valueOf(tmList.get(0).getQty() - tmList.get(0).getAvailableRooms()));
            lblNonAc.setText(String.valueOf(tmList.get(1).getQty() - tmList.get(1).getAvailableRooms()));
            lblNonAcFood.setText(String.valueOf(tmList.get(2).getQty() - tmList.get(2).getAvailableRooms()));
            lblAc.setText(String.valueOf(tmList.get(3).getQty() - tmList.get(3).getAvailableRooms()));
        }
        List<ReservationDTO> not_payed = reservationService.findAll().stream().filter(reservationDTO -> reservationDTO.getKeyMoneyStatus().equalsIgnoreCase("Not Payed")).collect(Collectors.toList());
        lblKeyMoneyCount.setText(String.valueOf(not_payed.size()));
    }

    private void initializeTable() {

        clmId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        clmContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        clmOption.setCellValueFactory(new PropertyValueFactory<>("option"));

        tblStudent.setItems(FXCollections.observableArrayList(studentService.findAllStudent().stream().map(studentDTO -> new StudentTm(studentDTO.getStudentId(), studentDTO.getName(), studentDTO.getAddress(), studentDTO.getContact(), studentDTO.getDob(), studentDTO.getGender(), getBtn())).collect(Collectors.toList())));
        lblStudentCount.setText(String.valueOf(count));
    }

    private JFXButton getBtn() {

        count++;
        JFXButton btn = new JFXButton("");
        btn.setStyle("-fx-max-width: 2px; -fx-max-height:5px; -fx-background-color: #1dd1a1; -fx-background-radius: 12rem;");
        return btn;
    }

    private void setDateAndTime() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd / MM / yyyy");
        lblDate.setText(String.valueOf(LocalDate.now().format(formatter)));

        Timeline time = new Timeline(
                new KeyFrame(Duration.ZERO, event -> {
                    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH : mm   a");
                    lblTime.setText(LocalDateTime.now().format(formatter1));

                }), new KeyFrame(Duration.seconds(1)));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void btnSeeMoreOnAction(ActionEvent actionEvent) {

        Navigation.navigate(Routes.STUDENT, pane);
    }
}
