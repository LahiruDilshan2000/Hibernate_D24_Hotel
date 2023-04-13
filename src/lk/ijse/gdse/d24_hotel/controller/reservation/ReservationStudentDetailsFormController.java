package lk.ijse.gdse.d24_hotel.controller.reservation;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.gdse.d24_hotel.dto.ReservationDTO;
import lk.ijse.gdse.d24_hotel.dto.StudentDTO;
import lk.ijse.gdse.d24_hotel.service.ServiceFactory;
import lk.ijse.gdse.d24_hotel.service.ServiceType;
import lk.ijse.gdse.d24_hotel.service.custom.ReservationService;
import lk.ijse.gdse.d24_hotel.view.tm.ReservationStudentDetailTm;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationStudentDetailsFormController {

    public Label lblName;
    public TableView tblDetails;
    public TableColumn clmReservationId;
    public TableColumn clmRoomId;
    public TableColumn clmRoomType;
    public TableColumn clmKeyMoney;
    public TableColumn clmKeyMoneyStatus;
    public TableColumn clmStatus;
    public TableColumn clmDate;
    public TableColumn clmOption;
    public Label lblId;
    public Label lblContact;
    public Label lblDob;
    public Label lblAddress;
    public Label lblGender;

    public ReservationService reservationService;
    public List<ReservationDTO> reservationDTOList;
    public ReservationStudentDetailsFormController controller;
    double x,y=0;

    public void init(ReservationStudentDetailsFormController controller,StudentDTO studentDTO){

        this.controller=controller;
        this.reservationService= ServiceFactory.getInstances().getService(ServiceType.RESERVATION);
        reservationDTOList=reservationService.getReservationListByStudentId(studentDTO.getStudentId());
        setLabel(reservationDTOList.get(0).getStudentDTO());
        initializeTable();
        setTableData();
    }

    private void initializeTable() {

        clmReservationId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        clmRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        clmRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        clmKeyMoney.setCellValueFactory(new PropertyValueFactory<>("keyMoney"));
        clmKeyMoneyStatus.setCellValueFactory(new PropertyValueFactory<>("keyMoneyStatus"));
        clmStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        clmDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        clmOption.setCellValueFactory(new PropertyValueFactory<>("option"));

    }

    private JFXButton getBtn(ReservationDTO reservationDTO) {

        JFXButton btn = new JFXButton("Update");
        btn.setStyle("-fx-max-width: 70px; -fx-background-color: #1e90ff; -fx-background-radius: 10; -fx-text-fill: #ffffff;");
        btn.setOnAction(event -> {
            updateReservation(reservationDTO);
        });
        return btn;
    }

    private void updateReservation(ReservationDTO reservationDTO) {

        try {
            URL resource = this.getClass().getResource("/lk/ijse/gdse/d24_hotel/resources/view/reservation/ReservationStudentDetailUpdateForm.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            Parent load = fxmlLoader.load();
            ReservationStudentDetailUpdateFormController detailUpdateFormController = fxmlLoader.getController();
            detailUpdateFormController.init(controller, reservationDTO);
            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            load.setOnMousePressed(event -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            load.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTableData() {

        tblDetails.setItems(FXCollections.observableArrayList(reservationDTOList.stream().map(reservationDTO -> new ReservationStudentDetailTm(reservationDTO.getRes_id(), reservationDTO.getRoomDTO().getRoomId(), reservationDTO.getRoomDTO().getType(), reservationDTO.getRoomDTO().getKeyMoney(), reservationDTO.getKeyMoneyStatus(), reservationDTO.getStatus(), reservationDTO.getDate(), getBtn(reservationDTO))).collect(Collectors.toList())));

    }

    private void setLabel(StudentDTO studentDTO) {

        lblId.setText(studentDTO.getStudentId());
        lblName.setText(studentDTO.getName());
        lblAddress.setText(studentDTO.getAddress());
        lblContact.setText(studentDTO.getContact());
        lblDob.setText(String.valueOf(studentDTO.getDob()));
        lblGender.setText(studentDTO.getGender());
    }

    public void btnCloseOnAction(ActionEvent actionEvent) {

        Stage stage = (Stage) lblGender.getScene().getWindow();
        stage.close();
    }
}
