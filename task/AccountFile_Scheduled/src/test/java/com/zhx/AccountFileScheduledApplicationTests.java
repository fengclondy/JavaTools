package com.zhx;

import com.zhx.utils.DateUtil;
import com.zhx.utils.FileUtil;
import com.zhx.utils.SFTPUtils;
import com.zhx.vo.AccountFile;
import com.zhx.service.AccountFileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountFileScheduledApplicationTests {

	@Value("${file.baseFilePath}")
	private String baseFilePath;

	@Autowired
	AccountFileService accountFileService;

	@Test
	public void contextLoads() throws Exception {

		List<AccountFile> accountFiles = accountFileService.listPayAccount();
		for (AccountFile accountFile : accountFiles) {
			System.out.println(accountFile.toString());
		}
		System.out.println(accountFiles.size());

		String fileName = DateUtil.yesterdayDate() + ".txt";
		FileUtil.createFile(null, accountFiles, DateUtil.yesterdayDate(), baseFilePath, fileName);

		List<AccountFile> accountFiles1 = accountFileService.listRefundAccount();
		FileUtil.createFile(null,accountFiles1, DateUtil.yesterdayDate(), baseFilePath, fileName);

		String propertyPath = System.getProperty("user.dir"); // 项目绝对路径
		String folderName = DateUtil.yesterdayDate();         // 文件夹名称 20170913
		String fileAbsolutePath = propertyPath + File.separator + baseFilePath + File.separator + folderName;   // 文件绝对路径

		String host = "10.6.5.35";
		String username = "zhxs";
		String password = "ZHXzhx123123";
		String srcPath = fileAbsolutePath;
		String destPath = "/user/zhxs";

		System.out.println("host:" + host);
		System.out.println("username:" + username);
		System.out.println("password:" + password);
		System.out.println("srcPath:" + srcPath);
		System.out.println("destPath:" + destPath);


		SFTPUtils.uploadFile(host, -1, username, password, srcPath, destPath, fileName);
	}



}
