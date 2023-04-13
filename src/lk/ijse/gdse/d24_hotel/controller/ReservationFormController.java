package lk.ijse.gdse.d24_hotel.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import lk.ijse.gdse.d24_hotel.dto.ReservationDTO;
import lk.ijse.gdse.d24_hotel.dto.RoomDTO;
import lk.ijse.gdse.d24_hotel.dto.StudentDTO;
import lk.ijse.gdse.d24_hotel.service.ServiceFactory;
import lk.ijse.gdse.d24_hotel.service.ServiceType;
import lk.ijse.gdse.d24_hotel.service.custom.ReservationService;
import lk.ijse.gdse.d24_hotel.service.custom.RoomService;
import lk.ijse.gdse.d24_hotel.service.custom.StudentService;
import lk.ijse.gdse.d24_hotel.service.exception.AllReadyBookedException;
import lk.ijse.gdse.d24_hotel.util.Navigation;
import lk.ijse.gdse.d24_hotel.util.Routes;
import lk.ijse.gdse.d24_hotel.view.tm.ReservationTm;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReservationFormController {
    public Label lblReservationId;
    public Label lblDate;
    public JFXComboBox cmbStudentId;
    public JFXComboBox cmbRoomId;
    public JFXButton btnAddStudent;
    public Pane paneStudent;
    public Label lblName;
    public Label lblAddress;
    public Label lblContact;
    public Label lblType;
    public Label lblRoomQty;
    public Label lblKeyMoney;
    public JFXCheckBox checkboxPayed;
    public TableView tblReservation;
    public TableColumn clmReservationId;
    public TableColumn clmStudentId;
    public TableColumn clmStudentName;
    public TableColumn clmRoomId;
    public TableColumn clmRoomType;
    public TableColumn clmKeyMoney;
    public TableColumn clmStatus;
    public TableColumn clmDate;
    public TableColumn clmOption;
    public JFXButton btnPlaceUpdate;
    public AnchorPane pane;

    public ReservationService reservationService;
    public StudentService studentService;
    public RoomService roomService;
    public StudentDTO studentDTO;
    public RoomDTO roomDTO;
    public ReservationDTO reservationDTO;

    public void initialize() {

        this.reservationService = ServiceFactory.getInstances().getService(ServiceType.RESERVATION);
        this.studentService = ServiceFactory.getInstances().getService(ServiceType.STUDENT);
        this.roomService = ServiceFactory.getInstances().getService(ServiceType.ROOM);
        initializeTable();
        initializeComboBox();
        paneStudent.setVisible(false);
        reservationService.getNextId();
        setDateAndResId();
    }

    private void setDateAndResId() {

        lblReservationId.setText(reservationService.getNextId());
        lblDate.setText(String.valueOf(LocalDate.now()));
    }

    private void initializeComboBox() {

        cmbStudentId.setItems(FXCollections.observableArrayList(studentService.findAllStudent().stream().map(studentDTO -> studentDTO.getStudentId()).collect(Collectors.toList())));
        cmbStudentId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setStudentDetails((String) newValue);
            }
        });

        cmbRoomId.setItems(FXCollections.observableArrayList(roomService.findAllRoom().stream().map(roomDTO -> roomDTO.getRoomId()).collect(Collectors.toList())));
        cmbRoomId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setRoomDetails((String) newValue);
            }
        });

    }

    private void setRoomDetails(String roomId) {

        roomDTO = roomService.getRoom(roomId);
        lblType.setText(roomDTO.getType());
        lblRoomQty.setText(String.valueOf(roomDTO.getAvailableRooms()));
        lblKeyMoney.setText(String.valueOf(roomDTO.getKeyMoney()));

    }

    private void setStudentDetails(String studentId) {

        studentDTO = studentService.getStudent(studentId);
        lblName.setText(studentDTO.getName());
        lblAddress.setText(studentDTO.getAddress());
        lblContact.setText(studentDTO.getContact());
        paneStudent.setVisible(true);

    }

    private void initializeTable() {

        clmReservationId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        clmStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        clmStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        clmRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        clmKeyMoney.setCellValueFactory(new PropertyValueFactory<>("keyMoney"));
        clmStatus.setCellValueFactory(new PropertyValueFactory<>("keyMoneyStatus"));
        clmDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        clmOption.setCellValueFactory(new PropertyValueFactory<>("option"));

        tblReservation.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setData();
                btnPlaceUpdate.setText("Update Place");
                btnPlaceUpdate.setStyle("-fx-background-color: #eb4d4b; -fx-background-radius: 40px");
            }
        });
    }

    private void setData() {

        cmbStudentId.setValue(reservationDTO.getStudentDTO().getStudentId().toString());
        cmbRoomId.setValue(reservationDTO.getRoomDTO().getRoomId().toString());
        if(reservationDTO.getKeyMoneyStatus().equalsIgnoreCase("Payed")){
            checkboxPayed.setSelected(true);
        }
    }

    public void btnAddStudentOnAction(ActionEvent actionEvent) {

        Navigation.navigate(Routes.STUDENT, pane);
    }

    public void btnPlaceUpdateOnAction(ActionEvent actionEvent) {

        if (cmbStudentId.getSelectionModel().isSelected(-1)) {
            new Alert(Alert.AlertType.ERROR, "Please select the student ID !", ButtonType.OK).show();
            cmbStudentId.setFocusColor(Paint.valueOf("Red"));
            cmbStudentId.requestFocus();
            return;
        } else if (cmbRoomId.getSelectionModel().isSelected(-1)) {
            new Alert(Alert.AlertType.ERROR, "Please select the room ID !", ButtonType.OK).show();
            cmbRoomId.setFocusColor(Paint.valueOf("Red"));
            cmbRoomId.requestFocus();
            return;
        } else if (Integer.parseInt(lblRoomQty.getText()) <= 0) {
            new Alert(Alert.AlertType.ERROR, "Sorry no available rooms found !", ButtonType.OK).show();
            cmbRoomId.setFocusColor(Paint.valueOf("Red"));
            cmbRoomId.requestFocus();
            return;
        } else if (!checkboxPayed.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure do you want to place reservation without key money ?",
                    ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.get() == ButtonType.YES) {
                placeReservation("Not Payed");
            }
            return;
        }
        placeReservation("Payed");
    }

    private void placeReservation(String keyMoneyStatus) {

        reservationDTO = new ReservationDTO(lblReservationId.getText(), Date.valueOf(LocalDate.now()), "Live", keyMoneyStatus, studentDTO, roomDTO);
        new Alert(Alert.AlertType.CONFIRMATION, "Reservation added !",ButtonType.OK).show();

        List<ReservationTm> list = new ArrayList<>();
        list.add(new ReservationTm(reservationDTO.getRes_id(), studentDTO.getStudentId(), studentDTO.getName(), roomDTO.getRoomId(), roomDTO.getType(), roomDTO.getKeyMoney(), reservationDTO.getKeyMoneyStatus(), reservationDTO.getDate(), getBtn()));
        tblReservation.setItems(FXCollections.observableArrayList(list));
        clear();
        return;
    }

    private JFXButton getBtn() {

        JFXButton btn = new JFXButton("Delete");
        btn.setStyle("-fx-max-width: 80px; -fx-background-color: #e55039; -fx-background-radius: 10; -fx-text-fill: #ffffff;");
        btn.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure do you want to delete this place ?",
                    ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.get() == ButtonType.YES) {
                tblReservation.getItems().clear();
                clear();
            }
        });
        return btn;
    }

    public void btnPlaceReservationOnAction(ActionEvent actionEvent) {

        if (tblReservation.getItems().isEmpty()) {
            System.out.println(tblReservation.getSelectionModel().isEmpty());
            new Alert(Alert.AlertType.ERROR, "Please select thr reservation details first !", ButtonType.OK).show();
            return;
        }
        try{
            if (reservationService.saveReservation(reservationDTO) == null) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the reservation !",ButtonType.OK).show();
                return;
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully save reservation !", ButtonType.OK).show();
            Navigation.navigate(Routes.RESERVATION, pane);
            return;

        }catch (AllReadyBookedException e){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, e.getMessage(),
                    ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.get() == ButtonType.YES) {
                if (reservationService.updateReservationStatus(studentDTO.getStudentId())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Successfully closed existing reservation !",ButtonType.OK).show();
                    return;
                }
            }
        }
    }

    private void clear() {

        cmbStudentId.getSelectionModel().clearSelection();
        paneStudent.setVisible(false);
        cmbRoomId.getSelectionModel().clearSelection();
        lblType.setText("XXXX");
        lblRoomQty.setText("XX");
        lblKeyMoney.setText("XX");
        checkboxPayed.setSelected(false);
        btnPlaceUpdate.setText("Place");
        btnPlaceUpdate.setStyle("-fx-background-color:  #54a0ff; -fx-background-radius: 40px");
    }
}
