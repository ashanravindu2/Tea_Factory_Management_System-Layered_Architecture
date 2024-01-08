package lk.captain.Controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.captain.bo.BOFactory;
import lk.captain.bo.custom.TeaCollectorBO;
import lk.captain.dto.*;
import lk.captain.dto.tm.TeaLeafEntryTM;
import lk.captain.dto.tm.WorkerManageTM;
import lk.captain.model.*;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.lang.Double.parseDouble;

public class TeaLeafEntryController {

    @FXML
    private JFXComboBox<String> cmbSuppId;

    @FXML
    private JFXComboBox<String> cmbTeaColecId;

    @FXML
    private Label lblNetWeight;


    @FXML
    private DatePicker txtSearchDate;


    @FXML
    private Label lblSuppName;

    @FXML
    private Label lblTeaColecName;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtGrossWeight;

    @FXML
    private TextField txtWater;

    @FXML
    private TextField txtsearchSuppId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colGros;

    @FXML
    private TableColumn<?, ?> colNetWeight;

    @FXML
    private TableColumn<?, ?> colSupp;

    @FXML
    private TableColumn<?, ?> colWaterCon;

    @FXML
    private TableColumn<?, ?> colRemove;

    @FXML
    private TableView<TeaLeafEntryTM> tblTeaEntry;

    @FXML
    private Label txtwaterDetail;
    TeaCollectorBO teaCollectorBO = (TeaCollectorBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.TEACOLLECTOR);
    private TeaLeafModel teaLeafModel = new TeaLeafModel();
    private SupplierManageModel suppliermanageModel =new SupplierManageModel();


