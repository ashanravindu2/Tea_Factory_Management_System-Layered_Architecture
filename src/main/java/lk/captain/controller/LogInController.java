package lk.captain.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.captain.bo.BOFactory;
import lk.captain.bo.custom.UserRegBO;


import java.io.IOException;
import java.util.regex.Pattern;


public class LogInController {

    @FXML
    public AnchorPane loginRoot;
    public Label labelFocusNNotMatched;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;
    @FXML
    private ImageView warnning1;

    @FXML
    private ImageView warnning2;


    DashBoardController dashBoardController = new DashBoardController();

    UserRegBO userRegBO = (UserRegBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.USERREG);
    public void initialize() {
        labelFocusNNotMatched.setVisible(false);
        warnning1.setVisible(false);
        warnning2.setVisible(false);
    }

    @FXML
    public void btnLoginAction(ActionEvent actionEvent) throws IOException {

boolean isValid = valid();
if (isValid==true) {
    String adminUser = txtUserName.getText();
    String adminPass = txtPassword.getText();

   try {
        boolean isIn =userRegBO.getUser(adminUser, adminPass);
         if (isIn == true) {
        Parent borderPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(borderPane);
        Stage stage = (Stage) this.loginRoot.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("USER DASHBOARD");

       } else if (adminUser.equals("admin") && adminPass.equals("Admin@123")) {
            Parent borderPane = FXMLLoader.load(this.getClass().getResource("/view/adminDashBoard_form.fxml"));
            Scene scene = new Scene(borderPane);
            Stage stage = (Stage) this.loginRoot.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("ADMIN DASHBOARD");

        } else if (adminUser.equals("admin")) {
            txtUserName.setStyle("-fx-border-color: green");
            txtPassword.setStyle("-fx-border-color: red");
            warnning1.setVisible(false);
            warnning2.setVisible(false);
            labelFocusNNotMatched.setVisible(true);
            txtPassword.requestFocus();

        } else {
            txtUserName.setStyle("-fx-border-color: red");
            txtPassword.setStyle("-fx-border-color: red");
            txtUserName.requestFocus();
            new Alert(Alert.AlertType.WARNING, "Invalid UserName or Password").show();
            warnning1.setVisible(true);
            warnning2.setVisible(true);
            labelFocusNNotMatched.setVisible(false);
        }
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
    }

   private boolean valid(){
        String username = txtUserName.getText();
        String password = txtPassword.getText();

        boolean isUserNameValid = Pattern.matches("^[a-zA-Z0-9_-]{3,8}$",username);
        boolean isUserPassvalid = Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+{};:,<.>]).{8,12}$",password);

        if(!isUserNameValid){
            new Alert(Alert.AlertType.ERROR,"Invalid UserName").show();
            return false;
        }
        if(!isUserPassvalid){
            new Alert(Alert.AlertType.ERROR,"Invalid User Password").show();
            return false;
        }
        return true;
   }


    }


