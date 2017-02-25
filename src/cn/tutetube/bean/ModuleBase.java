package cn.tutetube.bean;

/**
 * Created by cxd on 2017/2/14.
 */
public class ModuleBase {
    private String moduleBase_id;//基本模块
    private String modulePart_name;//模块内部分名
    private String modulePart_type;//模块内部分类型
    private String module_id;//模块id

    public String getModuleBase_id() {
        return moduleBase_id;
    }

    public void setModuleBase_id(String moduleBase_id) {
        this.moduleBase_id = moduleBase_id;
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

    public String getModule_id() {
        return module_id;
    }

    public void setModule_id(String module_id) {
        this.module_id = module_id;
    }
}
