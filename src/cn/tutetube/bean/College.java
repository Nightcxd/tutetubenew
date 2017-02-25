package cn.tutetube.bean;

import java.io.Serializable;

/**
 * Created by cxd on 2017/2/9.
 * 学院bean
 */
public class College implements Serializable {
    private String college_id;//学院id
    private String college_name;//学院名称
    private String university_id;//大学id
    private String college_type;//学院类型
    private String college_description;//学院描述

    public String getCollege_id() {
        return college_id;
    }

    public void setCollege_id(String college_id) {
        this.college_id = college_id;
    }

    public String getCollege_name() {
        return college_name;
    }

    public void setCollege_name(String college_name) {
        this.college_name = college_name;
    }

    public String getUniversity_id() {
        return university_id;
    }

    public void setUniversity_id(String university_id) {
        this.university_id = university_id;
    }

    public String getCollege_type() {
        return college_type;
    }

    public void setCollege_type(String college_type) {
        this.college_type = college_type;
    }

    public String getCollege_description() {
        return college_description;
    }

    public void setCollege_description(String college_description) {
        this.college_description = college_description;
    }

    @Override
    public String toString() {
        return "College{" +
                "college_id='" + college_id + '\'' +
                ", college_name='" + college_name + '\'' +
                ", university_id='" + university_id + '\'' +
                ", college_type='" + college_type + '\'' +
                ", college_description='" + college_description + '\'' +
                '}';
    }
}
