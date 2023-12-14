package lk.captain.dto.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import lk.captain.Controller.AttendenceController;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {


    public static String dateNow() {
        return null;
    }
    public static String timenow(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
        return simpleDateFormat.format(new Date());
    }
}
