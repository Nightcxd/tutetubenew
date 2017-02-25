package cn.tutetube.servlet.TeachingEvaluation;

import cn.tutetube.util.bean.UserData;
import cn.tutetube.util.common.GetSingleEvaluation;
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
 * Created by cxd on 2017/1/4.
 */
@WebServlet(name = "EvaluationDetailsServlet")
public class EvaluationDetailsServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(EvaluationDetailsServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        Cookie[] cookies = request.getCookies();
        String cname="";
        String xh="";
        String psd="";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().trim().equals("cname")) {
                    cname = URLDecoder.decode(c.getValue().trim(), "UTF-8");
                }
               else if (c.getName().trim().equals("xh")) {
                    xh=c.getValue().trim();
                }
                else if (c.getName().trim().equals("psd")) {
                    psd=c.getValue().trim();
                }
            }
            if (cname.trim().equals("")||xh.trim().equals("")||psd.trim().equals("")){
                response.getWriter().write("0");
            }
            else {
                GetSingleEvaluation getSingleEvaluation = new GetSingleEvaluation();
                UserData st = new UserData();
                st.setPasswd(psd.trim());
                st.setNum(xh.trim());
                //System.out.println(getCurrentScore.getGrades(st));
                //返回评估表
                String value=getSingleEvaluation.SingleEvaluation(st, cname.trim());
                if (value.equals("104")){
                    response.getWriter().write("104");
                }
                else {
                    //log.info(value);
                    response.getWriter().write(value);
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
