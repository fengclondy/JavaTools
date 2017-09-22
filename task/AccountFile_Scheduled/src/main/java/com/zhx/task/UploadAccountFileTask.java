package com.zhx.task;

import com.zhx.service.AccountFileService;
import com.zhx.utils.DateUtil;
import com.zhx.utils.FileUtil;
import com.zhx.utils.SFTPUtils;
import com.zhx.vo.AccountFile;
import com.zhx.vo.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * 上传文件定时任务
 * Created by admin on 2017/9/13.
 */
@Component
public class UploadAccountFileTask {

    private static Logger logger = LoggerFactory.getLogger(UploadAccountFileTask.class);


    @Value("${file.baseFilePath}")
    private String baseFilePath;

    @Value("${sftp_host}")
    private String host;

    @Value("${sftp_username}")
    private String username;

    @Value("${sftp_password}")
    private String password;

    @Value("${sftp_destFilePath}")
    private String destFilePath;

    private static final String UPLOAD_TIME = "0 50 2 * * *"; // 每天凌晨3点确保文件已生成，所有2：50开始执行       0 0 3 ? * * *

    private static final String suffix_txt = "txt"; // txt文件后缀

    @Autowired
    private AccountFileService accountFileService;


    @Scheduled(cron = UPLOAD_TIME)
//    @Scheduled(fixedRate = 5000)
    public void uploadFile(){
        logger.info("定时任务开始，读取数据库获取数据-----");
        // 读取数据库获取支付数据
        List<AccountFile> accountPayFiles = accountFileService.listPayAccount();
        logger.info("读取支付数据成功，共" + accountPayFiles.size() + "条");
        // 读取数据库获取退款数据
        List<AccountFile> accountRefundFiles = accountFileService.listRefundAccount();
        logger.info("读取退款数据成功，共" + accountRefundFiles.size() + "条");


        String propertyPath = System.getProperty("user.dir"); // 项目绝对路径
        String folderName = DateUtil.yesterdayDate();         // 文件夹名称 20170913
        String fileName = DateUtil.yesterdayDate() + "." + suffix_txt;  // 文件名 20170913.txt
        String folderAbsolutePath = destFilePath + File.separator + folderName + File.separator;   // 文件夹绝对路径
        String fileAbsolutePath = propertyPath + File.separator + baseFilePath + File.separator + folderName + File.separator;   // 文件绝对路径
        logger.info("项目绝对路径:" + propertyPath);
        logger.info("文件夹绝对路径:" + folderAbsolutePath);
        logger.info("文件绝对路径:" + fileAbsolutePath);
        logger.info("文件名:" + fileName);

        // 判断文件是否存在，存在则删除，防止多次执行内容重复
        if (FileUtil.isExits(fileAbsolutePath, fileName)) {
            FileUtil.deleteAll(fileAbsolutePath); // 删除目录20170914下所有文件
            logger.info("文件已存在，删除目录：" + fileAbsolutePath + "下所有文件成功！");
        }
        // 将数据写入文件
        Header header = new Header("gatewayOrderNo", "transactionId", "amount", "dealTime", "transactionType");

        try {
            FileUtil.createFile(header, accountPayFiles, DateUtil.yesterdayDate(), baseFilePath, fileName);
            // 追加到文件中
            FileUtil.createFile(null, accountRefundFiles, DateUtil.yesterdayDate(), baseFilePath, fileName);
            logger.info("写入数据到文件成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 判断文件是否生成成功
        if (!FileUtil.isExits(fileAbsolutePath, fileName)) {
            logger.info("文件:" + fileName + "不存在，重新调用方法");
            uploadFile(); // 重新执行
        }
        // 上传文件到sftp
        logger.info("开始上传文件" + fileName + "到sftp服务器中的" + folderAbsolutePath + "文件夹下");
        SFTPUtils.uploadFile(host, -1, username, password, fileAbsolutePath, folderAbsolutePath, fileName);

    }

}
