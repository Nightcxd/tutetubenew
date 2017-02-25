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

/**
 * Created by cxd on 2016/12/31.
 */
@WebServlet(name = "GetQueryResultServlet")
public class GetQueryResultServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        Cookie[] cookies = request.getCookies();
        String xh="";
        String psd="";
        if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().trim().equals("xh")) {
                        xh=c.getValue();
                    }
                    else if(c.getName().trim().equals("psd")){
                        psd=c.getValue();
                    }
                }
            if (xh.trim().equals("")||psd.trim().equals("")){
                response.getWriter().write("0");
            }
            else {
                GetCurrentScore getCurrentScore = new GetCurrentScore();
                UserData st = new UserData();
                st.setPasswd(psd.trim());
                st.setNum(xh.trim());
                //返回成绩单情况
                String gradelist=" <div id=\"table-title\" class=\"clearfix\">\n" +
                        "        <div class=\"table-h h-8-5\">课程名称</div>\n" +
                        "        <div class=\"table-h h-2-5\">成绩</div>\n" +
                        "    </div>";
                String itWhat=getCurrentScore.getGrades(st);
                if (itWhat.equals("1")) {
                    response.getWriter().write(gradelist+"<div class=\"table-row clearfix\">\n" +
                            "                <div class=\"table-score h-2-5 font-small-gray\">本学期成绩未发布！</div>\n" +
                            "</div>");
                }
                else
                {
                    response.getWriter().write(itWhat);
                }
            }
        }
        else
        {
            response.getWriter().write("0");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
