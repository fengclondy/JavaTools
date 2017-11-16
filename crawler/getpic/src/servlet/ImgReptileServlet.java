package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.DownloadUtil;
import test.getUrl;

/**
 * Servlet implementation class ImgReptileServlet
 */
public class ImgReptileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String url = request.getParameter("url");
		// 获取目标网址的主域名
		String firstUrl = getUrl.getFirstUrl(url);

		List<String> list = new ArrayList<String>();

		// 调用工具类
		String htmlSource = DownloadUtil.htmlSource(url, "utf-8");
		// 获取图片url
		List<String> imageSrc = getUrl.getImageSrc(htmlSource);
		for (int i = 0; i < imageSrc.size(); i++) {
			if (!imageSrc.get(i).contains("http://") && !imageSrc.get(i).contains("https://")) {
				list.add(firstUrl + imageSrc.get(i));
				continue;
			}
			list.add(imageSrc.get(i));
		}


		// 将list集合放在request
		request.setAttribute("imageSrc", list);

		request.getRequestDispatcher("/index.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