    public void initialize(){
        txtWater.setText("0");
        try {
            load();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        loadAllTeaEntry();
        setCellValueFactory();
    }
    public void waterContentDetailAction(javafx.scene.input.MouseEvent event) {
        txtwaterDetail.setVisible(true);
    }
    public void NowaterContentDetailAction(MouseEvent event) {
        txtwaterDetail.setVisible(false);
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }
    public void btnNotWaterSetNetWeightAction(KeyEvent keyEvent) {
        double gross = Double.parseDouble(txtGrossWeight.getText());
        lblNetWeight.setText(String.valueOf(gross));
    }
    @FXML
    void btnReducedWeightAction(KeyEvent event) {
        try {
            double grossweight = parseDouble(txtGrossWeight.getText());
            String waterContent = txtWater.getText();
            double x = Double.parseDouble(waterContent);
            lblNetWeight.setText(String.valueOf(grossweight-x));

        }catch (Exception ignored){}
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean isValid = Valid();
        if (isValid == true) {
            String suppId = cmbSuppId.getValue();
            String teacolecId = cmbTeaColecId.getValue();
            double grosWeight = parseDouble(txtGrossWeight.getText());
            double wateron = Double.parseDouble(txtWater.getText());
            String date = String.valueOf(txtDate.getValue());
            double netWeight = parseDouble(lblNetWeight.getText());


            try {
                boolean isSaved = teaLeafModel.tealeafManage(new TeaLeafEntryDTO(suppId, teacolecId, grosWeight, wateron, netWeight, date));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, suppId + " is " + "Tea Leaf added Successfully").show();
                    setCellValueFactory();
                    loadAllTeaEntry();
                    //       genersteWorkerId();
                    return;
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }
    }
/*private boolean validText(String text){
        for (int i = 0; i<text.length();i++){
            if (text.charAt(i) !=' '){
                return true;
                }else {
                return false;
            }
            }
            return false;
        }*/


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        boolean isValid = Valid();
        if (isValid == true) {
            String suppId = cmbSuppId.getValue();
            String teacolecId = cmbTeaColecId.getValue();
            double grosWeight = parseDouble(txtGrossWeight.getText());
            double wateron = Double.parseDouble(txtWater.getText());
            String date = String.valueOf(txtDate.getValue());
            double netWeight = parseDouble(lblNetWeight.getText());

            var dto = new TeaLeafEntryDTO(suppId, teacolecId, grosWeight, wateron, netWeight, date);

            try {
                boolean isUpdated = teaLeafModel.updateTeaLeaf(dto);
                System.out.println(isUpdated);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Tea Leaf Entry Updated Success!").show();
                    initialize();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }
    }
    @FXML
    void cmbSuppIdOnAction(ActionEvent event) throws SQLException {
        String id = cmbSuppId.getValue();
        SupplierManageDTO dto = suppliermanageModel.searchSupplierId(id);

        lblSuppName.setText(dto.getSuppName());

    }

    @FXML
    void cmbTeaColecIdOnAction(ActionEvent event) throws SQLException {
        String id =cmbTeaColecId.getValue();
        TeaCollctorDTO dto = null;
        try {
            dto = teaCollectorBO.searchTeaColecId(id);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        lblTeaColecName.setText(dto.getName());

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String date = String.valueOf(txtSearchDate.getValue());
        String id = txtsearchSuppId.getText();

        var teaLeafModel = new TeaLeafModel();
        try {
            TeaLeafEntryDTO dto = teaLeafModel.seacrhTeaLeaf(id,date);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Tea Entry not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void fillFields(TeaLeafEntryDTO dto) {
        txtWater.setText(String.valueOf(dto.getWaterCon()));
        txtGrossWeight.setText(String.valueOf(dto.getGrosWeight()));
        lblNetWeight.setText(String.valueOf(dto.getNetWeight()));
        txtDate.setValue(LocalDate.parse(dto.getDate()));
        cmbTeaColecId.setValue(dto.getTeaColecId());
        cmbSuppId.setValue(dto.getSupplierId());
    }


    private void load() throws ClassNotFoundException {
        ObservableList<String> obSuppList = FXCollections.observableArrayList();
        ObservableList<String> obColecList = FXCollections.observableArrayList();
        try {
            List<SupplierManageDTO> suppList = suppliermanageModel.getAllTeaSupp();
            List<TeaCollctorDTO> ColecList = teaCollectorBO.getAll();

            for (SupplierManageDTO dto : suppList) {
                obSuppList.add(dto.getSupplierId());
            }

            for (TeaCollctorDTO dto : ColecList ){
                obColecList.add(dto.getTeaColecId());
            }
            cmbTeaColecId.setItems(obColecList);
            cmbSuppId.setItems(obSuppList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private void loadAllTeaEntry() {
        var teaLeafModel  = new TeaLeafModel();
        try {
        ObservableList<TeaLeafEntryTM> obList = FXCollections.observableArrayList();


            List<TeaLeafEntryDTO> dtoList = teaLeafModel.getAllDetail();

            for(TeaLeafEntryDTO dto : dtoList) {
                Button btn = new Button("Remove");
                btn.setCursor(Cursor.HAND);

                btn.setOnAction((e) ->{
                    ButtonType yes = new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No",ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION,"Are You Sure To Remove ?",yes,no).showAndWait();
                    if (type.orElse(no)==yes){
                        int selectedIndex = tblTeaEntry.getSelectionModel().getSelectedIndex();
                        String supId = (String) colSupp.getCellData(selectedIndex);
                        String date = (String) colDate.getCellData(selectedIndex);

                        deleteteaId(supId,date);

                        obList.remove(selectedIndex);
                        tblTeaEntry.refresh();
                    }

                });

                obList.add(
                        new TeaLeafEntryTM(
                                dto.getSupplierId(),
                             //  dto.getTeaColecId(),
                                dto.getGrosWeight(),
                                dto.getWaterCon(),
                                dto.getNetWeight(),
                                dto.getDate(),
                                btn

                        )
                );
            }

            tblTeaEntry.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setCellValueFactory() {
        colSupp.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colGros.setCellValueFactory(new PropertyValueFactory<>("grosWeight"));
        colWaterCon.setCellValueFactory(new PropertyValueFactory<>("waterCon"));
        colNetWeight.setCellValueFactory(new PropertyValueFactory<>("netWeight"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }
    private void deleteteaId(String suppId,String date){
        try {
            boolean isDeleted = teaLeafModel.deleteTeaLeaf(suppId,date);
            if (isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION,"Tea Leaf Stock Removed !").show();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    private boolean Valid() {
        String grossWeight = txtGrossWeight.getText();
        String waterCon = txtWater.getText();

        boolean isgrossWeight = Pattern.matches("(^A-Z^)|[0-9]\\S[^A-Z,a-z$]{0,}|[^A-Z,a-z$]", grossWeight);
        boolean iswaterCon = Pattern.matches("(^A-Z^)|[0-9]\\S[^A-Z,a-z$]{0,}|[^A-Z,a-z$]", waterCon);


        if (!isgrossWeight) {
            new Alert(Alert.AlertType.ERROR, "Invalid GrossWeight ").show();
            return false;
        }
        if (!iswaterCon) {
            new Alert(Alert.AlertType.ERROR, "Invalid Water Content").show();
            return false;
        }
        return true;

    }

}
