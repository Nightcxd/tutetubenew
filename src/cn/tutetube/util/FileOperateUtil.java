package cn.tutetube.util;

import org.apache.log4j.Logger;

import java.io.File;

/**
 * Created by cxd on 2017/2/9.
 */
public class FileOperateUtil {
    private static Logger log = Logger.getLogger(FileOperateUtil.class);
    public static synchronized String deleteFile(String ImgPath){
        try {
            File file=new File(ImgPath);
            if(file.isFile()){
                file.delete();//这是主要的操作
                return "1";
            }
            else {
                return "0";
            }
        }
        catch (Exception e){
            log.error("错误信息",e);
            return "0";
        }
    }
}
