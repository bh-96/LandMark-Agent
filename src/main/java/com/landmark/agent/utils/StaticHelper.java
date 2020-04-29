package com.landmark.agent.utils;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StaticHelper {

    public static Date longToDate(Long dateTime, String format) {
        Date date = new Date();

        try {
            String stringTime = String.valueOf(dateTime);
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(stringTime);
        } catch (Exception e) {
        }

        return date;
    }

    public static String dateToString(Date dateTime, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(dateTime);
        } catch (Exception e) {
            return "";
        }
    }

    public static String subDateToString(int subDay, String format) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -subDay);
            return new SimpleDateFormat(format).format(calendar.getTime());
        } catch (Exception e) {
            return "";
        }
    }

    public static String encodeUTF8(String keyword) {
        return URLEncoder.encode(keyword);
    }

}
