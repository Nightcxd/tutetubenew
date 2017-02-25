package cn.tutetube.util.queryall;

import com.sun.xml.internal.bind.v2.runtime.output.Encoded;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.beans.Encoder;
import java.net.URLEncoder;

/**
 * Created by cxd on 2017/2/22.
 */
public class CET46Query {
    private static Logger log = Logger.getLogger(CET46Query.class);
    public String login(String id, String xm) {
        try {
            Connection con = Jsoup.connect("http://cet.99sushe.com/")
                    .timeout(10000)
                    .method(Connection.Method.POST);
            Connection.Response response = con.execute();
            Document doc = response.parse();

            Connection con1 = Jsoup.connect("http://cet.99sushe.com/getscore120110162200104")
                    .timeout(10000)
                    .method(Connection.Method.POST);
            Connection.Response response1 = con1.execute();
            Document doc1 = response1.parse();
            log.info(doc1);



                return null;
            }

        catch (Exception e) {
            log.error("连接超时或其他异常", e);
            return "103";        // TODO 错误代码103：连接超时或其他异常
        }

    }

    public static void main(String[] args) {
        CET46Query cet=new CET46Query();
        cet.login("120110162200104","曹先东");
    }
}
