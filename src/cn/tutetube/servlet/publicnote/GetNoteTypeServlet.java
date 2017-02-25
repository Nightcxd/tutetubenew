package cn.tutetube.servlet.publicnote;

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
 * Created by cxd on 2017/1/31.
 */
@WebServlet(name = "GetNoteTypeServlet")
public class GetNoteTypeServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(GetNoteTypeServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        Cookie[] cookies = request.getCookies();
        String note_type = "";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().trim().equals("note_type")) {
                    note_type = URLDecoder.decode(c.getValue(), "UTF-8");//获取帖子类型
                }
            }
            log.info("GetNoteTypeServlet----"+note_type+"\n");
            if (note_type.equals("")){
                response.getWriter().write("0");//会话过期，请重新操作
            }
            else {
                response.getWriter().write(note_type);//返回帖子类型
            }
        }
        else {
            response.getWriter().write("0");//会话过期，请重新操作
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
