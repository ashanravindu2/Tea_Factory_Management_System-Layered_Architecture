package lk.captain.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lk.captain.util.qrGenerate.QrCodeScanner;
import lk.captain.bo.BOFactory;
import lk.captain.bo.custom.AttendenceBO;
import lk.captain.bo.custom.TeaCollectorBO;
import lk.captain.bo.custom.WorkerBO;
import lk.captain.dto.AttendenceDTO;
import lk.captain.dto.TeaCollctorDTO;
import lk.captain.dto.WorkerManageDTO;
import lk.captain.util.util.DateTimeUtil;



import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class EmpQSscannerPopUp {


    public Label lblDate;
    public Text lblInformatio;
    public Label ibiWelcome;
    public Label lblNotRegister;
    public Label lbltryAgain;
    public Label lblSuchFul;
    public Label lblAttNoted;

    @FXML
    private Text lblTime;

    public Rectangle penwa;
    QrCodeScanner qrCodeScanner = new QrCodeScanner();
    AttendenceBO attendenceBO = (AttendenceBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.ATTENDENCE);
    TeaCollectorBO teaCollectorBO = (TeaCollectorBO) lk.captain.bo.BOFactory.getBoFactory().getBOTypes(lk.captain.bo.BOFactory.BOTypes.TEACOLLECTOR);
    WorkerBO workerBO = (WorkerBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.WORKER);

    public void initialize(){
      ibiWelcome.setVisible(true);
      lblNotRegister.setVisible(false);
      lbltryAgain.setVisible(false);
      lblSuchFul.setVisible(false);
      lblAttNoted.setVisible(false);
        realTime();
        lblDate.setText(String.valueOf(LocalDate.now()));
    }
    @FXML
    void btnQRpopUpAction(ActionEvent event) throws SQLException {

        try {
            String emp = qrCodeScanner.QrScanner();
            String dates = String.valueOf(LocalDate.now());

            //search attendence
            boolean isSuppId = Pattern.matches("\\b[KkWw]\\w*\\b", emp);
            if (isSuppId) {
                boolean isUpdate = attendenceBO.update(new AttendenceDTO(emp, null, 0, dates, null));
                if (!isUpdate) {
                    initialize();
                    ibiWelcome.setVisible(false);
                    lblAttNoted.setVisible(true);
                } else if (isSuppId) {
                    isAttendenceMarked(emp);
                }
            } else {
                initialize();
                ibiWelcome.setVisible(false);
                lblNotRegister.setVisible(true);
            }
        }catch (Exception e){
            new RuntimeException(e);
        }


    }
    public void isAttendenceMarked(String empID) throws ClassNotFoundException {


        boolean isWorkerId = Pattern.matches("\\b[Ww]\\w*\\b",empID);
        boolean isSupId = Pattern.matches("\\b[Kk]\\w*\\b",empID);

        if (isWorkerId){
            try {
                WorkerManageDTO dto = workerBO.searchWorkerId(empID);
                if(dto==null) {
                    initialize();
                    ibiWelcome.setVisible(false);
                   lblNotRegister.setVisible(true);
                } else {
                    String name = dto.getWorkName();
                    String date = String.valueOf(LocalDate.now());
                    int isMark = 1;
                    String time = lblTime.getText();

                    boolean isSaved = attendenceBO.manage(new AttendenceDTO(empID, name, isMark, date, time));
                    if (isSaved) {
                        initialize();
                        ibiWelcome.setVisible(false);
                     lblSuchFul.setVisible(true);
                    }else {
                        initialize();
                        ibiWelcome.setVisible(false);
                        lbltryAgain.setVisible(true);
                    }
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else if (isSupId) {
            try {
                TeaCollctorDTO dto = null;
                try {
                    dto = teaCollectorBO.searchTeaColecId(empID);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if(dto==null) {
                    initialize();
                    ibiWelcome.setVisible(false);
                   lblNotRegister.setVisible(true);
                } else {
                    String Wname = dto.getName();
                    String date = String.valueOf(LocalDate.now());
                    int isMark = 1;
                    String time = lblTime.getText();

                    boolean isSaved = attendenceBO.manage(new AttendenceDTO(empID, Wname, isMark, date, time));
                    if (isSaved) {
                        initialize();
                        ibiWelcome.setVisible(false);
                        lblSuchFul.setVisible(true);
                    }else {
                        initialize();
                        ibiWelcome.setVisible(false);
                       lbltryAgain.setVisible(true);
                    }
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }else {
            initialize();
            ibiWelcome.setVisible(false);
            lblNotRegister.setVisible(true);
        }

    }
    public void realTime(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> lblTime.setText(DateTimeUtil.timenow())));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        lblTime.setText(DateTimeUtil.dateNow());
    }


}
