package cn.tutetube.util.common;

import cn.tutetube.util.StudentUtil;
import cn.tutetube.util.bean.UserData;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

/**
 * Created by cxd on 2017/1/1.
 */
public class GetStudentInfo {
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

    public String getRoll(UserData uData) {
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
            Elements xhxm=tblView_tr.get(0).select("td");


            //获取学生类别
            Elements xslb=tblView_tr.get(3).select("td");

            //获取学籍状态
            Elements xjzt=tblView_tr.get(4).select("td");

            //获取民族
            Elements mz=tblView_tr.get(5).select("td");

            //获取出生日期
            Elements csrq=tblView_tr.get(6).select("td");

            //获取政治面貌
            Elements zzmm=tblView_tr.get(7).select("td");

            //获取入学日期和系所
            Elements rxrqxs=tblView_tr.get(12).select("td");

            //获取专业方向
            Elements zyfx=tblView_tr.get(13).select("td");

            //获取班级
            Elements bj=tblView_tr.get(14).select("td");

            //获取宿舍地址
            Elements ssdz=tblView_tr.get(17).select("td");

            //System.out.println(cjwj_info.get(3).html().trim());
            String info=" <tr>\n" +
                    "                    <td>姓名</td>\n" +
                    "                    <td>"+xhxm.get(3).html().trim()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <td>学号</td>\n" +
                    "                    <td>"+xhxm.get(1).html().trim()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <td>班级</td>\n" +
                    "                    <td>"+bj.get(3).html().trim()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <td>出生日期</td>\n" +
                    "                    <td>"+csrq.get(3).html().trim()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <td>民族</td>\n" +
                    "                    <td>"+mz.get(3).html().trim()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <td>政治面貌</td>\n" +
                    "                    <td>"+zzmm.get(1).html().trim()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <td>学籍状态</td>\n" +
                    "                    <td>"+xjzt.get(3).html().trim()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <td>学生类别</td>\n" +
                    "                    <td>"+xslb.get(3).html().trim()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <td>入学日期</td>\n" +
                    "                    <td>"+rxrqxs.get(1).html().trim()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <td>系所</td>\n" +
                    "                    <td>"+rxrqxs.get(3).html().trim()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <td>专业方向</td>\n" +
                    "                    <td>"+zyfx.get(3).html().trim()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <td>宿舍地址</td>\n" +
                    "                    <td>"+ssdz.get(3).html().trim()+"</td>\n" +
                    "                </tr>\n";
            return info;
        } catch (Exception e) {
            return "104";
        }
    }

}
