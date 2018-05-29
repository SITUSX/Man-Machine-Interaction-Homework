package util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static config.Config.DATE_FORMAT;
import static config.Config.TIME_FORMAT;

/**
 * 通用时间处理类
 *
 * @author heleninsa
 */
public class Time {

    public static final Date DEFAULT_DATE;

    static {
        Date date = null;
        try {
            date = DATE_FORMAT.parse("2014-04-29");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DEFAULT_DATE = date;
    }

    public static final Date BEGIN_DATE;

    /**
     * 初始化
     */
    static {
        Date start = null;
        try {
            start = DATE_FORMAT.parse("2005-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        BEGIN_DATE = start;
    }

    /**
     * 判断某天是否为周末
     *
     * @param date
     * @return 周末返回true 否则返回false
     */
    public static boolean isWeekend(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    /**
     * 获取某日期前后确定天数对应日期
     *
     * @param date     基准日期
     * @param deltaDay 变化天数，负数向前推移，正数向后推移
     * @return 偏移后日期
     */
    public static Date moveDate(Date date, int deltaDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, deltaDay);

        return calendar.getTime();
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间字符串
     */
    public static String getCurrentTime() {
        return TIME_FORMAT.format(new Date());
    }

    /**
     * 获取当前日期
     *
     * @return 当前日期字符串
     */
    public static String getCurrentDate() {
        return DATE_FORMAT.format(new Date());
    }

    public static long deltaTime(Date beginTime, Date endTime) {
        // 计算两个时间点间的时间间隔（单位为秒）
        return (endTime.getTime() - beginTime.getTime()) / 1000;
    }

    /**
     * 计算时间间隔
     *
     * @param date1 日期一
     * @param date2 日期二
     * @return 返回为正如果Date2晚于Date1
     * @throws ParseException
     */
    public static int deltaDate(Date date1, Date date2) throws ParseException {
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24));
    }

}
