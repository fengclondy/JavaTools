package com.jimzhang.utils;

/**
 * 静态变量
 * Created by admin on 2017/8/1.
 */
public class Constants {



    //sftp
    public static final String SFTP_IP = "sftpIP"; //
    public static final int SFTP_PORT = 22;
    public static final String SFTP_USERNAME = "username";
    public static final String SFTP_PASSWORD = "password";
    public static final String SFTP_PATH = "/zhxs/data";     // 文件保存路径
    public static final String FILE_TEMP_PATH = "wxdata"; // 文件临时保存路径
    public static final String SUFFIX_TXT = ".txt"; // txt文件后缀
    public static final String SUFFIX_CSV = ".csv"; // csv文件后缀

    //mysql - dev
    public static final String MYSQL_URL = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8";
    public static final String MYSQL_USERNAME = "xxxxx";
    public static final String MYSQL_PASSWORD = "xxxxx";
    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";


}
