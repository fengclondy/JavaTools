package com.zhx.dao;

import com.zhx.vo.AccountFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by admin on 2017/9/13.
 */
@Repository
public class AccountFileDao {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<AccountFile> queryPayList() {

//        String sql = "SELECT p.gatewayOrderNo , p.backOrderNo as transactionId,p.amount,p.updateDate as payTime " +
//                "FROM  gateway_order o, gateway_order_pay_detail p WHERE TO_DAYS(NOW()) - TO_DAYS( p.updateDate) = 1 " +
//                "AND o.paymentWay='501' AND p.`status`='00' AND o.gatewayOrderNo=p.gatewayOrderNo ORDER BY p.updateDate DESC";

        String sql = "SELECT p.gatewayOrderNo , p.backOrderNo as transactionId,p.amount,p.updateDate as payTime " +
                "FROM  gateway_order o, gateway_order_pay_detail p WHERE TO_DAYS('2017-09-02 16:42:05') - TO_DAYS( p.updateDate) = 1 " +
                "AND o.paymentWay='501' AND p.`status`='00' AND o.gatewayOrderNo=p.gatewayOrderNo ORDER BY p.updateDate DESC";

        List<AccountFile> accountFiles = jdbcTemplate.query(sql
                , new RowMapper<AccountFile>() {
                    @Override
                    public AccountFile mapRow(ResultSet resultSet, int i) throws SQLException {
                        AccountFile accountFile = new AccountFile();
                        accountFile.setGatewayOrderNo(resultSet.getString("gatewayOrderNo"));
                        accountFile.setTransactionId(resultSet.getString("transactionId"));
                        accountFile.setAmount(resultSet.getString("amount"));
                        accountFile.setPayTime(resultSet.getTimestamp("payTime"));
                        accountFile.setTransactionType("PAY");  // 支付
                        return accountFile;
                    }
                });


        return accountFiles;
    }


    public List<AccountFile> queryRefundList() {

//        String sql = "SELECT p.gatewayOrderNo , p.backOrderNo as transactionId,p.amount,p.refundUpdateDate as redundTime " +
//                "FROM gateway_order o, gateway_order_pay_detail p WHERE TO_DAYS(NOW()) - TO_DAYS( p.updateDate) = 1 " +
//                "AND o.paymentWay='501' AND p.`status`='00' AND p.refundStatus='00' AND o.gatewayOrderNo=p.gatewayOrderNo ORDER BY p.updateDate DESC";

        String sql = "SELECT p.gatewayOrderNo , p.backOrderNo as transactionId,p.amount,p.refundUpdateDate as redundTime " +
        "FROM gateway_order o, gateway_order_pay_detail p WHERE TO_DAYS('2017-09-02 16:42:05') - TO_DAYS( p.updateDate) = 1 " +
        "AND o.paymentWay='501' AND p.`status`='00' AND p.refundStatus='00' AND o.gatewayOrderNo=p.gatewayOrderNo ORDER BY p.updateDate DESC";

        List<AccountFile> accountFiles = jdbcTemplate.query(sql
                , new RowMapper<AccountFile>() {
                    @Override
                    public AccountFile mapRow(ResultSet resultSet, int i) throws SQLException {
                        AccountFile accountFile = new AccountFile();
                        accountFile.setGatewayOrderNo(resultSet.getString("gatewayOrderNo"));
                        accountFile.setTransactionId(resultSet.getString("transactionId"));
                        accountFile.setAmount(resultSet.getString("amount"));
                        accountFile.setPayTime(resultSet.getTimestamp(4));
                        accountFile.setTransactionType("REFUND");  // 退款
                        return accountFile;
                    }
                });
        return accountFiles;
    }


}
