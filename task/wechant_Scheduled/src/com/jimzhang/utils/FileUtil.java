package com.jimzhang.utils;

import com.csvreader.CsvWriter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * CSV工具类
 * Created by admin on 2017/7/31.
 */
public class FileUtil {

    /**
     * 生成csv文件
     */
    public static void createCsvFile(File file, String[] title, List<String[]> items){

        Writer writer = null;
        CsvWriter csvWriter = null;
        try {
            writer = new FileWriter(file);
            csvWriter = new CsvWriter(writer, ',');

            if (title != null) {
                csvWriter.writeRecord(title);
            }
            for (String[] strings : items){
                csvWriter.writeRecord(strings);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("创建csv文件失败！");
        }finally {
            if (csvWriter != null ){
                csvWriter.close();
            }
            if (writer != null ){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建文件
     */
    public static File createFile(String fileName){
        File file = new File(fileName);
        try {
            file.createNewFile();
            System.out.println(fileName + "文件创建！");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    /**
     * 创建文件夹
     */
    public static void createDirectory(String dirName){
        File dir = new File(dirName);
        if(!dir.exists()){
            System.out.println(dirName+"文件夹创建!");
            dir.mkdirs();
        }else{
            System.out.println(dirName+"文件夹已经存在!");
            dir.delete();
            dir.mkdirs();
        }
    }

    /**
     * 计算文件的md5
     */
    public static String getMd5(String filePath){
        File file = new File(filePath);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return DigestUtils.md5Hex(IOUtils.toByteArray(fis));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将目录下的文件压缩为tgz格式的文件
     * @param folder 文件目录
     * @param tgzFile 压缩后的tgz文件
     */
    public static void compressTgz(String folder,String tgzFile) {
        File file = new File(folder);
        File[] files = file.listFiles();
        File tempFile = new File(folder + File.separator + "temp.tar");
        File destFile = new File(tgzFile);
        try {
            compressTar(files,tempFile);
            compressGzip(tempFile,destFile);
            tempFile.deleteOnExit();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * tar压缩
     */
    public static void compressTar(File srcFile, File destFile) throws IOException {

        TarArchiveOutputStream out = null;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(srcFile), 1024);
            out = new TarArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(destFile), 1024));
            TarArchiveEntry entry = new TarArchiveEntry(srcFile.getName());
            entry.setSize(srcFile.length());
            out.putArchiveEntry(entry);
            IOUtils.copy(is, out);
            out.closeArchiveEntry();
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(out);
        }
    }

    /**
     *
     * @param srcFiles
     * @param destFile
     * @throws IOException
     */
    public static void compressTar(File[] srcFiles, File destFile) throws IOException {

        TarArchiveOutputStream out = null;
        InputStream is = null;
        try {
            out = new TarArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(destFile), 1024));
            for (int i = 0;i<srcFiles.length;i++){
                TarArchiveEntry entry = new TarArchiveEntry(srcFiles[i].getName());
                entry.setSize(srcFiles[i].length());
                out.putArchiveEntry(entry);
                is = new BufferedInputStream(new FileInputStream(srcFiles[i]), 1024);
                IOUtils.copy(is, out);
                out.closeArchiveEntry();
            }
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(out);
        }
    }
    /**
     * gzip压缩
     */
    public static void compressGzip(File srcFile, File destFile) throws IOException{
        OutputStream out = null;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(srcFile), 1024);
            out = new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(destFile), 1024));
            IOUtils.copy(is, out);
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(out);
        }
    }


    /**
     * 判断文件是否存在
     *
     * @param filePath 文件绝对路径
     * @param fileName 文件名
     * @return
     */
    public static boolean isExits(String filePath, String fileName) {

        File file = new File(filePath + File.separator + fileName);
        if (file.exists()) {
            return true;
        }
        return false;
    }

}
