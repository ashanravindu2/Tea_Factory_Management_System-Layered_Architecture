package lk.captain.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.captain.bo.BOFactory;
import lk.captain.bo.custom.AttendenceBO;
import lk.captain.bo.custom.TeaCollectorBO;
import lk.captain.bo.custom.WorkerBO;
import lk.captain.dto.AttendenceDTO;
import lk.captain.dto.TeaCollctorDTO;
import lk.captain.dto.WorkerManageDTO;
import lk.captain.view.tm.AttendenceTM;
import lk.captain.util.util.DateTimeUtil;




import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class AttendenceController {

    @FXML
    private Label lblEmpName;

    @FXML
    private Label lblGender;

    @FXML
    private Label lblWorkPath;
    @FXML
    private RadioButton absentRadio;
    @FXML
    private RadioButton prsentRadio;
    @FXML
    private TextField txtempId;
    @FXML
    private Label lblTime1;

    @FXML
    private TextField txtsearchId;

    @FXML
    private Label lblEmpPath1;

    @FXML
    private Label lblName1;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<AttendenceTM> tblAttendence;

    @FXML
    private TableColumn<?, ?> colAttendence;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEMpName;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colTime;


    @FXML
    private Label lblTime;


    WorkerBO workerBO = (WorkerBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.WORKER);
    TeaCollectorBO teaCollectorBO = (TeaCollectorBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.TEACOLLECTOR);
    AttendenceBO attendenceBO = (AttendenceBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.ATTENDENCE);
    public void initialize() throws ClassNotFoundException {
        setCellValueFactory();
        loadAttendence();
        realTime();
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    void btnScanAction(ActionEvent event) {

    }

    @FXML
    void txtSerchEmpIdOnAction(ActionEvent event) throws ClassNotFoundException {
        String Id = txtempId.getText();
        boolean isWorkerId = Pattern.matches("\\b[Ww]\\w*\\b",Id);
        boolean isSuppId = Pattern.matches("\\b[Kk]\\w*\\b",Id);
        if (isWorkerId){

                try {
                    WorkerManageDTO dto = workerBO.searchWorkerId(Id);

                    if(dto != null) {
                        workerField(dto);
                    } else {
                        new Alert(Alert.AlertType.INFORMATION, "Worker is not found!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            }
        if (isSuppId){
            try {
                TeaCollctorDTO dto = null;
                try {
                    dto = teaCollectorBO.searchTeaColecId(Id);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

                if(dto != null) {
                    suppIdField(dto);
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Tea Collector is not found!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }
        if (!(isSuppId | isWorkerId)){
            new Alert(Alert.AlertType.ERROR, Id+" Employee is not found this Factory!").show();
        }
        }
    private void suppIdField(TeaCollctorDTO dto) {
        lblEmpName.setText(dto.getName());
        lblGender.setText(dto.getGender());
        lblWorkPath.setText("Tea Collector");
    }

    private void workerField(WorkerManageDTO dto) {
        lblEmpName.setText(dto.getWorkName());
        lblGender.setText(dto.getWorkGen());
        lblWorkPath.setText("Worker");
    }


    @FXML
    void btnAddAttendencAciton(ActionEvent event) throws ClassNotFoundException {
        boolean prsentselect = prsentRadio.isSelected();
        boolean absentselect = absentRadio.isSelected();

        int isMark = 0;
        if (prsentselect){
            isMark=1;
        }
        if (absentselect){
            isMark=0;
        }
        String EmpId = txtempId.getText();
        String name = lblEmpName.getText();
        String date = String.valueOf(LocalDate.now());
        String time = lblTime.getText();
       try {
            boolean isUpdate = attendenceBO.update(new AttendenceDTO(EmpId, name, isMark, date, time));
           if (isUpdate) {
               boolean isSaved = attendenceBO.manage(new AttendenceDTO(EmpId, name, isMark, date, time));
               if (isSaved) {
                   new Alert(Alert.AlertType.CONFIRMATION, lblEmpName.getText() + "is Attendence Mark Successfully").show();
                   clearField();
                   initialize();
               }
           } else if (!isUpdate) {
               new Alert(Alert.AlertType.ERROR,EmpId+" Is Attendence Is Noted Today").show();
           }
        } catch (SQLException e) {
          new Alert(Alert.AlertType.ERROR, e.getMessage());
       }
    }


    public void btnPrsentAction(javafx.scene.input.MouseEvent event) {

    }

    public void btnAbsentAction(javafx.scene.input.MouseEvent event) {

    }

    void loadAttendence() throws ClassNotFoundException {

        ObservableList<AttendenceTM> obList = FXCollections.observableArrayList();

        try {
            List<AttendenceDTO> dtoList = attendenceBO.getAllAttendeceDetail();

            for(AttendenceDTO dto : dtoList) {
                obList.add(
                        new AttendenceTM(
                                dto.getEmpAttenId(),
                                dto.getName(),
                                dto.getAttMark(),
                                dto.getDate(),
                                dto.getTime()
                        )
                );
            }
            tblAttendence.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("EmpAttenId"));
        colEMpName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAttendence.setCellValueFactory(new PropertyValueFactory<>("attMark"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

    }
    @FXML
    void txtSerchOnAction(ActionEvent event) throws ClassNotFoundException {
        String Id = txtsearchId.getText();
        String date = String.valueOf(datePicker.getValue());
        boolean is = Pattern.matches("\\b[WwKk]\\w*\\b",Id);

        if (is==true) {
            try {
                AttendenceDTO dto = attendenceBO.searchEmplIsDate(Id, date);

                if (dto != null) {
                    attField(dto);
                    new Alert(Alert.AlertType.INFORMATION, txtsearchId.getText() + " Employee is Prsent").show();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, txtsearchId.getText() + " Employee is Absent").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Employee is not found this Factory!").show();
        }
    }
    private void attField(AttendenceDTO dto) throws SQLException, ClassNotFoundException {
        String empId = dto.getEmpAttenId();
        boolean isWorkerId = Pattern.matches("\\b[Ww]\\w*\\b",empId);
        if (isWorkerId){
            WorkerManageDTO wDto = workerBO.searchWorkerId(empId);
            lblName1.setText(wDto.getWorkName());
            lblEmpPath1.setText("Worker");
            lblTime1.setText(dto.getTime());
        }
        if (!isWorkerId){
            TeaCollctorDTO tDto = null;
            try {
                tDto = teaCollectorBO.searchTeaColecId(empId);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            lblName1.setText(tDto.getName());
            lblEmpPath1.setText("Tea Collector");
            lblTime1.setText(dto.getTime());
        }



    }
    public void realTime(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> lblTime.setText(DateTimeUtil.timenow())));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        lblTime.setText(DateTimeUtil.dateNow());
    }

    public void clearField(){
        lblGender.setText(" ");
        lblEmpName.setText(" ");
        lblWorkPath.setText(" ");
    }

    @FXML
    void btnQRScanAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/empQRscannerPopUp_form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Attendence Marker");
        stage.show();
        stage.getIcons().add(new Image("/assets/png/QrPng.png"));
    }
    @FXML
    void btnSyncTableAction(ActionEvent event) throws ClassNotFoundException {
            initialize();
    }
}
