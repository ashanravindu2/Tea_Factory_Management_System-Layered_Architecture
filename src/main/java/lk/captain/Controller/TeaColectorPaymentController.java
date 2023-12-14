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
import lk.captain.dto.PaymentDto;
import lk.captain.dto.PaymentWorkerDTO;
import lk.captain.dto.TeaCollctorDTO;
import lk.captain.dto.WorkerManageDTO;
import lk.captain.dto.tm.PaymentWorkerTM;
import lk.captain.dto.util.GenerateTransactionCode;
import lk.captain.model.AttendenceModel;
import lk.captain.model.PaymentModel;
import lk.captain.model.TeaCollectorModel;
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

    TeaCollectorModel teaCollectorModel = new TeaCollectorModel();
    AttendenceModel attendenceModel = new AttendenceModel();
    PaymentModel paymentModel = new PaymentModel();
    public void initialize() throws SQLException {load();generateTransacId();AllPaymentDetails();setCellValueFactory();}

    @FXML
    void btnPayOnAction(ActionEvent event) {
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
            boolean isSaved = paymentModel.addPayement(new PaymentDto(tranCode,id,date1,date2,date,totalPay,0,0,workCount,extraPay));
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
        TeaCollctorDTO dto = teaCollectorModel.searchTeaColecId(Id);
        lblName.setText(dto.getName());
    }

    @FXML
    void datepicker2OnAction(ActionEvent event) throws SQLException {
        String date1 = String.valueOf(datepicker1.getValue());
        String date2 = String.valueOf(datepicker2.getValue());
        String colecId = cmbId.getValue();

        int count = attendenceModel.issearchAttendenceCount(colecId,date1,date2);
        lblWorkCount.setText(String.valueOf(count));
    }

    @FXML
    void btntxtserchTransactionActioin(ActionEvent event) {
        String code = txtserchTransaction.getText();

        var paymentModel = new PaymentModel();
        try {

            ResultSet resultSet = paymentModel.searchTransacCodeColec(code);

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
    private void AllPaymentDetails() throws SQLException {
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

    private void load() {
        ObservableList<String> obTeaColecList = FXCollections.observableArrayList();

        try {
            List<TeaCollctorDTO> teaCollctorDTOS = teaCollectorModel.getAllCollector();

            for (TeaCollctorDTO dto : teaCollctorDTOS) {
                obTeaColecList.add(dto.getTeaColecId());
            }

            cmbId.setItems(obTeaColecList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateTransacId() {
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
    private void supplierBill() {
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
                    DbConnection.getInstance().getConnection()
            );
            JasperViewer.viewReport(jasperPrint,false);
        }catch (JRException | SQLException e){
            e.printStackTrace();
        }
    }

}
