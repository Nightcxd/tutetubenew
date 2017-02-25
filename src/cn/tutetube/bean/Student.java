package cn.tutetube.bean;

import java.io.Serializable;

/**
 * Created by cxd on 2016/10/2.
 * 学生bean
 */
public class Student implements Serializable {
    private String id;//学生id
    private String sno;//教务系统urp学生学号
    private String mm;//教务系统urp密码
    private String sname;//学生姓名
    private Integer sex;//性别
    private String nickname;//app用户名
    private String password;//app密码
    private String mobile;//手机
    private String headicon;//头像
    private String classname;//班级
    private String college;//学院
    private String nicknamecn;//昵称
    private String description;//个人简介

    public String getNicknamecn() {
        return nicknamecn;
    }

    public void setNicknamecn(String nicknamecn) {
        this.nicknamecn = nicknamecn;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHeadicon() {
        return headicon;
    }

    public void setHeadicon(String headicon) {
        this.headicon = headicon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", sno='" + sno + '\'' +
                ", mm='" + mm + '\'' +
                ", sname='" + sname + '\'' +
                ", sex=" + sex +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", headicon='" + headicon + '\'' +
                ", classname='" + classname + '\'' +
                ", college='" + college + '\'' +
                ", nicknamecn='" + nicknamecn + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
