package com.bsoft.xnsmservice.util;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * author:caoyuxiang 2019年9月10日 下午2:11:57
 */
public class DateTools extends DateUtils {

    /**
     * 获取服务器时间
     *
     * @return
     */
    public static Date getSysDate() {
        return new Date();
    }

    /**
     * 获取年月日时分秒  系统时间
     *
     * @return
     */
    public static String getSysdateYYYY_MM_DDHHMMSS() {
        return getDateFormat(getSysDate(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getSysdateYYYY_MM_DD() {
        return getDateFormat(getSysDate(), "yyyy-MM-dd");
    }

    /**
     * 获取年份
     *
     * @return
     */
    public static String getSysdateYear() {
        return getDateFormat(getSysDate(), "yyyy");
    }

    /**
     * 按照 格式 获取服务器时间
     *
     * @param format
     * @return
     */
    public static String getSysDate(String format) {
        return getDateFormat(getSysDate(), format);
    }

    /**
     * 格式化时间
     *
     * @param date
     * @param format
     * @return
     */
    public static String getDateFormat(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String dateStr = dateFormat.format(date);
        return dateStr;
    }

    /**
     * 字符串转时间格式
     *
     * @param dateStr
     * @return
     */
    public static Date parseDate(String dateStr) {
        return StringUtil.strToDate(dateStr);
    }

    /**
     * 根据格式转化
     *
     * @param dateStr
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String dateStr, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(dateStr);
    }

    /**
     * 忽略转化异常，不是正确的日期格式 转化为空
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Date parseDateIgnoreException(String dateStr, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date resultDate = null;
        try {
            resultDate = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultDate;
    }

    /**
     * 计算日期相差天数
     *
     * @param endDate
     * @param nowDate
     * @return
     */
    public static long calTowDateOfDay(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        return day;
    }

    /**
     * 计算日期相差小时数
     *
     * @param endDate
     * @param nowDate
     * @return
     */
    public static long calTowDateOfHour(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少小时
        long hour = diff % nd / nh;
        return hour;
    }

    /**
     * 计算日期相差分钟
     *
     * @param endDate
     * @param nowDate
     * @return
     */
    public static long calTowDateOfMin(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        return min;
    }

    /**
     * 计算日期相差秒数
     *
     * @param endDate
     * @param nowDate
     * @return
     */
    public static long calTowDateOfSecond(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少分钟
        long second = diff % nd % nh % nm / ns;
        return second;
    }

    public static Date strToDate(String strDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos = new ParsePosition(0);
            Date strtodate = formatter.parse(strDate, pos);
            return strtodate;
        } catch (Exception e) {
            return new Date();
        }
    }

    public static String formatStrDateToyyyyMMddHHmmss(String strDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            Date strtodate = formatter.parse(strDate, pos);
            return getDateFormat(strtodate, "yyyy-MM-dd HH:mm:ss");
        } catch (Exception e) {
            return getDateFormat(getSysDate(), "yyyy-MM-dd HH:mm:ss");
        }
    }

    /**
     * 检验日期格式是否正确
     *
     * @param datein
     * @param formatStr
     * @return
     */
    public static boolean iSDateByFormat(String datein, String formatStr) {
        DateLocaleConverter dcl = new DateLocaleConverter();
        try {
            dcl.convert(datein, formatStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
