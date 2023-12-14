package lk.captain.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

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
