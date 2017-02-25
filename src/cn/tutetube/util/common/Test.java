package cn.tutetube.util.common;

import cn.tutetube.util.bean.PersonalInfo;
import cn.tutetube.util.bean.UserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cxd on 2016/10/24.
 */
public class Test {
    public static void main(String[] args) {
        NetHelper t=new NetHelper();
        UserData st=new UserData();
        st.setPasswd("IGPSGL");
        st.setNum("02210140201");

//       t.getRoll(st);
        //System.out.println(studentinfo.keySet());
        System.out.println(t.getCourse(st).toString());
    }
}
