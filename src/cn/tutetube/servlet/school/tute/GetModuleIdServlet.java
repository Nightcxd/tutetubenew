package cn.tutetube.servlet.school.tute;

import cn.tutetube.bean.ModuleBase;
import cn.tutetube.util.schoolutil.tute.ModuleBaseUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by cxd on 2017/2/25.
 */
@WebServlet(name = "GetModuleIdServlet")
public class GetModuleIdServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(GetModuleIdServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        ModuleBaseUtil mbu=new ModuleBaseUtil();
        String module_id=request.getParameter("module_id").trim();
        String result="";
        try {
            List<ModuleBase> moduleBases= mbu.getByModuleId(module_id);
            int size=moduleBases.size();
            for (int i=0;i<size;i++) {
                result+=""+moduleBases.get(i).getModuleBase_id()+":"+moduleBases.get(i).getModulePart_name()+"#$%";
            }
            log.info(result);
            response.getWriter().write(result);
        }
        catch (Exception e){
            log.error("异常信息---",e);
            response.getWriter().write("100");//网络异常
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
