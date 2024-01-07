package lk.captain.Controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.captain.db.DbConnection;
import lk.captain.dto.PaymenSuppDetailGetDTO;
import lk.captain.dto.PaymentDto;
import lk.captain.dto.SupplierManageDTO;
import lk.captain.dto.tm.PaymentSupplierTM;
import lk.captain.dto.util.GenerateTransactionCode;
import lk.captain.model.SupplierManageModel;
import lk.captain.model.PaymentModel;
import lk.captain.model.TeaLeafModel;
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

public class TeaSuppplierPaymentController {

    @FXML
    private JFXComboBox<String> cmbSuppId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colNetWeight;

    @FXML
    private TableColumn<?, ?> colSuppName;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> coltransactionId;

    @FXML
    private DatePicker datapicker2;

    @FXML
    private DatePicker datepicker1;

    @FXML
    private Label lblSalaryTotl;

    @FXML
    private Label lblSuppName;

    @FXML
    private Label lblnetWeight;

    @FXML
    private AnchorPane root;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableView<PaymentSupplierTM> tblPayment;

    @FXML
    private TextField txtPay;

    @FXML
    private TextField txtfertilizer;

    @FXML
    private TextField txtserchTransaction;

    @FXML
    private Label lbltranCode;


    SupplierManageModel supplierManageModel = new SupplierManageModel();
    TeaLeafModel teaLeafModel = new TeaLeafModel();
    PaymentModel paymentModel = new PaymentModel();

public void initialize() throws SQLException {
    load();
    generateTransacId();
    AllPaymentDetails();
    setCellValueFactory();
}
    @FXML
    void btnPayOnAction(ActionEvent event) {
        boolean isValid = Valid();
        if (isValid == true) {
            String tranCod = lbltranCode.getText();
            String suppId = cmbSuppId.getValue();
            String date1 = java.lang.String.valueOf(datepicker1.getValue());
            String date2 = java.lang.String.valueOf(datapicker2.getValue());
            String trasacDate = String.valueOf(LocalDate.now());
            double amount = Double.parseDouble(lblSalaryTotl.getText());
            double fertilizer = Double.parseDouble(txtfertilizer.getText());
            double netTotal = (amount - fertilizer);
            double netWeight = Double.parseDouble(lblnetWeight.getText());

            try {
                boolean isSaved = paymentModel.addPayement(new PaymentDto(tranCod, suppId, date1, date2, trasacDate, netTotal, netWeight, fertilizer, 0, 0));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, lblSuppName.getText() + " is " + "Payment Successfully").show();
                    supplierBill();
                    setCellValueFactory();
                    AllPaymentDetails();
                    generateTransacId();
                    return;
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private void supplierBill() {
        String code = lbltranCode.getText();
        LocalDate date1 = datepicker1.getValue();
        LocalDate date2 = datapicker2.getValue();
        HashMap hashMap = new HashMap();
        hashMap.put("1kgtoPay",txtPay.getText());
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/report/PaymentSupplier.jrxml");
            JasperDesign load = JRXmlLoader.load(resourceAsStream);

            JRDesignQuery jrDesignQuery = new JRDesignQuery();
            jrDesignQuery.setText("SELECT\n" +
                    "    p.transctionId,\n" +
                    "    p.EmpId,\n" +
                    "    p.netWeight,\n" +
                    "    p.fertilizerReduced,\n" +
                    "    p.netTotal,\n" +
                    "    s.suppName,\n" +
                    "    t.supplierId,\n" +
                    "    t.teaColecId,\n" +
                    "    t.grosWeight,\n" +
                    "    t.waterCon,\n" +
                    "    t.netWeight AS TeaLeafNetWeight,\n" +
                    "    t.date\n" +
                    "FROM payment AS p\n" +
                    "         JOIN suppliers AS s ON p.EmpId = s.supplierId\n" +
                    "         JOIN tea_leafentry AS t ON p.EmpId = t.supplierId\n" +
                    "WHERE p.transctionId = '"+code+"' AND t.date BETWEEN '"+date1+"' AND '"+date2+"'");
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

    public void generateTransacId() {
        try {
            String tranId = GenerateTransactionCode.generateTransacId();
            lbltranCode.setText(tranId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btntxtserchTransactionActioin(ActionEvent event) {
        String code = txtserchTransaction.getText();

        var paymentModel = new PaymentModel();
        try {

             ResultSet resultSet = paymentModel.searchTransacCode(code);

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
            lblSuppName.setText(resultSet.getString("suppName"));
            cmbSuppId.setValue(resultSet.getString("EmpId"));
            datepicker1.setValue(LocalDate.parse(resultSet.getString("startTime")));
            datapicker2.setValue(LocalDate.parse(resultSet.getString("endDate")));
            lblnetWeight.setText(resultSet.getString("netWeight"));
            lblSalaryTotl.setText(resultSet.getString("netTotal"));
            txtfertilizer.setText(resultSet.getString("fertilizerReduced"));
        }

    }

    @FXML
    void cmbSuppIdOnAction(ActionEvent event) throws SQLException {
        String id = cmbSuppId.getValue();
        SupplierManageDTO dto = supplierManageModel.searchSupplierId(id);
        lblSuppName.setText(dto.getSuppName());

    }

    @FXML
    void datapicker2OnAction(ActionEvent event) throws SQLException {
    String date1 = String.valueOf(datepicker1.getValue());
    String date2 = String.valueOf(datapicker2.getValue());
    String suppId = cmbSuppId.getValue();
        double netleaf = teaLeafModel.getNetValue(date1,date2,suppId);
        lblnetWeight.setText(String.valueOf(netleaf));

    }

    @FXML
    void btnGetTotalAction(KeyEvent event) {
    double netWeight = Double.parseDouble(lblnetWeight.getText());
    double oneKgTOpay = Double.parseDouble(txtPay.getText());
    lblSalaryTotl.setText(String.valueOf(netWeight*oneKgTOpay));

    }

    private void load() {
        ObservableList<String> obSuppList = FXCollections.observableArrayList();

        try {
            List<SupplierManageDTO> suppList = supplierManageModel.getAllTeaSupp();

            for (SupplierManageDTO dto : suppList) {
                obSuppList.add(dto.getSupplierId());
            }

            cmbSuppId.setItems(obSuppList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    private void AllPaymentDetails() throws SQLException {
            var getSuppSlary  = new GenerateTransactionCode();

            ObservableList<PaymentSupplierTM> obList = FXCollections.observableArrayList();

            try {
                List<PaymenSuppDetailGetDTO> dtoList = getSuppSlary.getAllDetail();

                for(PaymenSuppDetailGetDTO dto : dtoList) {

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
                            new PaymentSupplierTM(
                                    dto.getTransctionId(),
                                    dto.getTransactionDate(),
                                    dto.getNetTotal(),
                                    dto.getNetWeight(),
                                    dto.getSuppName(),
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
        colSuppName.setCellValueFactory(new PropertyValueFactory<>("suppName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        colNetWeight.setCellValueFactory(new PropertyValueFactory<>("netWeight"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("netTotal"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }
    private boolean Valid() {
        String pay = txtPay.getText();
        String fertilizer = txtfertilizer.getText();


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

}
