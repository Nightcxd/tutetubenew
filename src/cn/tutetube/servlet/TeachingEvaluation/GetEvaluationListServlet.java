package cn.tutetube.servlet.TeachingEvaluation;

import cn.tutetube.util.bean.UserData;
import cn.tutetube.util.common.GetCurrentScore;
import cn.tutetube.util.common.TeachingEvaluation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cxd on 2017/1/3.
 */
@WebServlet(name = "GetEvaluationListServlet")
public class GetEvaluationListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        Cookie[] cookies = request.getCookies();
        String xh="";
        String psd="";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().trim().equals("xh")) {
                    xh=c.getValue().trim();
                }
                else if(c.getName().trim().equals("psd")){
                    psd=c.getValue().trim();
                }
            }
            if (xh.trim().equals("")||psd.trim().equals("")){
                response.getWriter().write("0");
            }
            else {
                TeachingEvaluation te=new TeachingEvaluation();
                UserData st=new UserData();
                st.setPasswd(psd);
                st.setNum(xh);
                String status=te.Evaluation(st).trim();
                if (status.equals("104")) {
                    response.getWriter().write("104");
                }
                else {
                    response.getWriter().write(status);
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
