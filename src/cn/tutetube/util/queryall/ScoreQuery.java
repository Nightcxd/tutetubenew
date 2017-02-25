package cn.tutetube.util.queryall;

import cn.tutetube.util.bean.PersonalInfo;
import cn.tutetube.util.bean.UserData;
import cn.tutetube.util.common.Utility;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cxd on 2017/1/25.
 */
public class ScoreQuery {

    // 个人信息数据
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

    /**
     * 获取用户在教务系统的SESSION值
     *
     * @param uData 用户数据
     * @param force 是否强制更新SESSION
     * @return SESSION
     */
    private String getSession(UserData uData, boolean force) {
        int lastLogin = uData.getLastlogin();
        if (force || (Utility.time() - lastLogin > 1800)) {
            String newSession = login(uData.getNum(), uData.getPasswd());
            return newSession;
        } else {
            return uData.getSession();
        }
    }

    public String getScore(UserData uData, String semester) {
        try {
            String session = getSession(uData, false);
            if (session.length() <= 3) {
                session = getSession(uData, true);
            }
            //标题
            String gradelist = " <div id=\"table-title\" class=\"clearfix\">\n" +
                    "        <div class=\"table-h h-8-5\">课程名称</div>\n" +
                    "        <div class=\"table-h h-2-5\">成绩</div>\n" +
                    "    </div>";
            Document doc = Jsoup.connect("http://202.113.244.44:9013/gradeLnAllAction.do?type=ln&oper=qbinfo&lnxndm")
                    .cookie("JSESSIONID", session)
                    .timeout(10000)
                    .get();

            // 读取表格
            Elements sm = doc.select("a");
            int size = sm.size();//获取总的学期数
            if (size == 0) {
                return "401";//TODO 学期异常
            } else
                for (int i = 0; i < size; i++) {
                    String sg = sm.get(i).toString().split("\"")[1].trim();
                    if (sg.equals(semester.trim())) {//判断学期是不是当前选择的学期
                         //返回当前学期成绩情况
                        Elements scoreTable = doc.getElementsByClass("titleTop2");
                        if (scoreTable.toString().trim().equals("")) {
                            return "402";//TODO 成绩未发布
                        } else {
                            Element temp = scoreTable.get(i);
                            Elements list = temp.getElementsByClass("odd");
                            if (list.toString().trim().equals("")) {
                                return "403"; //TODO 成绩未发布1
                            } else {
                                int listSize = list.size();
                                for (int j = 0; j < listSize; j++) {
                                    Element singlescore = list.get(j);
                                    Elements course = singlescore.select("td");
                                    Element coursename = course.get(2);
                                    Element grade = course.get(6);
                                    if (grade.children().html().trim().equals("")) {
                                        gradelist += "<div class=\"table-row clearfix\">\n" +
                                                "                <div class=\"table-course h-8-5\"><span class=\"arrow\"></span>" + coursename + "</div>\n" +
                                                "                <div class=\"table-score h-2-5 font-small-gray\">未发布</div>\n" +
                                                "</div>";
                                    } else {
                                        gradelist += "<div class=\"table-row clearfix\">\n" +
                                                "                <div class=\"table-course h-8-5\"><span class=\"arrow\"></span>" + coursename.html() + "</div>\n" +
                                                "                <div class=\"table-score h-2-5 font-small-gray\">" + grade.children().html().trim() + "</div>\n" +
                                                "</div>";
                                    }
                                }
                            }
                        }

                    }
                    else
                        continue;
                }
            // System.out.println(sm);
            return gradelist;


        } catch (Exception e) {
            return "500";//TODO 网络异常
        }
    }

    public static void main(String[] args) {
        ScoreQuery sq = new ScoreQuery();
        UserData st = new UserData();
        st.setPasswd("IGPSGL");
        st.setNum("02210140201");
        System.out.println(sq.getScore(st, "2016-2017学年秋(两学期"));
    }
}
