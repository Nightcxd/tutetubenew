package cn.tutetube.util.queryall;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by cxd on 2017/1/1.
 */
public class ChineseQuery {
    public String login(String name, String idCard) {
        try {
            Connection con = Jsoup.connect("http://www.cltt.org/StudentScore/ScoreResult")
                    .data("name", name)
                    .data("idCard",idCard)
                    .timeout(10000)
                    .method(Connection.Method.POST);
            Connection.Response response = con.execute();
            Document doc = response.parse();

            //获取基本查询信息（姓名 成绩 性别 等级  准考证号 证书编号 身份证号）
            Elements report = doc.getElementsByTag("table");
            Elements info = report.select("span");

            //获取考试时间 测试省份 测试站
            Elements report1 = doc.getElementsByClass("cont-box-tit");
            Elements info1=report1.select("span");

            String chineseinfo=" <div class=\"w-detail\">\n" +
                    "            <p class=\"w-d-name\">姓名：</p>\n" +
                    "            <p class=\"w-d-value\">"+info.get(1).html().trim()+"</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"w-detail\">\n" +
                    "            <p class=\"w-d-name\">证件号：</p>\n" +
                    "            <p class=\"w-d-value\">"+info.get(7).html().trim()+"</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"w-detail\">\n" +
                    "            <p class=\"w-d-name\">准考证号：</p>\n" +
                    "            <p class=\"w-d-value\">"+info.get(5).html().trim().trim()+"</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"w-detail\">\n" +
                    "            <p class=\"w-d-name\">最终分：</p>\n" +
                    "            <p class=\"w-d-value\">"+info.get(2).html().trim()+"</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"w-detail\">\n" +
                    "            <p class=\"w-d-name\">等级：</p>\n" +
                    "            <p class=\"w-d-value\">"+info.get(4).html().trim()+"</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"w-detail\">\n" +
                    "            <p class=\"w-d-name\">证书编号：</p>\n" +
                    "            <p class=\"w-d-value\">"+info.get(6).html().trim()+"</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"w-detail\">\n" +
                    "            <p class=\"w-d-name\">省份城市：</p>\n" +
                    "            <p class=\"w-d-value\">"+info1.get(1).html().trim().substring(5).trim()+"</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"w-detail\">\n" +
                    "            <p class=\"w-d-name\">测试站点：</p>\n" +
                    "            <p class=\"w-d-value\">"+info1.get(2).html().trim().substring(4).trim()+"</p>\n" +
                    "        </div>\n"+
                    "        <div class=\"w-detail\">\n" +
                    "            <p class=\"w-d-name\">考试时间：</p>\n" +
                    "            <p class=\"w-d-value\">"+info1.get(0).html().trim().substring(5).trim()+"</p>\n" +
                    "        </div>";



            return chineseinfo;
        }
        catch (Exception e) {
            return "104";		// TODO 错误代码104：连接超时或其他异常
        }
    }

    public static void main(String[] args) {
        ChineseQuery cq=new ChineseQuery();
        System.out.println(cq.login("曹先东", "342626199508191272"));
    }

}
