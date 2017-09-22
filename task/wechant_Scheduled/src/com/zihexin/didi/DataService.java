package com.zihexin.didi;//package com.zihexin;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * 获取数据业务类
// * Created by admin on 2017/7/31.
// */
//public class DataService {
//
//    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//
//    /**
//     * 获取某一天的数据(日期)
//     * @param dateStr
//     * @return
//     */
//    public List<String[]> getData(String dateStr){
//
//        String beginTime = dateStr + " 00:00:00";
//        String endTime = dateStr + " 23:59:59";
//        //获取连接
//        Connection connection = JdbcUtil.getConnection();
//        String sql = "SELECT merchantUserId, tripcardType, amount, bindTime FROM zhxpay_bindcard_order WHERE bindTime BETWEEN ? AND ? AND STATUS = '00' AND bindCardType = 202 AND amount >= 100 ORDER BY createTime \n";
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        List<String[]> items = null;
//        try {
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1,beginTime);
//            preparedStatement.setString(2,endTime);
//            resultSet = preparedStatement.executeQuery();
//            items = new LinkedList<>();
//            String[] strings = null;
//            while (resultSet.next()){
//                strings = new String[4];
//                strings[0] = resultSet.getString(1);
//                strings[1] = resultSet.getString(2);
//                strings[2] = resultSet.getString(3);
//                strings[3] = DATE_FORMAT.format(resultSet.getTimestamp(4));
//                items.add(strings);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }finally {
//            JdbcUtil.close(resultSet);
//            JdbcUtil.close(preparedStatement);
//            JdbcUtil.close(connection);
//        }
//        return items;
//    }
//
//
//}
