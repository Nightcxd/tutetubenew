package cn.tutetube.dao.Imp;

import cn.tutetube.bean.Student;
import cn.tutetube.dao.IRegisterDao;
import cn.tutetube.util.StudentUtil;

/**
 * Created by cxd on 2016/10/18.
 */
public class RegisterDaoImp implements IRegisterDao{

    @Override
    public boolean register(Object user) {
        Student st= (Student) user;
        StudentUtil tools=new StudentUtil();
        tools.save(st);
        return true;
    }

}
