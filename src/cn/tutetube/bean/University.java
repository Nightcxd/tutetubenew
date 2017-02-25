package cn.tutetube.bean;

import java.io.Serializable;

/**
 * Created by cxd on 2017/2/9.
 * 大学bean
 */
public class University implements Serializable {
    private String university_id;//大学id
    private String university_name;//大学名称
    private String university_type;//大学类型
    private String university_description;//大学描述

    public String getUniversity_id() {
        return university_id;
    }

    public void setUniversity_id(String university_id) {
        this.university_id = university_id;
    }

    public String getUniversity_name() {
        return university_name;
    }

    public void setUniversity_name(String university_name) {
        this.university_name = university_name;
    }

    public String getUniversity_type() {
        return university_type;
    }

    public void setUniversity_type(String university_type) {
        this.university_type = university_type;
    }

    public String getUniversity_description() {
        return university_description;
    }

    public void setUniversity_description(String university_description) {
        this.university_description = university_description;
    }

    @Override
    public String toString() {
        return "University{" +
                "university_id='" + university_id + '\'' +
                ", university_name='" + university_name + '\'' +
                ", university_type='" + university_type + '\'' +
                ", university_description='" + university_description + '\'' +
                '}';
    }
}
