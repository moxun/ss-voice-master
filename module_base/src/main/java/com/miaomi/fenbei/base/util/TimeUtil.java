package com.miaomi.fenbei.base.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static final SimpleDateFormat DATE_FORMAT_DATE    = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_FORMAT_FM    = new SimpleDateFormat("hh:mm:ss");

    public static final SimpleDateFormat DATE_FORMAT_DAY    = new SimpleDateFormat("MM-dd hh:mm:ss");

    public static final SimpleDateFormat DATE_FORMAT_DATE_AND_FM    = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static String getTime(long timeInMillis) {
        return DATE_FORMAT_DATE.format(new Date(timeInMillis));
    }

    public static String getMSTime(long timeInMillis) {
        return DATE_FORMAT_FM.format(new Date(timeInMillis*1000));
    }

    public static String getDataAndFmTime(long timeInMillis) {
        return DATE_FORMAT_DATE_AND_FM.format(new Date(timeInMillis*1000));
    }

    public static String getDayTime(long timeInMillis) {
        return DATE_FORMAT_DAY.format(new Date(timeInMillis*1000));
    }
    /**
     * String型时间戳格式化
     *
     * @return
     */
    public static String longFormatTime(long time) {
        time = time * 1000;
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //转换为日期
        Date date = new Date();
        date.setTime(time);
        if (isThisYear(date)) {//今年
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            if (isToday(date)) { //今天
                int minute = minutesAgo(time);
                if (minute < 60) {//1小时之内
                    if (minute <= 1) {//一分钟之内，显示刚刚
                        return "刚刚";
                    } else {
                        return minute + "分钟前";
                    }
                } else {
                    return simpleDateFormat.format(date);
                }
            } else {
                if (isYestYesterday(date)) {//昨天，显示昨天
                    return "昨天 " + simpleDateFormat.format(date);
                } else if (isThisWeek(date)) {//本周,显示周几
                    String weekday = null;
                    if (date.getDay() == 1) {
                        weekday = "周一";
                    }
                    if (date.getDay() == 2) {
                        weekday = "周二";
                    }
                    if (date.getDay() == 3) {
                        weekday = "周三";
                    }
                    if (date.getDay() == 4) {
                        weekday = "周四";
                    }
                    if (date.getDay() == 5) {
                        weekday = "周五";
                    }
                    if (date.getDay() == 6) {
                        weekday = "周六";
                    }
                    if (date.getDay() == 0) {
                        weekday = "周日";
                    }
                    return weekday + " " + simpleDateFormat.format(date);
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
                    return sdf.format(date);
                }
            }
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(date);
        }
    }
    private static int minutesAgo(long time) {
        return (int) ((System.currentTimeMillis() - time) / (60000));
    }

    public static boolean isToday(Date date) {
        Date now = new Date();
        return (date.getYear() == now.getYear()) && (date.getMonth() == now.getMonth()) && (date.getDate() == now.getDate());
    }

    private static boolean isYestYesterday(Date date) {
        Date now = new Date();
        return (date.getYear() == now.getYear()) && (date.getMonth() == now.getMonth()) && (date.getDate() + 1 == now.getDate());
    }

    private static boolean isThisWeek(Date date) {
        Date now = new Date();
        if ((date.getYear() == now.getYear()) && (date.getMonth() == now.getMonth())) {
            if (now.getDay() - date.getDay() < now.getDay() && now.getDate() - date.getDate() > 0 && now.getDate() - date.getDate() < 7) {
                return true;
            }
        }
        return false;
    }

    private static boolean isThisYear(Date date) {
        Date now = new Date();
        return date.getYear() == now.getYear();
    }




    public static String getTimeScope() {
        Date now = new Date();
        int hours = now.getHours();
        if (hours == 0){
            return "23:00 - 24:00";
        }else{
            return hours-1+ ":00 - "+(hours)+":00";
        }
    }

    public static String getTimeSurplus() {
        Date now = new Date();
        int minutes = now.getMinutes();
        return "剩"+(60 - minutes)+"分钟结束榜单";
    }

    public static String getTimeSFM(long timeInMillis) {
        timeInMillis = timeInMillis*1000;
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 计算差多少小时
        long hour = timeInMillis % nd / nh;
        // 计算差多少分钟
        long min = timeInMillis % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = timeInMillis % nd % nh % nm / ns;
        return String.format("%02d:%02d:%02d",hour,min,sec);
    }

    public static String getTimeSpaceSFM(long timeInMillis) {
        timeInMillis = timeInMillis*1000;
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 计算差多少小时
        long hour = timeInMillis % nd / nh;
        // 计算差多少分钟
        long min = timeInMillis % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = timeInMillis % nd % nh % nm / ns;
        return String.format("%02d  %02d  %02d",hour,min,sec);
    }

    public static String getRecordTime(long timeInMillis) {
//        Date date = new Date();
//        date.setTime(timeInMillis*1000);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
//        return simpleDateFormat.format(date);
        timeInMillis = timeInMillis/1000;
        long nm = 60;
        // 计算差多少小时
        // 计算差多少分钟
        long min = timeInMillis /  nm;
        // 计算差多少秒//输出结果
        long sec = timeInMillis % nm;
        return String.format("%02d:%02d",min,sec);
    }

    public static String getZSTime(long timeInMillis) {
//        Date date = new Date();
//        date.setTime(timeInMillis*1000);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
//        return simpleDateFormat.format(date);
        timeInMillis = timeInMillis/1000;
        long nm = 60;
        // 计算差多少小时
        // 计算差多少分钟
        long min = timeInMillis /  nm;
        // 计算差多少秒//输出结果
        long sec = timeInMillis % nm;
//        return String.format("%02d    %02d",min,sec);
        return String.format("%02d",min);
    }
    public static String getZSTimeSec(long timeInMillis) {
//        Date date = new Date();
//        date.setTime(timeInMillis*1000);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
//        return simpleDateFormat.format(date);
        timeInMillis = timeInMillis/1000;
        long nm = 60;
        // 计算差多少小时
        // 计算差多少分钟
        long min = timeInMillis /  nm;
        // 计算差多少秒//输出结果
        long sec = timeInMillis % nm;
        return String.format("%02d",sec);
    }
    public static String getSurplusTime(long timeInMillis){
        timeInMillis = timeInMillis - System.currentTimeMillis()/1000;
        if (timeInMillis < 0){
            return "已过期";
        }
        long nm = 60*60;
        // 计算差多少小时
        long hours = timeInMillis /  nm;
        boolean isNeeedAdd = false;
        // 计算差多少天
        long hour = hours % 24;
        long day = hours/24;
        if (timeInMillis % nm > 0){
            isNeeedAdd = true;
        }
        if (isNeeedAdd){
            hour = hour +1;
        }
        return String.format("剩%d天%d小时",day,hour);
    }


    public static boolean getIsYoungModelTime() {
        Long[] mills = new Long[2];
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 22);
        mills[0] = cal.getTimeInMillis();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 6);
        mills[1] = cal.getTimeInMillis();
        cal.clear();
        return (System.currentTimeMillis() >= mills[0] && System.currentTimeMillis() <= mills[1]);
    }

    /**
     * 获取下一个整点跟当前时间的时间差
     * @return
     */
    public static Long getFromNextHourTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 1);
        Long mill = cal.getTimeInMillis();
        cal.clear();
        return mill - System.currentTimeMillis();
    }

}
