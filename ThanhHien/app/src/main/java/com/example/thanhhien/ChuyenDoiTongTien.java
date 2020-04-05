package com.example.thanhhien;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChuyenDoiTongTien {
    public static String priceWithoutDecimal (Double price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(price);
    }

    public static String formatDateTime(String datatime){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = null;
        try {
            date = format.parse(datatime.replaceAll("Z$", "+0000"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (checkTime(date.getDate()+"")+"-"+checkTime((date.getMonth()+1)+"")+"-"+ (cal.get(Calendar.YEAR))+" "+checkTime(date.getHours()+"")+":"+checkTime(date.getMinutes()+""));
    }
   public static String checkTime(String i) {
        if (Integer.parseInt(i) < 10) {
            i = "0" + i ;
        }
        return i;
    }

}
