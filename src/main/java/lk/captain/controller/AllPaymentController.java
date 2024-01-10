package lk.captain.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AllPaymentController {


    @FXML
    private AnchorPane RootNode;

    public void initialize() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/workerPayment_form.fxml"));
        this.RootNode.getChildren().clear();
        this.RootNode.getChildren().add(anchorPane);
    }

    @FXML
    void btnCollectorPayment(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/teaColectorPayment_form.fxml"));
        this.RootNode.getChildren().clear();
        this.RootNode.getChildren().add(anchorPane);

    }

    @FXML
    void btnSupplierPayment(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/suplierPayment_form.fxml"));
        this.RootNode.getChildren().clear();
        this.RootNode.getChildren().add(anchorPane);

    }

    @FXML
    void btnWorkerPayment(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/workerPayment_form.fxml"));
        this.RootNode.getChildren().clear();
        this.RootNode.getChildren().add(anchorPane);
    }
}
