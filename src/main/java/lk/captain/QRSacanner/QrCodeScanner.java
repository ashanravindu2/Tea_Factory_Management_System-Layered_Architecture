package lk.captain.QRSacanner;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.protobuf.Value;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class QrCodeScanner {
    public static ArrayList<Integer> scannedValues = new ArrayList<>();
    //String[][] details = DatabaseConnecter.getDetails("user",4);
    public static String value;

    public  static String QrScanner(){
        Webcam webcam = Webcam.getDefault();   //Generate Webcam Object
        webcam.setViewSize(new Dimension(640,480));
        WebcamPanel webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setMirrored(false);
        JFrame jFrame = new JFrame();
        jFrame.add(webcamPanel);
        jFrame.pack();
      //  jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);


        do {
            try {
                BufferedImage image = webcam.getImage();
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                Result result = new MultiFormatReader().decode(bitmap);
                if(result.getText() != null) {

                     value = result.getText();

                    //LocalDate date = LocalDate.now();
                    //LocalTime time = LocalTime.now();

                    /*if (!scannedValues.contains(value)) {
                        scannedValues.add(value);
                    } else {
                        scannedValues.remove(Integer.valueOf(value));
                    }*/

                    //DBC.setDetails("INSERT INTO turtlescare.ticket (issueDate, issueTime, code) VALUES ('"+ date +"', '"+ time +"', '" + value +"')");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    jFrame.setVisible(false);
                    jFrame.dispose();
                    webcam.close();
                    return value;

                }
                // break;

            }catch (NotFoundException e ) {
                //new Alert(Alert.AlertType.INFORMATION,"Try using search bar").show();
            }
        } while(true);
}

}