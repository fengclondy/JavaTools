package com.zihexin.utils;

/**
 * 静态变量
 * Created by admin on 2017/8/1.
 */
public class Constants {


    //sftp
//    public static final String SFTP_IP = "123.207.209.72";
//    public static final int SFTP_PORT = 10021;
//    public static final String SFTP_USERNAME = "zihexin";
//    public static final String SFTP_PASSWORD = "df#nj2@fg23!7hgd%sd!s3n";
//    public static final String SFTP_PATH = "/data";


    //sftp
    public static final String SFTP_IP = "106.37.176.97"; //   10.6.5.35
//    public static final String SFTP_IP = "10.6.5.35"; //   10.6.5.35
    public static final int SFTP_PORT = 22;
    public static final String SFTP_USERNAME = "zhxs";
    public static final String SFTP_PASSWORD = "*@209872()+2*_070919";
    public static final String SFTP_PATH = "/zhxs/data";     // 文件保存路径
    public static final String FILE_TEMP_PATH = "wxdata"; // 文件临时保存路径
    public static final String SUFFIX_TXT = ".txt"; // txt文件后缀
    public static final String SUFFIX_CSV = ".csv"; // csv文件后缀

    //mysql - dev
    public static final String MYSQL_URL = "jdbc:mysql://10.6.4.154:3306/zhx_gateway?useUnicode=true&characterEncoding=utf-8";
    public static final String MYSQL_USERNAME = "zhx_online";
    public static final String MYSQL_PASSWORD = "123456";
    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    //mysql - prod
//    public static final String MYSQL_URL = "jdbc:mysql://10.10.126.61:3306/zhx_gateway?useUnicode=true&characterEncoding=utf-8";
//    public static final String MYSQL_USERNAME = "zhx_online";
//    public static final String MYSQL_PASSWORD = "10Liugehetao#22";
//    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

}
