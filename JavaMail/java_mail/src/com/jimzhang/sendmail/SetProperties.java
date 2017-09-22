package com.jimzhang.sendmail;

import java.io.IOException;
import java.util.Properties;

/**
 * 用以获得java mail 中的配置文件
 * @author PCPC
 *
 */
public class SetProperties {

	private String subject;
	private String content;
	private String from;
	private String host;
	private String username;
	private String password;


		public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

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


	/**
	 * 初始化各各参数
	 * @throws IOException
	 */
		public SetProperties() throws IOException{
			Properties prop=new Properties();
			prop.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
			this.subject=prop.getProperty("subject");
			this.content=prop.getProperty("content");
			this.from=prop.getProperty("from");
			this.host=prop.getProperty("host");
			this.username=prop.getProperty("username");
			this.password=prop.getProperty("password");
		}	
}
