package cn.tutetube.servlet.publicnote;

import cn.tutetube.bean.Note;
import cn.tutetube.bean.NoteForImages;
import cn.tutetube.bean.Student;
import cn.tutetube.util.NoteForImagesUtil;
import cn.tutetube.util.NoteUtil;
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
import java.util.List;

/**
 * Created by cxd on 2017/1/31.
 */
@WebServlet(name = "GetPublicNoteServlet")
public class GetPublicNoteServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(GetPublicNoteServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        Cookie[] cookies = request.getCookies();
        String note_type = "";
        String topic_list="";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().trim().equals("note_type")) {
                    note_type = URLDecoder.decode(c.getValue().trim(), "UTF-8");//获取帖子类型
                }
            }
        }
        log.info("GetPublicNoteServlet---"+note_type+"\n");
        NoteUtil nu = new NoteUtil();
        List<Note> ntList = nu.getNotesByType(note_type.trim());//获取这个类型帖子的集合
        int size = ntList.size();  //获取这个类型的帖子的总数量
        for (int i = 0; i < size; i++) {
            Note t = ntList.get(i);//获取第i个帖子
            String note_id=t.getNote_id().trim();//获取帖子id
            String id = t.getId();//获取这个帖子的发起人id
            StudentUtil su = new StudentUtil();
            Student st = su.getById(id);//获取发起人信息
            String nicknamecn = st.getNicknamecn();//获取发起人昵称
            String release_time = t.getRelease_time().split("\\[")[0];//获取发起时间
            String college = st.getCollege();//获取发起人的学院
            String headicon = st.getHeadicon();//获取发起人头像
            String note_content = t.getNote_content();//获取帖子内容
            NoteForImagesUtil nt=new NoteForImagesUtil();
            List<NoteForImages> nfi= nt.getNoteForImagesById(note_id,id);
            int images_count=nfi.size();//图片数量
            String images="";
            for (int j=0;j<images_count;j++){
                images+=" <a class=\"amplifyImg\"><img src=\""+nfi.get(j).getNote_images()+"\"\n" +
                        "class=\"\"></a>";
            }
            topic_list+=" <li class=\"topic-li\">\n" +
                    "            <div class=\"main\">\n" +
                    "                <div class=\"user title clearfix\">\n" +
                    "                    <img class=\"user-icon float-left-box\"\n" +
                    "                         src=\""+headicon.trim()+"\">\n" +
                    "\n" +
                    "                    <div class=\"title\">\n" +
                    "                        <p class=\"name\">"+nicknamecn.trim()+"</p>\n" +
                    "\n" +
                    "                        <p class=\"relate\"><span class=\"time\">"+release_time.trim()+"</span><span class=\"from\">来自["+college.trim()+"]</span></p>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "\n" +
                    "                <div class=\"content\">"+note_content.trim()+"</div>\n" +
                    "\n" +
                    "                <div class=\"img-div clearfix\">\n" +
                    "\n" +
                    "                    <div class=\"multi-pic clearfix\">"+images+"</div>\n" +
                    "\n" +
                    "                </div>\n" +
                    "\n" +
                    "                <div class=\"read display-none\">阅读<span class=\"num\">1393</span></div>\n" +
                    "\n" +
                    "            </div>\n" +
                    "            <ul class=\"btns clearfix\">\n" +
                    "\n" +
                    "                <li class=\"like like-btn\">\n" +
                    "                    <span class=\"icon\"></span>\n" +
                    "\n" +
                    "                    <p class=\"like-box\"><span class=\"txt\">26</span></p>\n" +
                    "\n" +
                    "                    <div class=\"icon-like-box\"></div>\n" +
                    "                </li>\n" +
                    "\n" +
                    "                <li class=\"comment\">\n" +
                    "                    <span class=\"icon\"></span>\n" +
                    "\n" +
                    "                    <p>12</p>\n" +
                    "                </li>\n" +
                    "            </ul>\n" +
                    "\n" +
                    "        </li>";
//            System.out.println(nicknamecn + "\n" + release_time + "\n" + college + "\n" + headicon + "\n" + note_content+"\n"+nfi);
        }
//        System.out.println(topic_list);
         response.getWriter().write(topic_list);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
