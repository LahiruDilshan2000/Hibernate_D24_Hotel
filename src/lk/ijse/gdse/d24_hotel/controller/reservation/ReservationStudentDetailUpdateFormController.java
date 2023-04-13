package lk.ijse.gdse.d24_hotel.controller.reservation;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import lk.ijse.gdse.d24_hotel.dto.ReservationDTO;
import lk.ijse.gdse.d24_hotel.service.ServiceFactory;
import lk.ijse.gdse.d24_hotel.service.ServiceType;
import lk.ijse.gdse.d24_hotel.service.custom.ReservationService;


public class ReservationStudentDetailUpdateFormController {

    public Label lblReservationId;
    public Label lblRoomId;
    public Label lblKeyMoneyStatus;
    public JFXComboBox cmbKeyMoneyStatus;
    public Label lblStatus;
    public JFXComboBox cmbStatus;

    public ReservationStudentDetailsFormController controller;
    public ReservationDTO reservationDTO;
    public ReservationService reservationService;

    public void init(ReservationStudentDetailsFormController controller, ReservationDTO reservationDTO){

        this.controller=controller;
        this.reservationDTO=reservationDTO;
        this.reservationService= ServiceFactory.getInstances().getService(ServiceType.RESERVATION);
        setData();

    }

    private void setData() {

        cmbKeyMoneyStatus.setItems(FXCollections.observableArrayList("Payed", "Not Payed"));
        cmbStatus.setItems(FXCollections.observableArrayList(reservationDTO.getStatus().equalsIgnoreCase("Live") ? (FXCollections.observableArrayList("Live", "closed")) : FXCollections.observableArrayList("closed")));

        lblReservationId.setText(reservationDTO.getRes_id());
        lblRoomId.setText(reservationDTO.getRoomDTO().getRoomId());
        lblKeyMoneyStatus.setText(reservationDTO.getKeyMoneyStatus());
        lblStatus.setText(reservationDTO.getStatus());
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

        if (cmbKeyMoneyStatus.getSelectionModel().isSelected(-1)) {
            new Alert(Alert.AlertType.ERROR, "Please select the key money status !", ButtonType.OK).show();
            cmbKeyMoneyStatus.setFocusColor(Paint.valueOf("Red"));
            cmbKeyMoneyStatus.requestFocus();
            return;
        } else if (cmbStatus.getSelectionModel().isSelected(-1)) {
            new Alert(Alert.AlertType.ERROR, "Please select the room status !", ButtonType.OK).show();
            cmbStatus.setFocusColor(Paint.valueOf("Red"));
            cmbStatus.requestFocus();
            return;
        }
        reservationDTO.setKeyMoneyStatus(cmbKeyMoneyStatus.getValue().toString());
        reservationDTO.setStatus(cmbStatus.getValue().toString());

        if (reservationService.updateReservation(reservationDTO) == null) {
            new Alert(Alert.AlertType.ERROR, "Failed to update reservation !", ButtonType.OK).show();
            return;
        }
        new Alert(Alert.AlertType.CONFIRMATION, "Successfully update reservation !", ButtonType.OK).show();
        controller.setTableData();
        exit();
    }

    public void btnCloseOnAction(ActionEvent actionEvent) {

        exit();
    }

    private void exit() {

        Stage stage = (Stage) lblReservationId.getScene().getWindow();
        stage.close();
    }
}
