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
import lk.captain.bo.custom.WoodBO;
import lk.captain.dto.WoodMaterialDTO;
import lk.captain.view.tm.WoodMatirialTM;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class WoodManageController {
    public TextField txtsearchId;
    @FXML
    private AnchorPane root;

        @FXML
        private JFXButton btnUsed;

        @FXML
        private ComboBox<String> cmbWoodId;

        @FXML
        private TableColumn<?, ?> colAction;

        @FXML
        private TableColumn<?, ?> colCode;

        @FXML
        private TableColumn<?, ?> colWCategory;

        @FXML
        private TableColumn<?, ?> colWeight;

        @FXML
        private Label lblAvailableWood;

        @FXML
        private Label lblRegId;

        @FXML
        private Label lblUsedAvl;

        @FXML
        private Label lblWoodCount;

        @FXML
        private TableView<WoodMatirialTM> tblWood;

        @FXML
        private TextField txtUsedWeight;

        @FXML
        private TextField txtWCategory;

    @FXML
    private Label lblGFuelId;

        @FXML
        private TextField txtWeight;



    WoodBO woodBO = (WoodBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.WOOD);
    FuelBO fuelBO = (FuelBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.FUEL);

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {generateWoodId();
        load();
        loadWood();setCellValueFactory();getAvl();}

        @FXML
        void btnClearFieldAction(ActionEvent event) {
            txtWCategory.setText(" ");
            txtWeight.setText(" ");
            lblRegId.setText("");
        }

        @FXML
        void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException {
            boolean isValid = Valid();
            if (isValid == true) {
                String id = lblGFuelId.getText();
                String category = txtWCategory.getText();
                double weight = Double.parseDouble(txtWeight.getText());

                try {
                    boolean isSaved = woodBO.save(new WoodMaterialDTO(id, category, weight));
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Barrel Saved Successful").show();
                        initialize();

                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            }
        }

        @FXML
        void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
            boolean isValid = Valid();
            if (isValid == true) {
                String category = txtWCategory.getText();
                double weight = Double.parseDouble(txtWeight.getText());
                String id = lblRegId.getText();


                var dto = new WoodMaterialDTO(id, category, weight);


                try {
                    boolean isUpdated = woodBO.update(dto);
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

        @FXML
        void btnUsedCleraFieldAction(ActionEvent event) {
                cmbWoodId.setValue(" ");
                txtUsedWeight.setText(" ");
                lblUsedAvl.setText(" ");
        }

        @FXML
        void btnUsedOnAction(ActionEvent event) throws ClassNotFoundException {
            boolean isValid = Valids();
            if (isValid == true) {
                String id = cmbWoodId.getValue();
                double used = Double.parseDouble(txtUsedWeight.getText());

                try {
                    boolean isSaved = woodBO.usedUpdateWood(id, used);
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Wood Category Used Successful").show();
                        initialize();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }

            }
        }

        @FXML
        void cmbWoodOnAction(ActionEvent event) {
            try {
                String id = cmbWoodId.getValue();
                WoodMaterialDTO dto = woodBO.search(id);
                lblUsedAvl.setText(String.valueOf(dto.getWWeight()));
                double available = Double.parseDouble(lblUsedAvl.getText());
                if (available <= 0) {
                    txtUsedWeight.setDisable(true);
                    btnUsed.setDisable(true);
                } else {
                    txtUsedWeight.setDisable(false);
                    btnUsed.setDisable(false);
                }
            }catch (Exception e){
                new RuntimeException(e);
            }

        }

        @FXML
        void txtWeight(ActionEvent event) {

        }

    @FXML
    void btnBakc(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/stock_form.fxml"));
        this.root.getChildren().clear();
        this.root.getChildren().add(anchorPane);

    }
    public void generateWoodId() throws ClassNotFoundException {
        try {
            ResultSet resultSet = fuelBO.generateFuelId();
            boolean isExist = resultSet.next();

            if(isExist){
                String oldBId = resultSet.getString(1);
                oldBId =oldBId.substring(1,oldBId.length());
                int intId =Integer.parseInt(oldBId);
                intId =intId+1;

                if (intId <10){
                    lblGFuelId.setText("B00" +intId);
                } else if (intId <100) {
                    lblGFuelId.setText("B0"+intId);

                }else {
                    lblGFuelId.setText("B"+intId);
                }
            }else {
                lblGFuelId.setText("B001");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private void load() throws ClassNotFoundException {
        ObservableList<String> obWoodList = FXCollections.observableArrayList();

        try {
            List<WoodMaterialDTO> suppList = woodBO.getAll();

            for (WoodMaterialDTO dto : suppList) {
                obWoodList.add(dto.getBarrelId());
            }
            cmbWoodId.setItems(obWoodList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadWood() throws ClassNotFoundException {

        ObservableList<WoodMatirialTM> obList = FXCollections.observableArrayList();

        try {
            List<WoodMaterialDTO> dtoList = woodBO.getAll();

            for (WoodMaterialDTO dto : dtoList) {

                Button btn = new Button("Remove");
                btn.setCursor(Cursor.HAND);

                btn.setOnAction((e) ->{
                    ButtonType yes = new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No",ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION,"Are You Sure To Remove ?",yes,no).showAndWait();
                    if (type.orElse(no)==yes){
                        int selectedIndex = tblWood.getSelectionModel().getSelectedIndex();
                        String id = (String) colCode.getCellData(selectedIndex);

                        try {
                            deletedWId(id);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }

                        obList.remove(selectedIndex);
                        tblWood.refresh();
                    }

                });


                obList.add(
                        new WoodMatirialTM(
                                dto.getBarrelId(),
                                dto.getWCategory(),
                                dto.getWWeight(),
                                btn
                        )
                );
            }

            tblWood.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void deletedWId(String id) throws ClassNotFoundException {
        try {
            boolean isDeleted = woodBO.delete(id);
            if (isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION,"Wood Stock Removed !").show();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("barrelId"));
        colWCategory.setCellValueFactory(new PropertyValueFactory<>("wCategory"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("wWeight"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    public void getAvl() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = woodBO.getAllAvalable();

        int count = 0;
        double weight = 0;
        if (resultSet.next()){
            count=resultSet.getInt(1);
            weight=resultSet.getDouble(2);
        }
        lblWoodCount.setText(String.valueOf(count));
        lblAvailableWood.setText(String.valueOf(weight));
    }

    private boolean Valid() {

        String category = txtWCategory.getText();
        String weight = txtWeight.getText();

        boolean isCate = Pattern.matches("^([\\w]+)([\\w\\s]+)$", category);
        boolean isWeight =  Pattern.matches("^\\S[0-9]{0,}", weight);

        if (!isCate) {
            new Alert(Alert.AlertType.ERROR, "Invalid Category").show();
            return false;
        }
        if (!isWeight) {
            new Alert(Alert.AlertType.ERROR, "Invalid Weight").show();
            return false;
        }
        return true;

    }
    private boolean Valids() {
        String useweight = txtUsedWeight.getText();
        boolean isweight =  Pattern.matches("^\\S[0-9]{0,}", useweight);

        if (!isweight) {
            new Alert(Alert.AlertType.ERROR, "Invalid Weight").show();
            return false;
        }
        return true;

    }
    @FXML
    void txtSearchIdOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = txtsearchId.getText();

        try {
            WoodMaterialDTO dto = woodBO.search(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Wood Category not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void fillFields(WoodMaterialDTO dto) {
        txtWCategory.setText(String.valueOf(dto.getWCategory()));
        txtWeight.setText(String.valueOf(dto.getWWeight()));
        lblRegId.setText(dto.getBarrelId());
    }
}




