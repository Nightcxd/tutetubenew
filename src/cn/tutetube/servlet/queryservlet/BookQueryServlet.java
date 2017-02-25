package cn.tutetube.servlet.queryservlet;

import cn.tutetube.util.queryall.BookInfo;
import cn.tutetube.util.queryall.LibraryInfoQuery;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by cxd on 2017/1/2.
 */
@WebServlet(name = "BookQueryServlet")
public class BookQueryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String user_id=request.getParameter("user_id").trim();
        String password=request.getParameter("password").trim();
        BookInfo bq=new BookInfo();
        String status=bq.login(user_id.trim(),password.trim()).trim();
        if (status.trim().equals("101")){
            response.getWriter().write("101");
        }
        else if(status.trim().equals("102")){
            response.getWriter().write("102");
        }
        else if(status.trim().equals("103")){
            response.getWriter().write("103");
        }
//        else if(status.trim().equals("104")){
//            response.getWriter().write("104");
//        }
        else {
            Cookie info = new Cookie("bookInfo", URLEncoder.encode(status, "UTF-8"));
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
