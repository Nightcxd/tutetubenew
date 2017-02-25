package cn.tutetube.servlet.cookies;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AddCookieServlet extends HttpServlet {

	public void service(
			HttpServletRequest request, 
			HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
       String nickname= request.getParameter("nickname");
        String password= request.getParameter("password");
//        System.out.println(nickname+password);
        // 创建cookie
		Cookie c1 = new Cookie("nickname", nickname.trim());
		Cookie c2 = new Cookie("password", password.trim());
        c1.setPath("/");
        c2.setPath("/");
        c1.setMaxAge(24*60*60);
        c2.setMaxAge(24*60*60);
		// 添加cookie到response
		response.addCookie(c1);
		response.addCookie(c2);
		out.close();
	}
}



