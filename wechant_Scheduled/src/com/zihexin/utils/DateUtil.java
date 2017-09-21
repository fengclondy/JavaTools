package com.zihexin.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * date util
 * Created by admin on 2017/7/31.
 */
public class DateUtil {

    /**
     * 获取前一天的时间
     */
    public static Date getPreDate(){
        //获取日历
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        return calendar.getTime();
    }

    /**
     * 获取格式化的前一天时间
     */
    public static String getFormatPreDate(String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(getPreDate());
    }



}
