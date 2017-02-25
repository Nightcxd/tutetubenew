package cn.tutetube.servlet.school;

import cn.tutetube.util.schoolutil.HtmlParse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cxd on 2016/11/21.
 */
@WebServlet(name = "TitleListServlet")
public class TitleListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        try {
            String title1=HtmlParse.test5("http://www.tute.edu.cn/");
            String time1=HtmlParse.test7("http://www.tute.edu.cn/");
            String title2=HtmlParse.test8("http://www.tute.edu.cn/");
            String time2=HtmlParse.test9("http://www.tute.edu.cn/");
            //写入校园网首页两条信息
            String title="<dt class=\"news-tit\">校园资讯</dt>\n" +
                    "    <dd class=\"news-content clearfix\">\n" +
                    "        <div class=\"nc-left fl\">\n" +
                    "            <img src=\"img/title1.jpg\">\n" +
                    "        </div>\n" +
                    "        <div class=\"nc-right fr\">\n" +
                    "            <a class=\"ncr-top/index.html\">"+title1.trim()+"\n" +
                    "            </a>\n" +
                    "            <span class=\"nc-time\">"+time1.trim()+"</span>\n" +
                    "        </div>\n" +
                    "    </dd>\n" +
                    "    <dd class=\"news-content clearfix\">\n" +
                    "        <div class=\"nc-left fl\">\n" +
                    "            <img src=\"img/title2.jpg\">\n" +
                    "        </div>\n" +
                    "        <div class=\"nc-right fr\">\n" +
                    "            <a class=\"ncr-top/index.html\">"+title2.trim()+"\n" +
                    "            </a>\n" +
                    "            <span class=\"nc-time\">"+time2.trim()+"</span>\n" +
                    "        </div>\n" +
                    "    </dd>";
           // System.out.println(title);
            response.getWriter().write(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request,response);
    }
}
