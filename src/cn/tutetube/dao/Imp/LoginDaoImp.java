package cn.tutetube.dao.Imp;

import cn.tutetube.dao.ILoginDao;
import cn.tutetube.util.StudentUtil;

/**
 * Created by cxd on 2016/10/2.
 */
public class LoginDaoImp implements ILoginDao{
    private StudentUtil studentUtil=new StudentUtil();
    @Override
    public boolean loginCheck(String Nickname, String password) {
        int num=studentUtil.check(Nickname,password);
        if (num==1){
            return true;
        }
            return  false;
    }
}
