package cn.tutetube.servlet.queryservlet;

import cn.tutetube.util.bean.UserData;
import cn.tutetube.util.common.GetMyCourse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cxd on 2017/2/1.
 */
@WebServlet(name = "GetMyCourseServlet")
public class GetMyCourseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        Cookie[] cookies = request.getCookies();
        String xh="";
        String psd="";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().trim().equals("xh")) {
                    xh = c.getValue();
                } else if (c.getName().trim().equals("psd")) {
                    psd = c.getValue();
                }
            }
            if (xh.trim().equals("") || psd.trim().equals("")) {
                response.getWriter().write("0");
            }
            else {
                GetMyCourse gnc=new GetMyCourse();
                UserData st=new UserData();
                st.setPasswd(psd.trim());
                st.setNum(xh.trim());
                String result=gnc.getCourseTable(st);
                if (result.equals("104")){
                    response.getWriter().write("104");//网络异常
                }
                else {
                    response.getWriter().write(result);
                }
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
