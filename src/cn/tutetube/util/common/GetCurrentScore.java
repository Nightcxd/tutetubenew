package cn.tutetube.util.common;

import cn.tutetube.util.bean.UserData;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * Created by cxd on 2016/12/31.
 */
public class GetCurrentScore {
    private class PInfo {
        public String name;            // 学生姓名
        public Date inSchool;        // 入学时间
        public float creditGot;        // 获得学分
        public float creditTotal;    // 总学分
    }

    private class CourseProperty {
        public String tmpStringID;
        public String tmpStringName;
        public String tmpStringNum;
        public String tmpStringCredit;
        public String tmpStringAttr;
        public String tmpStringExam;
        public String tmpStringTeacher;
        public String tmpStringWeeks;
        public String tmpStringWeek;
        public String tmpStringLesson;
        public String tmpStringCamp;
        public String tmpStringBld;
        public String tmpStringPlace;
    }

    ;

    public String login(String num, String passwd) {
        try {
            Connection con = Jsoup.connect("http://202.113.244.44:9013/loginAction.do")
                    .data("zjh", num)
                    .data("mm", passwd)
                    .timeout(10000)
                    .method(Connection.Method.POST);
            Connection.Response response = con.execute();
            Document doc = response.parse();

            if (doc.title().equals("学分制综合教务")) {
                Iterator<Map.Entry<String, String>> ite = response.cookies().entrySet().iterator();
                while (ite.hasNext()) {
                    Map.Entry<String, String> entry = ite.next();
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (key.equals("JSESSIONID"))
                        return value;
                }
                return "100";    // TODO 错误代码100：未获取到SESSION
            }

            Element prompt = doc.getElementsByAttributeValue("class", "errorTop").first();
            String strong = prompt.select("strong>font").text().trim();

            if (strong.contains("不存在")) {
                return "101";    // TODO 错误代码101：学号不存在
            } else if (strong.contains("密码不正确")) {
                return "102";    // TODO 错误代码102：密码不正确
            } else {
                return "103";    // TODO 错误代码103：网页格式错误
            }
        } catch (Exception e) {
            return "104";        // TODO 错误代码104：连接超时或其他异常
        }
    }

    private String getSession(UserData uData, boolean force) {
        int lastLogin = uData.getLastlogin();
        if (force || (Utility.time() - lastLogin > 1800)) {
            String newSession = login(uData.getNum(), uData.getPasswd());
            return newSession;
        } else {
            return uData.getSession();
        }
    }

    public String getGrades(UserData uData) {
        String gradelist=" <div id=\"table-title\" class=\"clearfix\">\n" +
                "        <div class=\"table-h h-8-5\">课程名称</div>\n" +
                "        <div class=\"table-h h-2-5\">成绩</div>\n" +
                "    </div>";
        try {

            String session = getSession(uData, false);
            if (session.length() <= 3) {
                session = getSession(uData, true);
            }

            Document doc = Jsoup.connect("http://202.113.244.44:9013/bxqcjcxAction.do")
                    .cookie("JSESSIONID", session)
                    .timeout(10000)
                    .get();

            // 读取表格
            Element user = doc.getElementById("user");
            Elements tblView_tr = user.select("tr");
            int size=tblView_tr.size();
            if(size==1){
                return "1";
            }
            else
            for (int i=1;i<size;i++){
                Element singlescore=tblView_tr.get(i);
                Elements course=singlescore.select("td");
                Element coursename=course.get(2);
                Element grade=course.get(6);
                if (grade.html().trim().equals("")) {
                     gradelist+="<div class=\"table-row clearfix\">\n" +
                            "                <div class=\"table-course h-8-5\"><span class=\"arrow\"></span>" + coursename + "</div>\n" +
                            "                <div class=\"table-score h-2-5 font-small-gray\">未发布</div>\n" +
                            "</div>";
                }
                else {
                    gradelist+="<div class=\"table-row clearfix\">\n" +
                            "                <div class=\"table-course h-8-5\"><span class=\"arrow\"></span>" + coursename.html() + "</div>\n" +
                            "                <div class=\"table-score h-2-5 font-small-gray\">"+grade.html()+"</div>\n" +
                            "</div>";
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return gradelist;
    }

    public static void main(String[] args) {
        GetCurrentScore gse=new GetCurrentScore();
        UserData st=new UserData();
        st.setPasswd("ZPXCDJ");
        st.setNum("03360151436");
        System.out.println(gse.getGrades(st));
    }
}
