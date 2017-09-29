package com.jimzhang.wechant;



import com.jimzhang.utils.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 2017/9/14.
 */
public class AccountFileDataService {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<String[]> queryPayList() {
        //获取连接
        Connection connection = JdbcUtil.getConnection();
        String sql = "SELECT p.gatewayOrderNo , p.backOrderNo as transactionId,p.amount,p.updateDate as payTime " +
                "FROM  gateway_order o, gateway_order_pay_detail p WHERE TO_DAYS(NOW()) - TO_DAYS( p.updateDate) = 1 " +
                "AND o.paymentWay='501' AND p.`status`='00' AND o.gatewayOrderNo=p.gatewayOrderNo ";

//        String sql = "SELECT p.gatewayOrderNo , p.backOrderNo as transactionId,p.amount,p.updateDate as payTime " +
//                "FROM  gateway_order o, gateway_order_pay_detail p WHERE TO_DAYS('2017-09-02 16:42:05') - TO_DAYS( p.updateDate) = 1 " +
//                "AND o.paymentWay='501' AND p.`status`='00' AND o.gatewayOrderNo=p.gatewayOrderNo ORDER BY p.updateDate DESC";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<String[]> items = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, beginTime);
//            preparedStatement.setString(2, endTime);
            resultSet = preparedStatement.executeQuery();
            items = new LinkedList<>();
            String[] strings = null;
            while (resultSet.next()) {
                strings = new String[5];
                strings[0] = resultSet.getString(1);
                strings[1] = resultSet.getString(2);
                strings[2] = resultSet.getString(3);
                strings[3] = DATE_FORMAT.format(resultSet.getTimestamp(4));
                strings[4] = "PAY"; // 支付
                items.add(strings);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            JdbcUtil.close(resultSet);
            JdbcUtil.close(preparedStatement);
            JdbcUtil.close(connection);
        }
        return items;

    }


    public List<String[]> queryRefundList() {
        //获取连接
        Connection connection = JdbcUtil.getConnection();
        String sql = "SELECT p.gatewayOrderNo , p.backOrderNo as transactionId,p.amount,p.refundUpdateDate as redundTime " +
                "FROM gateway_order o, gateway_order_pay_detail p WHERE TO_DAYS(NOW()) - TO_DAYS( p.updateDate) = 1 " +
                "AND o.paymentWay='501' AND p.`status`='00' AND p.refundStatus='00' AND o.gatewayOrderNo=p.gatewayOrderNo ";

//        String sql = "SELECT p.gatewayOrderNo , p.backOrderNo as transactionId,p.amount,p.refundUpdateDate as redundTime " +
//                "FROM gateway_order o, gateway_order_pay_detail p WHERE TO_DAYS('2017-09-02 16:42:05') - TO_DAYS( p.updateDate) = 1 " +
//                "AND o.paymentWay='501' AND p.`status`='00' AND p.refundStatus='00' AND o.gatewayOrderNo=p.gatewayOrderNo ORDER BY p.updateDate DESC";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<String[]> items = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            items = new LinkedList<>();
            String[] strings = null;
            while (resultSet.next()) {
                strings = new String[5];
                strings[0] = resultSet.getString(1);
                strings[1] = resultSet.getString(2);
                strings[2] = resultSet.getString(3);
                strings[3] = DATE_FORMAT.format(resultSet.getTimestamp(4));
                strings[4] = "REFUND"; // 退款
                items.add(strings);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            JdbcUtil.close(resultSet);
            JdbcUtil.close(preparedStatement);
            JdbcUtil.close(connection);
        }
        return items;

    }
}
