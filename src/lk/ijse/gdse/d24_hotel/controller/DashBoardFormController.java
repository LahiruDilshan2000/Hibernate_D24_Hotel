package lk.ijse.gdse.d24_hotel.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.gdse.d24_hotel.dto.UserDTO;
import lk.ijse.gdse.d24_hotel.util.Navigation;
import lk.ijse.gdse.d24_hotel.util.Routes;
import lk.ijse.gdse.d24_hotel.util.UserHolder;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class DashBoardFormController {
    public JFXButton btnDashBoard;
    public JFXButton btnStudent;
    public JFXButton btnRoom;
    public JFXButton btnReservation;
    public AnchorPane pane;
    public JFXButton btnDetails;
    public Label lblReception;

    public UserDTO userDTO;
    double x,y=0;

    public void initialize() {

        setButtonStyle(btnDashBoard);
        userDTO=UserHolder.getInstance().getUserDTO();
        lblReception.setText(userDTO.getUserId());
    }

    public void btnDashBoardOnAction(ActionEvent actionEvent) {

        reSetButton();
        Navigation.navigate(Routes.DASHBOARDITEM, pane);
        setButtonStyle(btnDashBoard);
    }

    public void btnStudentOnAction(ActionEvent actionEvent) {

        reSetButton();
        Navigation.navigate(Routes.STUDENT, pane);
        setButtonStyle(btnStudent);
    }

    public void btnRoomOnAction(ActionEvent actionEvent) {

        reSetButton();
        Navigation.navigate(Routes.ROOM, pane);
        setButtonStyle(btnRoom);
    }

    public void btnReservationOnAction(ActionEvent actionEvent) {

        reSetButton();
        Navigation.navigate(Routes.RESERVATION, pane);
        setButtonStyle(btnReservation);
    }

    public void btnDetailsOnAction(ActionEvent actionEvent) {

        reSetButton();
        Navigation.navigate(Routes.RESERVATIONDETAIL, pane);
        setButtonStyle(btnDetails);
    }

    public void btnSettingOnAction(ActionEvent actionEvent) {

        try {
            URL resource = this.getClass().getResource("/lk/ijse/gdse/d24_hotel/resources/view/SettingForm.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            Parent load = fxmlLoader.load();
            SettingFormController controller = fxmlLoader.getController();
            controller.init(userDTO);
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

    public void btnLogOutOnAction(ActionEvent actionEvent) {

        Navigation.navigate(Routes.LOGIN, pane);
    }

    private void reSetButton() {

        List<JFXButton> buttonList = FXCollections.observableArrayList(btnDashBoard, btnStudent, btnRoom, btnReservation, btnDetails);
        for (int i = 0; i < buttonList.size(); i++) {
            buttonList.get(i).setStyle("-fx-text-fill: #d7d7d7;");
        }
    }

    private void setButtonStyle(JFXButton btn) {

        btn.setStyle("-fx-text-fill: #ffffff;\n" +
                "    -fx-font-size: 20px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #5352ed,  #2430FF);\n" +
                "    -fx-effect: dropShadow(three-pass-box, rgba(2.2,10.2,0.2,0.2), 2.0, 0.0, 2.0, 2.0);");
    }
}
