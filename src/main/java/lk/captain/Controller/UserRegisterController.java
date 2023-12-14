package lk.captain.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.captain.dto.UserRegisterDTO;
import lk.captain.model.UserRegisterModel;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class UserRegisterController {

    @FXML
    private AnchorPane loginRoot;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPass;

    @FXML
    private TextField txtTele;

    @FXML
    private TextField txtUserName;
    @FXML
    private Label lblUserId;

    private  UserRegisterModel userRegisterModel = new UserRegisterModel();


   public void initialize(){

       generateNextUserId();
   }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        boolean isUser = validUse();
        if (isUser==true) {

            generateNextUserId();
            String userId = lblUserId.getText();
            String name = txtName.getText();
            String userTele = txtTele.getText();
            String userAddress = txtAddress.getText();
            String userEmail = txtEmail.getText();
            String userName = txtUserName.getText();
            String userPass = txtPass.getText();

            try {
                boolean isSaved = userRegisterModel.registerUser(new UserRegisterDTO(userId, userName, userPass, userTele, userAddress, name, userEmail));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "User Added Successfully").show();
                    generateNextUserId();
                    return;
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    }
    public void generateNextUserId(){
        try {
            String userId = userRegisterModel.generateNextUserId();
            lblUserId.setText(userId);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    private boolean validUse(){
        String name = txtName.getText();
        String userTele = txtTele.getText();
        String userAddress = txtAddress.getText();
        String userEmail = txtEmail.getText();
        String userName = txtUserName.getText();
        String userPass = txtPass.getText();


        boolean isNameValid = Pattern.matches("([A-Z][a-z]{3,})",name);
        boolean isUserTele = Pattern.matches("^\\+?\\d{3}[-\\s]?\\(?\\d{3}\\)?[-\\s]?\\d{3}[-\\s]?\\d",userTele);
        boolean isUserAdd = Pattern.matches("^([\\w\\s]+)\\s([\\w\\s]+)$",userAddress);
        boolean isuserEmail = Pattern.matches("^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,}$",userEmail);
        boolean isuserName = Pattern.matches("^[a-zA-Z0-9_-]{3,8}$",userName);
        boolean isUserPass = Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+{};:,<.>]).{8,12}$",userPass);

        if(!isNameValid){
            new Alert(Alert.AlertType.ERROR,"Invalid Name").show();
            return false;
        }
        if(!isUserTele){
            new Alert(Alert.AlertType.ERROR,"Invalid Telephone").show();
            return false;
        }
        if(!isUserAdd){
            new Alert(Alert.AlertType.ERROR,"Invalid Address").show();
            return false;
        }
        if(!isuserEmail){
            new Alert(Alert.AlertType.ERROR,"Invalid Email Address").show();
            return false;
        }
        if(!isuserName){
            new Alert(Alert.AlertType.ERROR,"Invalid UserName").show();
            return false;
        }
        if(!isUserPass){
            new Alert(Alert.AlertType.ERROR,"Invalid Password ").show();
            return false;
        }
        return true;
    }

}
