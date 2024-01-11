package lk.captain.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.captain.bo.BOFactory;
import lk.captain.bo.custom.TeaLeafEntryBO;
import lk.captain.bo.custom.TeaPowderBO;
import lk.captain.dto.TeaPowderDTO;
import lk.captain.dto.TeaPowderGetDTO;
import lk.captain.view.tm.CartTM;
import lk.captain.view.tm.TeaPowderTM;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class TeaPowderAvailableController {

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colFree;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private JFXButton savebtn;

    @FXML
    private Label warningUnsuff;

    @FXML
    private TableColumn<?, ?> colPowderStock;

    @FXML
    private TableColumn<?, ?> colReAction;

    @FXML
    private TableColumn<?, ?> colSdate;

    @FXML
    private TableView<TeaPowderTM> tblteaPowder;

    @FXML
    private JFXButton updatebtn;

    @FXML
    private TableColumn<?, ?> colUsed;

    @FXML
    private DatePicker datePickerId;

    @FXML
    private Label lblFreeTea;

    @FXML
    private Label lblTeaNet;

    @FXML
    private Label lblUsedTea;

    @FXML
    private TableView<CartTM> tblTeaPowder;

    @FXML
    private Label sayEnterPress;
    @FXML
    private JFXButton addcartbtn;

    private final ObservableList<CartTM> obList = FXCollections.observableArrayList();

    TeaLeafEntryBO teaLeafEntryBO = (TeaLeafEntryBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.TEALEAFENTRY);
    TeaPowderBO teaPowderBO = (TeaPowderBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.TEAPOWDER);
    @FXML
    private TextField txtTeaPowder;

    public void initialize() throws ClassNotFoundException {
        setCellValueFactory2();
        setCellValueFactory();
        loadAllDetail();
    }

    @FXML
    void btnaddAction(ActionEvent event) throws Exception {
        boolean isValid = Valid();
        if (isValid == true) {
            String date = String.valueOf(datePickerId.getValue());
            double used = Double.parseDouble(txtTeaPowder.getText());

            Button btn = new Button("remove");
            btn.setCursor(Cursor.HAND);

            btn.setOnAction((e) -> {
                ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                if (type.orElse(no) == yes) {
                    int index = tblTeaPowder.getSelectionModel().getSelectedIndex();
                    obList.remove(index);
                    tblTeaPowder.refresh();

                }
            });

            for (int i = 0; i < tblTeaPowder.getItems().size(); i++) {
                if (date.equals(colDate.getCellData(i))) {
                    double use = (double) colUsed.getCellData(i);
                    used = use + used;
                    obList.get(i).setUseTea(used);
                    tblTeaPowder.refresh();

                    return;
                }
            }

            obList.add(new CartTM(
                    date,
                    used,
                    btn
            ));

            tblTeaPowder.setItems(obList);
            txtTeaPowder.clear();

        }
    }

    public void clearField(){
        txtTeaPowder.setText(" ");
    }

    public void timepickkerAction(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        String date = String.valueOf(datePickerId.getValue());
        double netTotal = teaLeafEntryBO.getNetTotal(date);
        double dbstockTeapowder = teaPowderBO.searchDate(date);
        lblTeaNet.setText(String.valueOf(netTotal));
            if(dbstockTeapowder>=netTotal){
                txtTeaPowder.setDisable(true);
                updatebtn.setDisable(true);
                savebtn.setDisable(true);
                lblFreeTea.setVisible(false);
                warningUnsuff.setVisible(true);

            } else if (dbstockTeapowder<=netTotal) {
                updatebtn.setDisable(false);
                txtTeaPowder.setDisable(false);
                savebtn.setDisable(false);
                warningUnsuff.setVisible(false);
                lblFreeTea.setVisible(true);
            }
            lblFreeTea.setText(String.valueOf(netTotal - dbstockTeapowder));
    }

    public void btnMouseEnterAction(MouseEvent event) {
        sayEnterPress.setVisible(true);
    }

    public void btnMouseOutAction(MouseEvent event) {
        sayEnterPress.setVisible(false);
    }

    @FXML
    void btnEnterAction(KeyEvent event) throws Exception {
        boolean isValid = Valid();
        if (isValid == true) {
            String date = String.valueOf(datePickerId.getValue());
            double net = Double.parseDouble(lblTeaNet.getText());
            double tewpowder = Double.parseDouble(txtTeaPowder.getText());


            if (tewpowder > net) {
                addcartbtn.setDisable(true);
            } else {
                addcartbtn.setDisable(false);
            }
            for (int i = 0; i < tblTeaPowder.getItems().size(); i++) {
                if (date.equals(colDate.getCellData(i))) {

                    double cc = (double) colUsed.getCellData(i);
                    if (cc + tewpowder > net) {
                        addcartbtn.setDisable(true);
                    } else {
                        addcartbtn.setDisable(false);
                    }
                    return;
                }

            }
        }

    }

    private void setCellValueFactory() {
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colUsed.setCellValueFactory(new PropertyValueFactory<>("useTea"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }
    private void setCellValueFactory2() {
        colSdate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPowderStock.setCellValueFactory(new PropertyValueFactory<>("useTea"));
        colReAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }


    @FXML
    void btnPlaceAction(ActionEvent event) throws ClassNotFoundException {

        List<CartTM> tmList = new ArrayList<>();

        for (CartTM cartTm : obList) {
            tmList.add(cartTm);
        }
        var teaPowderDTO =new TeaPowderDTO(
                tmList
        );

        try {
            boolean isSuccess = teaPowderBO.placeOrder(teaPowderDTO);
            if(isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "Stock completed!").show();
                clearField();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void btnUpdateAction(ActionEvent event) throws ClassNotFoundException {

            List<CartTM> tmList = new ArrayList<>();

            for (CartTM cartTm : obList) {
                tmList.add(cartTm);
            }
            var teaPowderDTO = new TeaPowderDTO(
                    tmList
            );


            try {
                boolean isSuccess = teaPowderBO.updateTeaStock(teaPowderDTO);
                if (isSuccess) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Stock Updated!").show();
                    clearField();
                    initialize();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    public void loadAllDetail() throws ClassNotFoundException {
        try {
            List<TeaPowderGetDTO>dtoList = teaPowderBO.getAll();

            ObservableList<TeaPowderTM> observableList = FXCollections.observableArrayList();

            for (TeaPowderGetDTO dto: dtoList){
                Button btn = new Button("Remove");
                btn.setCursor(Cursor.HAND);

                btn.setOnAction((e) ->{
                    ButtonType yes = new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No",ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION,"Are You Sure To Remove ?",yes,no).showAndWait();
                    if (type.orElse(no)==yes){
                        int selectedIndex = tblteaPowder.getSelectionModel().getSelectedIndex();
                        String dates = (String) colSdate.getCellData(selectedIndex);

                        try {
                            deleteTeaStock(dates);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }

                        observableList.remove(selectedIndex);
                        tblteaPowder.refresh();
                    }

                });
                var tList = new TeaPowderTM(
                    dto.getDate(),
                        dto.getUseTea(),
                        btn
                );
                observableList.add(tList);
            }
            tblteaPowder.setItems(observableList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    private void deleteTeaStock(String date) throws ClassNotFoundException {
        try {
            boolean isDeleted = teaPowderBO.delete(date);
            if (isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION," Stock Deleted !").show();

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    private boolean Valid() {

        String weight = txtTeaPowder.getText();

        boolean isWeight =  Pattern.matches("(^A-Z^)|[0-9]\\S[^A-Z,a-z$]{0,}|[^A-Z,a-z$]", weight);

        if (!isWeight) {
            new Alert(Alert.AlertType.ERROR, "Invalid Tea Powder Weight").show();
            return false;
        }
        return true;

    }

}
