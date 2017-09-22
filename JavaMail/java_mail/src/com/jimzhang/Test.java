package com.jimzhang;

import com.jimzhang.sendmail.MailSender;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;

/**
 * 测试
 *
 * @author zhangjm
 * @create 2017-09-22 14:24
 **/
public class Test {

    public static void main(String[] args) {
        MailSender ms;
        try {
            ms = new MailSender();
//            ms.setTo("zhangjinmiao@zihexin.com");
            // 发送一人
//            ms.sendMessage();
            // 发送多人
            ms.setTo("zhangjinmiao@zihexin.com,VIP<itzjm@qq.com>"); // 以英文','分隔，带用户名：用户名<邮箱>
            ms.sendMessageMulit();

        } catch (AddressException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


