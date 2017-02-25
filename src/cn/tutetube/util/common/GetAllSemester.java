package cn.tutetube.util.common;

import cn.tutetube.util.bean.UserData;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by cxd on 2017/1/25.
 */
public class GetAllSemester {
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

    public String getScore(UserData uData) {
        try {

            String session = getSession(uData, false);
            if (session.length() <= 3) {
                session = getSession(uData, true);
            }

            Document doc = Jsoup.connect("http://202.113.244.44:9013/gradeLnAllAction.do?type=ln&oper=qbinfo&lnxndm")
                    .cookie("JSESSIONID", session)
                    .timeout(10000)
                    .get();

            // 读取表格
            String semester="";
            Elements sm = doc.select("a");
            int size = sm.size();//获取总的学期数
            if (size == 0) {
                return "401";//TODO 学期异常
            } else
                for (int i = 0; i < size; i++) {
                    if(i==size-1){
                    String sg = sm.get(i).toString().split("\"")[1].trim();
                    semester+="<div class=\"option selected\" onclick=\"changesm(this)\">"+sg+"</div>\n";
                    }
                    else {
                        String sg = sm.get(i).toString().split("\"")[1].trim();
                        semester+="<div  class=\"option\" onclick=\"changesm(this)\">"+sg+"</div>\n";
                    }
                }
            return semester;
        } catch (IOException e) {
            return "401";
        }

    }

    public static void main(String[] args) {
        GetAllSemester sq = new GetAllSemester();
        UserData st = new UserData();
        st.setPasswd("IGPSGL");
        st.setNum("02210140201");
        System.out.println(sq.getScore(st));
    }
}
