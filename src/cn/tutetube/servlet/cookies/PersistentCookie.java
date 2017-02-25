package cn.tutetube.servlet.cookies;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PersistentCookie extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//创建Cookie
		Cookie c1 = new Cookie("uname","Kitty");
		//设置c1的过期时间
		c1.setMaxAge(100);
		response.addCookie(c1);
		Cookie c2 = new Cookie("city","Bejing");
		response.addCookie(c2);
		out.close();
	}
}
