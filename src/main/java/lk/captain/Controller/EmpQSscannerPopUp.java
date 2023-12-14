package lk.captain.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lk.captain.QRSacanner.QrCodeScanner;
import lk.captain.dto.AttendenceDTO;
import lk.captain.dto.TeaCollctorDTO;
import lk.captain.dto.WorkerManageDTO;
import lk.captain.dto.util.DateTimeUtil;
import lk.captain.model.AttendenceModel;
import lk.captain.model.TeaCollectorModel;
import lk.captain.model.WokerManageModel;

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
    AttendenceModel attendenceModel = new AttendenceModel();

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
                boolean isUpdate = attendenceModel.isUpdated(emp, dates);
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
    public void isAttendenceMarked(String empID){


        boolean isWorkerId = Pattern.matches("\\b[Ww]\\w*\\b",empID);
        boolean isSupId = Pattern.matches("\\b[Kk]\\w*\\b",empID);

        if (isWorkerId){
            var wokerManageModel = new WokerManageModel();
            try {
                WorkerManageDTO dto = wokerManageModel.searchWorkerId(empID);
                if(dto==null) {
                    initialize();
                    ibiWelcome.setVisible(false);
                   lblNotRegister.setVisible(true);
                } else {
                    String name = dto.getWorkName();
                    String date = String.valueOf(LocalDate.now());
                    int isMark = 1;
                    String time = lblTime.getText();

                    boolean isSaved = attendenceModel.manage(new AttendenceDTO(empID, name, isMark, date, time));
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
            var teaCollectorModel = new TeaCollectorModel();
            try {
                TeaCollctorDTO dto = teaCollectorModel.searchTeaColecId(empID);
                if(dto==null) {
                    initialize();
                    ibiWelcome.setVisible(false);
                   lblNotRegister.setVisible(true);
                } else {
                    String Wname = dto.getName();
                    String date = String.valueOf(LocalDate.now());
                    int isMark = 1;
                    String time = lblTime.getText();

                    boolean isSaved = attendenceModel.manage(new AttendenceDTO(empID, Wname, isMark, date, time));
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
