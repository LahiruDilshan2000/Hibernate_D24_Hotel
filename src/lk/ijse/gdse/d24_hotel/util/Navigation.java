package lk.ijse.gdse.d24_hotel.util;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class Navigation {

    private static AnchorPane anchorPane;

    public static void navigate(Routes route, AnchorPane anchorPane){
        Navigation.anchorPane=anchorPane;
        Navigation.anchorPane.getChildren().clear();
        Stage window = (Stage) Navigation.anchorPane.getScene().getWindow();

        switch (route){
            case STUDENT:
                window.setTitle("Student Management Form");
                initUI("StudentForm",true);
                break;
            case ROOM:
                window.setTitle("Room Management Form");
                initUI("RoomForm",true);
                break;
            case RESERVATION:
                window.setTitle("Reservation Management Form");
                initUI("ReservationForm",true);
                break;
            case DASHBOARD:
                window.setTitle("Dashboard Form");
                initUI("DashBoardForm",false);
                break;
            case LOGIN:
                window.setTitle("SignIn Form");
                initUI("LoginForm",false);
                break;
            case SIGNUP:
                window.setTitle("SignUp Form");
                initUI("SignUpForm",true);
                break;
            case DASHBOARDITEM:
                window.setTitle("DashBoard Form");
                initUI("DashBoardItemForm",true);
                break;
            case RESERVATIONDETAIL:
                window.setTitle("Reservation Details Form");
                initUI("reservation/ReservationDetailsForm",true);
                break;
        }
    }
    public static void initUI(String location,boolean flag){
        try {
            if(flag) {
                Parent root= FXMLLoader.load(Navigation.class.getResource("/lk/ijse/gdse/d24_hotel/resources/view/" +location+".fxml"));
                Navigation.anchorPane.getChildren().add(root);
                root.translateXProperty().set(Navigation.anchorPane.getWidth());

                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(t -> {
                    Navigation.anchorPane.getChildren().remove(anchorPane);
                });
                timeline.play();
                //Navigation.anchorPane.getChildren().add(FXMLLoader.load(Navigation.class.getResource("/lk/ijse/gdse/d24_hotel/resources/view/" +location+".fxml")));
            }else{
                Stage stage = (Stage) Navigation.anchorPane.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(Navigation.class.getResource("/lk/ijse/gdse/d24_hotel/resources/view/" +location+".fxml"))));
                stage.centerOnScreen();
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
