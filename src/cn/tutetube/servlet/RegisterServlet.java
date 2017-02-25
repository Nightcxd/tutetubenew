package cn.tutetube.servlet;

import cn.tutetube.bean.Student;
import cn.tutetube.dao.IRegisterDao;
import cn.tutetube.dao.Imp.RegisterDaoImp;
import cn.tutetube.util.GenerateSequenceUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cxd on 2016/10/18.
 */
@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private IRegisterDao iRegisterDao=new RegisterDaoImp();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String nickname = request.getParameter("nickname").trim();
        String password=request.getParameter("password").trim();
        String nicknamecn=new String(request.getParameter("nicknamecn").getBytes("iso8859-1"),"utf-8");
        Student st=new Student();
        st.setId("s"+GenerateSequenceUtil.generateSequenceNo());
        st.setNickname(nickname.trim());
        st.setPassword(password.trim());
        st.setNicknamecn(nicknamecn.trim());
        st.setHeadicon("upload/img/default.jpg");
        boolean result=iRegisterDao.register(st);
        if (result == true){
            response.getWriter().write("1");//注册成功
        }
        else {
            response.getWriter().write("0");//注册失败
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
