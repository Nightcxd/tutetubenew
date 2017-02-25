package cn.tutetube.util.common;

import cn.tutetube.util.bean.CourseInfo;
import cn.tutetube.util.bean.UserData;

/**
 * Created by cxd on 2016/12/10.
 */
public class CourseTest {
    public static void main(String[] args) {
       NetHelper nh=new NetHelper();
        UserData userData=new UserData();
        userData.setPasswd("IGPSGL");
        userData.setNum("02210140201");
        System.out.println(nh.getGrades(userData));
    }
}
