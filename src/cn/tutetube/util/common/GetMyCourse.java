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
 * Created by cxd on 2017/2/1.
 */
public class GetMyCourse {
    private static Logger log = Logger.getLogger(GetMyCourse.class);
    // 个人信息数据
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

    /**
     * 获取用户在教务系统的SESSION值
     * @param uData 用户数据
     * @param force 是否强制更新SESSION
     * @return SESSION
     */
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
    public String getCourseTable(UserData uData) {
        try {
            String session = getSession(uData, false);
            if (session.length() <= 3) {
                session = getSession(uData, true);
            }

            Document doc = Jsoup.connect("http://202.113.244.44:9013/xkAction.do?actionType=6")
                    .cookie("JSESSIONID", session)
                    .timeout(10000)
                    .get();
            Element table = doc.select(".displayTag").get(0);
            Elements tableTr = table.select("tbody tr");
//            System.out.println(tableTr+"\n\n\n");
            int trSize=tableTr.size();
            int jieCi=1;
            String Temp=" <table>\n" +
                    "            <tbody><tr>\n" +
                    "                <th></th>\n" +
                    "                <th>一</th>\n" +
                    "                <th>二</th>\n" +
                    "                <th>三</th>\n" +
                    "                <th>四</th>\n" +
                    "                <th>五</th>\n" +
                    "                <th>六</th>\n" +
                    "                <th>日</th>\n" +
                    "            </tr>";
            for (int i=1;i<trSize;i++) {
                Elements tableTd = tableTr.get(i).select("td");
//                System.out.println(tableTd);
                int tdSize = tableTd.size();
                if (tdSize <= 1) {
                    continue;
                } else if (tdSize == 9) {
                    for (int j = 1; j < tdSize; j++) {
                        if (j == 1) {
                            Temp += "<tr>\n<td >" + tableTd.get(j).html().trim() + "</td>\n";
                        } else if (j == tdSize - 1) {
                            Temp += " <td class=\"timetable-course\" >" + tableTd.get(j).html().trim() + "</td>\n" +
                                    "            </tr>\n";
                            jieCi++;
                        } else {
                            Temp += "<td class=\"timetable-course\" >" + tableTd.get(j).html().trim() + "</td>\n";
                        }
                    }
                }
                else {
                    for (int j = 0; j < tdSize; j++) {
                        if (j == 0) {
                            Temp += "<tr>\n<td >" + tableTd.get(j).html().trim() + "</td>\n";
                        } else if (j == tdSize - 1) {
                            Temp += " <td class=\"timetable-course\" >" + tableTd.get(j).html().trim() + "</td>\n" +
                                    "            </tr>\n";
                            jieCi++;
                        } else {
                            Temp += "<td class=\"timetable-course\" >" + tableTd.get(j).html().trim() + "</td>\n";
                        }
                    }
                }
            }
            Temp+="</tbody></table>";
            return Temp;
        } catch (Exception e) {
            log.error("异常错误信息！",e);
            return "104";//TODO 网络异常！
        }
    }

    public static void main(String[] args) {
        GetMyCourse gnc=new GetMyCourse();
        UserData st=new UserData();
        st.setPasswd("IGPSGL");
        st.setNum("02210140201");
        System.out.println(gnc.getCourseTable(st));
    }
}
