package cn.tutetube.bean;

import java.io.Serializable;

/**
 * Created by cxd on 2017/1/27.
 * 帖子bean
 */
public class Note implements Serializable {
    private String note_id;//贴子id
    private String id;//学生id
    private String release_time;//发布时间
    private String note_type;//帖子类型
    private String note_content;//帖子内容

    public String getNote_id() {
        return note_id;
    }

    public void setNote_id(String note_id) {
        this.note_id = note_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    public String getNote_type() {
        return note_type;
    }

    public void setNote_type(String note_type) {
        this.note_type = note_type;
    }

    public String getNote_content() {
        return note_content;
    }

    public void setNote_content(String note_content) {
        this.note_content = note_content;
    }

    @Override
    public String toString() {
        return "Note{" +
                "note_id=" + note_id +
                ", id=" + id +
                ", release_time='" + release_time + '\'' +
                ", note_type='" + note_type + '\'' +
                ", note_content='" + note_content + '\'' +
                '}';
    }
}
