package cn.tutetube.bean;

import java.io.Serializable;

/**
 * Created by cxd on 2017/2/9.
 * 模块内部分bean
 */
public class ModulePart implements Serializable {
    private String m_pid;//模块映射id
    private String modulePart_id;//模块内部分id
    private String modulePart_name;//模块内部分名
    private String modulePart_type;//模块内部分类型
    private String modulePart_description;//模块内部分描述
    private String modulePart_fileUrl;//模块内部分文件路径
    private String modulePart_fileType;//模块内部分文件类型
    private String modulePart_content;//模块内部分文字内容
    private String modulePart_releaseTime;//模块发布时间
    private String from_module_id;//模块id

    public String getModulePart_id() {
        return modulePart_id;
    }

    public void setModulePart_id(String modulePart_id) {
        this.modulePart_id = modulePart_id;
    }

    public String getModulePart_name() {
        return modulePart_name;
    }

    public void setModulePart_name(String modulePart_name) {
        this.modulePart_name = modulePart_name;
    }

    public String getModulePart_type() {
        return modulePart_type;
    }

    public void setModulePart_type(String modulePart_type) {
        this.modulePart_type = modulePart_type;
    }

    public String getModulePart_description() {
        return modulePart_description;
    }

    public void setModulePart_description(String modulePart_description) {
        this.modulePart_description = modulePart_description;
    }

    public String getModulePart_fileUrl() {
        return modulePart_fileUrl;
    }

    public void setModulePart_fileUrl(String modulePart_fileUrl) {
        this.modulePart_fileUrl = modulePart_fileUrl;
    }

    public String getModulePart_fileType() {
        return modulePart_fileType;
    }

    public void setModulePart_fileType(String modulePart_fileType) {
        this.modulePart_fileType = modulePart_fileType;
    }

    public String getModulePart_content() {
        return modulePart_content;
    }

    public void setModulePart_content(String modulePart_content) {
        this.modulePart_content = modulePart_content;
    }

    public String getModulePart_releaseTime() {
        return modulePart_releaseTime;
    }

    public void setModulePart_releaseTime(String modulePart_releaseTime) {
        this.modulePart_releaseTime = modulePart_releaseTime;
    }

    public String getFrom_module_id() {
        return from_module_id;
    }

    public void setFrom_module_id(String from_module_id) {
        this.from_module_id = from_module_id;
    }

    public String getM_pid() {
        return m_pid;
    }

    public void setM_pid(String m_pid) {
        this.m_pid = m_pid;
    }

    @Override
    public String toString() {
        return "ModulePart{" +
                "m_pid='" + m_pid + '\'' +
                ", modulePart_id='" + modulePart_id + '\'' +
                ", modulePart_name='" + modulePart_name + '\'' +
                ", modulePart_type='" + modulePart_type + '\'' +
                ", modulePart_description='" + modulePart_description + '\'' +
                ", modulePart_fileUrl='" + modulePart_fileUrl + '\'' +
                ", modulePart_fileType='" + modulePart_fileType + '\'' +
                ", modulePart_content='" + modulePart_content + '\'' +
                ", modulePart_releaseTime='" + modulePart_releaseTime + '\'' +
                ", from_module_id='" + from_module_id + '\'' +
                '}';
    }
}
