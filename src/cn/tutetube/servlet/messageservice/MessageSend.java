package cn.tutetube.servlet.messageservice;

import cn.tutetube.service.message.MessageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cxd on 2016/10/5.
 */
@WebServlet(name = "MessageSend")
public class MessageSend extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String mobile = request.getParameter("mobile").trim();
        System.out.println(mobile);
        MessageService messageSend=new MessageService();
        messageSend.sendMessage(mobile);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
