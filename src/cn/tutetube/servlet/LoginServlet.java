package cn.tutetube.servlet;

import cn.tutetube.dao.ILoginDao;
import cn.tutetube.dao.Imp.LoginDaoImp;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by cxd on 2016/10/2.
 */
@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(LoginServlet.class);
    private ILoginDao iLoginDao=new LoginDaoImp();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String nickname = request.getParameter("nickname").trim();
        String password = request.getParameter("password").trim();
        log.info(nickname+"   "+password);
        //System.out.println(nickname+password);
        HttpSession session = request.getSession();
        if (nickname==""||password==""){
            response.getWriter().write("用户名或密码不能为空！");
        }
        else {
            boolean iswhat=iLoginDao.loginCheck(nickname,password);
            if (iswhat==true){
//                Cookie cookie = new Cookie("nickname",nickname);
//                Cookie cookie1 = new Cookie("password",password);
//                cookie.setPath("/");
//                cookie1.setPath("/");
//                cookie.setMaxAge(24*60*60);
//                cookie1.setMaxAge(24*60*60);
//                response.addCookie(cookie);
//                response.addCookie(cookie1);
              response.getWriter().write("true");
            }
            else {
                response.getWriter().write("用户名或密码错误！");
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
