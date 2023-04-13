package lk.ijse.gdse.d24_hotel.controller.reservation;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.gdse.d24_hotel.dto.StudentDTO;
import lk.ijse.gdse.d24_hotel.service.ServiceFactory;
import lk.ijse.gdse.d24_hotel.service.ServiceType;
import lk.ijse.gdse.d24_hotel.service.custom.ReservationService;
import lk.ijse.gdse.d24_hotel.view.tm.StudentTm;

import java.io.IOException;
import java.net.URL;
import java.util.stream.Collectors;

public class ReservationDetailsFormController {
    public TableView tblStudent;
    public TableColumn clmStudentId;
    public JFXTextField txtSearch;
    public TableColumn clmName;
    public TableColumn clmAddress;
    public TableColumn clmContact;
    public TableColumn clmDob;
    public TableColumn clmOption;

    public ReservationService reservationService;
    double x,y=0;

    public void initialize() {

        this.reservationService = ServiceFactory.getInstances().getService(ServiceType.RESERVATION);
        initializeTable();
        setTableData();


    }

    private void setTableData() {

        tblStudent.setItems(FXCollections.observableArrayList(reservationService.getReservationList().stream().map(reservationDTO -> new StudentTm(reservationDTO.getStudentDTO().getStudentId(), reservationDTO.getStudentDTO().getName(), reservationDTO.getStudentDTO().getAddress(), reservationDTO.getStudentDTO().getContact(), reservationDTO.getStudentDTO().getDob(), reservationDTO.getStudentDTO().getGender(), getBtn(reservationDTO.getStudentDTO()))).collect(Collectors.toList())));
    }

    private JFXButton getBtn(StudentDTO studentDTO) {

        JFXButton btn = new JFXButton("Details");
        btn.setStyle("-fx-max-width: 70px; -fx-background-color: #eb2f06; -fx-background-radius: 10; -fx-text-fill: #ffffff;");
        btn.setOnAction(event -> {
            getDetail(studentDTO);
        });
        return btn;
    }

    private void initializeTable() {

        clmStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        clmContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        clmDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        clmOption.setCellValueFactory(new PropertyValueFactory<>("option"));

    }

    private void getDetail(StudentDTO studentDTO) {

        try {
            URL resource = this.getClass().getResource("/lk/ijse/gdse/d24_hotel/resources/view/reservation/ReservationStudentDetailsForm.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            Parent load = fxmlLoader.load();
            ReservationStudentDetailsFormController controller = fxmlLoader.getController();
            controller.init(controller, studentDTO);
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

    public void txtSearchKeyReleaseEvent(KeyEvent keyEvent) {

        tblStudent.setItems(FXCollections.observableArrayList(reservationService.getReservationByText(txtSearch.getText()).stream().map(studentDTO -> new StudentTm(studentDTO.getStudentId(), studentDTO.getName(), studentDTO.getAddress(), studentDTO.getContact(), studentDTO.getDob(), studentDTO.getGender(), getBtn(studentDTO))).collect(Collectors.toList())));
    }
}
