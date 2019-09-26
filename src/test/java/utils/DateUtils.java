package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    //Returns todays date +/- the month counter
    public String getDateAsString(int offSetMonths){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, offSetMonths);
        return sdf.format(calendar.getTime());
    }

    //Returns todays date +/- the days counter
    public String getDateAsStringOffsetDays(int offSetDays){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, offSetDays);
        return sdf.format(calendar.getTime());
    }

    //Returns the specified date +/- the days counter
    public String offsetDateWithDays(String oldDate, int offSetDays){
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(oldDate));
            calendar.add(Calendar.DATE, offSetDays);
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    //returns current year
    public String yearAsString(){
        Calendar calendar = Calendar.getInstance();
        sdf = new SimpleDateFormat("yyyy");
        return sdf.format(calendar.getTime());
    }
}
