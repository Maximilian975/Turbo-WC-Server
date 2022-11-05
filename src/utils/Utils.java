package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils{
    public static String getCurrTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		String currDate = dtf.format(now);
        return currDate;
    }
}