package com.jimzhang.mailServer;


import com.jimzhang.utils.mail.Mail;
import com.jimzhang.utils.mail.MailUtil;

/**
 *
 * Created by jimzhang on 2017/9/21.
 */
public class MailSend {

    private static final String MAIL_HOST = "smtp.163.com"; // 内网IP
    private static final String MAIL_SENDER = "xxx@163.com";
    private static final String[] MAIL_RECEIVE = { "lisi@qq.com", "itzjm@qq.com"};
    private static final String MAIL_USERNAME = "xxx@163.com";
    private static final String MAIL_PASSWORD = "XXXX";
    private static final String MAIL_SUBJECT = "APP Store公众号对账单";
    private static final String MAIL_MESSAGE = "对账日期：%s，支付笔数：%d笔，退款笔数：%d笔";


    public static boolean sender(String data, int payNum, int refundNum){
        Mail mail = new Mail();
        mail.setHost(MAIL_HOST);            // 设置邮件服务器
        mail.setSender(MAIL_SENDER);        // 发送人
        mail.setReceiver(MAIL_RECEIVE);     // 接收人
        mail.setUsername(MAIL_USERNAME);    // 登录账号,一般都是和邮箱名一样吧
        mail.setPassword(MAIL_PASSWORD);    // 发件人邮箱的登录密码
        mail.setSubject(MAIL_SUBJECT);      // 主题
        mail.setMessage(String.format(MAIL_MESSAGE, data, payNum, refundNum));      // 内容
        boolean send = MailUtil.send(mail);

        return send;
    }


}
