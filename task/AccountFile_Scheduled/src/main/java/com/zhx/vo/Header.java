package com.zhx.vo;

import lombok.Data;


/**
 * 文件头信息，可加可不加
 * Created by admin on 2017/9/13.
 */
@Data
public class Header implements IEntity {

    private String gatewayOrderNo;  // 网关订单号
    private String transactionId;   // 微信交易流水号
    private String amount;          // 订单金额
    private String dealTime;        // 交易时间 （支付/退款）
    private String transactionType; // 交易类型


    public Header(String gatewayOrderNo, String transactionId, String amount, String dealTime, String transactionType) {
        this.gatewayOrderNo = gatewayOrderNo;
        this.transactionId = transactionId;
        this.amount = amount;
        this.dealTime = dealTime;
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(gatewayOrderNo)
                .append(",")
                .append(transactionId)
                .append(",")
                .append(amount)
                .append(",")
                .append(dealTime)
                .append(",")
                .append(transactionType);

        return sb.toString();
    }

//    @Override
//    public String toString() {
//        StringBuffer sb = new StringBuffer();
//        sb.append(gatewayOrderNo)
//                .append("|")
//                .append(transactionId)
//                .append("|")
//                .append(amount)
//                .append("|")
//                .append(payTime)
//                .append("|")
//                .append(transactionType);
//
//        return sb.toString();
//    }
}
