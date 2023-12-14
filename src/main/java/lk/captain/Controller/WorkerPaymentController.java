package lk.captain.Controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.captain.Db.DbConnection;
import lk.captain.dto.*;
import lk.captain.dto.tm.PaymentWorkerTM;
import lk.captain.dto.util.GenerateTransactionCode;
import lk.captain.model.AttendenceModel;
import lk.captain.model.PaymentModel;
import lk.captain.model.WokerManageModel;
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

public class WorkerPaymentController {

    public Label lbltranCode;

    @FXML
    private TableView<PaymentWorkerTM> tblPayment;
    @FXML
    private JFXComboBox<String> cmbWorkId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colExtraSlary;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colWorkCount;

    @FXML
    private TableColumn<?, ?> coltransactionId;

    @FXML
    private TableColumn<?, ?> coltotal;

    @FXML
    private DatePicker datePicker1;

    @FXML
    private DatePicker datePicker2;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblworkcount;

    @FXML
    private Label lblworkerName;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtserchTransaction;

    @FXML
    private TextField txtExtraSlary;

    @FXML
    private TextField txtSalary;

    AttendenceModel attendenceModel = new AttendenceModel();
    WokerManageModel wokerManageModel = new WokerManageModel();
    PaymentModel paymentModel = new PaymentModel();

    public void initialize() throws SQLException {
        load();
        AllPaymentDetails();
         setCellValueFactory();
        generateTransacId();
    }

    @FXML
    void cmbWorkIdOnAction(ActionEvent event) throws SQLException {
        String ids = cmbWorkId.getValue();
        WorkerManageDTO dto = wokerManageModel.searchWorkerId(ids);
        lblworkerName.setText(dto.getWorkName());
    }

    @FXML
    void datePicker2OnAction(ActionEvent event) throws SQLException {
       String date1 = String.valueOf(datePicker1.getValue());
       String date2 = String.valueOf(datePicker2.getValue());
       String workId = cmbWorkId.getValue();
      //  System.out.println(workId);
         int vv = attendenceModel.issearchAttendenceCount(workId,date1,date2);
      lblworkcount.setText(String.valueOf(vv));
    }



    @FXML
    void txttxtSalaryOnAction(ActionEvent event) {
       double salarydaily = Double.parseDouble(txtSalary.getText());
       double workCount = Double.parseDouble(lblworkcount.getText());
       double total = workCount*salarydaily;
       lblTotal.setText(String.valueOf(total));
    }

    @FXML
    void btntxtserchTransactionActioin(ActionEvent event) {
            String code = txtserchTransaction.getText();

            var paymentModel = new PaymentModel();
            try {

                ResultSet resultSet = paymentModel.searchTransacCodeWorker(code);

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
            lblworkerName.setText(resultSet.getString("workName"));
            cmbWorkId.setValue(resultSet.getString("EmpId"));
            datePicker1.setValue(LocalDate.parse(resultSet.getString("startTime")));
            datePicker2.setValue(LocalDate.parse(resultSet.getString("endDate")));
            lblworkcount.setText(resultSet.getString("workCount"));
            txtExtraSlary.setText(resultSet.getString("extraSalary"));
            lblTotal.setText(resultSet.getString("netTotal"));
        }

    }

