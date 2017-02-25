package cn.tutetube.servlet.cookies;

import cn.tutetube.bean.Student;
import cn.tutetube.util.StudentUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

public class FindCookieServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(FindCookieServlet.class);
	public void service(
			HttpServletRequest request, 
			HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(
				"text/html;charset=UTF-8");
		// 获取Cookie
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				String nickname = c.getName();
                if (nickname.trim().equals("nickname")) {
                    StudentUtil su=new StudentUtil();
                    Student st=su.getStudentByNickName(c.getValue().trim()).get(0);
                    String nicknamecn=URLDecoder.decode(st.getNicknamecn(), "UTF-8");
                    String headicon=st.getHeadicon();
                    String jsonStr="nicknamecn#$%"+nicknamecn.trim()+"#$%headicon#$%"+headicon.trim()+"";
                    log.info(nickname+"\n");
                    log.info(headicon+"\n");
                    try {
                        response.getWriter().write(jsonStr);
                    }
                    catch (Exception e){
                        jsonStr="nicknamecn#$%你还没有完善个人信息#$%headicon#$%upload/img/default.jpg";
                        response.getWriter().write(jsonStr);
                        log.error("昵称为空！");
                    }
                }
			}
		} else {
            response.getWriter().write("");
		}
	}
}
