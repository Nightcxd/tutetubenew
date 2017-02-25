package cn.tutetube.bean;

import java.io.Serializable;

/**
 * Created by cxd on 2017/1/31.
 * 帖子图片bean
 */
public class NoteForImages implements Serializable {
    private String images_id;//图片id
    private String note_id;//帖子id
    private String id;//学生id
    private String note_images;//图片url

    public String getImages_id() {
        return images_id;
    }

    public void setImages_id(String images_id) {
        this.images_id = images_id;
    }

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

    public String getNote_images() {
        return note_images;
    }

    public void setNote_images(String note_images) {
        this.note_images = note_images;
    }

    @Override
    public String toString() {
        return "NoteForImages{" +
                "images_id=" + images_id +
                ", note_id=" + note_id +
                ", id=" + id +
                ", note_images='" + note_images + '\'' +
                '}';
    }
}
