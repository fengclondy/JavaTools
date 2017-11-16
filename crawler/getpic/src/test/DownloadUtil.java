package test;
import java.io.*;
import java.net.*;
public class DownloadUtil {
	
// 下载指定路径图片
public static boolean downloadImg(String netImg,String filePath, String fileName){
	try {
		//第一步，创建网络连接
		URL url = new URL(netImg);
		//第二步，打开连接
		URLConnection urlconnection = url.openConnection();
		//设置请求超时为5s
		urlconnection.setConnectTimeout(5 * 1000);
		//第三步，获取流对象
		InputStream inputStream = urlconnection.getInputStream();
		// 1K的数据缓冲
		byte[] bs = new byte[1024];
		//第四步，创建本地文件路径
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		//第五步，下载
		OutputStream outputStream = new FileOutputStream(filePath + fileName);
		int temp = 0;
		while((temp = inputStream.read(bs))!=-1){
			outputStream.write(bs,0,temp);
		}
		//第六步，关闭IO
		outputStream.close();
		inputStream.close();
		return true;
	} catch (Exception e) {
		e.printStackTrace();
		return false;
	}
}
// 获取网页源代码
public static String htmlSource(String link,String encoding){
	StringBuilder stringBuilder = new StringBuilder();
	//1，创建连接
	try {
		URL url=new URL(link);
		//2，打开连接
		URLConnection urlConnection =url.openConnection();
//		urlConnection.setRequestProperty("User-Agent", "MSIE");
//		urlConnection.setConnectTimeout(10000);
		//3，打开流
		InputStream inputStream = urlConnection.getInputStream();
		//4,利用转换流将字节流转换为字符流，并存入缓冲区
		InputStreamReader isr = new InputStreamReader(inputStream,encoding);
		BufferedReader bufferedReader = new BufferedReader(isr);
		String line = null;
		while((line = bufferedReader.readLine())!=null){
			stringBuilder.append(line+"\r\n");
		}
		//5,关闭
		bufferedReader.close();
		inputStream.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return stringBuilder.toString();
}
}
