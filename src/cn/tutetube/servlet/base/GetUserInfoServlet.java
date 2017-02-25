package cn.tutetube.servlet.base;

import cn.tutetube.bean.Student;
import cn.tutetube.util.StudentUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by cxd on 2017/2/17.
 */
@WebServlet(name = "GetUserInfoServlet")
public class GetUserInfoServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(GetUserInfoServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        Cookie[] cookies = request.getCookies();
        String nickname = "";
        String password = "";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().trim().equals("nickname")) {
                    nickname = URLDecoder.decode(c.getValue().trim(), "UTF-8");
//                    System.out.println(nickname);
                } else if (c.getName().trim().equals("password")) {
                    password = c.getValue().trim();
//                   System.out.println(password);
                }

            }
            if (nickname.equals("")||password.equals("")){
                response.getWriter().write("0");
            }
            else {
                StudentUtil su=new StudentUtil();
                try {
                    Student st= su.getStudentByNicknameAndPassword(nickname.trim(), password.trim());
                    log.info("学生对象获取成功！");
                    response.getWriter().write(""+st.getNicknamecn()+"#$%"+st.getSex()+"#$%"+st.getDescription()+"");
                }
                catch (Exception e){
                    log.error("异常信息",e);
                    response.getWriter().write("0");
                }

            }
        }
        else {
            response.getWriter().write("0");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
