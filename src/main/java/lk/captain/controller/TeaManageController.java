package lk.captain.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.captain.bo.BOFactory;
import lk.captain.bo.custom.StoreDetailBO;
import lk.captain.bo.custom.TeaTypeBO;
import lk.captain.bo.custom.WareHouseBO;
import lk.captain.dto.StoreDetailsDTO;
import lk.captain.dto.TeaTypeDTO;
import lk.captain.view.tm.StoreDetailsTM;




import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class TeaManageController {

    public TableColumn colTeaName;
    @FXML
    private JFXComboBox<String> cmbTeaCode;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colQth;

    @FXML
    private TableColumn<?, ?> colTeaType;

    @FXML
    private TableColumn<?, ?> colAction;
    
    @FXML
    private Label lblteaNameId;

    @FXML
    private DatePicker datapickkerId;

    @FXML
    private Label lblteaPowderAvailable;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<StoreDetailsTM> tblTeaStock;
    

    @FXML
    private TextField txtsearchId;
    @FXML
    private TextField txtStock;

    @FXML
    private JFXButton savebtnId;



    TeaTypeBO teaTypeBO = (TeaTypeBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.TEATYPE);
    WareHouseBO wareHouseBO = (WareHouseBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.WAREHOUSE);
    StoreDetailBO storeDetailBO = (StoreDetailBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.STOREDETAIL);

    @FXML
    void btnBakc(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/stock_form.fxml"));
        this.root.getChildren().clear();
        this.root.getChildren().add(anchorPane);
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        double netTeaPowder = wareHouseBO.getCount();
        lblteaPowderAvailable.setText(String.valueOf(netTeaPowder));
        load();
        setCellValueFactory();
        loadAllDetail();
    }

    public void btnStockCheckAction(KeyEvent keyEvent) {
        savebtnId.setDisable(false);
        double chec = Double.parseDouble(lblteaPowderAvailable.getText());
        double stock = Double.parseDouble(txtStock.getText());
        if(stock>=chec){
            savebtnId.setDisable(true);
        }
    }


    @FXML
    void btnSaveAction(ActionEvent event) throws ClassNotFoundException {
        boolean isValid = Valid();
        if (isValid == true) {
            String wareHouseId = "W001";
            String teaTypeId = cmbTeaCode.getValue();
            String date = String.valueOf(LocalDate.now());
            double quantity = Double.parseDouble(txtStock.getText());

            try {
                boolean isSaved = storeDetailBO.save(new StoreDetailsDTO(wareHouseId, teaTypeId, date, quantity));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "TeaType Stock added Successfully").show();
                    initialize();
                    loadAllDetail();
                    setCellValueFactory();
                    return;
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    }
    public void loadAllDetail() throws ClassNotFoundException {
        try {
            List<StoreDetailsDTO>dtoList = storeDetailBO.getAll();

            ObservableList<StoreDetailsTM> observableList = FXCollections.observableArrayList();

            for (StoreDetailsDTO dto: dtoList){
                Button btn = new Button("Remove");
                btn.setCursor(Cursor.HAND);

                btn.setOnAction((e) ->{
                    ButtonType yes = new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No",ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION,"Are You Sure To Remove ?",yes,no).showAndWait();
                    if (type.orElse(no)==yes){
                        int selectedIndex = tblTeaStock.getSelectionModel().getSelectedIndex();
                        String teaId = (String) colTeaType.getCellData(selectedIndex);
                        String teaTName = lblteaNameId.getText();

                        try {
                            deleteTea(teaId,teaTName);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }

                        observableList.remove(selectedIndex);
                        tblTeaStock.refresh();
                    }

                });
                var tList = new StoreDetailsTM(
                        dto.getWareHouseId(),
                        dto.getTeaTypeId(),
                        dto.getDate(),
                        dto.getQuantity(),
                        btn
                );
                observableList.add(tList);
            }
            tblTeaStock.setItems(observableList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    private void deleteTea(String teaTypeId,String teaTName) throws ClassNotFoundException {
        try {
            boolean isDeleted = storeDetailBO.delete(teaTypeId);
            if (isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION,teaTName+" Stock Deleted !").show();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }


    @FXML
    void cmbTeaTypAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String teacode = cmbTeaCode.getValue();
        TeaTypeDTO dto = teaTypeBO.serchOnTeaType(teacode);
        lblteaNameId.setText(dto.getTeaTypeName());

    }


    private void load() throws ClassNotFoundException {
        ObservableList<String> obTeaTypeList = FXCollections.observableArrayList();

        try {
            List<TeaTypeDTO> teaTypeDTOList = teaTypeBO.loadAllTeaTypes();

            for (TeaTypeDTO dto : teaTypeDTOList){
                obTeaTypeList.add(dto.getTeaTypeId());

            }
            cmbTeaCode.setItems(obTeaTypeList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setCellValueFactory() {
        colTeaType.setCellValueFactory(new PropertyValueFactory<>("teaTypeId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colQth.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    @FXML
    void txtSerchOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = txtsearchId.getText();

        try {
            StoreDetailsDTO dto = storeDetailBO.search(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Tea Type not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    private void fillFields(StoreDetailsDTO dto) {
        cmbTeaCode.setValue(dto.getTeaTypeId());
        txtStock.setText(String.valueOf(dto.getQuantity()));
    }

    @FXML
    void btnTeaStockUpdateOnAction(ActionEvent event) {
        /*
        double quantity = Double.parseDouble(txtStock.getText());
        String wareHouseId = "W001";
        String teaTypeId = cmbTeaCode.getValue();
        String teaName = lblteaNameId.getText();
        String date = String.valueOf(datapickkerId.getValue());


        var dto = new StoreDetailsDTO(wareHouseId,teaTypeId,date,quantity);

        try {
            boolean isUpdated = storeDetailsModel.updateTeaType(dto);
            System.out.println(isUpdated);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,teaName+" Stock Updated Success!").show();
                initialize();
                loadAllDetail();
                setCellValueFactory();
                setCellValueFactory();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }*/

    }

    public void btnClear(ActionEvent actionEvent) {

    }
    private boolean Valid() {

        String stock = txtStock.getText();

        boolean isStock =  Pattern.matches("(^A-Z^)|[0-9]\\S[^A-Z,a-z$]{0,}|[^A-Z,a-z$]", stock);

        if (!isStock) {
            new Alert(Alert.AlertType.ERROR, "Invalid Stock Count").show();
            return false;
        }
        return true;
    }


}
