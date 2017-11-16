package servlet;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.DownloadUtil;
import test.UploadUtils;
import utils.ChooseFolder;

/**
 * Servlet implementation class Addreptile
 */
@WebServlet("/addreptile")
public class Addreptile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Addreptile() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		boolean flag = false;
		String[] selected = request.getParameterValues("selected");
		List<String> selecteds = Arrays.asList(selected);
		// 调用工具类将图片下载到本地 图片重命名
		for (String s : selecteds) {
			String uuidName = UploadUtils.getUUIDName(s);
			// 默认地址
			String filePath = "D:/img/";
			if (ChooseFolder.getFolderPath() != null && ChooseFolder.getFolderPath() !="" ) {
				filePath = ChooseFolder.getFolderPath() + File.separator + uuidName;
			}
			flag = DownloadUtil.downloadImg(s, filePath , uuidName);

		}

		response.getOutputStream().print(flag);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
