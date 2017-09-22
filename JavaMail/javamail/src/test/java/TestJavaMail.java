import com.jimzhang.util.Mail;
import com.jimzhang.util.MailUtil;

public class TestJavaMail {

    public static void main(String[] args) {
        Mail mail = new Mail();
        mail.setHost("10.6.4.78"); // 设置邮件服务器,如果不用163的,自己找找看相关的
        mail.setSender("zhangjinmiao@zihexin.com");
        String[] str = {"zhangjinmiao@zihexin.com", "zjm528el@163.com"};
        mail.setReceiver(str); // 接收人
        mail.setUsername("zhangjinmiao@zihexin.com"); // 登录账号,一般都是和邮箱名一样吧
        mail.setPassword("ZHXabc123"); // 发件人邮箱的登录密码
        mail.setSubject("APP Store公众号对账单");
        mail.setMessage("2017-09-20 支付笔数500笔，支付金额5000￥；退款笔数2笔，退款金额200￥，净额：4800￥");
        MailUtil.send(mail);

        System.out.println("send ok");

//        SimpleEmail email = new SimpleEmail();
//        try {
//
//            email.setHostName("10.6.4.78"); //Yahoo的服务器地址是：smtp.mail.yahoo.com
//            email.addTo("1539745948@qq.com", "John Doe");
//            email.addTo("1539745948@qq.com", "zjm528el@163.com");
//            email.setFrom("zhangjinmiao@zihexin.com", "张晋苗");
//            email.setAuthentication("zhangjinmiao@zihexin.com", "ZHXabc123");
//            email.setSubject("APP Store公众号对账单");
//            email.setMsg("2017-09-20 支付笔数500笔，支付金额5000￥；退款笔数2笔，退款金额200￥，净额：4800￥");
//            email.send();
//
//        } catch (EmailException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }
}
