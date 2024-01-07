package lk.captain.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.captain.dto.TeaTypeDTO;

import lk.captain.dto.tm.TeaTypeTM;
import lk.captain.model.TeaTypeModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class TeaTypeController {

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDes;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPer;

    @FXML
    private TableView<TeaTypeTM> tblTeaType;

    @FXML
    private TextField txtTeaId;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtdes;

    @FXML
    private TextField txtsearchId;

    @FXML
    private TextField txtteaName;
    private TeaTypeModel teaTypemodel = new TeaTypeModel();

    public void initialize(){
        loadAllTeaType();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("teaTypeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("teaTypeName"));
        colDes.setCellValueFactory(new PropertyValueFactory<>("teaTypeDesc"));
        colPer.setCellValueFactory(new PropertyValueFactory<>("teaPerPrice"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    @FXML
    void btnTeTypeSave(ActionEvent event) {
        boolean isValid = Valid();
        if (isValid == true) {
            String teaTypeId = txtTeaId.getText();
            String teaTypeName = txtteaName.getText();
            String teaTypeDesc = txtdes.getText();
            double teaPerPrice = Double.parseDouble(txtPrice.getText());


            try {
                boolean isSaved = teaTypemodel.teatypeSave(new TeaTypeDTO(teaTypeId, teaTypeName, teaTypeDesc, teaPerPrice));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Tea Type added Successfully").show();
                    loadAllTeaType();
                    setCellValueFactory();
                    return;
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }
    }

    public void loadAllTeaType(){
        try {
            List<TeaTypeDTO>dtoList = teaTypemodel.loadAllTeaTypes();

            ObservableList<TeaTypeTM> observableList = FXCollections.observableArrayList();

            for (TeaTypeDTO dto: dtoList){
                Button btn = new Button("Remove");
                btn.setCursor(Cursor.HAND);

                btn.setOnAction((e) ->{
                    ButtonType yes = new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No",ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION,"Are You Sure To Remove ?",yes,no).showAndWait();
                    if (type.orElse(no)==yes){
                        int selectedIndex = tblTeaType.getSelectionModel().getSelectedIndex();
                        String teaId = (String) colId.getCellData(selectedIndex);

                        deleteteaId(teaId);

                        observableList.remove(selectedIndex);
                        tblTeaType.refresh();
                    }

                });
                var tm = new TeaTypeTM(
                        dto.getTeaTypeId(),
                        dto.getTeaTypeName(),
                        dto.getTeaTypeDesc(),
                        dto.getTeaPerPrice(),
                        btn


                );
              observableList.add(tm);
            }
            tblTeaType.setItems(observableList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void deleteteaId(String teaTypeId){
        try {
            boolean isDeleted = teaTypemodel.deleteTeaType(teaTypeId);
            if (isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION,"Tea Type Deleted !").show();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void txtSerchOnAction(ActionEvent event) {
        String id = txtsearchId.getText();

        var teaTypemodel = new TeaTypeModel();
        try {
            TeaTypeDTO dto = teaTypemodel.serchOnTeaType(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Tea Type not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void fillFields(TeaTypeDTO dto) {
        txtTeaId.setText(dto.getTeaTypeId());
        txtdes.setText(dto.getTeaTypeDesc());
        txtteaName.setText(dto.getTeaTypeName());
        txtPrice.setText(String.valueOf(dto.getTeaPerPrice()));
    }

    public void btnClearField(ActionEvent actionEvent) {
        txtTeaId.setText("");
        txtdes.setText("");
        txtteaName.setText("");
        txtPrice.setText("");
    }


    @FXML
    void btnTeaTypeUpdateOnAction(ActionEvent event) {
        boolean isValid = Valid();
        if (isValid == true) {
            String teaTypeId = txtTeaId.getText();
            String teaTypeName = txtteaName.getText();
            String teaTypeDesc = txtdes.getText();
            double teaPerPrice = Double.parseDouble(txtPrice.getText());

            var dto = new TeaTypeDTO(teaTypeId, teaTypeName, teaTypeDesc, teaPerPrice);

            var model = new TeaTypeModel();
            try {
                boolean isUpdated = model.updateTeaType(dto);
                System.out.println(isUpdated);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Tea Type Updated Success!").show();
                    loadAllTeaType();
                    setCellValueFactory();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    }

    private boolean Valid() {

        String desc = txtdes.getText();
        String teaName = txtteaName.getText();
        String price = txtPrice.getText();


        boolean isDesc = Pattern.matches("^([\\w]+)([\\w\\s]+)$", desc);
        boolean isName = Pattern.matches("^([\\w]+)([\\w\\s]+)$", teaName);
        boolean isPrice =  Pattern.matches("(^A-Z^)|[0-9]\\S[^A-Z,a-z$]{0,}|[^A-Z,a-z$]", price);

        if (!isDesc) {
            new Alert(Alert.AlertType.ERROR, "Invalid Description").show();
            return false;
        }
        if (!isName) {
            new Alert(Alert.AlertType.ERROR, "Insert First Letter for Capital").show();
            return false;
        }
        if (!isPrice) {
            new Alert(Alert.AlertType.ERROR, "Only Digit Numbers").show();
            return false;
        }
        return true;

    }

}
