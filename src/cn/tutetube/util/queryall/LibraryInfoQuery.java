package cn.tutetube.util.queryall;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


/**
 * Created by cxd on 2017/1/2.
 */
public class LibraryInfoQuery {
    private static Logger log = Logger.getLogger(LibraryInfoQuery.class);
    public String login(String user_id, String password) {
        try {
            Document doc = Jsoup.connect("http://211.81.31.34/uhtbin/cgisirsi/x/0/0/57/49?user_id=LIBTECHTEACH&password=LIBTE")
                    .timeout(50000).get();

            Elements report = doc.getElementsByTag("form");
            String link = "http://211.81.31.34" + report.get(1).toString().split("action")[1].split(">")[0].substring(2).replace("\"", "").trim();
            log.info(link);
            String account = getAccount(link.trim(), user_id.trim(), password.trim());
            log.info(account);
            if (account.trim().equals("101")) {
                return "101";   // TODO 错误代码101：登陆失败
            } else {
                String info = getBorrowLink(account.trim()).trim();
                if (info.trim().equals("102")) {
                    return "102";   // TODO 错误代码102：获取借阅、预约及申请纪录失败
                } else {
                    return info;
                }
            }

        } catch (Exception e) {
            log.error("连接超时或其他异常",e);
            return "103";        // TODO 错误代码103：连接超时或其他异常
        }
    }

    public String getBorrowLink(String link) {
        try {
            //获取借阅、预约及申请纪录
//            Connection con = Jsoup.connect(link.trim())
//                    .timeout(50000);
//            Connection.Response response = con.execute();
            Document doc1 = Jsoup.connect(link.trim())
                    .timeout(50000).get();

            log.info(doc1+"\n\n\n\n");

            Elements borrow = doc1.getElementsByTag("ul");
            log.info(borrow+"\n\n\n\n");
            Elements li=borrow.select("li");
            log.info(li+"\n\n\n\n");
            Elements aInfo=li.select("a");
            log.info(aInfo+"\n\n\n\n");
            String borrowLink="http://211.81.31.34"+aInfo.get(8).toString().split("\"")[1].toString().trim();
            log.info(borrowLink);

//            Connection con1 = Jsoup.connect(borrowLink.trim())
//                    .timeout(50000);
//            Connection.Response response1 = con1.execute();
            Document doc2 = Jsoup.connect(borrowLink.trim())
                    .timeout(50000).get();

            Elements bookInfo = doc2.getElementsByTag("table");
            Elements singleInfo = bookInfo.select("tr");

            String bookAllInfo = "<tr>\n" +
                    "                    <td>书名</td>\n" +
                    "                    <td>到期时间</td>\n" +
                    "                </tr>";
            int size = singleInfo.size() - 1;
            for (int i = 1; i < size; i++) {
                String bookName = singleInfo.get(i).children().get(0).html().substring(15).trim();
                String bookTime = singleInfo.get(i).children().get(2).children().get(0).html().trim();
                bookAllInfo += "                <tr>\n" +
                        "                    <td>" + bookName + "</td>\n" +
                        "                    <td>" + bookTime + "</td>\n" +
                        "                </tr>\n";
                log.info(bookName+"    "+bookTime);
            }
            log.info(bookAllInfo);
            return bookAllInfo.trim();
        } catch (Exception e) {
            log.error("获取借阅、预约及申请纪录失败",e);
            return "102";
        }
    }

    public String getBookAllInfo(String link) {
        try {

            Connection con = Jsoup.connect(link.trim())
                    .timeout(50000);
            Connection.Response response = con.execute();
            Document doc2 = response.parse();

            Elements bookInfo = doc2.getElementsByTag("table");
            Elements singleInfo = bookInfo.select("tr");

            String bookAllInfo = "<tr>\n" +
                    "                    <td>书名</td>\n" +
                    "                    <td>到期时间</td>\n" +
                    "                </tr>";
            int size = singleInfo.size() - 1;
            for (int i = 1; i < size; i++) {
                String bookName = singleInfo.get(i).children().get(0).html().substring(15).trim();
                String bookTime = singleInfo.get(i).children().get(2).children().get(0).html().trim();
                bookAllInfo += "                <tr>\n" +
                        "                    <td>" + bookName + "</td>\n" +
                        "                    <td>" + bookTime + "</td>\n" +
                        "                </tr>\n";
                log.info(bookName+"    "+bookTime);
            }
            log.info(bookAllInfo);
            return bookAllInfo;
        } catch (Exception e) {
            log.error(e.toString());
            return "103";  // TODO 错误代码103：获取最终信息失败
        }


    }

    public String getAccount(String link, String user_id, String password) {
        try {
            Connection con = Jsoup.connect(link.trim())
                    .data("user_id", user_id.trim())
                    .data("password", password.trim())
                    .timeout(50000)
                    .method(Connection.Method.POST);
            Connection.Response response = con.execute();
            Document doc = response.parse();

            //获取我的账户链接
            return "http://211.81.31.34" + doc.getElementsByClass("rootbar").get(1).toString().split(">")[0].split("\"")[1].trim();

        } catch (Exception e) {
            return "101";        // TODO 错误代码101：登陆失败
        }
    }


    public static void main(String[] args) {
        LibraryInfoQuery lq = new LibraryInfoQuery();
        System.out.println(lq.login("R100C201400533", "1111"));
    }

}
