package lk.captain.Controller;

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
import lk.captain.dto.FuelMaterialDTO;
import lk.captain.dto.WoodMaterialDTO;
import lk.captain.dto.tm.WoodMatirialTM;
import lk.captain.model.FuelManageModel;
import lk.captain.model.WoodManageModel;

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

    WoodManageModel woodManageModel = new WoodManageModel();
    FuelManageModel fuelManageModel = new FuelManageModel();

    public void initialize() throws SQLException {generateWoodId();
        load();loadWood();setCellValueFactory();getAvl();}

        @FXML
        void btnClearFieldAction(ActionEvent event) {
            txtWCategory.setText(" ");
            txtWeight.setText(" ");
            lblRegId.setText("");
        }

        @FXML
        void btnSaveOnAction(ActionEvent event) {
            boolean isValid = Valid();
            if (isValid == true) {
                String id = lblGFuelId.getText();
                String category = txtWCategory.getText();
                double weight = Double.parseDouble(txtWeight.getText());

                try {
                    boolean isSaved = woodManageModel.woodSave(new FuelMaterialDTO(id, category, weight));
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
        void btnUpdateOnAction(ActionEvent event) {
            boolean isValid = Valid();
            if (isValid == true) {
                String category = txtWCategory.getText();
                double weight = Double.parseDouble(txtWeight.getText());
                String id = lblRegId.getText();


                var dto = new WoodMaterialDTO(id, category, weight);


                try {
                    boolean isUpdated = woodManageModel.updateWood(dto);
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
        void btnUsedOnAction(ActionEvent event) {
            boolean isValid = Valids();
            if (isValid == true) {
                String id = cmbWoodId.getValue();
                double used = Double.parseDouble(txtUsedWeight.getText());

                try {
                    boolean isSaved = woodManageModel.usedUpdateWood(id, used);
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
                WoodMaterialDTO dto = woodManageModel.searchWoodId(id);
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
    public void generateWoodId() {
        try {
            String fuelId = fuelManageModel.generateFuelId();
            lblGFuelId.setText(fuelId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void load() {
        ObservableList<String> obWoodList = FXCollections.observableArrayList();

        try {
            List<WoodMaterialDTO> suppList = woodManageModel.getAllWoodId();

            for (WoodMaterialDTO dto : suppList) {
                obWoodList.add(dto.getBarrelId());
            }
            cmbWoodId.setItems(obWoodList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadWood() {

        ObservableList<WoodMatirialTM> obList = FXCollections.observableArrayList();

        try {
            List<WoodMaterialDTO> dtoList = woodManageModel.getAllWoodId();

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

                        deletedWId(id);

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
    private void deletedWId(String id){
        try {
            boolean isDeleted = woodManageModel.deletedWIds(id);
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

    public void getAvl() throws SQLException {
        ResultSet resultSet = woodManageModel.getAllAvalable();

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
    void txtSearchIdOnAction(ActionEvent event) {
        String id = txtsearchId.getText();

        try {
            WoodMaterialDTO dto = woodManageModel.searchWoodId(id);

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




