package lk.ijse.gdse.d24_hotel.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import lk.ijse.gdse.d24_hotel.dto.RoomDTO;
import lk.ijse.gdse.d24_hotel.service.ServiceFactory;
import lk.ijse.gdse.d24_hotel.service.ServiceType;
import lk.ijse.gdse.d24_hotel.service.custom.RoomService;
import lk.ijse.gdse.d24_hotel.service.exception.DuplicateException;
import lk.ijse.gdse.d24_hotel.view.tm.RoomTm;

import java.util.Optional;
import java.util.stream.Collectors;

public class RoomFormController {
    public TableView tblRoom;
    public TableColumn clmRoomId;
    public TableColumn clmRoomType;
    public TableColumn clmKeyMoney;
    public TableColumn clmQty;
    public TableColumn clmOption;
    public JFXTextField txtRoomId;
    public JFXComboBox cmbRoomType;
    public JFXTextField txtKeyMoney;
    public JFXTextField txtQty;
    public JFXButton btnAddUpdate;
    public JFXTextField txtSearch;
    public JFXButton btnClear;

    public RoomService roomService;
    public RoomTm roomTm;

    public void initialize() {

        this.roomService = ServiceFactory.getInstances().getService(ServiceType.ROOM);
        initializeTable();
        setTableData();
    }

    private void initializeTable() {

        clmRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        clmRoomType.setCellValueFactory(new PropertyValueFactory<>("type"));
        clmKeyMoney.setCellValueFactory(new PropertyValueFactory<>("keyMoney"));
        clmQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        clmOption.setCellValueFactory(new PropertyValueFactory<>("option"));

        tblRoom.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setData(roomTm=(RoomTm) newValue);
                btnAddUpdate.setText("Update");
                btnAddUpdate.setStyle("-fx-background-color: #706fd3; -fx-background-radius: 40px");
            }
        });

        cmbRoomType.setItems(FXCollections.observableArrayList("Non-AC", "Non-AC / Food", "AC", "AC / Food"));
    }

    private void setTableData() {

        tblRoom.setItems(FXCollections.observableArrayList(roomService.findAllRoom().stream().map(roomDTO -> new RoomTm(roomDTO.getRoomId(), roomDTO.getType(), roomDTO.getKeyMoney(), roomDTO.getQty(), roomDTO.getAvailableRooms(), getBtn(roomDTO))).collect(Collectors.toList())));
    }

    private JFXButton getBtn(RoomDTO roomDTO) {

        JFXButton btn = new JFXButton("Delete");
        btn.setStyle("-fx-max-width: 80px; -fx-background-color: #e55039; -fx-background-radius: 10; -fx-text-fill: #ffffff;");
        btn.setOnAction(event -> {
            deleteRoom(roomDTO);
        });
        return btn;
    }

    private void deleteRoom(RoomDTO roomDTO) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure do you want to delete this room ?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {

            roomService.deleteRoom(roomDTO);

            new Alert(Alert.AlertType.CONFIRMATION, "Successfully delete room !", ButtonType.OK).show();
            clear();

            if (!txtSearch.getText().equalsIgnoreCase("")) {
                searchRoom();
                return;
            }
            setTableData();
            clear();
        }
    }

    private void searchRoom() {

        tblRoom.setItems(FXCollections.observableArrayList(roomService.searchRoomByText(txtSearch.getText()).stream().map(roomDTO -> new RoomTm(roomDTO.getRoomId(), roomDTO.getType(), roomDTO.getKeyMoney(), roomDTO.getQty(), roomDTO.getAvailableRooms(), getBtn(roomDTO))).collect(Collectors.toList())));
    }

    private void setData(RoomTm roomTm) {

        txtRoomId.setEditable(false);
        txtRoomId.setText(roomTm.getRoomId());
        cmbRoomType.setValue(roomTm.getType());
        txtKeyMoney.setText(roomTm.getKeyMoney());
        txtQty.setText(String.valueOf(roomTm.getQty()));
    }

    public void btnAddUpdateOnAction(ActionEvent actionEvent) {

        if (txtRoomId.getText().isEmpty() || !txtRoomId.getText().matches("^(RM-)([0-9]{4,}$)")) {
            new Alert(Alert.AlertType.ERROR, "Room ID is invalid or empty", ButtonType.OK).show();
            txtRoomId.setFocusColor(Paint.valueOf("Red"));
            txtRoomId.requestFocus();
            return;
        } else if (cmbRoomType.getSelectionModel().isSelected(-1)) {
            new Alert(Alert.AlertType.ERROR, "Please select the room type !", ButtonType.OK).show();
            cmbRoomType.setFocusColor(Paint.valueOf("Red"));
            cmbRoomType.requestFocus();
            return;
        } else if (txtKeyMoney.getText().isEmpty() || !txtKeyMoney.getText().matches("^[0-9]{2,}")) {
            new Alert(Alert.AlertType.ERROR, "Key money invalid or empty", ButtonType.OK).show();
            txtKeyMoney.setFocusColor(Paint.valueOf("Red"));
            txtKeyMoney.requestFocus();
            return;
        } else if (txtQty.getText().isEmpty() || !txtQty.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Qty text invalid or empty", ButtonType.OK).show();
            txtQty.setFocusColor(Paint.valueOf("Red"));
            txtQty.requestFocus();
            return;
        } else if (btnAddUpdate.getText().equalsIgnoreCase("Add +")) {
            try {
                if (roomService.saveRoom(new RoomDTO(txtRoomId.getText(), cmbRoomType.getValue().toString(), txtKeyMoney.getText(), Integer.parseInt(txtQty.getText()), Integer.parseInt(txtQty.getText()))) == null) {
                    new Alert(Alert.AlertType.ERROR, "Failed to save the room !").show();
                    return;
                }
                new Alert(Alert.AlertType.CONFIRMATION, "Successfully added room !", ButtonType.OK).show();
                setTableData();
                clear();
                return;
            } catch (DuplicateException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                txtRoomId.setFocusColor(Paint.valueOf("Red"));
                txtRoomId.requestFocus();
            }
        } else {
            if (Integer.parseInt(txtQty.getText()) < roomTm.getAvailableRooms()){
                new Alert(Alert.AlertType.ERROR, "Room are all ready book !", ButtonType.OK).show();
                txtQty.setFocusColor(Paint.valueOf("Red"));
                txtQty.requestFocus();
                return;
            }
            if (roomService.updateRoom(new RoomDTO(txtRoomId.getText(), cmbRoomType.getValue().toString(), txtKeyMoney.getText(), Integer.parseInt(txtQty.getText()), (Integer.parseInt(txtQty.getText()) - roomTm.getQty()) + roomTm.getAvailableRooms())) == null) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the room !").show();
                return;
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully update room !", ButtonType.OK).show();
            setTableData();
            clear();
            return;
        }
    }

    public void txtSearchKeyReleaseEvent(KeyEvent keyEvent) {

        searchRoom();
    }

    public void btnClearOnAction(ActionEvent actionEvent) {

        clear();
    }

    private void clear() {

        txtRoomId.setEditable(true);
        txtRoomId.clear();
        cmbRoomType.getSelectionModel().clearSelection();
        txtKeyMoney.clear();
        txtQty.clear();
        btnAddUpdate.setText("Add +");
        btnAddUpdate.setStyle("-fx-background-color: #0097e6; -fx-background-radius: 40px");
    }
}
