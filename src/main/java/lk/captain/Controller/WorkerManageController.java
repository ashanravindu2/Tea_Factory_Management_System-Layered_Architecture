package lk.captain.Controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.captain.QrGenerate.QrcodeForUser;
import lk.captain.dto.TeaTypeDTO;
import lk.captain.dto.WorkerManageDTO;
import lk.captain.dto.tm.WorkerManageTM;
import lk.captain.model.TeaTypeModel;
import lk.captain.model.WokerManageModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class WorkerManageController {

    @FXML
    private AnchorPane root;

    @FXML
    private DatePicker txtWokerDoJ;

    @FXML
    private TextField txtWokerTele;

    @FXML
    private TextField txtWorkerAdd;

    @FXML
    private TextField txtWorkerAge;

    @FXML
    private DatePicker txtWorkerDoB;

    @FXML
    private Label lblWorkId;

    @FXML
    private TextField txtSearchId;

    @FXML
    private TextField txtWorkerName;

    @FXML
    private TableColumn<?, ?> colAdd;

    @FXML
    private TableColumn<?, ?> colAge;

    @FXML
    private TableColumn<?, ?> colDOB;

    @FXML
    private TableColumn<?, ?> colDOJ;

    @FXML
    private TableColumn<?, ?> colGen;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTele;

    @FXML
    private TableView<WorkerManageTM> tblworker;

    @FXML
    private JFXComboBox<String> cmbWorkGender;
    private WokerManageModel workerManageModel = new WokerManageModel();
    private  QrcodeForUser qrcodeForUser = new QrcodeForUser();

    public void initialize(){
        genersteWorkerId();
        gender();
        loadWorkers();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("workId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("workName"));
        colAdd.setCellValueFactory(new PropertyValueFactory<>("workAdress"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("workAge"));
        colTele.setCellValueFactory(new PropertyValueFactory<>("workTele"));
        colGen.setCellValueFactory(new PropertyValueFactory<>("workGen"));
        colDOJ.setCellValueFactory(new PropertyValueFactory<>("workJoin"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("workBirth"));
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean isValid = Valid();
        if (isValid == true) {
            String workId = lblWorkId.getText();
            String workName = txtWorkerName.getText();
            String workAddress = txtWorkerAdd.getText();
            int workAge = Integer.parseInt(txtWorkerAge.getText());
            String workTele = txtWokerTele.getText();
            String workGen = cmbWorkGender.getValue();
            String workJoin = String.valueOf(txtWokerDoJ.getValue());
            String workBirth = String.valueOf(txtWorkerDoB.getValue());

            try {
                //  String newAge = String.format(workAge);

                boolean isSaved = workerManageModel.workerManage(new WorkerManageDTO(workId, workName, workAddress, workAge, workTele, workGen, workJoin, workBirth));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Worker added Successfully").show();
                    qrcodeForUser.CreateQr(workId);
                    setCellValueFactory();
                    loadWorkers();
                    genersteWorkerId();
                    return;
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    }
    private void gender(){
        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.addAll("Male","Female");

        try {
           cmbWorkGender.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void cmbGenderOnAction(ActionEvent event) {
       // String gender = cmbWorkGender.getValue();
    }

    public void genersteWorkerId(){
        try {
            String userId = workerManageModel.generateNextWorkerId();
            lblWorkId.setText(userId);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    private void loadWorkers() {
        var wokerManageModel  = new WokerManageModel();

        ObservableList<WorkerManageTM> obList = FXCollections.observableArrayList();

        try {
            List<WorkerManageDTO> dtoList = wokerManageModel.getAllWorker();

            for(WorkerManageDTO dto : dtoList) {
                obList.add(
                        new WorkerManageTM(
                                dto.getWorkId(),
                                dto.getWorkName(),
                                dto.getWorkAdress(),
                                dto.getWorkAge(),
                                dto.getWorkTele(),
                                dto.getWorkGen(),
                                dto.getWorkJoin(),
                                dto.getWorkBirth()

                        )
                );
            }

            tblworker.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtSearchIdOnAction(ActionEvent event) {
        String id =txtSearchId .getText();

        var wokerManageModel = new WokerManageModel();
        try {
            WorkerManageDTO dto = wokerManageModel.searchWorkerId(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Worker Type not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void fillFields(WorkerManageDTO dto) {
        lblWorkId.setText(dto.getWorkId());
        txtWokerTele.setText(dto.getWorkTele());
        txtWorkerAdd.setText(dto.getWorkAdress());
        txtWokerDoJ.setValue(LocalDate.parse(dto.getWorkJoin()));
        txtWorkerDoB.setValue(LocalDate.parse(dto.getWorkBirth()));
        txtWorkerName.setText(dto.getWorkName());
        txtWorkerAge.setText(String.valueOf(dto.getWorkAge()));
        cmbWorkGender.setValue(dto.getWorkGen());
    }


    @FXML
    void btnCleraOnAction(ActionEvent event) {
        txtWorkerAge.setText("");
        txtWorkerName.setText("");
        cmbWorkGender.setValue("");
        txtWorkerAdd.setText("");
        txtWokerTele.setText("");
        txtSearchId.setText("");
        genersteWorkerId();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        boolean isValid = Valid();
        if (isValid == true) {
            String workId = lblWorkId.getText();
            String workName = txtWorkerName.getText();
            String workAddress = txtWorkerAdd.getText();
            int workAge = Integer.parseInt(txtWorkerAge.getText());
            String workTele = txtWokerTele.getText();
            String workGen = cmbWorkGender.getValue();
            String workJoin = String.valueOf(txtWokerDoJ.getValue());
            String workBirth = String.valueOf(txtWorkerDoB.getValue());

            var dto = new WorkerManageDTO(workId, workName, workAddress, workAge, workTele, workGen, workJoin, workBirth);

            var model = new WokerManageModel();
            try {
                boolean isUpdated = model.updateWorker(dto);
                System.out.println(isUpdated);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Workers Updated Success!").show();
                    loadWorkers();
                    setCellValueFactory();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    }

    @FXML
    void btnWorkerDeletOnAction(ActionEvent event) {
        String deleteId = txtSearchId.getText();

        try {
            boolean isDeleted = workerManageModel.deleteWorker(deleteId);
            if (isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION,deleteId+" "+"Worker is Deleted !").show();
            loadWorkers();
            setCellValueFactory();
            genersteWorkerId();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private boolean Valid() {
        String suppName = txtWorkerName.getText();
        String suppAddres = txtWorkerAdd.getText();
        String suppTele = txtWokerTele.getText();
        String age = txtWorkerAge.getText();


        boolean isSupName = Pattern.matches("([A-Z][a-z\\s]{3,}|[a-z])", suppName);
        boolean issuppAddres = Pattern.matches("^([\\w\\s]+)\\s([\\w\\s]+)$", suppAddres);
        boolean issuppTele = Pattern.matches("^\\+?\\d{3}[-\\s]?\\(?\\d{3}\\)?[-\\s]?\\d{3}[-\\s]?\\d", suppTele);
        boolean isAge =  Pattern.matches("^\\S[0-9]{1,2}", age);

        if (!isSupName) {
            new Alert(Alert.AlertType.ERROR, "Invalid Name").show();
            return false;
        }
        if (!issuppAddres) {
            new Alert(Alert.AlertType.ERROR, "Invalid Address").show();
            return false;
        }
        if (!issuppTele) {
            new Alert(Alert.AlertType.ERROR, "Invalid Telephone").show();
            return false;
        }
        if (!isAge) {
            new Alert(Alert.AlertType.ERROR, "Invalid Age").show();
            return false;
        }
        return true;

    }

}
