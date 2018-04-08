/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author moulaYounes
 */
public class DateUtil {

    public static java.sql.Date getSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static java.sql.Timestamp convertFromDateToTimestamp(java.util.Date date) {
        return new java.sql.Timestamp(date.getTime());
    }

    public static java.sql.Timestamp getSqlDateTime(java.util.Date date) {
        return new java.sql.Timestamp(date.getTime());
    }

    public static String getYearOfCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        return simpleDateFormat.format(new Date());
    }

    public static String formateDate(String pattern, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        if (date != null) {
            return simpleDateFormat.format(date);
        } else {
            return "";
        }
    }

    public static Date parse(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return simpleDateFormat.parse(date);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static java.sql.Date convertFormUtilToSql(java.util.Date date) {
        if (date != null) {
            return new java.sql.Date(date.getTime());
        } else {
            return null;
        }
    }

    public static java.sql.Timestamp convertFormUtilToTimestamp(java.util.Date date) {
        if (date != null) {
            return new java.sql.Timestamp(date.getTime());
        } else {
            return null;
        }
    }

    public static int addMonthToDate(int nb, java.util.Date dateAModify) {
        if (dateAModify != null) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(dateAModify);
            gc.add(GregorianCalendar.MONTH, nb);
            return gc.getTime().getMonth();
        } else {
            return -1;
        }
    }

    public static int addYearToDate(int nb, java.util.Date dateAModify) {
        if (dateAModify != null) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(dateAModify);
            gc.add(GregorianCalendar.YEAR, nb);
            return gc.getTime().getYear();
        } else {
            return -1;
        }
    }

    //test two dates
    public static int compareTwoDates(Date dateDebut, Date dateFin) {
        if (dateDebut == null || dateFin == null) {
            return -1;
        } else if (dateDebut.compareTo(dateFin) < 0) {
            return -2;
        }else if(dateDebut.getYear()!=dateFin.getYear()){
            return -3;
        }
        return 1;
    }

}
