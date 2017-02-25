package cn.tutetube.util.schoolutil;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * Created by cxd on 2016/9/29.
 */
public class HtmlTest {
    public static void main(String[] args) throws ParserException {
        getImage("http://www.tutetube.cn:9012/");
    }

    public static void getImage(String url) throws ParserException {
        Parser parser = new Parser(url);
        parser.setEncoding("UTF-8");
        PrototypicalNodeFactory pnfPrototypicalNodeFactory = new PrototypicalNodeFactory();
        pnfPrototypicalNodeFactory.registerTag(new Div());
        parser.setNodeFactory(pnfPrototypicalNodeFactory);

        NodeFilter filter1 = new NodeClassFilter(LinkTag.class);
        NodeList nodelist = parser.extractAllNodesThatMatch(filter1);
        for (Node node : nodelist.toNodeArray()) {
            if (node instanceof LinkTag) {
                LinkTag link = (LinkTag) node;
                if (link != null) {
                    System.out.println("地址:" + link.getLink()+"\t标题:"+link.getLinkText());
                }
            }
        }
    }
}
