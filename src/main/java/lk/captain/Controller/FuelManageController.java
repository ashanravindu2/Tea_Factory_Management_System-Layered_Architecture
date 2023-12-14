package lk.captain.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class FuelManageController {
    @FXML
    public AnchorPane fuelManageOnAction;

    public BorderPane stockNode;
    @FXML
    private BorderPane fuelNooD;



    @FXML
    void btnFuelBackAction(ActionEvent event) throws IOException {
        AnchorPane employeeRoot = FXMLLoader.load(getClass().getResource("/view/stock_form.fxml"));
        fuelNooD.setCenter(employeeRoot);

    }










}



