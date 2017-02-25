package cn.tutetube.util.common;

import cn.tutetube.util.bean.UserData;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by cxd on 2017/1/6.
 */
public class PostEvaluation {
    private static Logger log = Logger.getLogger(PostEvaluation.class);

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
    public String newLogin(UserData uData) {
        try {

            String session = getSession(uData, false);
            if (session.length() <= 3) {
                session = getSession(uData, true);
            }
            Connection con = Jsoup.connect("http://202.113.244.44:9013/jxpgXsAction.do?oper=listWj")
                    .timeout(10000)
                    .cookie("JSESSIONID", session)
                    .method(Connection.Method.POST);
            Connection.Response response = con.execute();
            Document doc = response.parse();
            if (doc.title().equals("学生评估问卷列表")) {
                Iterator<Map.Entry<String, String>> ite = response.cookies().entrySet().iterator();
                while (ite.hasNext()) {
                    Map.Entry<String, String> entry = ite.next();
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (key.equals("JSESSIONID"))
                       session= value;

                }
            }
            Connection connection = Jsoup.connect("http://202.113.244.44:9013/jxpgXsAction.do")
                    .timeout(10000)
                    .cookie("JSESSIONID", session)
                    .data("wjbm", "0000000091")
                    .data("bpr","12560020")
                    .data("pgnr","560503102")
                    .data("oper", "wjShow")
                     .data("pageSize", "20")
                     .data("page", "1")
                     .data("currentPage", "1")
                     .data("pageNo", "")
                    .method(Connection.Method.POST);
            Connection.Response response1 = connection.execute();
            Document doc1 = response1.parse();
            if (doc1.title().equals("问卷评估页面")) {
                Iterator<Map.Entry<String, String>> ite = response1.cookies().entrySet().iterator();
                while (ite.hasNext()) {
                    Map.Entry<String, String> entry = ite.next();
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (key.equals("JSESSIONID"))
                        session= value;

                }
            }
            Thread.sleep(2*60*1000);
            Connection connection1 = Jsoup.connect("http://202.113.244.44:9013/jxpgXsAction.do?oper=wjpg")
                    .cookie("JSESSIONID", session)
                    .data("wjbm", "0000000091")
                    .data("bpr","12560020")
                    .data("pgnr","560503102")
                    .data("0000000116","5_1")
                    .data("0000000117","5_1")
                    .data("0000000118","5_1")
                    .data("0000000119","5_1")
                    .data("0000000120","5_1")
                    .data("0000000121", "5_1")
                    .data("0000000122","5_1")
                    .data("0000000123","5_1")
                    .data("0000000124","5_1")
                    .data("0000000125", "5_1")
                    .data("0000000126", "5_1")
                    .data("0000000127", "5_1")
                    .data("0000000128", "5_1")
                    .data("0000000129", "5_1")
                    .data("0000000130", "5_1")
                    .data("0000000131", "5_1")
                    .data("0000000132", "5_1")
                    .data("0000000133", "5_1")
                    .data("0000000134", "5_1")
                    .data("0000000135", "5_1")
                    .data("zgpj", "very good!")
                    .timeout(50000)
                    .method(Connection.Method.POST);
            Connection.Response response2 = connection1.execute();
            Document doc2= response2.parse();
            System.out.println(doc2);
            return doc2.toString();

        } catch (Exception e) {
            log.error("连接超时或其他异常",e);
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
    public static void main(String[] args) {
        UserData st = new UserData();
        st.setPasswd("QRQCIB");
        st.setNum("09990150110");
        PostEvaluation postEvaluation=new PostEvaluation();
        postEvaluation.newLogin(st);
    }
}
