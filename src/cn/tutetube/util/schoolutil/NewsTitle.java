package cn.tutetube.util.schoolutil;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cxd on 2016/10/1.
 */
@WebServlet(name = "NewsTitle")
public class NewsTitle extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Parser myParser = null;
        try {
            myParser = new Parser("http://www.tute.edu.cn/");
            myParser.setEncoding("UTF-8");
            String filterStr = "table";
            NodeFilter filter = new TagNameFilter(filterStr);
            NodeList nodeList = myParser.extractAllNodesThatMatch(filter);
            TableTag tabletag = (TableTag) nodeList.elementAt(16);
            response.getWriter().write(tabletag.toHtml().trim());
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
