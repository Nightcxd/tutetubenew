package cn.tutetube.util;

import cn.tutetube.bean.College;
import cn.tutetube.bean.Module;
import cn.tutetube.util.schoolutil.tute.CollegeUtil;
import cn.tutetube.util.schoolutil.tute.ModuleUtil;

import java.util.List;

/**
 * Created by cxd on 2017/2/9.
 */
public class modulePartTest {
    public static void main(String[] args) {
        String college_id="wlzx";
        ModuleUtil moduleUtil=new ModuleUtil();
        List<Module> modules=moduleUtil.getModuleByCollegeId(college_id);
        System.out.println(modules.get(0));

    }
}
