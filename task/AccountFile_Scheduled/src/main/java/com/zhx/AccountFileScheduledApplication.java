package com.zhx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AccountFileScheduledApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountFileScheduledApplication.class, args);
		System.out.println("启动成功");
	}
}
