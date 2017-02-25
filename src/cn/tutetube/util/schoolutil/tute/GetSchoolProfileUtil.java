package cn.tutetube.util.schoolutil.tute;

import org.jsoup.Connection;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

/**
 * Created by cxd on 2017/2/3.
 */
public class GetSchoolProfileUtil {
    private static Logger log = Logger.getLogger(GetSchoolProfileUtil.class);
    @Test
    public void getLeaderTalk() {
        try {
            Document doc =  Jsoup.connect("http://www.tute.edu.cn/list_pic.jsp?urltype=tree.TreeTempUrl&wbtreeid=1109")
                    .timeout(10000).get();
            Elements table=doc.select("table");
            System.out.println(table);
        }
        catch (Exception e){
            log.error("异常错误---",e);
        }
    }

}
