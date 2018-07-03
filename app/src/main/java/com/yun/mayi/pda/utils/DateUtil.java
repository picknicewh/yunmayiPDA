package com.yun.mayi.pda.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.yun.mayi.pda.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 作者： wh
 * 时间：  2017/6/30
 * 名称：日期工具类
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class DateUtil {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 以yyyy-MM-dd格式获取日期
     *
     * @param date 日期
     */
    public static String getFormatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    /**
     * 以yyyy-MM-dd HH:ss格式获取日期
     *
     * @param date 日期
     */
    public static String getFormatTimeDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String getFormatHourDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }

    /**
     *  * 时间转换为时间戳 
     *  * @param timeStr 时间 例如: 2016-03-09 
     *  * @param format  时间对应格式  例如: yyyy-MM-dd 
     *  * @return     
     */
    public static long getTimeStamp(String timeStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(timeStr);
            long timeStamp = date.getTime();
            return timeStamp;
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return 0;
    }

    /**
     *  * 时间转换为时间戳 
     *  * @param timeStr 时间 例如: 2016-03-09 
     *  * @param format  时间对应格式  例如: yyyy-MM-dd 
     *  * @return     
     */
    public static Date getTimeDate(String timeStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(timeStr);

            return date;
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return date;
    }

    /**
     * 以yyyyn年MM月dd日HH:ss 格式获取日期
     *
     * @param time 日期时间戳
     */
    public static String getChineseDate(long time) {
        long time1000 = time * 1000;
        return new SimpleDateFormat("yyyy年MM月dd日HH:mm").format(time1000);
    }

    public static String getDate(long time) {
        long time1000 = time * 1000;
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time1000);
    }

    /**
     * 以yyyy-MM-dd格式获取与今天相差的days天的字符串数据
     *
     * @param date 日期
     * @param days 相隔天数
     */
    public static String getFormatDateBetweenDays(Date date, int days) {
        long between = days * 24 * 60 * 60 * 1000;
        long betweenTime = date.getTime() - between;
        Date mDate = new Date(betweenTime);
        return new SimpleDateFormat("yyyy-MM-dd").format(mDate);
    }

    public static int getBetweenDays(String startTime, String endTime) {
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(endTime);
            date2 = format.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int days = (int) ((date1.getTime() - date2.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * 以yyyy-MM-dd格式获取与今天相差的days天的字符串数据
     *
     * @param date 日期
     * @param days 相隔天数
     */
    public static Date getDateBetweenDays(Date date, int days) {
        long between = days * 24 * 60 * 60 * 1000;
        long betweenTime = date.getTime() - between;
        Date mDate = new Date(betweenTime);
        return mDate;
    }

    /**
     * 间隔年的日期
     */
    public static String getDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
        Date date = calendar.getTime();
        return format.format(date);
    }

    /**
     * 结束时间默认为23:59:59
     *
     * @param date 日期
     */
    public static Date geteEndDate(Date date) {
        long between = 23 * 60 * 60 * 1000 + 59 * 60 * 1000 + 59 * 1000;
        long betweenTime = date.getTime() + between;
        Date mDate = new Date(betweenTime);
        return mDate;
    }

    /**
     * 获取与今天具体时间点的相差的addDay天的日期
     *
     * @param hour   小时
     * @param minute 分钟
     * @param second 秒
     */
    public static Date getNeedTime(int hour, int minute, int second, int addDay) {
        Calendar calendar = Calendar.getInstance();
        if (addDay != 0) {
            calendar.add(Calendar.DATE, addDay);
        }
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * 日期选择对话框
     *
     * @param title    标题
     * @param context  上下文
     * @param listener 回掉监听
     */
    public static TimePickerView getTimePickerView(String title, Context context, Calendar calendar, TimePickerView.OnTimeSelectListener listener) {
        TimePickerView.Builder builder = new TimePickerView.Builder(context, listener);
        builder.setType(getTimeType())
                .setLabel("", "", "", "", "", "")
                .setCancelColor(ContextCompat.getColor(context, R.color.red_btn))
                .setSubmitColor(ContextCompat.getColor(context, R.color.red_btn))
                .setTitleText(title)
                .isCyclic(true)//是否循环滚动
                .setContentSize(16);
        TimePickerView timePickerView = builder.build();
        timePickerView.setDate(calendar);
        return timePickerView;
    }

    /**
     * 日期选择对话框
     *
     * @param title    标题
     * @param context  上下文
     * @param listener 回掉监听
     */
    public static TimePickerView getDatePickerView(String title, Context context, Calendar calendar, TimePickerView.OnTimeSelectListener listener) {
        TimePickerView.Builder builder = new TimePickerView.Builder(context, listener);
        builder.setType(getDateType())
                .setLabel("", "", "", "", "", "")
                .setCancelColor(ContextCompat.getColor(context, R.color.red_btn))
                .setSubmitColor(ContextCompat.getColor(context, R.color.red_btn))
                .setTitleText(title)
                .isCyclic(true)//是否循环滚动
                .setContentSize(16);
        TimePickerView timePickerView = builder.build();
        timePickerView.setDate(calendar);
        return timePickerView;
    }

    private static boolean[] getDateType() {
        boolean[] type = new boolean[6];
        for (int i = 0; i < 6; i++) {
            if (i > 2) {
                type[i] = false;
            } else {
                type[i] = true;
            }
        }
        return type;
    }

    private static boolean[] getTimeType() {
        boolean[] type = new boolean[6];
        for (int i = 0; i < 6; i++) {
            if (i > 3) {
                type[i] = false;
            } else {
                type[i] = true;
            }
        }
        return type;
    }

    /**
     * 选择选项对话框
     *
     * @param options  选项列表
     * @param title    标题
     * @param context  上下文
     * @param listener 回调监听
     */
    public static OptionsPickerView getOptionPickerView(String title, List<String> options, int position, Context context,
                                                        OptionsPickerView.OnOptionsSelectListener listener) {
        OptionsPickerView.Builder builder = new OptionsPickerView.Builder(context, listener);
        builder.setCancelColor(ContextCompat.getColor(context, R.color.red_btn))
                .setSubmitColor(ContextCompat.getColor(context, R.color.red_btn))
                .setTitleText(title)
                .setSelectOptions(position)
                .setCyclic(false, false, false);
        OptionsPickerView optionsPickerView = builder.build();
        optionsPickerView.setPicker(options);
        return optionsPickerView;
    }
}
