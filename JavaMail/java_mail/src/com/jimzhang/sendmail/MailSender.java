package com.jimzhang.sendmail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;


/**
 * 用过初始化mail的发送的过程，
 * 略去设置邮箱中的各种的过程，直接从配置文件中读取要发送的内容与基本的配置文件
 * 只要用setTo("mailaddress@host");来设置发送给谁就行了，
 * 然后调用sendMessage();来发送邮件
 *
 * @author jimzhang
 */

public class MailSender {

	private String host;
	private String username;
	private String password;
	private String from;
	private String content;
	private String subject;
	MimeMessage msg;
	private String to;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * 创建session
	 * @return
	 */
    protected  Session createSession(){
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.auth", "true");

        // 创建认证
        Authenticator auth = new Authenticator() {
        @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        Session session = Session.getInstance(props, auth);

        return session;
    }

    /**
	 * 用以建 session
	 * message 中的各参数的设置
	 *
	 * @throws AddressException
	 * @throws MessagingException
	 */
    protected void mailPropSet() throws AddressException, MessagingException {
        // 创建session
        Session session = createSession();

        // 创建邮件信息的对像
        msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(RecipientType.TO, to); // 发送给一人
        msg.setSubject(subject);
        msg.setContent(content, "text/html;charset=utf-8");

        // 发送邮件
    }


    protected void mailPropSetMulit() throws MessagingException, UnsupportedEncodingException {
        Session session = createSession();
		// 创建邮件信息的对像
        msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));

		String[] mailToArrays = to.split(",");
		InternetAddress[] toAddress = new InternetAddress[mailToArrays.length];
		for (int i = 0; i < mailToArrays.length; i++) {
			toAddress[i] = parseInternetAddress(mailToArrays[i]);
		}
        msg.setRecipients(RecipientType.TO, toAddress); // RecipientType.TO//收信人 RecipientType.CC//抄送人 RecipientType.BCC//暗送人
        msg.setSubject(subject);
        msg.setContent(content, "text/html;charset=utf-8");
	}


	private InternetAddress parseInternetAddress(String address) throws AddressException, UnsupportedEncodingException {
    	InternetAddress actual = null;
		if ((address.contains("<")) && (address.contains(">"))) {
			String email = address.substring(address.indexOf("<") + 1, address.indexOf(">"));
			String personal = address.substring(0, address.indexOf("<"));
			actual = new InternetAddress(email, personal, "UTF-8");
		} else {
			actual = new InternetAddress(address);
		}
    	return actual;
	}



     // 收件人为一人
    public void sendMessage() throws MessagingException {
        mailPropSet();
        Transport.send(msg);
    }

    // 收件人为多人
    public void sendMessageMulit() throws MessagingException, UnsupportedEncodingException {
		mailPropSetMulit();
		Transport.send(msg);

    }

    /**
	 * 用以初始化各种参数，设置邮箱。
	 *
	 * @throws IOException
	 * @throws AddressException
	 * @throws MessagingException
	 */
    public MailSender() throws IOException, AddressException, MessagingException {
        SetProperties setProperties = new SetProperties();
        this.content = setProperties.getContent();
        this.from = setProperties.getFrom();
        this.host = setProperties.getHost();
        this.password = setProperties.getPassword();
        this.subject = setProperties.getSubject();
        this.username = setProperties.getUsername();

    }

}
