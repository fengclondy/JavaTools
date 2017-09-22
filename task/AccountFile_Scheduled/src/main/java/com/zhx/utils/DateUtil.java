package com.zhx.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by admin on 2017/9/13.
 */
public class DateUtil {

    /**
     * 获取昨天日期
     * @return
     */
    public static String yesterdayDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE , -1);

        return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
    }


    public static String dataDiff(long start, long end) {

        long diff = (end - start) / 1000; // 转为秒

        // 方法一
        long day = diff / (24 * 3600);
        long hour = diff % (24 * 3600) / 3600;
        long minute = diff % 3600 / 60;
        long second = diff % 60 ;

        String string = "" + day + "天" + hour + "小时" + minute + "分" + second + "秒";
        System.out.println(string);

        // 方法二
//        long days = diff / ( 60 * 60 * 24);
//        long hours = (diff - days * (60 * 60 * 24)) / (60 * 60);
//        long minutes = (diff - days * (60 * 60 * 24) - hours * ( 60 * 60)) / ( 60);
//        long seconds = (diff - days * (60 * 60 * 24) - hours * (60 * 60) - minutes * (60));
//        String strings = "" + days + "天" + hours + "小时" + minutes + "分" + seconds + "秒";
//        System.out.println(strings);
        return string;
    }



    public static void main(String[] args) throws InterruptedException {
        String s = yesterdayDate();
        System.out.println(s);

        String propertyPath = System.getProperty("user.dir");
        System.out.println(propertyPath);

    }
}
