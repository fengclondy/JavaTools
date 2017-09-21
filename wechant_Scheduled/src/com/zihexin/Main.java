package com.zihexin;//package com.zihexin;
//
//import java.io.File;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Main {
//
//    //生成文件根目录
//    private static final String TEMP_PATH_NAME = "data";
//    //生成文件名
//    private static final String FILE_NAME = "didi_bind_list_";
//    //文件title
//    private static final String[] DATA_TITLE = new String[]{"user_id","card_type","amount","bind_time"};
//    //文件title
//    private static final String[] README_TITLE = new String[]{"total_number","total_amount","md5"};
//
//
//    public static void main(String[] args) {
//        //获取数据
//        DataService dataService = new DataService();
//
//        String formatPreDate = DateUtil.getFormatPreDate("yyyyMMdd");
//        String recordDate = DateUtil.getFormatPreDate("yyyy-MM-dd");
//        System.out.println("数据日期："+formatPreDate);
//        List<String[]> data = dataService.getData(recordDate);
//
//        String totalNumber,totalAmount;
//
//        if (data == null){
//            System.out.println(formatPreDate + ":data is null");
//            return;
//        }
//        if (data.size() == 0){
//            totalAmount = "0";
//            totalNumber = "0";
//        }else{
//            totalNumber = String.valueOf(data.size());
//            BigDecimal bd = new BigDecimal("0");
//            for (String[] strings : data){
//                bd = bd.add(new BigDecimal(strings[2]));
//            }
//            totalAmount = bd.toString();
//        }
//        System.out.println("totalAmount:"+totalAmount);
//        System.out.println("totalNumber:"+totalNumber);
//        //生成文件
//        String dirPath = TEMP_PATH_NAME + File.separator + formatPreDate;
//        FileUtil.createDirectory(dirPath);
//        String fileName = FILE_NAME+formatPreDate+".csv";
//        File dataFile = FileUtil.createFile(dirPath + File.separator + fileName);
//
//        File readme = FileUtil.createFile(dirPath + File.separator + "readme.txt");
//        //写入数据
//        FileUtil.createCsvFile(dataFile,DATA_TITLE,data);
//
//        //文件md5
//        String md5 = FileUtil.getMd5(dirPath+File.separator+fileName);
//
//        //写入readme文件中
//        ArrayList<String[]> strings = new ArrayList<>();
//        String[] strings1 = {totalNumber, totalAmount, md5};
//        strings.add(strings1);
//        FileUtil.createCsvFile(readme,README_TITLE,strings);
//        //文件压缩tgz格式
//        String tgzFileName = dirPath + File.separator + FILE_NAME + formatPreDate + ".tgz";
//        FileUtil.compressTgz(dirPath,tgzFileName);
//        //文件上传
////        String destFolder = Constants.SFTP_PATH +"/" +formatPreDate + "/";
////        try {
////            SFTPClientUtil.sshSftp(Constants.SFTP_IP,Constants.SFTP_USERNAME,Constants.SFTP_PASSWORD,Constants.SFTP_PORT,tgzFileName,destFolder);
////        } catch (Exception e) {
////            e.printStackTrace();
////            System.out.println("文件上传失败");
////        }
//    }
//
//}