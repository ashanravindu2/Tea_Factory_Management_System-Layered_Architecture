package lk.captain.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.captain.bo.BOFactory;
import lk.captain.bo.custom.FuelBO;
import lk.captain.dto.*;
import lk.captain.view.tm.FuelMatiralTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class FuelController {

    @FXML
    private AnchorPane root;

    @FXML
    private ComboBox<String> cmbBId;

    @FXML
    private Label lblFuelId;

    @FXML
    private Label lblAvailableBarrel;

    @FXML
    private JFXButton btnUsed;

    @FXML
    private TableColumn<?, ?> colBarrelId;

    @FXML
    private TableColumn<?, ?> colLiter;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private Label lblAvilable;


    @FXML
    private Label lblLiterAval;

    @FXML
    private Label lblRegId;

    @FXML
    private TableView<FuelMatiralTM> tblFuel;

    @FXML
    private TextField txtsearchId;

    @FXML
    private TextField txtBId;

    @FXML
    private TextField txtBLId;

    @FXML
    private TextField txtUseFuel;



    FuelBO fuelBO = (FuelBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.FUEL);

    public void initialize() throws SQLException, ClassNotFoundException {
        getAvl();
        loadFuel();
        generateSupId();
        load();
        setCellValueFactory();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException {
        boolean isValid = Valid();
        if (isValid == true) {
            String id = lblFuelId.getText();
            String barrel = txtBId.getText();
            double liter = Double.parseDouble(txtBLId.getText());

            try {
                boolean isSaved = fuelBO.fuelSave(new FuelMaterialDTO(id, barrel, liter));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Barrel Saved Successful").show();
                    initialize();
                    //loadTeaSupplier();
                    //  setCellValueFactory();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnUseOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        boolean isValid = Valids();
        if (isValid == true) {
            String id = cmbBId.getValue();
            double useLiter = Double.parseDouble(txtUseFuel.getText());

            try {
                boolean isSaved = fuelBO.usedUpdateFuel(id, useLiter);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Barrel Used Successful").show();
                    initialize();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void cmbBarrelId(ActionEvent event) throws SQLException {
        try {
            String id = cmbBId.getValue();
            FuelMaterialDTO dto = fuelBO.searchFuelId(id);
            lblAvilable.setText(String.valueOf(dto.getBLeter()));
            double available = Double.parseDouble(lblAvilable.getText());
            if (available <= 0) {
                txtUseFuel.setDisable(true);
                btnUsed.setDisable(true);
            } else {
                txtUseFuel.setDisable(false);
                btnUsed.setDisable(false);
            }
        }catch (Exception e){
            new RuntimeException(e);
        }

    }

    @FXML
    void btnBakc(ActionEvent event) throws IOException {

        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/stock_form.fxml"));
        this.root.getChildren().clear();
        this.root.getChildren().add(anchorPane);


    }
    public void generateSupId() throws ClassNotFoundException {
        try {
            ResultSet resultSet = fuelBO.generateFuelId();

                    boolean isExist = resultSet.next();

                    if(isExist){
                        String oldBId = resultSet.getString(1);
                        oldBId =oldBId.substring(1,oldBId.length());
                        int intId =Integer.parseInt(oldBId);
                        intId =intId+1;

                        if (intId <10){
                            lblFuelId.setText("B00" +intId);
                        } else if (intId <100) {
                            lblFuelId.setText("B0"+intId);

                        }else {
                            lblFuelId.setText("B"+intId);
                        }
                    }else {
                        lblFuelId.setText("B001");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
    }
    private void loadFuel() throws ClassNotFoundException {


        ObservableList<FuelMatiralTM> obList = FXCollections.observableArrayList();

        try {
            List<FuelMaterialDTO> dtoList = fuelBO.getAllFuel();

            for (FuelMaterialDTO dto : dtoList) {

                Button btn = new Button("Remove");
                btn.setCursor(Cursor.HAND);

                btn.setOnAction((e) ->{
                    ButtonType yes = new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No",ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION,"Are You Sure To Remove ?",yes,no).showAndWait();
                    if (type.orElse(no)==yes){
                        int selectedIndex = tblFuel.getSelectionModel().getSelectedIndex();
                        String id = (String) colCode.getCellData(selectedIndex);

                        try {
                            deleteteaId(id);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }

                        obList.remove(selectedIndex);
                        tblFuel.refresh();
                    }

                });


                obList.add(
                        new FuelMatiralTM(
                                dto.getBarrelId(),
                                dto.getBCategory(),
                                dto.getBLeter(),
                                btn
                        )
                );
            }

            tblFuel.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("barrelId"));
        colBarrelId.setCellValueFactory(new PropertyValueFactory<>("bCategory"));
        colLiter.setCellValueFactory(new PropertyValueFactory<>("bLeter"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }
    private void deleteteaId(String id) throws ClassNotFoundException {
        try {
            boolean isDeleted = fuelBO.deleteFuel(id);
            if (isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION,"Fuel Stock Removed !").show();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    @FXML
    void txtSearchIdOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = txtsearchId.getText();


        try {
            FuelMaterialDTO dto = fuelBO.searchFuelId(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Fuel Category not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    private void fillFields(FuelMaterialDTO dto) {
        txtBId.setText(String.valueOf(dto.getBCategory()));
        txtBLId.setText(String.valueOf(dto.getBLeter()));
        lblRegId.setText(dto.getBarrelId());
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        boolean isValid = Valid();
        if (isValid == true) {

            String category = txtBId.getText();
            double liter = Double.parseDouble(txtBLId.getText());
            String id = lblRegId.getText();

            try {
                boolean isUpdated = fuelBO.update(new FuelMaterialDTO(id, category, liter));
                System.out.println(isUpdated);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Fuel Updated Success!").show();
                    initialize();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private void load() throws ClassNotFoundException {
        ObservableList<String> obFuelList = FXCollections.observableArrayList();

        try {
            List<FuelMaterialDTO> suppList = fuelBO.getAllFuel();

            for (FuelMaterialDTO dto : suppList) {
                obFuelList.add(dto.getBarrelId());
            }
            cmbBId.setItems(obFuelList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public  void getAvl() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = fuelBO.getAllAvalable();

        int barrelCount = 0;
        double liter = 0;
        if (resultSet.next()){
            barrelCount=resultSet.getInt(1);
            liter=resultSet.getDouble(2);
        }
        lblAvailableBarrel.setText(String.valueOf(barrelCount));
        lblLiterAval.setText(String.valueOf(liter));

    }
    private boolean Valid() {

        String liter = txtBLId.getText();
        String  desc = txtBId.getText();

        boolean isDesc = Pattern.matches("^([\\w\\s]+)([\\w\\s]+)$", desc);
        boolean isLiter =  Pattern.matches("^\\S[0-9]{0,}", liter);

        if (!isDesc) {
            new Alert(Alert.AlertType.ERROR, "Invalid Category").show();
            return false;
        }
        if (!isLiter) {
            new Alert(Alert.AlertType.ERROR, "Invalid Liter").show();
            return false;
        }
        return true;

    }
    private boolean Valids() {
        String liter = txtUseFuel.getText();
        boolean isLiter =  Pattern.matches("^\\S[0-9]{0,}", liter);

        if (!isLiter) {
            new Alert(Alert.AlertType.ERROR, "Invalid Litre").show();
            return false;
        }
        return true;

    }

}
