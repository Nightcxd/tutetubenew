package cn.tutetube.util.bean;

public class UserData {
	private int uid;
	private String num;
	private String passwd;
	private String session;
	private int lastlogin;
	private int lastlogout;
	private int savepasswd;
	private int autologin;
	private String headshot;
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public int getLastlogin() {
		return lastlogin;
	}
	public void setLastlogin(int lastlogin) {
		this.lastlogin = lastlogin;
	}
	public int getLastlogout() {
		return lastlogout;
	}
	public void setLastlogout(int lastlogout) {
		this.lastlogout = lastlogout;
	}
	public int getSavepasswd() {
		return savepasswd;
	}
	public void setSavepasswd(int savepasswd) {
		this.savepasswd = savepasswd;
	}
	public int getAutologin() {
		return autologin;
	}
	public void setAutologin(int autologin) {
		this.autologin = autologin;
	}
	public String getHeadshot() {
		return headshot;
	}
	public void setHeadshot(String headshot) {
		this.headshot = headshot;
	}
	
}
