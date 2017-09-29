package com.jimzhang;



import com.jimzhang.mailServer.MailSend;
import com.jimzhang.utils.Constants;
import com.jimzhang.utils.DateUtil;
import com.jimzhang.utils.FileUtil;
import com.jimzhang.wechant.AccountFileDataService;
import com.jimzhang.utils.SFTPClientUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/9/14.
 */
public class ApplicationMain {

    //文件title
    private static final String[] DATA_TITLE = new String[]{"gatewayOrderNo", "transactionId", "amount", "dealTime", "transactionType"};


    public static void main(String[] args) {

        AccountFileDataService dataService = new AccountFileDataService();

        // 读取数据库获取支付数据
        List<String[]> payList = dataService.queryPayList();
        // 读取数据库获取退款数据
        List<String[]> refundList = dataService.queryRefundList();

//        // TODO 测试 start
//        String[] strs = {"zhxgw0001", "400001", "3", "2017-09-14 12:21:20","PAY"};
//        List<String[]> payList = new ArrayList<>();
//        payList.add(strs);
//        payList.add(strs);
//        payList.add(strs);
//        payList.add(strs);
//
//        String[] strs1 = {"zhxgw0001", "400001", "3", "2017-09-14 12:21:20", "REFUND"};
//        List<String[]> refundList = new ArrayList<>();
//        refundList.add(strs1);
//        // TODO 测试 end

        // 合并list
        List<String[]> allList = new ArrayList<>();
        allList.addAll(payList);
        allList.addAll(refundList);
        System.out.println("支付成功数据：" + payList.size() + "条");
        System.out.println("退款成功数据：" + refundList.size() + "条");
        System.out.println("总数据：" + allList.size() + "条");

        String formatPreDate = DateUtil.getFormatPreDate("yyyyMMdd");   // 20170914
        String fileName = formatPreDate + Constants.SUFFIX_TXT;     // 文件名
        String dirPath = Constants.FILE_TEMP_PATH + File.separator + formatPreDate;     // 文件夹 wxdata/20170914
        String fileAbsolutePath = dirPath + File.separator + fileName;   // 文件相对路径

        //生成文件夹 、文件
        FileUtil.createDirectory(dirPath);
        File dataFile = FileUtil.createFile(dirPath + File.separator + fileName);
        //写入数据
        FileUtil.createCsvFile(dataFile, DATA_TITLE, allList);
        System.out.println("数据写入成功");

        // 判断文件是否存在
        if (!FileUtil.isExits(dirPath, fileName)) {
            System.out.println("文件:" + fileName + "不存在");
             // 重新执行
            //生成文件夹 、文件
            FileUtil.createDirectory(dirPath);
            dataFile = FileUtil.createFile(dirPath + File.separator + fileName);
            //写入数据
            FileUtil.createCsvFile(dataFile, DATA_TITLE, allList);
            System.out.println("数据写入成功");
        }
        // 上传文件到sftp
        System.out.println("开始上传文件" + fileName + "到sftp服务器");

        // 上传路径 /zhxs/data/20170914
        String destFolder = Constants.SFTP_PATH + "/" + formatPreDate + "/";
        try {
            SFTPClientUtil.sshSftp(Constants.SFTP_IP, Constants.SFTP_USERNAME, Constants.SFTP_PASSWORD, Constants.SFTP_PORT, fileAbsolutePath, destFolder);
            System.out.println("文件上传成功！目录：" + destFolder);

            // 发送邮件通知
            System.out.println("开始发送邮件===============");
            MailSend mailSend = new MailSend();
            mailSend.sender(formatPreDate, payList.size(), refundList.size());
            System.out.println("==============");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件上传失败");
        }

    }
}
