package cn.tutetube.bean;

import java.io.Serializable;

/**
 * Created by cxd on 2017/2/9.
 * 学院学校模块bean
 */
public class Module implements Serializable {
    private String module_id;//模块id
    private String module_name;//模块名
    private String module_type;//模块类型
    private String module_description;//模块描述
    private String university_id;//大学id
    private String module_releaseTime;//模块发布时间
    private String college_id;//学院id

    public String getModule_id() {
        return module_id;
    }

    public void setModule_id(String module_id) {
        this.module_id = module_id;
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
    }

    public String getModule_type() {
        return module_type;
    }

    public void setModule_type(String module_type) {
        this.module_type = module_type;
    }

    public String getModule_description() {
        return module_description;
    }

    public void setModule_description(String module_description) {
        this.module_description = module_description;
    }

    public String getUniversity_id() {
        return university_id;
    }

    public void setUniversity_id(String university_id) {
        this.university_id = university_id;
    }

    public String getCollege_id() {
        return college_id;
    }

    public void setCollege_id(String college_id) {
        this.college_id = college_id;
    }

    public String getModule_releaseTime() {
        return module_releaseTime;
    }

    public void setModule_releaseTime(String module_releaseTime) {
        this.module_releaseTime = module_releaseTime;
    }

}
