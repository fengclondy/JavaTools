package com.jimzhang.server;/**
 * Created by zhangjm on 2017/9/22.
 */


import com.jimzhang.util.Mail;
import com.jimzhang.util.MailUtil;

/**
 * 邮件服务 对外提供
 *
 * @author zhangjm
 * @create 2017-09-22 12:27
 **/
public class MailServer {

    /**
     *
     * @param sender    发送人，string
     * @param receiver  接收人，string数组，支持多个
     * @param strings   按顺序String：邮件服务器、发送账户、邮箱密码、主题、内容
     * @return
     */
    public static boolean sendMail(String sender, String[] receiver, String ... strings){
        Mail mail = new Mail();
        mail.setSender(sender); // 发送人
        mail.setReceiver(receiver); // 接收人
        mail.setHost(strings[0]); // 设置邮件服务器,如果不用163的,自己找找看相关的
        mail.setUsername(strings[1]); // 发件名,一般都是和邮箱名一样吧
        mail.setPassword(strings[2]); // 发件人邮箱的登录密码
        mail.setSubject(strings[3]);
        mail.setMessage(strings[4]);
        boolean send = MailUtil.send(mail);

        return send;
    }

    public static void main(String[] args) {

        sendMail(args[0],args);

    }
}
