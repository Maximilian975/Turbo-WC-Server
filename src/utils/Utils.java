package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils{
    public static String getCurrTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		String currDate = dtf.format(now);
        return currDate;
    }
    public static String getMillis(String dateString){
        
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date reference = new Date();
        Date date = new Date();
        try {
            reference = dateFormat.parse("00:00:00");
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        long seconds = (date.getTime() - reference.getTime());
        return String.valueOf(seconds);
    }
}