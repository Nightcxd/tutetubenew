package cn.tutetube.servlet.school;

import cn.tutetube.bean.Student;
import cn.tutetube.util.StudentUtil;
import cn.tutetube.util.bean.UserData;
import cn.tutetube.util.common.GetStudentInfoJson;
import cn.tutetube.util.common.NetHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by cxd on 2017/1/1.
 */
@WebServlet(name = "BindingJwServlet")
public class BindingJwServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String xh=request.getParameter("xh").trim();
        String psd=request.getParameter("psd").trim();
        NetHelper nh=new NetHelper();
        UserData st=new UserData();
        st.setPasswd(psd.trim());
        st.setNum(xh.trim());
        String status=nh.login(st.getNum(), st.getPasswd());
        if (status.trim().equals("101")) {
            response.getWriter().write("101");
        }
        else if (status.trim().equals("102")) {
            response.getWriter().write("102");
        }
        else if (status.trim().equals("103")) {
            response.getWriter().write("103");
        }
        else if (status.trim().equals("104")) {
            response.getWriter().write("104");
        }
        else
        {
            Cookie[] cookies = request.getCookies();
            String nickname="";
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().trim().equals("nickname")) {
                        nickname = URLDecoder.decode(c.getValue().trim(), "UTF-8");
                    }
                }
            }
            //从数据库查询用户信息
            StudentUtil su=new StudentUtil();
            Student st1=su.getStudentByNickName(nickname).get(0);
            //从教务系统查询部分信息！
            GetStudentInfoJson studentInfoJson=new GetStudentInfoJson();
            UserData u1=new UserData();
            u1.setPasswd(psd.trim());
            u1.setNum(xh.trim());
            Student s1=studentInfoJson.getInfo(u1);
            if (s1.equals(null)){
                response.getWriter().write("104");//网络异常！
            }
            else {
                st1.setSno(s1.getSno());
                st1.setMm(s1.getMm());
                st1.setClassname(s1.getClassname());
                st1.setSname(s1.getSname());
                st1.setCollege(s1.getCollege());
                su.update(st1);
                Cookie cookie = new Cookie("xh", xh.trim());
                Cookie cookie1 = new Cookie("psd", psd.trim());

                // cookie.setMaxAge();
                cookie1.setPath("/");
                cookie.setPath("/");
                cookie.setMaxAge(24 * 60 * 60);
                cookie1.setMaxAge(24 * 60 * 60);
                response.addCookie(cookie);
                response.addCookie(cookie1);
                response.getWriter().write("1");
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
