package cn.tutetube.servlet.school.tute;

import cn.tutetube.bean.College;
import cn.tutetube.bean.Student;
import cn.tutetube.util.StudentUtil;
import cn.tutetube.util.schoolutil.tute.CollegeUtil;
import cn.tutetube.util.schoolutil.tute.ModuleForCollegeUtil;
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
 * Created by cxd on 2017/2/25.
 */
@WebServlet(name = "GetUserCollegeNewsServlet")
public class GetUserCollegeNewsServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(GetUserCollegeNewsServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String module_id=request.getParameter("module_id").trim();
        Cookie[] cookies = request.getCookies();
        String nickname = "";
        String password = "";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().trim().equals("nickname")) {
                    nickname = URLDecoder.decode(c.getValue().trim(), "UTF-8");
                } else if (c.getName().trim().equals("password")) {
                    password = c.getValue().trim();
                }

            }
            if (nickname.equals("")||password.equals("")){
                response.getWriter().write("0");
            }
            else {
                StudentUtil su=new StudentUtil();
                Student s=su.getStudentByNicknameAndPassword(nickname.trim(),password.trim());
                try {
                    CollegeUtil cu=new CollegeUtil();
                    College c=cu.getByCollegeName(s.getCollege().trim());
                    ModuleForCollegeUtil mcu=new ModuleForCollegeUtil();
                    log.info(mcu.getByModuleIdAndCollegeId(module_id.trim(),c.getCollege_id().trim()).getModule_name());
                }
                catch (Exception e){
                    log.error("异常信息位置---",e);
                    response.getWriter().write("0");
                }
            }
        }
        else {
            response.getWriter().write("0");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
