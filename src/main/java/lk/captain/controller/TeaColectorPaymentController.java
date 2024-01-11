package lk.captain.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.captain.bo.BOFactory;
import lk.captain.bo.custom.*;
import lk.captain.db.DbConnection;
import lk.captain.dto.PaymentDto;
import lk.captain.dto.PaymentWorkerDTO;
import lk.captain.dto.TeaCollctorDTO;
import lk.captain.view.tm.PaymentWorkerTM;
import lk.captain.util.util.GenerateTransactionCode;


import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class TeaColectorPaymentController {

    @FXML
    private JFXComboBox<String> cmbId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colExtraSlry;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colTrasac;

    @FXML
    private TableColumn<?, ?> colWorkCount;

    @FXML
    private DatePicker datepicker1;

    @FXML
    private DatePicker datepicker2;

    @FXML
    private Label lblName;

    @FXML
    private TextField txtserchTransaction;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblTrasacId;

    @FXML
    private Label lblWorkCount;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<PaymentWorkerTM> tblPayment;

    @FXML
    private TextField txtDailySlary;

    @FXML
    private TextField txtExtraSalry;
    TeaCollectorBO teaCollectorBO = (TeaCollectorBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.TEACOLLECTOR);
    AttendenceBO attendenceBO = (AttendenceBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.ATTENDENCE);
    AddCustomerBO addCustomerBO =(AddCustomerBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.ADDCUSTOMER);
    PaymentBO paymentbo = (PaymentBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.PAYMENT);
    QueryBO queryBO = (QueryBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.QUERY);


    public void initialize() throws SQLException, ClassNotFoundException {load();generateTransacId();AllPaymentDetails();setCellValueFactory();}

    @FXML
    void btnPayOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = cmbId.getValue();
        String tranCode = lblTrasacId.getText();
        String date1 = String.valueOf(datepicker1.getValue());
        String date2 = String.valueOf(datepicker2.getValue());
        int workCount = Integer.parseInt(lblWorkCount.getText());
        double extraPay = Double.parseDouble(txtExtraSalry.getText());
        double totals = Double.parseDouble(lblTotal.getText());
        double totalPay = totals+extraPay;
        String date = String.valueOf(LocalDate.now());

        try {
            boolean isSaved = paymentbo.addPayement(new PaymentDto(tranCode,id,date1,date2,date,totalPay,0,0,workCount,extraPay));
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, lblName.getText() +" is " + "Payment Successfully").show();
                supplierBill();
                initialize();
                return;
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void cmbIdOnAction(ActionEvent event) throws SQLException {
        String Id = cmbId.getValue();
        TeaCollctorDTO dto = null;
        try {
            dto = teaCollectorBO.searchTeaColecId(Id);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        lblName.setText(dto.getName());
    }

    @FXML
    void datepicker2OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String date1 = String.valueOf(datepicker1.getValue());
        String date2 = String.valueOf(datepicker2.getValue());
        String colecId = cmbId.getValue();

        int count = attendenceBO.issearchAttendenceCount(colecId,date1,date2);
        lblWorkCount.setText(String.valueOf(count));
    }

    @FXML
    void btntxtserchTransactionActioin(ActionEvent event) throws ClassNotFoundException {
        String code = txtserchTransaction.getText();

        try {

            ResultSet resultSet = queryBO.searchTransacCode(code);

            if (resultSet != null) {
                fillFields(resultSet);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Transaction is not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void fillFields(ResultSet resultSet) throws SQLException {

        while (resultSet.next()) {
            lblName.setText(resultSet.getString("Name"));
            cmbId.setValue(resultSet.getString("EmpId"));
            datepicker1.setValue(LocalDate.parse(resultSet.getString("startTime")));
            datepicker2.setValue(LocalDate.parse(resultSet.getString("endDate")));
            lblWorkCount.setText(resultSet.getString("workCount"));
            txtExtraSalry.setText(resultSet.getString("extraSalary"));
            lblTotal.setText(resultSet.getString("netTotal"));
        }

    }

    @FXML
    void txtDailySalryOnAction(ActionEvent event) {
        double salarydaily = Double.parseDouble(txtDailySlary.getText());
        double workCount = Double.parseDouble(lblWorkCount.getText());
        double total = workCount*salarydaily;
        lblTotal.setText(String.valueOf(total));

    }
    private void AllPaymentDetails() throws SQLException, ClassNotFoundException {
        var getColecSlary  = new GenerateTransactionCode();

        ObservableList<PaymentWorkerTM> obList = FXCollections.observableArrayList();

        try {
            List<PaymentWorkerDTO> dtoList = getColecSlary.getAllDetailTeaColec();

            for(PaymentWorkerDTO dto : dtoList) {

                Button btn = new Button("Remove");
                btn.setCursor(Cursor.HAND);

                btn.setOnAction((e) ->{
                    ButtonType yes = new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No",ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION,"Are You Sure To Remove ?",yes,no).showAndWait();
                    if (type.orElse(no)==yes){
                        int selectedIndex = tblPayment.getSelectionModel().getSelectedIndex();
                        String transacCode = (String) colTrasac.getCellData(selectedIndex);

                        try {
                            deleteteaId(transacCode);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }

                        obList.remove(selectedIndex);
                        tblPayment.refresh();
                    }
                });
                obList.add(
                        new PaymentWorkerTM(
                                dto.getTransctionId(),
                                dto.getTransactionDate(),
                                dto.getWorkCount(),
                                dto.getWorkName(),
                                dto.getExtraSalary(),
                                dto.getNetTotal(),
                                btn
                        )
                );
            }

            tblPayment.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteteaId(String transacCode) throws ClassNotFoundException {
        try {
            boolean isDeleted =paymentbo.deleteCode(transacCode);
            if (isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION,"Payment Detail Deleted !").show();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void load() {
        ObservableList<String> obTeaColecList = FXCollections.observableArrayList();

        try {
            List<TeaCollctorDTO> teaCollctorDTOS = null;
            try {
                teaCollctorDTOS = teaCollectorBO.getAll();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            for (TeaCollctorDTO dto : teaCollctorDTOS) {
                obTeaColecList.add(dto.getTeaColecId());
            }

            cmbId.setItems(obTeaColecList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateTransacId() throws ClassNotFoundException {
        try {
            String tranId = GenerateTransactionCode.generateTransacId();
            lblTrasacId.setText(tranId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setCellValueFactory() {
        colTrasac.setCellValueFactory(new PropertyValueFactory<>("transctionId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        colWorkCount.setCellValueFactory(new PropertyValueFactory<>("workCount"));
        colName.setCellValueFactory(new PropertyValueFactory<>("workName"));
        colExtraSlry.setCellValueFactory(new PropertyValueFactory<>("extraSalary"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("netTotal"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }
    private boolean Valid() {
        String pay = txtExtraSalry.getText();
        String fertilizer = txtDailySlary.getText();


        boolean isTeaLeaf =  Pattern.matches("(^A-Z^)|[0-9]\\S[^A-Z,a-z$]{0,}|[^A-Z,a-z$]", pay);
        boolean isFertilizer =  Pattern.matches("(^A-Z^)|[0-9]\\S[^A-Z,a-z$]{0,}|[^A-Z,a-z$]", fertilizer);


        if (!isTeaLeaf) {
            new Alert(Alert.AlertType.ERROR, "Invalid Tea Leaf Pay").show();
            return false;
        }
        if (!isFertilizer) {
            new Alert(Alert.AlertType.ERROR, "Invalid Fertilizer Pay").show();
            return false;
        }
        return true;

    }
    private void supplierBill() throws ClassNotFoundException {
        String code = lblTrasacId.getText();

        HashMap hashMap = new HashMap();
        hashMap.put("dailyPay",txtDailySlary.getText());
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/report/teacolec.jrxml");
            JasperDesign load = JRXmlLoader.load(resourceAsStream);

            JRDesignQuery jrDesignQuery = new JRDesignQuery();
            jrDesignQuery.setText("select p.EmpId ,p.startTime,p.endDate,p.workCount,p.extraSalary,p.netTotal,c.Name AS NAME FROM payment p,collector c WHERE p.EmpId=c.teaColecId AND transctionId ='"+code+"'");
            load.setQuery(jrDesignQuery);

            JasperReport compiledReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    compiledReport,
                    hashMap,
                    DbConnection.getDbConnection().getConnection()
            );
            JasperViewer.viewReport(jasperPrint,false);
        }catch (JRException | SQLException e){
            e.printStackTrace();
        }
    }

}
