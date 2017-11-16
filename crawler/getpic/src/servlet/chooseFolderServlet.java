package servlet;

import utils.ChooseFolder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description 选择文件夹的弹框
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-11-15 16:54
 */
@WebServlet("/chooseFolder")
public class chooseFolderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getAttribute("");
        JFrame f = new ChooseFolder();
        f.setSize(200, 300);
        f.setVisible(true);
    }
}
