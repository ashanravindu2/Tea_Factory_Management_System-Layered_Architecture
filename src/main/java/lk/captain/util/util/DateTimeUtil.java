package lk.captain.util.util;

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
