package cn.tutetube.servlet;

import cn.tutetube.dao.ICheckNickNameDao;
import cn.tutetube.dao.Imp.CheckNickNameDaoImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cxd on 2016/10/18.
 */
@WebServlet(name = "CheckNickNameServlet")
public class CheckNickNameServlet extends HttpServlet {
    private ICheckNickNameDao checkNickNameDaoImp = new CheckNickNameDaoImp();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String nickname = request.getParameter("nickname").trim();
        boolean result = checkNickNameDaoImp.check(nickname);
        if (result == true) {
            response.getWriter().write("1");//新用户
        } else {
            response.getWriter().write("0");//旧用户
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
