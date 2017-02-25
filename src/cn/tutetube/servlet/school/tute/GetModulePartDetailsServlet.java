package cn.tutetube.servlet.school.tute;

import cn.tutetube.bean.ModulePart;
import cn.tutetube.util.schoolutil.tute.ModulePartUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by cxd on 2017/2/24.
 */
@WebServlet(name = "GetModulePartDetailsServlet")
public class GetModulePartDetailsServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(GetModulePartDetailsServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        Cookie[] cookies = request.getCookies();
        String modulePart_id="";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().trim().equals("modulePart_id")) {
                    modulePart_id = c.getValue();
                }
            }
            if (modulePart_id.trim().equals("")) {
                response.getWriter().write("0");//TODO cookie为空
            }
            else {
                try {
                String result="";
                ModulePartUtil modulePartUtil=new ModulePartUtil();
                List<ModulePart> moduleParts= modulePartUtil.getByModulePart_id(modulePart_id);
                int size=moduleParts.size();
                for (int i=0;i<size;i++) {
                    ModulePart m=moduleParts.get(i);
                    result+=""+m.getModulePart_name()+"$@"+m.getModulePart_releaseTime()+"$@"+m.getModulePart_fileType()+"$@"+m.getModulePart_content()+"$@"+m.getModulePart_fileUrl()+"#$%";
                }
                    log.info(result);
                    response.getWriter().write(result);
                }
                catch (Exception e){
                    log.info("异常信息---",e);
                    response.getWriter().write("100");//TODO 网络异常
                }
            }
        }
        else {
            response.getWriter().write("0");//TODO cookie为空
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request,response);
    }
}
