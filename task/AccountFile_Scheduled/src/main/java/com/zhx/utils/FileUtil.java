package com.zhx.utils;


import com.zhx.vo.AccountFile;
import com.zhx.vo.Header;
import com.zhx.vo.IEntity;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author jimzhang
 *
 */
public class FileUtil {

	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	private static SimpleDateFormat format_file = new SimpleDateFormat("yyyy" + File.separator + "MM" + File.separator + "dd");
	private static SimpleDateFormat format_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	private static SimpleDateFormat format_db = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat format_bckup = new SimpleDateFormat(".yyyyMMddHHmmss");
	private static SimpleDateFormat format_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");


	/**
	 * 查看某个日期文件夹内 是否已经有这个文件
	 * 没有就创建一个文件并返回
	 *
	 * @param filename	文件名
	 * @param date		文件夹名称
	 * @param path		文件保存路径(绝对)
	 * @return
	 */
	public static File getOrcreateFile(String path, String date, String filename) {
		if (null == date || "".equals(date)) {
			Calendar calendar = Calendar.getInstance();
			date = format_YYYYMMDD.format(calendar.getTime());
		}

		File file = new File(path + File.separator + date);
		if (!file.exists()) {
			//如果当天日期的文件夹没有创建就创建一个
			file.mkdirs();
		}
		file = new File(path + File.separator + date + File.separator + filename);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return file;
	}


	/**
	 * 接受数据并写入到文件中，header参数是文件头，不为空的话代表新创建文件从头开始写
	 * Hdeader为空的话 则是将content追加写入到指定日期的文件里
	 *
	 * @param _header  文件头
	 * @param content  内容不能超过五千条数据 否则抛出异常
	 * @param _date    可为空，为空则默认是今天  格式YYYY-MM-DD
	 * @param path     文件保存的路径 调用方自己传入
	 * @param filename 处理的文件名
	 */
	public static void createFile(IEntity _header, List<AccountFile> content, String _date, String path, String filename) throws Exception {
		if (content == null || StringUtils.isEmpty(filename) || StringUtils.isEmpty(path)) {
			throw new Exception("参数为空");
		}
//		if (content.size() > 5000) {
//			throw new Exception("传入数据超量");
//		}
		if (StringUtils.isEmpty(_date)) {
			Calendar calendar = Calendar.getInstance();

			_date = format_YYYYMMDD.format(calendar.getTime());
		}
		File file = getOrcreateFile(path, _date, filename);
		if (_header != null) {
			FileUtils.write(file, _header.toString(), "GBK", false);
			FileUtils.writeStringToFile(file, "\r\n", "GBK", true);
		}
		for (IEntity entity : content) {
			FileUtils.write(file, entity.toString(), "GBK", true);
			FileUtils.writeStringToFile(file, "\r\n", "GBK", true);
		}
	}

	/**
	 * 判断文件是否存在
	 * @param filePath	文件绝对路径
	 * @param fileName	文件名
     * @return
     */
	public static boolean isExits(String filePath, String fileName) {

		File file = new File(filePath + File.separator +fileName);
		if (file.exists()) {
			return true;
		}
		return  false;
	}


	/**
	 * 删除所有文件，包括文件夹
	 *
	 * @param dirpath
	 * @author : chenssy
	 * @date : 2016年5月23日 下午12:41:08
	 */
	public static void deleteAll(String dirpath) {
		File path = new File(dirpath);
		try {
			if (!path.exists())
				return;// 目录不存在退出
			if (path.isFile()) // 如果是文件删除
			{
				path.delete();
				return;
			}
			File[] files = path.listFiles();// 如果目录中有文件递归删除文件
			for (int i = 0; i < files.length; i++) {
				deleteAll(files[i].getAbsolutePath());
			}
			path.delete();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		String path = "E://temp";

		Header header = new Header("gatewayOrderNo", "transactionId", "amount", "dealTime", "transactionType");

		List<AccountFile> list = new ArrayList<>();

		for (int i =0;i<100000; i++) {
			AccountFile a = new AccountFile();
			a.setGatewayOrderNo("121");
			a.setTransactionId("4000000000000122");
			a.setAmount("100");
			a.setPayTime(new Date());
			a.setTransactionType("PAY");

			list.add(a);
		}
		System.out.println(list.size());
		String fileName = DateUtil.yesterdayDate() + ".txt";
		System.out.println(fileName);

		long start = System.currentTimeMillis();
		System.out.println(start);
		createFile(header,list, DateUtil.yesterdayDate(), path, fileName);
		long end = System.currentTimeMillis();

		DateUtil.dataDiff(start, end);

	}


}