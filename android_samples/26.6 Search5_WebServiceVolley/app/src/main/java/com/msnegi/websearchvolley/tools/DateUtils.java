package com.msnegi.websearchvolley.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    public static String APP_DATE_FORMAT = "dd/MM/yyyy";
    public static String APP_DATE_FORMAT_NEW = "dd/MM/yyyy";

    public static String PW_DATE_FORMAT = "dd/MM/yyyy";
    public static String PW_DIRECTORY_FORMAT = "dd/MM/yy";
    public static String PW_DIRECTORY_FORMAT1 = "EEE, dd-MMM-yy [HH:mm]";
    public static String ALERT_DATE_FORMAT = "dd/MM/yyyy, HH:mm:ss";
    public static String DIRECTORY_DATE_FORMAT = "dd/MM/yyyy - HH:mm";
    public static String PW_CREATOR_DATE_FORMAT = "dd/MM/yyyy, hh:mm a";
    public static String VISIT_DATE_FORMAT = "dd/MM/yy - HH:mm:ss";
    public static String EVENT_DATE_FORMAT = "dd/MM/yyyy HH:mm";
    public static String PARSE_FROM_DATE_FORMAT = "yyyy-MM-dd";
    //2019-02-27T00:00:00
    public static String PARSE_SERVER_DATE_FORMAT = "yyyy-MM-dd\'T\'HH:mm:ss";
    public static String PARSE_SERVER_DATE_FORMAT_MILLISECINDS = "yyyy-MM-dd\'T\'HH:mm:ss.fff";
    public static int year, month, day;
    public static TimeZone tz = TimeZone.getTimeZone("GMT+0530");
    public static SimpleDateFormat dayDateFormat = new SimpleDateFormat(APP_DATE_FORMAT);
    public static SimpleDateFormat mjpSummaryDateFormat = new SimpleDateFormat("dd MMM yy");

    public static SimpleDateFormat defaultDateFormat = new SimpleDateFormat(APP_DATE_FORMAT);
    public static SimpleDateFormat defaultDateFormatNew = new SimpleDateFormat(APP_DATE_FORMAT_NEW);
    public static SimpleDateFormat myPagePerformanceDateFormat = new SimpleDateFormat(APP_DATE_FORMAT);

    /**
     * Thsi method returns current date in specific date format
     *
     * @return
     */
    public static void setDefaultTimeZone() {
        dayDateFormat.setTimeZone(tz);
        mjpSummaryDateFormat.setTimeZone(tz);
        defaultDateFormat.setTimeZone(tz);

    }

    public static String getTodayDate(Calendar c) {
        return mjpSummaryDateFormat.format(c.getTime());
    }

    public static String convertTimeStampTODateString(String respDate) {
        if (respDate == null || respDate.equals("")) {
            return "";
        }
        try {
            String dateInTimeStamp = respDate.replace("/Date(", "").replace(
                    "+0530)/", "");
            Calendar c = Calendar.getInstance();
            Long timeInMillis = Long.valueOf(dateInTimeStamp);
            c.setTimeInMillis(timeInMillis);
            return defaultDateFormat.format(c.getTime());
        } catch (Exception exp) {
            return "";
        }
    }

    public static String convertTimeStampToDashBoardScreen(String respDate) {
        if (respDate == null || respDate.equals("")) {
            return "";
        }
        String dateInTimeStamp = respDate.replace("/Date(", "").replace(
                "+0530)/", "");
        Calendar c = Calendar.getInstance();
        Long timeInMillis = Long.valueOf(dateInTimeStamp);
        c.setTimeInMillis(timeInMillis);
        return new SimpleDateFormat("dd MMM HH:mm", Locale.ENGLISH).format(c.getTime());
    }

    public static String convertTimeStampTODateStringNew(String respDate) {
        if (respDate == null || respDate.equals("")) {
            return "";
        }
        String dateInTimeStamp = respDate.replace("/Date(", "").replace(
                "+0530)/", "");
        Calendar c = Calendar.getInstance();
        Long timeInMillis = Long.valueOf(dateInTimeStamp);
        c.setTimeInMillis(timeInMillis);
        return defaultDateFormatNew.format(c.getTime());
    }

    public static String generateServerDate(Long date) {
        return "/Date(" + date + "+0530)/";
    }

    /**
     * Method to parse the server time stamp to local time stamp.
     *
     * @param respDate Accepted format is /Date(14254455445+0530)/
     * @return return the long value of time stamp.
     */
    public static long convertServerTimeStampToLocal(String respDate) {
        if (respDate == null || respDate.equals("")) {
            return -1;
        }
        String dateInTimeStamp = respDate.replace("/Date(", "").replace(
                "+0530)/", "");
        Calendar c = Calendar.getInstance();
        return Long.valueOf(dateInTimeStamp);
    }


    public static String convertDateToTimeStamp(String dateString, SimpleDateFormat defaultDateFormat) {
        try {
            Date date = defaultDateFormat.parse(dateString);
            Long tsLong = date.getTime();
            return "/Date(" + tsLong.toString() + "+0530)/";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long convertDateToLong(String dateString, SimpleDateFormat defaultDateFormat) {
        try {
            Date date = defaultDateFormat.parse(dateString);
            long tsLong = date.getTime();
            return tsLong;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String convertDateToTimeStampPerformance(String dateString,
                                                           SimpleDateFormat defaultDateFormat) {
        try {
            Date date = defaultDateFormat.parse(dateString);
            Long tsLong = date.getTime();
            return "/Date(" + tsLong.toString() + "+0530)/";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertDateToTimeStamp(String dateString) {
        try {
            Date date = defaultDateFormat.parse(dateString);
            Long tsLong = date.getTime();
            Calendar c = Calendar.getInstance();
            Long timeInMillis = Long.valueOf(tsLong);
            c.setTimeInMillis(timeInMillis);
            //LogManager.d("Converted date", "" + c.getTime().toString());
            return "/Date(" + c.getTimeInMillis() + "+0530)/";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertDateToTimeStamp(String dateString, DateFormat format) {
        try {
            Date date = format.parse(dateString);
            Long tsLong = date.getTime();
            return "/Date(" + tsLong.toString() + "+0530)/";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertTimeStampTODateString(String respDate, String format) {
        if (respDate == null || respDate.equals("")) {
            return "";
        }
        String dateInTimeStamp = respDate.replace("/Date(", "").replace(
                "+0530)/", "");
        SimpleDateFormat ndf = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        Long timeInMillis = Long.valueOf(dateInTimeStamp);
        c.setTimeInMillis(timeInMillis);
        return ndf.format(c.getTime());
    }


    public static String convertCurrentTimeStampTODateString( String format) {
        SimpleDateFormat ndf = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        return ndf.format(c.getTime());
    }


    public static String convertTimeStampTODateStringEvent(String respDate, String format, String requiredFormat) {
        if (respDate == null || respDate.equals("")) {
            return "";
        }
        String dateInTimeStamp = respDate.replace("T", " ");
        SimpleDateFormat ndf = new SimpleDateFormat(requiredFormat);
        Calendar c = Calendar.getInstance();
        Long timeInMillis = convertDateToLong(dateInTimeStamp, new SimpleDateFormat(format));
        c.setTimeInMillis(timeInMillis);
        return ndf.format(c.getTime());
    }

    public static long convertTimeStampTOLongEvent(String respDate, String format) {
        if (respDate == null || respDate.equals("")) {
            return 0;
        }
        String dateInTimeStamp = respDate.replace("T", " ");
        Long timeInMillis = convertDateToLong(dateInTimeStamp, new SimpleDateFormat(format));
        return timeInMillis;
    }


    public static String convertTimeStampTODateString1(String respDate, String format) {
        if (respDate == null || respDate.equals("")) {
            return "";
        }
        String dateInTimeStamp = respDate.replace("/Date(", "").replace(
                "+0530)/", "");
        SimpleDateFormat ndf = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();

        Long timeInMillis = Long.valueOf(dateInTimeStamp);
        c.setTimeInMillis(timeInMillis);

        //  c.add(Calendar.DAY_OF_MONTH,1);

        return ndf.format(c.getTime());
    }

    public static String datenum(int count) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -3 * count);
        SimpleDateFormat ndf = new SimpleDateFormat("MM/dd/yyyy");
        return ndf.format(c.getTime());
    }

    public static String getTodayDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(APP_DATE_FORMAT);
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    public static String getBackDate() {
        Calendar cal = Calendar.getInstance();

        // go back two days
        cal.add(Calendar.DAY_OF_YEAR, -1);

        // display
        SimpleDateFormat sdf = new SimpleDateFormat(APP_DATE_FORMAT);
        String string = sdf.format(cal.getTime());
        return string;

    }

    public static String getCurrentTimeStamp() {
        Long tsLong = System.currentTimeMillis();
        return "/Date(" + tsLong.toString() + "+0530)/";
    }

    public static String convertDateFormatTODayFormat(Date date) {
        //Date MyDate = newDateFormat.parse(date);
        dayDateFormat.applyPattern("dd MMM EEE");
        return dayDateFormat.format(date);
    }

    /**
     * This method returns current date in specific date format
     *
     * @return
     */
    public static boolean isTodayDate(String respDate) {
        if (respDate.equals("")) {
            return false;
        }
        // String dateInTimeStamp = respDate.replace("/Date(", "").replace(
        //  "+0530)/", "");

        Calendar c = Calendar.getInstance();
        String todayDate = defaultDateFormat.format(c.getTime());
        //String todayDate = "06/24/2016";

//        Long timeInMillis = Long.valueOf(date);
//        c.setTimeInMillis(timeInMillis);
//        String respDate = ndf.format(c.getTime());

        try {
            Date date1 = defaultDateFormat.parse(todayDate);
            Date date2 = defaultDateFormat.parse(respDate);
//
//            System.out.println(defaultDateFormat.format(date1));
//            System.out.println(defaultDateFormat.format(date2));

            if (date1.compareTo(date2) > 0) {
                //System.out.println("Date1 is after Date2");
            } else if (date1.compareTo(date2) < 0) {
                // System.out.println("Date1 is before Date2");
            } else if (date1.compareTo(date2) == 0) {
                // System.out.println("Date1 is equal to Date2");
                return true;
            } else {
                // System.out.println("How to get here?");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isDateValid(String fromdate, String todate) {
        try {
            Date date1 = defaultDateFormat.parse(fromdate);
            Date date2 = defaultDateFormat.parse(todate);
            if (date1.compareTo(date2) < 0) {
                // System.out.println("Date1 is before Date2");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String convertDateFromStringToJson(String date, String formate) {
        getDayMonthYear(date, formate);
        if (year != 0) {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day);
            long limit_time = cal.getTimeInMillis();

            System.out.print("/Date(" + limit_time + ")/");
            return "/Date(" + limit_time + ")/";
        }
        return "";
    }


    public static String convertDateToTimeStampWithFormat(String date, String formate) {
        getDayMonthYear(date, formate);
        if (year != 0) {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day);
            long limit_time = cal.getTimeInMillis();

            System.out.print("/Date(" + limit_time + ")/");
            return "/Date(" + limit_time + "+0530)/";
        }
        return "";
    }

    public static String convertDateToTimeStamp(String date, String formate) {
        getDayMonthYear(date, formate);
        if (year != 0) {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day);
            long limit_time = cal.getTimeInMillis();
            return "/Date(" + limit_time + "+0530)/";
        }
        return "";
    }


    public static void getDayMonthYear(String date, String formate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formate);
            Date parse = formatter.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(parse);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);
            year = cal.get(Calendar.YEAR);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }


    public static String getCurrentDatewithFormat(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentDate(DateFormat dateFormat) {
        //DateFormat dateFormat = new SimpleDateFormat(format);
        // Date date = new Date();
        return dateFormat.format(new Date());
    }

    public static long compareTwoTimeStamps(long oldTime) {
        Calendar c = Calendar.getInstance();

        long milliseconds1 = oldTime;
        long milliseconds2 = c.getTimeInMillis();

        long diff = milliseconds2 - milliseconds1;
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffHours;
    }

    public static Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static String getYesterdayDateString() {

        DateFormat dateFormat = new SimpleDateFormat(PW_DATE_FORMAT);
        return dateFormat.format(yesterday());

    }

    public static String getZipDate(String date) {
        try {
            String formatDate = date.replace("T", " ");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date convertedDate = formatter.parse(formatDate);
            return "/Date(" + convertedDate.getTime() + "+0530)/";
        } catch (Exception e) {
            //LogManager.printStackTrace(e);
        }
        return null;
    }

    public static long numberOfDays(long time1, long time) {
        long msDiff = time1 - time;
        long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);
        return daysDiff;
    }

    public static String changeDateFormat(String currentFormat, String requiredFormat, String dateString) {
        try {
            String result = "";
            SimpleDateFormat formatterOld = new SimpleDateFormat(currentFormat, Locale.getDefault());
            SimpleDateFormat formatterNew = new SimpleDateFormat(requiredFormat, Locale.getDefault());
            Date date = null;
            try {
                date = formatterOld.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date != null) {
                result = formatterNew.format(date);
            }
            return result;
        } catch (Exception e) {
            return "";
        }

    }

    public static Date getCurrentDateInstance() {
        return Calendar.getInstance().getTime();
    }

    public static String changeDateFormat(String requiredFormat, Date date) {
        try {
            String result = "";
            SimpleDateFormat formatterNew = new SimpleDateFormat(requiredFormat, Locale.getDefault());

            if (date != null) {
                result = formatterNew.format(date);
            }
            return result;
        } catch (Exception e) {
            return "";
        }

    }

    public static String getMonth(final int iMonth) {
        String strMonth;
        switch (iMonth) {
            case 1: {
                strMonth = "JAN";
            }
            break;
            case 2: {
                strMonth = "FEB";
            }
            break;
            case 3: {
                strMonth = "MAR";
            }
            break;
            case 4: {
                strMonth = "APR";
            }
            break;
            case 5: {
                strMonth = "MAY";
            }
            break;
            case 6: {
                strMonth = "JUN";
            }
            break;
            case 7: {
                strMonth = "JUL";
            }
            break;
            case 8: {
                strMonth = "AUG";
            }
            break;
            case 9: {
                strMonth = "SEP";
            }
            break;
            case 10: {
                strMonth = "OCT";
            }
            break;
            case 11: {
                strMonth = "NOV";
            }
            break;
            case 12: {
                strMonth = "DEC";
            }
            break;
            default: {
                strMonth = "";
            }
            break;
        }
        return strMonth;
    }
}
