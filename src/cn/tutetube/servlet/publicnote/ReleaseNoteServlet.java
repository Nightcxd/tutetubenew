package cn.tutetube.servlet.publicnote;

import cn.tutetube.bean.Note;
import cn.tutetube.bean.NoteForImages;
import cn.tutetube.bean.Student;
import cn.tutetube.util.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Enumeration;

/**
 * Created by cxd on 2017/2/7.
 */
@WebServlet(name = "ReleaseNoteServlet")
public class ReleaseNoteServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(ReleaseNoteServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String note_type=new String(request.getParameter("note_type").getBytes("iso8859-1"),"utf-8");
        log.info("ReleaseNoteServlet-----"+note_type+"\n");
        String release_time=new String(request.getParameter("release_time").getBytes("iso8859-1"),"utf-8");
        log.info("ReleaseNoteServlet-----"+release_time+"\n");
        String note_content=new String(request.getParameter("note_content").getBytes("iso8859-1"),"utf-8");
        log.info("ReleaseNoteServlet-----"+note_content+"\n");
        String images[]=request.getParameterValues("imgList[]");
        int size=images.length;    //
        String img[]=new String[size];
        String temp="";
        for (int i=0;i<size;i++){
            temp=images[i].split(",")[1].replace("\\n","").trim();
            img[i]=BaseUtil.GenerateImage(temp,GenerateSequenceUtil.generateSequenceNo()+"img"+i+"");
            if ("101".equals(img[i].trim())){
                response.getWriter().write("100");//图像数据为空
            }
            else if ("100".equals(img[i].trim())){
                response.getWriter().write("101");//图像存储失败
            }
            log.info(img[i]);
        }
        Cookie[] cookies = request.getCookies();
        String nickname = "";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().trim().equals("nickname")) {
                    nickname = URLDecoder.decode(c.getValue().trim(), "UTF-8");
                }
            }
            if(nickname.equals("")){
                response.getWriter().write("102");//会话失效
            }
            else {
                StudentUtil su=new StudentUtil();
                Student st=su.getStudentByNickName(nickname).get(0);
                if (st.equals(null)){
                    response.getWriter().write("103");//网络异常
                }
                else {
                    String s_id=st.getId();
                    log.info("用户id" + s_id);
                    Note nt=new Note();
                    nt.setNote_id("note"+GenerateSequenceUtil.generateSequenceNo());
                    nt.setId(s_id);
                    nt.setRelease_time(release_time);
                    nt.setNote_type(note_type);
                    nt.setNote_content(note_content);
                    NoteUtil nu=new NoteUtil();
                    nu.save(nt);
                    log.info("帖子基本信息插入成功！");
                    int count=img.length;
                   for (int j=0;j<count;j++){
                       NoteForImages nfi=new NoteForImages();
                       nfi.setId(s_id);
                       nfi.setNote_id(nt.getNote_id());
                       nfi.setImages_id("img"+GenerateSequenceUtil.generateSequenceNo());
                       nfi.setNote_images(img[j]);
                       NoteForImagesUtil ntu=new NoteForImagesUtil();
                       ntu.save(nfi);
                   }
                    log.info("帖子图片地址全部插入成功！");
                    response.getWriter().write("1");//帖子发布成功
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
