package cn.tutetube.util.common;

import cn.tutetube.util.bean.PersonalInfo;
import cn.tutetube.util.bean.UserData;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

/**
 * Created by cxd on 2017/1/12.
 */
public class Etest {
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

    public List<Map<String, String>> getAvg(UserData uData) {
        try {
            String session = getSession(uData, false);
            if (session.length() <= 3) {
                session = getSession(uData, true);
            }

//            PInfo info = new PInfo();
//            PersonalInfo pInfo = new PersonalInfo();
//            List<Map<String, String>> data = new ArrayList<Map<String, String>>();

            Document doc = Jsoup.connect("http://202.113.244.44:9013/reportFiles/cj/cj_zwcjd.jsp")
                    .cookie("JSESSIONID", session)
                    //.data("LS_XH", "02210121322")
                    .timeout(10000)
                    .get();
            Document doc1 = Jsoup.connect("http://202.113.244.44:9013/setReportParams")
                    .cookie("JSESSIONID", session)
                    .data("LS_XH", "02210140201")
                    .data("resultPage","http://202.113.244.44:9013/reportFiles/cj/cj_zwcjd.jsp?")
                    .timeout(10000)
                    .method(Connection.Method.POST).get();

            Element report = doc1.getElementById("report1");
            Elements tblView_tr = report.select("tr");
            System.out.println(tblView_tr);
            List <Map<String, String>> data = new ArrayList<Map<String,String>>();

            // 解析表格成绩，把数据添加到data中
            String tmpStringAttr= new String();
            String tmpStringSubject= new String();
            String tmpStringCredit= new String();
            String tmpStringGrade= new String();
            String tmpStringTime = new String();
            Map<String, String> tmpMap = new HashMap<String, String>();

            // 第一次循环，读取左列
            for (int j = 7; j < tblView_tr.size(); j++) {
                Element tblView_line = tblView_tr.get(j);
                String height = tblView_line.attr("height").trim();
                if (!height.equals("12")) {
                    break;
                }

                tmpStringAttr = tblView_line.select("td").eq(4).text().trim();
                tmpStringSubject = tblView_line.select("td").eq(1).text().trim();
                tmpStringCredit = tblView_line.select("td").eq(2).text().trim();
                tmpStringGrade = tblView_line.select("td").eq(3).text().trim();
                tmpStringTime = tblView_line.select("td").eq(5).text().trim();

                if (tmpStringSubject.length() != 0) {
                    tmpMap = new HashMap<String, String>();
                    tmpMap.put("attr", tmpStringAttr);
                    tmpMap.put("subject", tmpStringSubject);
                    tmpMap.put("credit", tmpStringCredit);
                    tmpMap.put("grade", tmpStringGrade);
                    tmpMap.put("time", tmpStringTime);
                    data.add(tmpMap);
                }
            }

            // 第二次循环，读取右列
            for (int j = 7; j < tblView_tr.size(); j++) {
                Element tblView_line = tblView_tr.get(j);
                String height = tblView_line.attr("height").trim();
                if (!height.equals("12")) {
                    break;
                }

                tmpStringAttr = tblView_line.select("td").eq(9).text().trim();
                tmpStringSubject = tblView_line.select("td").eq(6).text().trim();
                tmpStringCredit = tblView_line.select("td").eq(7).text().trim();
                tmpStringGrade = tblView_line.select("td").eq(8).text().trim();
                tmpStringTime = tblView_line.select("td").eq(10).text().trim();

                if (tmpStringSubject.length() != 0) {
                    tmpMap = new HashMap<String, String>();
                    tmpMap.put("attr", tmpStringAttr);
                    tmpMap.put("subject", tmpStringSubject);
                    tmpMap.put("credit", tmpStringCredit);
                    tmpMap.put("grade", tmpStringGrade);
                    tmpMap.put("time", tmpStringTime);
                    data.add(tmpMap);
                }
            }
            System.out.println(data);
            return data;
            //return doc.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Etest etest=new Etest();
        UserData st=new UserData();
        st.setPasswd("IGPSGL");
        st.setNum("02210140201");
        //NetHelper n=new NetHelper();
        System.out.println(etest.getAvg(st));
    }
}
