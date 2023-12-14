package lk.captain.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.captain.dto.util.DateTimeUtil;
import lk.captain.model.UserRegisterModel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import  java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;
import java.util.SimpleTimeZone;

public class DashBoardController {


    public JFXButton btnLogOut;
    @FXML
    private AnchorPane roodCenter;
    @FXML
    private Label lblLoaclDate;

    @FXML
    private Label lblLocalTime;

    @FXML
    private Label lblName;

    @FXML
    private Text lbltime;

    @FXML
    public BorderPane dashboardBorderPane;

    UserRegisterModel userRegisterModel = new UserRegisterModel();

    public void initialize() throws IOException {
        home();
        setLblLoaclDate();
    }


    public void home() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/home_form.fxml"));
        dashboardBorderPane.setCenter(anchorPane);
        setLblLoaclDate();
    }

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        home();
    }

    public void setLblLoaclDate() {
        lblLoaclDate.setText(String.valueOf(LocalDate.now()));
        realTime();
    }

    public void realTime(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> lbltime.setText(DateTimeUtil.timenow())));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        lbltime.setText(DateTimeUtil.dateNow());
    }


    @FXML
    void btnStockOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/stock_form.fxml"));
        dashboardBorderPane.setCenter(anchorPane);
        setLblLoaclDate();

    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) {

    }


    @FXML
    void btnTeaTypeOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/teaType_form.fxml"));
        dashboardBorderPane.setCenter(anchorPane);
        setLblLoaclDate();
    }

    @FXML
    void btnEmployeeAction(ActionEvent event) throws IOException {
        BorderPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/EmployeeAll_form.fxml"));
        dashboardBorderPane.setCenter(anchorPane);
        setLblLoaclDate();

    }

    @FXML
    void btnTeaLeafEntryAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/tealeafEntry_form.fxml"));
        dashboardBorderPane.setCenter(anchorPane);
        setLblLoaclDate();

    }


    @FXML
    void btnSellsOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/teaSells_form.fxml"));
        dashboardBorderPane.setCenter(anchorPane);
        setLblLoaclDate();

    }


    @FXML
    void btnPaymentAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/payment_form.fxml"));
        dashboardBorderPane.setCenter(anchorPane);
        setLblLoaclDate();

    }

    @FXML
    void btnAttendenceAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/attendenc_form.fxml"));
        dashboardBorderPane.setCenter(anchorPane);
        setLblLoaclDate();

    }
        @FXML
        void btnLogOutAction (ActionEvent e) throws IOException {
        try {
            btnLogOut.setOnAction((e1) -> {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are You Sure To LogOut ?", yes, no).showAndWait();
                if (type.orElse(no) == yes) {
                    Parent rootNode = null;
                    try {
                        rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Scene scene = new Scene(rootNode);
                    dashboardBorderPane.getChildren().clear();
                    Stage primaryStage = (Stage) dashboardBorderPane.getScene().getWindow();
                    primaryStage.setScene(scene);
                    primaryStage.centerOnScreen();
                    primaryStage.setTitle("User Log In Page");
                }
            });
        }catch (Exception e1){
            throw new RuntimeException(e1);
        }
}

    public void btnPowderAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/TeaPowderAvailable_form.fxml"));
        dashboardBorderPane.setCenter(anchorPane);
        setLblLoaclDate();
    }

}

