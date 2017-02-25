package cn.tutetube.util;
  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
  
import sun.misc.BASE64Decoder;  
import sun.misc.BASE64Encoder;  
  
public class BaseUtil
{  
    public static void main(String[] args)  
    {  
        String strImg = "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";
        System.out.println(strImg);
        GenerateImage(strImg,"456");
    }  
    //图片转化成base64字符串  
    public static String GetImageStr()  
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
        String imgFile = "img/1.png";//待处理的图片
        InputStream in = null;  
        byte[] data = null;  
        //读取图片字节数组  
        try   
        {  
            in = new FileInputStream(imgFile);          
            data = new byte[in.available()];  
            in.read(data);  
            in.close();  
        }   
        catch (IOException e)   
        {  
            e.printStackTrace();  
        }  
        //对字节数组Base64编码  
        BASE64Encoder encoder = new BASE64Encoder();  
        return encoder.encode(data);//返回Base64编码过的字节数组字符串  
    }  
      
    //base64字符串转化成图片  
    public static String GenerateImage(String imgStr,String imgName) {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空  
            return "100";
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码  
            byte[] b = decoder.decodeBuffer(imgStr.replace(" ", "+"));
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
//            String imgFilePath ="D:\\IdeaProjects\\tutetubenew\\web\\upload\\img\\"+imgName+".jpg"; ; ;//新生成的图片
//            OutputStream out = new FileOutputStream(imgFilePath);
//            out.write(b);
//            out.flush();
//            out.close();
            String imgFilePath = "/root/upload/img/" + imgName + ".jpg";
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            String rimgFilePath = "/root/software/apache-tomcat/webapps/tutetubenew/upload/img/" + imgName + ".jpg";
            OutputStream out1 = new FileOutputStream(rimgFilePath);
            out1.write(b);
            out1.flush();
            out1.close();
            return "upload/img/" + imgName + ".jpg";
        } catch (Exception e) {
            return "101";  //图像存储失败
        }
    }
    public static String GenerateIcon(String imgStr,String imgName)
    {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return "100";
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr.replace(" ","+"));
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
//            String imgFilePath ="D:\\IdeaProjects\\tutetubenew\\web\\upload\\icon\\"+imgName+".jpg"; ; ;//新生成的图片
//            OutputStream out = new FileOutputStream(imgFilePath);
//            out.write(b);
//            out.flush();
//            out.close();
            String imgFilePath ="/root/upload/icon/"+imgName+".jpg"; ; ;//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            String rimgFilePath="/root/software/apache-tomcat/webapps/tutetubenew/upload/icon/"+imgName+".jpg";
            OutputStream out1 = new FileOutputStream(rimgFilePath);
            out1.write(b);
            out1.flush();
            out1.close();
            return "upload/icon/"+imgName+".jpg";
        }
        catch (Exception e)
        {
            return "101";  //图像存储失败
        }
    }

}  