package cn.tutetube.servlet.TeachingEvaluation;

import cn.tutetube.util.common.TeachingEvaluation;
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
 * Created by cxd on 2017/1/4.
 */
@WebServlet(name = "EvaluationServlet")
public class EvaluationServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(EvaluationServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String temp=new String(request.getParameter("cname").getBytes("iso8859-1"),"utf-8");
        String wjbm=request.getParameter("wjbm").trim();
        String bpr=request.getParameter("bpr").trim();
        String pgnr=request.getParameter("pgnr").trim();
        log.info(wjbm+"  "+bpr+"   "+pgnr);
        int size=temp.length();
        String cname=temp.substring(0,size-1);
        if (cname.equals("")||wjbm.equals("")||bpr.equals("")||pgnr.equals("")){
            response.getWriter().write("101");//网络异常
        }
        else {
            Cookie info = new Cookie("cname", URLEncoder.encode(cname, "UTF-8"));
            Cookie info1 = new Cookie("wjbm", wjbm);
            Cookie info2 = new Cookie("bpr", bpr);
            Cookie info3 = new Cookie("pgnr", pgnr);
            info.setPath("/");
            info1.setPath("/");
            info2.setPath("/");
            info3.setPath("/");
            info.setMaxAge(365*24*60*60);
            info1.setMaxAge(365*24*60*60);
            info2.setMaxAge(365*24*60*60);
            info3.setMaxAge(365*24*60*60);
            response.addCookie(info);
            response.addCookie(info1);
            response.addCookie(info2);
            response.addCookie(info3);
            response.getWriter().write("1");
        }
       // System.out.println(cname);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request,response);
    }
}
