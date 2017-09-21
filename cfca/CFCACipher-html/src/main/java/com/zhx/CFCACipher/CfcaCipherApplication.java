package com.zhx.CFCACipher;

import com.zhx.CFCACipher.server.CFCAServerDecryption;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class CfcaCipherApplication {

	public static void main(String[] args) {
		SpringApplication.run(CfcaCipherApplication.class, args);
		System.out.println("启动成功");
	}


	@GetMapping("/")
	public String test(String sm2_data, String sm2_clientRandom) {

//		String sm2_data = "a2J6g+Gx00NIfLsOtgYLiw==";
//		String sm2_clientRandom = "ExP0tr0WaQmp0SVEzyHGUtCeesWt9M5aR92pY7I1q+jkt8luTv8SRA3cNdEbatLHG+JaLTdID+sTvXGrl0JdiMU6nJozsLdiFsEMnEakBcFKiY9I8kZQWcsIW1FFlVuNdHJj4i5C6bfMC8xgHa6UXw==";

        String password = CFCAServerDecryption.getPassword(sm2_data, sm2_clientRandom);
        System.out.println(password);
		return password;
	}

}
