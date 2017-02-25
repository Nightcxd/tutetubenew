package cn.tutetube.servlet.queryservlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by cxd on 2017/1/2.
 */
@WebServlet(name = "BookInfoServlet")
public class BookInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        Cookie[] cookies = request.getCookies();
        String bookInfo = "";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().trim().equals("bookInfo")) {
                    bookInfo = URLDecoder.decode(c.getValue().trim(), "UTF-8");
                }
            }
            if (bookInfo.trim().equals("")){
                response.getWriter().write("0");
            }
            else {
                response.getWriter().write(bookInfo);
            }
        }
        else {
            response.getWriter().write("0");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
