package cn.tutetube.util.schoolutil;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;

/**
 * Created by cxd on 2016/9/29.
 */
public class HtmlParse {
    public static void testHtml() {
        try {
            String sCurrentLine;
            String sTotalString;
            sCurrentLine = "";
            sTotalString = "";
            java.io.InputStream l_urlStream;
            java.net.URL l_url = new java.net.URL("http://www.tute.edu.cn/");
            java.net.HttpURLConnection l_connection = (java.net.HttpURLConnection) l_url.openConnection();
            l_connection.connect();
            l_urlStream = l_connection.getInputStream();
            java.io.BufferedReader l_reader = new java.io.BufferedReader(new java.io.InputStreamReader(l_urlStream));
            while ((sCurrentLine = l_reader.readLine()) != null) {
                sTotalString += sCurrentLine+"\r\n";
                //  System.out.println(sTotalString);
            }
            String testText = extractText(sTotalString);
            System.out.println( testText );

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String extractText(String inputHtml) throws Exception {
        StringBuffer text = new StringBuffer();
        Parser parser = Parser.createParser(new String(inputHtml.getBytes(),"UTF-8"), "UTF-8");
        // 遍历所有的节点
        NodeList nodes = parser.extractAllNodesThatMatch(new NodeFilter() {
            public boolean accept(Node node) {
                return true;
            }
        });

        System.out.println(nodes.size()); //打印节点的数量
        for (int i=0;i<nodes.size();i++){
            Node nodet = nodes.elementAt(i);
            //System.out.println(nodet.getText());
            text.append(new String(nodet.toPlainTextString().getBytes("UTF-8"))+"\r\n");
        }
        return text.toString();
    }

    public static String test5(String resource) throws Exception {
        Parser myParser = new Parser(resource);
        myParser.setEncoding("UTF-8");
        String filterStr = "table";
        NodeFilter filter = new TagNameFilter(filterStr);
        NodeList nodeList = myParser.extractAllNodesThatMatch(filter);
        TableTag tabletag = (TableTag) nodeList.elementAt(16);

//        extractText(tabletag.toHtml().trim());
       return nodeList.elementAt(16).getChildren().elementAt(1).getChildren().elementAt(2).getChildren().elementAt(1).getChildren().elementAt(1).getChildren().toHtml();
    }
    public static void test6(String resource) throws Exception {
        Parser myParser = new Parser(resource);
        myParser.setEncoding("UTF-8");
        String filterStr = "table";
        NodeFilter filter = new TagNameFilter(filterStr);
        NodeList nodeList = myParser.extractAllNodesThatMatch(filter);
        TableTag tabletag = (TableTag) nodeList.elementAt(16);
        NodeFilter afilter=new NodeClassFilter(LinkTag.class);
        System.out.println(tabletag.toHtml().trim());
    }
    public static String test7(String resource) throws Exception {
        Parser myParser = new Parser(resource);
        myParser.setEncoding("UTF-8");
        String filterStr = "table";
        NodeFilter filter = new TagNameFilter(filterStr);
        NodeList nodeList = myParser.extractAllNodesThatMatch(filter);
        TableTag tabletag = (TableTag) nodeList.elementAt(16);

//        extractText(tabletag.toHtml().trim());
        return ((TableTag) nodeList.elementAt(16)).getChild(1).getLastChild().getChildren().toHtml();
    }
    public static String test8(String resource) throws Exception {
        Parser myParser = new Parser(resource);
        myParser.setEncoding("UTF-8");
        String filterStr = "table";
        NodeFilter filter = new TagNameFilter(filterStr);
        NodeList nodeList = myParser.extractAllNodesThatMatch(filter);
        TableTag tabletag = (TableTag) nodeList.elementAt(16);

//        extractText(tabletag.toHtml().trim());
        return ((TableTag) nodeList.elementAt(16)).getChildren().elementAt(3).getChildren().elementAt(2).getChildren().elementAt(1).getChildren().elementAt(1).getChildren().toHtml();
    }
    public static String test9(String resource) throws Exception {
        Parser myParser = new Parser(resource);
        myParser.setEncoding("UTF-8");
        String filterStr = "table";
        NodeFilter filter = new TagNameFilter(filterStr);
        NodeList nodeList = myParser.extractAllNodesThatMatch(filter);
        TableTag tabletag = (TableTag) nodeList.elementAt(16);

//        extractText(tabletag.toHtml().trim());
        return ((TableTag) nodeList.elementAt(16)).getChildren().elementAt(3).getChildren().elementAt(4).getChildren().toHtml();
    }
    public static String theList(String resource) throws Exception {
        Parser myParser = new Parser(resource);
        myParser.setEncoding("UTF-8");
        String filterStr = "table";
        NodeFilter filter = new TagNameFilter(filterStr);
        NodeList nodeList = myParser.extractAllNodesThatMatch(filter);
        TableTag tabletag = (TableTag) nodeList.elementAt(14);
        Parser mylist=new Parser(String.valueOf(tabletag));
        mylist.setEncoding("UTF-8");

        return tabletag.toHtml();
    }
    public static void main(String[] args) throws Exception {
        System.out.println(theList("http://www.tute.edu.cn/list.jsp?urltype=tree.TreeTempUrl&wbtreeid=1022"));
    }
}
