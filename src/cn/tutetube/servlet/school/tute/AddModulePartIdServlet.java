package cn.tutetube.servlet.school.tute;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cxd on 2017/2/24.
 */
@WebServlet(name = "AddModulePartIdServlet")
public class AddModulePartIdServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(AddModulePartIdServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String modulePart_id=request.getParameter("modulePart_id").trim();
        log.info("模块id"+modulePart_id);
        Cookie cookie = new Cookie("modulePart_id",modulePart_id.trim());
        cookie.setPath("/");
        cookie.setMaxAge(24*60*60);
        response.addCookie(cookie);
        response.getWriter().write("1");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
