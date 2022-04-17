package com.nhom6.messageroomapp.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@SuppressLint("SimpleDateFormat")
public class DateTimeUtils {

    public static String getCurrent() {
        Calendar c = Calendar.getInstance();
        DateFormat pstFormat = new SimpleDateFormat("HH:mm dd/MM");
        return pstFormat.format(c.getTime());
    }

    public static String getFullDateFromServer(String dateTimeString) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(dateTimeString);
            Calendar c = Calendar.getInstance();
            if (date != null) {
                c.setTime(date);
                DateFormat pstFormat = new SimpleDateFormat("dd/MM/yyyy");
                return pstFormat.format(c.getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertToServer(dateTimeString, Constant.FullDatePattern);
    }

    public static String convertToServer(String dateTimeString, String inputPattern) {
        //if (dateTimeString == null || dateTimeString.isEmpty() || dateTimeString.length() <= 9) return "";
        DateFormat format = new SimpleDateFormat(inputPattern);
        try {
            Date date = format.parse(dateTimeString);
            Calendar c = Calendar.getInstance();
            if (date != null) {
                c.setTime(date);
                DateFormat pstFormat = new SimpleDateFormat("yyyy-MM-dd");
                return pstFormat.format(c.getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
        //return convertToServer(dateTimeString, "dd-MM-yyyy");
    }

    public static String convertDateTimeMillis(long millis, String outPattern) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        DateFormat pstFormat = new SimpleDateFormat(outPattern);
        return pstFormat.format(c.getTime());
    }

    public static String convertDateTime(String dateTimeString, String inPattern, String outPattern) {
        DateFormat format = new SimpleDateFormat(inPattern);
        format.setTimeZone(TimeZone.getTimeZone("GMT-8"));
        try {
            Date date = format.parse(dateTimeString);
            Calendar c = Calendar.getInstance();
            if (date != null) {
                c.setTime(date);
                DateFormat pstFormat = new SimpleDateFormat(outPattern);
                return pstFormat.format(c.getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTimeString;
    }
}
