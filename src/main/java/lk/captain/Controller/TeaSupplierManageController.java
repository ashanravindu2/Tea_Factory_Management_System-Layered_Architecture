package lk.captain.Controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.captain.dto.SupplierManageDTO;
import lk.captain.dto.WorkerManageDTO;
import lk.captain.dto.tm.TeaSupplierManageTM;
import lk.captain.model.SupplierManageModel;
import lk.captain.model.WokerManageModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class TeaSupplierManageController {

    @FXML
    private JFXComboBox<String> cmbGender;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtSupplierAdd;


    @FXML
    private Label lblSuppId;

    @FXML
    private TextField txtSupplierName;

    @FXML
    private TextField txtSupplierTele;

    @FXML
    private TableColumn<?, ?> colAdd;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTele;

    @FXML
    private TextField txtsearchId;

    @FXML
    private TableView<TeaSupplierManageTM> tblTeaSupp;

    private SupplierManageModel suppliermanageModel = new SupplierManageModel();

    public void initialize() {
        suppgender();
        generateSupId();
        loadTeaSupplier();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("suppName"));
        colAdd.setCellValueFactory(new PropertyValueFactory<>("suppAddres"));
        colTele.setCellValueFactory(new PropertyValueFactory<>("suppTele"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("suppGen"));
    }

    @FXML
    void btnSupplierSaveOnAction(ActionEvent event) {

        boolean isValid = Valid();
        if (isValid == true) {
            String supplierId = lblSuppId.getText();
            String suppName = txtSupplierName.getText();
            String suppAddres = txtSupplierAdd.getText();
            String suppTele = txtSupplierTele.getText();
            String suppGen = cmbGender.getValue();

            try {
                boolean isSaved = suppliermanageModel.supplierSave(new SupplierManageDTO(supplierId, suppName, suppAddres, suppTele, suppGen));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Supplier Saved Successful").show();
                    generateSupId();
                    loadTeaSupplier();
                    setCellValueFactory();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    }

    public void suppgender() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.addAll("Male", "Female");

        try {
            cmbGender.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void generateSupId() {
        try {
            String suppid = suppliermanageModel.generateSupId();
            lblSuppId.setText(suppid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTeaSupplier() {
        var suppliermanageModel = new SupplierManageModel();

        ObservableList<TeaSupplierManageTM> obList = FXCollections.observableArrayList();

        try {
            List<SupplierManageDTO> dtoList = suppliermanageModel.getAllTeaSupp();

            for (SupplierManageDTO dto : dtoList) {
                obList.add(
                        new TeaSupplierManageTM(
                                dto.getSupplierId(),
                                dto.getSuppName(),
                                dto.getSuppAddres(),
                                dto.getSuppTele(),
                                dto.getSuppGen()
                        )
                );
            }

            tblTeaSupp.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearAction(ActionEvent event) {
        txtSupplierName.setText("");
        txtSupplierTele.setText("");
        txtSupplierAdd.setText("");
        cmbGender.setValue("");
        txtsearchId.setText("");
        generateSupId();
    }

    @FXML
    void btnDeleteAction(ActionEvent event) {
        String deleteId = txtsearchId.getText();

        try {
            boolean isDeleted = suppliermanageModel.deleteSupplier(deleteId);
            if (isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION, deleteId + " " + deleteId + " " + "Supplier is Deleted !").show();
            loadTeaSupplier();
            setCellValueFactory();
            generateSupId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void btnUpdateAction(ActionEvent event) {
        String supplierId = lblSuppId.getText();
        String suppName = txtSupplierName.getText();
        String suppAddres = txtSupplierAdd.getText();
        String suppTele = txtSupplierTele.getText();
        String suppGen = cmbGender.getValue();


        var dto = new SupplierManageDTO(supplierId, suppName, suppAddres, suppTele, suppGen);

        var model = new SupplierManageModel();
        try {
            boolean isUpdated = model.updateSupplier(dto);
            System.out.println(isUpdated);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Updated Success!").show();
                loadTeaSupplier();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void txtSearchIdOnAction(ActionEvent event) {
        String id = txtsearchId.getText();

        var supplierManageModel = new SupplierManageModel();
        try {
            SupplierManageDTO dto = supplierManageModel.searchSupplierId(id);

            if (dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Supplier is not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void fillFields(SupplierManageDTO dto) {
        lblSuppId.setText(dto.getSupplierId());
        txtSupplierName.setText(dto.getSuppName());
        cmbGender.setValue(dto.getSuppGen());
        txtSupplierTele.setText(dto.getSuppTele());
        txtSupplierAdd.setText(dto.getSuppAddres());

    }

    private boolean Valid() {
        String suppName = txtSupplierName.getText();
        String suppAddres = txtSupplierAdd.getText();
        String suppTele = txtSupplierTele.getText();


        boolean isSupName = Pattern.matches("([A-Z][a-z\\s]{3,}|[a-z])", suppName);
        boolean issuppAddres = Pattern.matches("^([\\w\\s]+)\\s([\\w\\s]+)$", suppAddres);
        boolean issuppTele = Pattern.matches("^\\+?\\d{3}[-\\s]?\\(?\\d{3}\\)?[-\\s]?\\d{3}[-\\s]?\\d", suppTele);

        if (!isSupName) {
            new Alert(Alert.AlertType.ERROR, "Invalid Name").show();
            return false;
        }
        if (!issuppAddres) {
            new Alert(Alert.AlertType.ERROR, "Invalid Address").show();
            return false;
        }
        if (!issuppTele) {
            new Alert(Alert.AlertType.ERROR, "Invalid Telephone").show();
            return false;
        }
        return true;

    }
}
