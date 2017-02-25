package cn.tutetube.util.queryall;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Map;

/**
 * Created by cxd on 2017/1/2.
 */
public class BookInfo {
    private static Logger log = Logger.getLogger(BookInfo.class);
    public String login(String user_id, String password) {
        try {
            Document doc = Jsoup.connect("http://211.81.31.34/uhtbin/cgisirsi/x/0/0/57/49?user_id=LIBTECHTEACH&password=LIBTE")
                    .timeout(10*60*1000).get();

            Elements report = doc.getElementsByTag("form");
            String link = "http://211.81.31.34" + report.get(1).toString().split("action")[1].split(">")[0].substring(2).replace("\"", "").trim();


            Connection.Response res = Jsoup.connect(link.trim())
                    .data("user_id", user_id.trim())
                    .data("password", password.trim())
                    .timeout(10 * 60 * 1000)
                    .method(Connection.Method.POST)
                    .execute();
               Document doc1=res.parse();


            Document doc2=Jsoup.connect("http://211.81.31.34" + doc1.getElementsByClass("rootbar").get(1).toString().split(">")[0].split("\"")[1].trim())
                    .timeout(10 * 60 * 1000)
                    .get();
          //log.info(doc2+"\n\n\n\n");

            Elements borrow = doc2.getElementsByTag("ul");
            //log.info(borrow+"\n\n\n\n");
            Elements li=borrow.select("li");
            //log.info(li+"\n\n\n\n");
            String aInfo=li.select("a").get(8).toString();
            //log.info(aInfo+"\n\n\n\n");
            String borrowLink="http://211.81.31.34"+aInfo.split("\"")[1].toString().trim();
          //  log.info(borrowLink+"\n\n\n\n");

            Document doc3 = Jsoup.connect(borrowLink.trim())
                    .timeout(10 * 60 * 1000)
                    .get();
          //  log.info(doc3);
            Elements bookInfo = doc3.getElementsByTag("table");
            Elements singleInfo = bookInfo.select("tr");
            String temp=singleInfo.get(0).select("th").get(2).html().toString().trim();
            if (temp.equals("原因")){
                String bookAllInfo = "<tr>\n" +
                        "                    <td>书名</td>\n" +
                        "                    <td>原因</td>\n" +
                        "                </tr>";
                int size = singleInfo.size() - 1;
                //log.info(size);
                for (int i = 1; i < size; i++) {
                    String bookName = singleInfo.get(i).children().get(0).html().substring(15).trim();
                    String reason = singleInfo.get(i).children().get(2).html().trim();
                    bookAllInfo += "                <tr>\n" +
                            "                    <td>" + bookName + "</td>\n" +
                            "                    <td>" + reason + "</td>\n" +
                            "                </tr>\n";
                    //log.info(bookName+"    "+reason+"\n\n\n\n");
                }
                return bookAllInfo;
            }
            else {
                String bookAllInfo = "<tr>\n" +
                        "                    <td>书名</td>\n" +
                        "                    <td>到期日期/催还到期日期</td>\n" +
                        "                </tr>";
                int size = singleInfo.size() - 1;
                //log.info(size);
                for (int i = 1; i < size; i++) {
                    String bookName = singleInfo.get(i).children().get(0).html().substring(15).trim();
                    String bookTime = singleInfo.get(i).children().get(2).children().get(0).html().trim();
                    bookAllInfo += "                <tr>\n" +
                            "                    <td>" + bookName + "</td>\n" +
                            "                    <td>" + bookTime + "</td>\n" +
                            "                </tr>\n";
                   // log.info(bookName + "    " + bookTime + "\n\n\n\n");
                }
                //log.info(bookAllInfo + "\n\n\n\n");
                return bookAllInfo;
            }

        } catch (Exception e) {
            log.error("连接超时或其他异常", e);
            return "103";        // TODO 错误代码103：连接超时或其他异常
        }

    }

    public static void main(String[] args) {
        BookInfo bf=new BookInfo();
        System.out.println(bf.login("R100C201503598","1111"));
    }

}
