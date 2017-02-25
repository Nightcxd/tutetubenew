package cn.tutetube.util.common;

import cn.tutetube.bean.Student;
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
 * Created by cxd on 2017/2/8.
 */
public class GetStudentInfoJson {
    private static Logger log = Logger.getLogger(GetStudentInfoJson.class);
    private class PInfo {
        public String name;			// 学生姓名
        public Date inSchool;		// 入学时间
        public float creditGot;		// 获得学分
        public float creditTotal;	// 总学分
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
    };
    public String login(String num, String passwd) {
        try {
            Connection con = Jsoup.connect("http://202.113.244.44:9013/loginAction.do")
                    .data("zjh", num)
                    .data("mm",passwd)
                    .timeout(10000)
                    .method(Connection.Method.POST);
            Connection.Response response = con.execute();
            Document doc = response.parse();

            if(doc.title().equals("学分制综合教务"))
            {
                Iterator<Map.Entry<String, String>> ite = response.cookies().entrySet().iterator();
                while(ite.hasNext()){
                    Map.Entry<String, String> entry = ite.next();
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (key.equals("JSESSIONID"))
                        return value;
                }
                return "100";	// TODO 错误代码100：未获取到SESSION
            }

            Element prompt = doc.getElementsByAttributeValue("class", "errorTop").first();
            String strong = prompt.select("strong>font").text().trim();

            if(strong.contains("不存在"))
            {
                return "101";	// TODO 错误代码101：学号不存在
            }
            else if(strong.contains("密码不正确"))
            {
                return "102";	// TODO 错误代码102：密码不正确
            }
            else {
                return "103";	// TODO 错误代码103：网页格式错误
            }
        }
        catch (Exception e) {
            return "104";		// TODO 错误代码104：连接超时或其他异常
        }
    }

    private String getSession(UserData uData, boolean force) {
        int lastLogin = uData.getLastlogin();
        if (force || (Utility.time() - lastLogin > 1800)) {
            String newSession = login(uData.getNum(), uData.getPasswd());
            return newSession;
        }
        else {
            return uData.getSession();
        }
    }
    public Student getInfo(UserData uData){
        try {
            String session = getSession(uData, false);
            if (session.length() <= 3) {
                session = getSession(uData, true);
            }

            Document doc = Jsoup.connect("http://202.113.244.44:9013/xjInfoAction.do?oper=xjxx")
                    .cookie("JSESSIONID", session)
                    .timeout(10000)
                    .get();
            Element tblView = doc.getElementById("tblView");
            Elements tblView_tr = tblView.select("tr");

            //获取学号和姓名
            Elements xhxm = tblView_tr.get(0).select("td");
            String sname=xhxm.get(3).html().trim();

            //获取入学日期和系所
            Elements rxrqxs=tblView_tr.get(12).select("td");
            String college=rxrqxs.get(3).html().trim();

            //获取班级
            Elements bj=tblView_tr.get(14).select("td");
            String classname=bj.get(3).html().trim();
            Student st=new Student();
            st.setMm(uData.getPasswd());
            st.setSno(uData.getNum());
            st.setSname(sname);
            st.setClassname(classname);
            st.setCollege(college);

            return st;
        }
        catch (Exception e){
            log.error("异常信息位置---",e);
            return null;//网络异常
        }
    }

    public static void main(String[] args) {
        GetStudentInfoJson studentInfoJson=new GetStudentInfoJson();
        UserData st=new UserData();
        st.setPasswd("IGPSGL");
        st.setNum("02210140201");
        System.out.println(studentInfoJson.getInfo(st));

    }
}
