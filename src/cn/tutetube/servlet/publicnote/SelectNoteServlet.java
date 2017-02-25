package cn.tutetube.servlet.publicnote;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by cxd on 2017/1/27.
 */
@WebServlet(name = "SelectNoteServlet")
public class SelectNoteServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(SelectNoteServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String note_type=new String(request.getParameter("note_type").getBytes("iso8859-1"),"utf-8");
        log.info("SelectNoteServlet-----"+note_type+"\n");
        Cookie info = new Cookie("note_type", URLEncoder.encode(note_type.trim(), "UTF-8"));
        info.setPath("/");
        info.setMaxAge(365*24*60*60);
        response.addCookie(info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request,response);
    }
}
