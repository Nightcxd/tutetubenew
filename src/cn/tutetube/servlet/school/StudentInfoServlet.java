package cn.tutetube.servlet.school;

import cn.tutetube.util.bean.UserData;
import cn.tutetube.util.common.GetStudentInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cxd on 2017/1/1.
 */
@WebServlet(name = "StudentInfoServlet")
public class StudentInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        Cookie[] cookies = request.getCookies();
        String xh="";
        String psd="";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().trim().equals("xh")) {
                    xh = c.getValue().trim();
                } else if (c.getName().trim().equals("psd")) {
                    psd = c.getValue().trim();
                }
            }
            if(xh.trim().equals("")||psd.trim().equals("")){
                response.getWriter().write("0");
            }
            else {
                GetStudentInfo studentInfo = new GetStudentInfo();
                UserData st = new UserData();
                st.setPasswd(psd);
                st.setNum(xh);
                response.getWriter().write(studentInfo.getRoll(st));
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
