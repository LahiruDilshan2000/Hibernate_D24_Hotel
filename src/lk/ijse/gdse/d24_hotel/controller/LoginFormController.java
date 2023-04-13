package lk.ijse.gdse.d24_hotel.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.gdse.d24_hotel.controller.reservation.ReservationStudentDetailsFormController;
import lk.ijse.gdse.d24_hotel.dto.UserDTO;
import lk.ijse.gdse.d24_hotel.service.ServiceFactory;
import lk.ijse.gdse.d24_hotel.service.ServiceType;
import lk.ijse.gdse.d24_hotel.service.custom.UserService;
import lk.ijse.gdse.d24_hotel.util.Navigation;
import lk.ijse.gdse.d24_hotel.util.Routes;
import lk.ijse.gdse.d24_hotel.util.UserHolder;

import java.io.IOException;
import java.net.URL;

public class LoginFormController {
    public JFXTextField txtUserId;
    public JFXTextField txtPassword;
    public ImageView picHidePass;
    public JFXPasswordField txtPasswordField;
    public ImageView picShowPass;

    public UserService userService;
    public AnchorPane pane;

    public void initialize(){

        this.userService= ServiceFactory.getInstances().getService(ServiceType.USER);
        txtPassword.setVisible(false);
        picHidePass.setVisible(false);
    }

    public void btnSignInOnAction(ActionEvent actionEvent) {

        if (txtUserId.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "User ID is empty", ButtonType.OK).show();
            txtUserId.setFocusColor(Paint.valueOf("Red"));
            txtUserId.requestFocus();
            return;
        } else if (txtPassword.isVisible() && txtPassword.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Password is empty", ButtonType.OK).show();
            txtPassword.setFocusColor(Paint.valueOf("Red"));
            txtPassword.requestFocus();
            return;
        } else if (txtPasswordField.isVisible() && txtPasswordField.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Password is empty", ButtonType.OK).show();
            txtPasswordField.setFocusColor(Paint.valueOf("Red"));
            txtPasswordField.requestFocus();
            return;
        }

        UserDTO user = userService.getUserDetails(txtUserId.getText());

        if (user == null) {
            new Alert(Alert.AlertType.ERROR, "User ID is invalid", ButtonType.OK).show();
            txtUserId.setFocusColor(Paint.valueOf("Red"));
            txtUserId.requestFocus();
            return;
        } else if (txtPassword.isVisible() && !user.getPassword().equalsIgnoreCase(txtPassword.getText())) {
            new Alert(Alert.AlertType.ERROR, "Password is invalid", ButtonType.OK).show();
            txtPassword.setFocusColor(Paint.valueOf("Red"));
            txtPassword.requestFocus();
            return;
        } else if (txtPasswordField.isVisible() && !user.getPassword().equalsIgnoreCase(txtPasswordField.getText())) {
            new Alert(Alert.AlertType.ERROR, "Password is invalid", ButtonType.OK).show();
            txtPasswordField.setFocusColor(Paint.valueOf("Red"));
            txtPasswordField.requestFocus();
            return;
        } else {
            try {
                UserHolder.getInstance().setUserDTO(user);
                URL resource = this.getClass().getResource("/lk/ijse/gdse/d24_hotel/resources/view/DashBoardForm.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader(resource);
                Stage stage = new Stage();
                AnchorPane container = fxmlLoader.load(resource);
                AnchorPane pneContainer = (AnchorPane)container.lookup("#pane");
                stage.setScene(new Scene(container));
                stage.centerOnScreen();
                Stage window =(Stage) txtPassword.getScene().getWindow();
                window.hide();
                Navigation.navigate(Routes.DASHBOARDITEM, pneContainer);
                stage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void btnHideShowOnAction(ActionEvent actionEvent) {

        if(txtPasswordField.isVisible()){
            txtPassword.setText(txtPasswordField.getText());
            txtPasswordField.setVisible(false);
            txtPassword.setVisible(true);
            picShowPass.setVisible(false);
            picHidePass.setVisible(true);
            return;
        }
        txtPasswordField.setText(txtPassword.getText());
        txtPasswordField.setVisible(true);
        txtPassword.setVisible(false);
        picShowPass.setVisible(true);
        picHidePass.setVisible(false);
    }

    public void btnCloseOnAction(ActionEvent actionEvent) {

        System.exit(0);
    }

    public void lblSignUpOnAction(MouseEvent mouseEvent) {

        Navigation.navigate(Routes.SIGNUP, pane);
    }
}
