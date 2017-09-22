package com.zhx.utils.file;

import com.alibaba.fastjson.JSON;
import com.zhx.utils.DateUtil;
import com.zhx.vo.AccountFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xiaolin  on 2017/8/8.
 */
public class FileIOOperateV2 {
    private static final String FILE_PATH = "E:\\temp\\20170917\\20170917.txt";
    private static final String FILE_OUT_PATH = "E:\\temp\\20170917\\1.txt";

    /**
     * 以字节为单位读写文件内容
     *
     * @param filePath
     *            ：需要读取的文件路径
     */
    public static void readFileByByte(String filePath) {
        File file = new File(filePath);
        // InputStream:此抽象类是表示字节输入流的所有类的超类。
        InputStream ins = null;
        OutputStream outs = null;
        try {
            // FileInputStream:从文件系统中的某个文件中获得输入字节。
            ins = new FileInputStream(file);
            outs = new FileOutputStream("d:/work/readFileByByte.txt");
            int temp;
            // read():从输入流中读取数据的下一个字节。
            while ((temp = ins.read()) != -1) {
                outs.write(temp);
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (ins != null && outs != null) {
                try {
                    outs.close();
                    ins.close();
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
    }

    /**
     * 以字符为单位读写文件内容
     *
     * @param filePath
     */
    public static void readFileByCharacter(String filePath) {
        File file = new File(filePath);
        // FileReader:用来读取字符文件的便捷类。
        FileReader reader = null;
        FileWriter writer = null;
        try {
            reader = new FileReader(file);
            writer = new FileWriter(FILE_OUT_PATH);
            int temp;
            while ((temp = reader.read()) != -1) {
                writer.write((char)temp);
            }
        } catch (IOException e) {
            e.getStackTrace();
        } finally {
            if (reader != null && writer != null) {
                try {
                    reader.close();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 以行为单位读写文件内容
     *
     * @param filePath
     */
    public static void readFileByLine(String filePath) {
        File file = new File(filePath);
        // BufferedReader:从字符输入流中读取文本，缓冲各个字符，从而实现字符、数组和行的高效读取。
        BufferedReader bufReader = null;
        BufferedWriter bufWriter = null;
        try {
            // FileReader:用来读取字符文件的便捷类。
            bufReader = new BufferedReader(new FileReader(file));
            bufWriter = new BufferedWriter(new FileWriter(FILE_OUT_PATH));
            // buf = new BufferedReader(new InputStreamReader(new
            // FileInputStream(file)));
            String temp = null;
            while ((temp = bufReader.readLine()) != null) {
                bufWriter.write(temp+"\n");
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (bufReader != null && bufWriter != null) {
                try {
                    bufReader.close();
                    bufWriter.close();
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
    }

    /**
     * 使用Java.nio ByteBuffer字节将一个文件输出至另一文件
     *
     * @param filePath
     */
    public static void readFileByBybeBuffer(String filePath) {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            // 获取源文件和目标文件的输入输出流
            in = new FileInputStream(filePath);
            out = new FileOutputStream(FILE_OUT_PATH);
            // 获取输入输出通道
            FileChannel fcIn = in.getChannel();
            FileChannel fcOut = out.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                // clear方法重设缓冲区，使它可以接受读入的数据
                buffer.clear();
                // 从输入通道中将数据读到缓冲区
                int r = fcIn.read(buffer);
                if (r == -1) {
                    break;
                }
                // flip方法让缓冲区可以将新读入的数据写入另一个通道
                buffer.flip();
                fcOut.write(buffer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null && out != null) {
                try {
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public static void readFileByNio(String content, File inFile){

        FileOutputStream fos = null;
        FileChannel channel = null;
        try {
            fos = new FileOutputStream(inFile, true);
            channel = fos.getChannel(); // 获取文件通道
            //构造一个预设1024byte的nio的缓冲容器
            ByteBuffer buf = ByteBuffer.allocate(1024);
            String str = content + "\n";
            //把数据写入缓冲容器
            buf.put(str.getBytes());
            //开启容器写模式,让缓冲区将新读入的数据写入另一个通道
            buf.flip();
            //把容器中置入的数据写入文件通道
            while (buf.hasRemaining()) {
                channel.write(buf);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {//关闭流
                if (channel != null) channel.close();
                if (fos != null) fos.close();
            } catch (IOException e) {

            }
        }
    }

    public static long getTime(){
        return System.currentTimeMillis();
    }

    public static void main(String args[]) {
        long time1 = getTime() ;
        // readFileByByte(FILE_PATH);// 8734,8281,8000,7781,8047
        // readFileByCharacter(FILE_PATH);// 734, 437, 437, 438, 422
        // readFileByLine(FILE_PATH);// 110, 94,  94,  110, 93
        readFileByBybeBuffer(FILE_PATH);// 125, 78,  62,  78, 62
        long time2 = getTime() ;
        System.out.println(time2-time1);
        DateUtil.dataDiff(time1, time2);

        List<AccountFile> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            AccountFile a = new AccountFile();
            a.setGatewayOrderNo("121");
            a.setTransactionId("4000000000000122");
            a.setAmount("100");
            a.setPayTime(new Date());
            a.setTransactionType("PAY");

            list.add(a);
        }
        File fout1 = new File("E:\\temp\\20170917\\2.txt");//写出的文件
        String string = JSON.toJSONString(list);
        long start1 = System.currentTimeMillis();
        readFileByNio(string, fout1 );
        long end1 = System.currentTimeMillis();
        DateUtil.dataDiff(start1, end1);
    }
}
