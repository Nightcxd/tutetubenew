package cn.tutetube.util.common;

import cn.tutetube.util.bean.UserData;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by cxd on 2017/1/3.
 */
public class TeachingEvaluation {
    private static Logger log = Logger.getLogger(TeachingEvaluation.class);
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

    public String Evaluation(UserData uData) {

        try {
            String session = getSession(uData, false);
            if (session.length() <= 3) {
                session = getSession(uData, true);
            }

            Document doc = Jsoup.connect("http://202.113.244.44:9013/jxpgXsAction.do?oper=listWj")
                    .cookie("JSESSIONID", session)
                    .timeout(50000)
                    .get();
            Elements tblView = doc.getElementsByClass("odd");
            int size=tblView.size();
            String info="<div id=\"table-title\" class=\"clearfix\" >\n" +
                    "        <div class=\"table-h h-8-5\">课程名称</div>\n" +
                    "        <div class=\"table-h h-2-5\">评估状态</div>\n" +
                    "    </div>";
            for (int i=0;i<size;i++){
                String value=tblView.get(i).select("img").toString().split("\"")[1].trim();
                String status=tblView.get(i).select("td").get(3).html().trim();
                String pgnrm=value.split("#@")[4].trim();
                info+="<div class=\"table-row clearfix\" onclick=\"postCName(this)\">\n" +
                        "        <input type=\"hidden\" value=\""+value.trim()+"\">\n" +
                        "        <div class=\"table-course h-8-5\"><span class=\"arrow\"></span>"+pgnrm.trim()+"</div>\n" +
                        "        <div class=\"table-score h-2-5 font-small-gray\">"+status.trim()+"</div>\n" +
                        "    </div>";
            }
            //System.out.println(info);
            //System.out.println(tblView);
//            String single=tblView.get(0).select("img").toString().split("\"")[1];
//            String wjbm=single.split("#@")[0].trim();
//            String bpr=single.split("#@")[1].trim();
//            String pgnr=single.split("#@")[5].trim();
//            String oper="wjShow";
//            String wjmc=single.split("#@")[3].trim();
//            String bprm=single.split("#@")[2].trim();
//            String pgnrm=single.split("#@")[4].trim();
//
//            Document doc1 = Jsoup.connect("http://202.113.244.44:9013/jxpgXsAction.do")
//                    .cookie("JSESSIONID", session)
//                    .data("wjbm", wjbm)
//                    .data("bpr", bpr)
//                    .data("pgnr",pgnr)
//                    .data("oper", oper)
//                    .data("wjmc",wjmc)
//                    .data("bprm",bprm)
//                    .data("pgnrm",pgnrm)
//                    .timeout(10000)
//                    .get();
//            System.out.println(doc1);
            return info;
        }
        catch (Exception e){
            log.error("网络错误！",e);
            return "104";
        }

    }

    public static void main(String[] args) {
        TeachingEvaluation te=new TeachingEvaluation();
        UserData st=new UserData();
        st.setPasswd("IGPSGL");
        st.setNum("02210140201");
        System.out.println(te.Evaluation(st));

    }
}
