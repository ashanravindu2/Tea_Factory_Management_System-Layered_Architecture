package lk.captain.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.captain.bo.BOFactory;
import lk.captain.bo.custom.TeaSupplierBO;
import lk.captain.dto.SupplierManageDTO;
import lk.captain.view.tm.TeaSupplierManageTM;



import java.sql.ResultSet;
import java.sql.SQLException;
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

   TeaSupplierBO teaSupplierBO = (TeaSupplierBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.TEASUPPLIER);

    public void initialize() throws ClassNotFoundException {
        suppgender();
        generateSupId();
        try {
            loadTeaSupplier();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
    void btnSupplierSaveOnAction(ActionEvent event) throws ClassNotFoundException {

        boolean isValid = Valid();
        if (isValid == true) {
            String supplierId = lblSuppId.getText();
            String suppName = txtSupplierName.getText();
            String suppAddres = txtSupplierAdd.getText();
            String suppTele = txtSupplierTele.getText();
            String suppGen = cmbGender.getValue();

            try {
                boolean isSaved = false;
                try {
                    isSaved = teaSupplierBO.supplierSave(new SupplierManageDTO(supplierId, suppName, suppAddres, suppTele, suppGen));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
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

    public void generateSupId() throws ClassNotFoundException {
        try {
            ResultSet resultSet = teaSupplierBO.generateSupId();
            boolean isExist = resultSet.next();

            if(isExist){
                String oldSupId = resultSet.getString(1);
                oldSupId =oldSupId.substring(1,oldSupId.length());
                int intId =Integer.parseInt(oldSupId);
                intId =intId+1;

                if (intId <10){
                    lblSuppId.setText("S00" +intId);
                } else if (intId <100) {
                    lblSuppId.setText("S0"+intId);

                }else {
                    lblSuppId.setText("S"+intId);
                }
            }else {
                lblSuppId.setText("S001");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    private void loadTeaSupplier() throws ClassNotFoundException {

        ObservableList<TeaSupplierManageTM> obList = FXCollections.observableArrayList();

        try {
            List<SupplierManageDTO> dtoList = teaSupplierBO.getAllTeaSupp();

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
    void btnClearAction(ActionEvent event) throws ClassNotFoundException {
        txtSupplierName.setText("");
        txtSupplierTele.setText("");
        txtSupplierAdd.setText("");
        cmbGender.setValue("");
        txtsearchId.setText("");
        generateSupId();
    }

    @FXML
    void btnDeleteAction(ActionEvent event) throws ClassNotFoundException {
        String deleteId = txtsearchId.getText();

        try {
            boolean isDeleted = teaSupplierBO.deleteSupplier(deleteId);
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
    void btnUpdateAction(ActionEvent event) throws ClassNotFoundException {
        String supplierId = lblSuppId.getText();
        String suppName = txtSupplierName.getText();
        String suppAddres = txtSupplierAdd.getText();
        String suppTele = txtSupplierTele.getText();
        String suppGen = cmbGender.getValue();


        var dto = new SupplierManageDTO(supplierId, suppName, suppAddres, suppTele, suppGen);


        try {
            boolean isUpdated = teaSupplierBO.updateSupplier(dto);
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
    void txtSearchIdOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = txtsearchId.getText();

        try {
            SupplierManageDTO dto = teaSupplierBO.searchSupplierId(id);

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
