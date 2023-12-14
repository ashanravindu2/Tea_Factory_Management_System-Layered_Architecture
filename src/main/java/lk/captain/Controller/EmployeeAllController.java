package lk.captain.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EmployeeAllController {

    @FXML
    private AnchorPane RootNode;

    public void initialize() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/workerManage_form.fxml"));
        this.RootNode.getChildren().clear();
        this.RootNode.getChildren().add(anchorPane);

    }

    @FXML
    void btnTeaCollcManage(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/teaCollectorManage_form.fxml"));
        this.RootNode.getChildren().clear();
        this.RootNode.getChildren().add(anchorPane);

    }

    @FXML
    void btnTeaSuppliManage(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/teaSupplierManage_form.fxml"));
        this.RootNode.getChildren().clear();
        this.RootNode.getChildren().add(anchorPane);

    }

    @FXML
    void btnWorkerManage(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/workerManage_form.fxml"));
        this.RootNode.getChildren().clear();
        this.RootNode.getChildren().add(anchorPane);

    }


}
