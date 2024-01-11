package lk.captain.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class StocklController {


    public Label popupWare;
    @FXML
    private AnchorPane stockNode;

    @FXML
    void btnFuelMnageAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/fuel_manage_form.fxml"));
        this.stockNode.getChildren().clear();
        this.stockNode.getChildren().add(anchorPane);

    }

    @FXML
    void btnTeaMnageAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/teaManage_from.fxml"));
        this.stockNode.getChildren().clear();
        this.stockNode.getChildren().add(anchorPane);

    }

    @FXML
    void btnWoodMnageAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/wood_manage_form.fxml"));
        this.stockNode.getChildren().clear();
        this.stockNode.getChildren().add(anchorPane);

    }
}
