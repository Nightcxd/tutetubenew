package cn.tutetube.util.schoolutil.tute;

import cn.tutetube.bean.ModulePart;
import cn.tutetube.bean.University;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by cxd on 2017/2/9.
 */
public class ModulePartUtil {
    private static SessionFactory sessionFactory;

    static {
        Configuration cfg = new Configuration();
        cfg.configure("cn/tutetube/util/hibernate.cfg.xml");
        sessionFactory = cfg.buildSessionFactory();
    }

    /*
    *保存模块内信息
     */
    public void save(ModulePart mp) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(mp);//保存
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
    删除指定id的模块内部分信息
     */
    public void delete(String modulePart_id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            ModulePart mp = (ModulePart) session.get(ModulePart.class, modulePart_id);
            session.delete(mp);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
    更新模块内部分信息
     */
    public void update(ModulePart mp) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(mp);//保存
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
    通过id获取模块内部分信息
     */
    public List<ModulePart> getByModulePart_id(String modulePart_id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<ModulePart> modulePartList = session.createQuery("From ModulePart where modulePart_id='" + modulePart_id.trim() + "'").list();//获取
            tx.commit();
            return modulePartList;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }


    /*
    查询所有模块内部分
     */
    public List<ModulePart> findAll() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<ModulePart> list = session.createQuery("From ModulePart").list();
            tx.commit();
            return list;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

//    通过module_id获取所有的modulePart
    public List<ModulePart> getByModuleId(String from_module_id){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<ModulePart> moduleParts = session.createQuery("From ModulePart where from_module_id='" + from_module_id.trim() + "'").list();
            tx.commit();
            return moduleParts;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public static void main(String[] args) {
         ModulePartUtil modulePartUtil=new ModulePartUtil();
         List<ModulePart> moduleParts= modulePartUtil.getByModulePart_id("ldtc");
         int size=moduleParts.size();
         for (int i=0;i<size;i++) {
            System.out.println(moduleParts.get(i).toString());
        }
    }

}
