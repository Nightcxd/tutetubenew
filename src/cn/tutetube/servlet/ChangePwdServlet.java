package cn.tutetube.servlet;

import cn.tutetube.bean.Student;
import cn.tutetube.util.StudentUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by cxd on 2017/1/27.
 */
@WebServlet(name = "ChangePwdServlet")
public class ChangePwdServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String oldpwd=request.getParameter("oldpwd").trim();
        String newpwd1=request.getParameter("newpwd1").trim();
        String newpwd2=request.getParameter("newpwd2").trim();
//        System.out.println(oldpwd+" "+newpwd1+"  "+newpwd2);
        if (newpwd1.equals(newpwd2)){
            Cookie[] cookies = request.getCookies();
            String nickname = "";
            String password = "";
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().trim().equals("nickname")) {
                        nickname = URLDecoder.decode(c.getValue().trim(), "UTF-8");
                    }
                }
                if (nickname.equals("")){
                    response.getWriter().write("102");//cookie失效
                }
                else {
                    StudentUtil su=new StudentUtil();
                    Student st=su.getStudentByNickName(nickname).get(0);
                    if (st.getPassword().equals(oldpwd.trim())){
                        st.setPassword(newpwd1.trim());
                        su.update(st);
                        //清除浏览器cookie
                        for(Cookie cookie:cookies){
                            cookie.setMaxAge(0);
                            cookie.setPath("/");
                            response.addCookie(cookie);
                        }
                        response.getWriter().write("1");
                    }
                    else {
                        response.getWriter().write("103");//旧密码输入错误！
                    }
                }
            }
            else
            {
                response.getWriter().write("101");//不存在cookie
            }

        }
        else {
            response.getWriter().write("100");//两次密码不相同
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
