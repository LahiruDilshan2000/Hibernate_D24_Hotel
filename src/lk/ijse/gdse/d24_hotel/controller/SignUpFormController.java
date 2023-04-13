package lk.ijse.gdse.d24_hotel.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.gdse.d24_hotel.dto.UserDTO;
import lk.ijse.gdse.d24_hotel.service.ServiceFactory;
import lk.ijse.gdse.d24_hotel.service.ServiceType;
import lk.ijse.gdse.d24_hotel.service.custom.UserService;
import lk.ijse.gdse.d24_hotel.util.Navigation;
import lk.ijse.gdse.d24_hotel.util.Routes;

import java.util.List;

public class SignUpFormController {
    public AnchorPane pane;
    public JFXTextField txtUserId;
    public Label lblAlert;
    public JFXTextField txtPassword;
    public JFXTextField txtPasswordReEnter;
    public JFXButton btnSignUp;

    public UserService userService;
    public List<UserDTO> userDTOList;

    public void initialize(){

        this.userService= ServiceFactory.getInstances().getService(ServiceType.USER);
        btnSignUp.setDisable(true);
        userDTOList=userService.findAll();

        txtUserId.setOnKeyReleased(event -> {

            if(isExist()){
                btnSignUp.setDisable(true);
                lblAlert.setText("User ID all ready exist !");
                txtUserId.setFocusColor(Paint.valueOf("Red"));
                txtUserId.requestFocus();
                return;
            }
            lblAlert.setText("");
            btnSignUp.setDisable(false);
        });
    }

    private boolean isExist(){

        for (UserDTO userDTO : userDTOList) {
            if (userDTO.getUserId().equalsIgnoreCase(txtUserId.getText())) {
                return true;
            }
        }
        return false;
    }
    public void btnSignUpOnAction(ActionEvent actionEvent) {

        if (txtUserId.getText().isEmpty() || !txtUserId.getText().matches("^[a-zA-Z0-9@]{4,}$")) {
            new Alert(Alert.AlertType.ERROR, "User ID invalid or empty", ButtonType.OK).show();
            txtUserId.setFocusColor(Paint.valueOf("Red"));
            txtUserId.requestFocus();
            return;
        } else if (txtPassword.getText().isEmpty() || !txtPassword.getText().matches("^[a-zA-Z0-9_]{8,}$")) {
            new Alert(Alert.AlertType.ERROR, "Password invalid or empty", ButtonType.OK).show();
            txtPassword.setFocusColor(Paint.valueOf("Red"));
            txtPassword.requestFocus();
            return;
        } else if (txtPasswordReEnter.getText().isEmpty() || !txtPassword.getText().equalsIgnoreCase(txtPasswordReEnter.getText())) {
            new Alert(Alert.AlertType.ERROR, "Password text dose not match or empty", ButtonType.OK).show();
            txtPasswordReEnter.setFocusColor(Paint.valueOf("Red"));
            txtPasswordReEnter.requestFocus();
            return;
        } else {
            if (userService.saveUser(new UserDTO(txtUserId.getText(), txtPassword.getText())) == null) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the user !", ButtonType.OK).show();
                return;
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully added User !", ButtonType.OK).showAndWait();
            Navigation.navigate(Routes.LOGIN, pane);
        }
    }

    public void btnCloseOnAction(ActionEvent actionEvent) {

        System.exit(0);
    }

    public void lblSignInOnAction(MouseEvent mouseEvent) {

        Navigation.navigate(Routes.LOGIN, pane);
    }
}
