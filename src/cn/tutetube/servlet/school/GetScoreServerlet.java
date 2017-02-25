package cn.tutetube.servlet.school;

import cn.tutetube.util.bean.UserData;
import cn.tutetube.util.common.GetCurrentScore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by cxd on 2016/12/31.
 */
@WebServlet(name = "GetScoreServerlet")
public class GetScoreServerlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String xh=request.getParameter("xh").trim();
        String psd=request.getParameter("psd").trim();
        String semester=new String(request.getParameter("semester").getBytes("iso8859-1"),"utf-8");
//        System.out.println(xh+psd+semester);
        GetCurrentScore getCurrentScore=new GetCurrentScore();
        UserData st=new UserData();
        st.setPasswd(psd.trim());
        st.setNum(xh.trim());
        String status=getCurrentScore.login(st.getNum(), st.getPasswd());
        if (status.trim().equals("101")) {
            response.getWriter().write("101");
        }
        else if (status.trim().equals("102")) {
            response.getWriter().write("102");
        }
        else if (status.trim().equals("103")) {
            response.getWriter().write("103");
        }
        else if (status.trim().equals("104")) {
            response.getWriter().write("104");
        }
        else
        {
            Cookie cookie = new Cookie("xh",xh.trim());
            Cookie cookie1=new Cookie("psd",psd.trim());
            Cookie cookie2=new Cookie("semester", URLEncoder.encode(semester.trim(), "UTF-8"));
           // cookie.setMaxAge();
            cookie.setPath("/");
            cookie1.setPath("/");
            cookie2.setPath("/");
            cookie.setMaxAge(365*24*60*60);
            cookie1.setMaxAge(365*24*60*60);
            cookie2.setMaxAge(60);
            response.addCookie(cookie);
            response.addCookie(cookie1);
            response.addCookie(cookie2);
            response.getWriter().write("1");
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
