package cn.tutetube.util.bean;

public class PersonalInfo {
	private int uid;
	private int sid;
	private String name;
	private int days;
	private float percent;
	private float avarage;
	private float gpa;
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
        this.uid = uid;
    }
    public int getSid() {
        return sid;
    }
    public void setSid(int sid) {
        this.sid = sid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getDays() {
        return days;
    }
    public void setDays(int days) {
        this.days = days;
    }
    public float getPercent() {
        return percent;
    }
    public void setPercent(float percent) {
        this.percent = percent;
    }
    public float getAvarage() {
        return avarage;
    }
    public void setAvarage(float avarage) {
        this.avarage = avarage;
    }
    public float getGpa() {
        return gpa;
    }
    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return "个人信息{" +
                "姓名：'" + name + '\'' +"，学号："+sid+
                ",天职师大经历了" + days +
                "天, 学习方案完成了百分之" + percent +
                ", 平均学分绩为" + avarage +
                ", GPA为" + gpa +
                '}';
    }
}
