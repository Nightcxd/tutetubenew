package cn.tutetube.servlet.queryservlet;

import cn.tutetube.util.queryall.ChineseQuery;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by cxd on 2017/1/1.
 */
@WebServlet(name = "ChineseQueryServlet")
public class ChineseQueryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String name=new String(request.getParameter("name").getBytes("iso8859-1"),"utf-8");
        String idCard=request.getParameter("idCard").trim();
        //System.out.println(name+idCard);
        ChineseQuery cq=new ChineseQuery();
        String status=cq.login(name,idCard);
        if (status.trim().equals("104")){
            response.getWriter().write("104");
        }
        else {
            Cookie info = new Cookie("cinfo", URLEncoder.encode(status, "UTF-8"));
            info.setPath("/");
            info.setMaxAge(365*24*60*60);
            response.addCookie(info);
            response.getWriter().write("1");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
