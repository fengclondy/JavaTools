package com.zhx.vo;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2017/9/13.
 */
@Data
public class AccountFile implements IEntity{

    private String gatewayOrderNo;  // 网关订单号

    private String transactionId;   // 微信交易流水号

    private String amount;          // 订单金额

    private Date payTime;           // 支付时间

    private String transactionType; // 交易类型


    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String payTimeStr = format.format(payTime);
        StringBuffer sb = new StringBuffer();
        sb.append(gatewayOrderNo)
                .append(",")
                .append(transactionId)
                .append(",")
                .append(amount)
                .append(",")
                .append(payTimeStr)
                .append(",")
                .append(transactionType);

        return sb.toString();
    }

//    @Override
//    public String toString() {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String payTimeStr = format.format(payTime);
//        StringBuffer sb = new StringBuffer();
//        sb.append(gatewayOrderNo)
//                .append("|")
//                .append(transactionId)
//                .append("|")
//                .append(amount)
//                .append("|")
//                .append(payTimeStr)
//                .append("|")
//                .append(transactionType);
//
//        return sb.toString();
//    }
}
