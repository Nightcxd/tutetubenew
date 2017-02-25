package cn.tutetube.dao.Imp;

import cn.tutetube.dao.ICheckNickNameDao;
import cn.tutetube.util.StudentUtil;

/**
 * Created by cxd on 2016/10/18.
 */
public class CheckNickNameDaoImp implements ICheckNickNameDao {
    @Override
    public boolean check(String nickname) {
        StudentUtil su = new StudentUtil();
        int num = su.getByNickName(nickname);
        if (num > 0) {
            return false;
        } else {
            return true;
        }
    }
}