    public void generateTransacId() {
        try {
            String tranId = GenerateTransactionCode.generateTransacId();
            lbltranCode.setText(tranId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnPayOnAction(ActionEvent event) {
        boolean isValid = Valid();
        if (isValid == true) {
            String workId = cmbWorkId.getValue();
            String tranCode = lbltranCode.getText();
            String date1 = String.valueOf(datePicker1.getValue());
            String date2 = String.valueOf(datePicker2.getValue());
            int workCount = Integer.parseInt(lblworkcount.getText());
            double extraPay = Double.parseDouble(txtExtraSlary.getText());
            double totals = Double.parseDouble(lblTotal.getText());
            double totalPay = totals + extraPay;
            String date = String.valueOf(LocalDate.now());

            try {
                boolean isSaved = paymentModel.addPayement(new PaymentDto(tranCode, workId, date1, date2, date, totalPay, 0, 0, workCount, extraPay));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, lblworkerName.getText() + " is " + "Payment Successfully").show();
                    supplierBill();
                    initialize();
                    return;
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }


    private void load() {
        ObservableList<String> obWorkerList = FXCollections.observableArrayList();

        try {
            List<WorkerManageDTO> workerManageDTOS = wokerManageModel.getAllWorker();

            for (WorkerManageDTO dto : workerManageDTOS) {
                obWorkerList.add(dto.getWorkId());
            }

            cmbWorkId.setItems(obWorkerList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        private void AllPaymentDetails() throws SQLException {
            var getSuppSlary  = new GenerateTransactionCode();

            ObservableList<PaymentWorkerTM> obList = FXCollections.observableArrayList();

            try {
                List<PaymentWorkerDTO> dtoList = getSuppSlary.getAllDetailworker();

                for(PaymentWorkerDTO dto : dtoList) {

                    Button btn = new Button("Remove");
                    btn.setCursor(Cursor.HAND);

                    btn.setOnAction((e) ->{
                        ButtonType yes = new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
                        ButtonType no = new ButtonType("No",ButtonBar.ButtonData.CANCEL_CLOSE);

                        Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION,"Are You Sure To Remove ?",yes,no).showAndWait();
                        if (type.orElse(no)==yes){
                            int selectedIndex = tblPayment.getSelectionModel().getSelectedIndex();
                            String transacCode = (String) coltransactionId.getCellData(selectedIndex);

                            deleteteaId(transacCode);

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
    private void deleteteaId(String transacCode){
        try {
            boolean isDeleted =paymentModel.deleteCode(transacCode);
            if (isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION,"Payment Detail Deleted !").show();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        coltransactionId.setCellValueFactory(new PropertyValueFactory<>("transctionId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        colWorkCount.setCellValueFactory(new PropertyValueFactory<>("workCount"));
        colName.setCellValueFactory(new PropertyValueFactory<>("workName"));
        colExtraSlary.setCellValueFactory(new PropertyValueFactory<>("extraSalary"));
        coltotal.setCellValueFactory(new PropertyValueFactory<>("netTotal"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }
    private boolean Valid() {
        String salary = txtSalary.getText();
        String ExtraSlry = txtExtraSlary.getText();


        boolean isSlry =  Pattern.matches("(^A-Z^)|[0-9]\\S[^A-Z,a-z$]{0,}|[^A-Z,a-z$]", salary);
        boolean isExtraSlry =  Pattern.matches("(^A-Z^)|[0-9]\\S[^A-Z,a-z$]{0,}|[^A-Z,a-z$]", ExtraSlry);


        if (!isSlry) {
            new Alert(Alert.AlertType.ERROR, "Invalid Salary Entry ").show();
            return false;
        }
        if (!isExtraSlry) {
            new Alert(Alert.AlertType.ERROR, "Invalid Extra Salary Entry").show();
            return false;
        }
        return true;

    }
    private void supplierBill() {
        String code = lbltranCode.getText();

        HashMap hashMap = new HashMap();
        hashMap.put("dailyPay",txtSalary.getText());
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/report/teacolec.jrxml");
            JasperDesign load = JRXmlLoader.load(resourceAsStream);

            JRDesignQuery jrDesignQuery = new JRDesignQuery();
            jrDesignQuery.setText("select p.EmpId ,p.startTime,p.endDate,p.workCount,p.extraSalary,p.netTotal,w.workName AS NAME FROM payment p,workers w WHERE p.EmpId=w.workId AND transctionId ='"+code+"'");
            load.setQuery(jrDesignQuery);

            JasperReport compiledReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    compiledReport,
                    hashMap,
                    DbConnection.getInstance().getConnection()
            );
            JasperViewer.viewReport(jasperPrint,false);
        }catch (JRException | SQLException e){
            e.printStackTrace();
        }
    }

}
