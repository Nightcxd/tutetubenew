package cn.tutetube.servlet.base;

import cn.tutetube.bean.Student;
import cn.tutetube.util.BaseUtil;
import cn.tutetube.util.FileOperateUtil;
import cn.tutetube.util.GenerateSequenceUtil;
import cn.tutetube.util.StudentUtil;
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
 * Created by cxd on 2017/2/8.
 */
@WebServlet(name = "UploadHeadIconServlet")
public class UploadHeadIconServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(UploadHeadIconServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        Cookie[] cookies = request.getCookies();
        String nickname = "";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().trim().equals("nickname")) {
                    nickname = URLDecoder.decode(c.getValue().trim(), "UTF-8");
                }
            }
            if (nickname.equals("")) {
                response.getWriter().write("102");//会话失效
            }
            else {
                StudentUtil su = new StudentUtil();
                Student st = su.getStudentByNickName(nickname).get(0);
                if (st.equals(null)){
                    response.getWriter().write("103");//网络异常
                }
                else {
                    String headicon=request.getParameter("headicon").split(",")[1].replace("\\n","").trim();
                    String imgurl= BaseUtil.GenerateIcon(headicon, GenerateSequenceUtil.generateSequenceNo()+"icon");
                    if(imgurl.equals("100")||imgurl.equals("101")){
                        response.getWriter().write("0");//图像存储失败
                    }
                    else {
                        String oldImgUrl=st.getHeadicon();//旧图片
                        st.setHeadicon(imgurl);
                        su.update(st);
                        log.info(imgurl+"更新成功");
                        String temp=FileOperateUtil.deleteFile("/root/"+oldImgUrl);
                        String temp1=FileOperateUtil.deleteFile("/root/software/apache-tomcat/webapps/tutetubenew/" + oldImgUrl);
                        if (temp1.equals("0")||temp.equals("0")){
                            log.info(oldImgUrl+"旧图片删除失败");
                        }
                        else if(temp.equals("1")||temp1.equals("1")){
                            log.info("旧图片删除成功！");
                        }
                        response.getWriter().write("1");//头像更新成功！
                    }
                }
            }
        }
        else {
            response.getWriter().write("102");//会话失效
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
