package cn.tutetube.servlet.queryservlet;

import cn.tutetube.util.bean.UserData;
import cn.tutetube.util.queryall.ScoreQuery;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by cxd on 2017/1/25.
 */
@WebServlet(name = "GetSelectSemesterScoreServlet")
public class GetSelectSemesterScoreServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        Cookie[] cookies = request.getCookies();
        String xh="";
        String psd="";
        String semester="";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().trim().equals("xh")) {
                    xh=c.getValue();
                }
                else if(c.getName().trim().equals("psd")){
                    psd=c.getValue();
                }
                else if(c.getName().trim().equals("semester")){
                    semester= URLDecoder.decode(c.getValue().trim(), "UTF-8");
                }
            }
            if (xh.trim().equals("")||psd.trim().equals("")){
                response.getWriter().write("0");
            }
            else if (semester.trim().equals("")){
                response.getWriter().write("2");
            }
            else {
                ScoreQuery sq=new ScoreQuery();
                UserData st = new UserData();
                st.setPasswd(psd.trim());
                st.setNum(xh.trim());
                String result=sq.getScore(st,semester.trim());
                if (result.equals("401")){
                    response.getWriter().write("401");
                }
                else if (result.equals("402")){
                    response.getWriter().write("402");
                }
                else if (result.equals("403")){
                    response.getWriter().write("403");
                }
                else if (result.equals("500")){
                    response.getWriter().write("500");
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
