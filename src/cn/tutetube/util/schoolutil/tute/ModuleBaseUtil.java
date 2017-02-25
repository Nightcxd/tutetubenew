package cn.tutetube.util.schoolutil.tute;

import cn.tutetube.bean.ModuleBase;
import cn.tutetube.bean.ModulePart;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by cxd on 2017/2/14.
 */
public class ModuleBaseUtil {
    private static SessionFactory sessionFactory;

    static {
        Configuration cfg = new Configuration();
        cfg.configure("cn/tutetube/util/hibernate.cfg.xml");
        sessionFactory = cfg.buildSessionFactory();
    }

    /*
    *保存模块内信息
     */
    public void save(ModuleBase mb) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(mb);//保存
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
    public void delete(String moduleBase_id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            ModuleBase mp = (ModuleBase) session.get(ModuleBase.class, moduleBase_id);
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
    public void update(ModuleBase mp) {
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
    public ModuleBase getById(String moduleBase_id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            ModuleBase ut = (ModuleBase) session.get(ModuleBase.class, moduleBase_id);//获取
            tx.commit();
            return ut;
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
    public List<ModuleBase> findAll() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<ModuleBase> list = session.createQuery("From ModuleBase ").list();
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
    public List<ModuleBase> getByModuleId(String module_id){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<ModuleBase> moduleParts = session.createQuery("From ModuleBase where module_id='" + module_id.trim() + "'").list();
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
        ModuleBaseUtil msb=new ModuleBaseUtil();
        List<ModuleBase> moduleBases=msb.getByModuleId("xxgk");
        int size=moduleBases.size();
        for (int i=0;i<size;i++){
            System.out.println(moduleBases.get(i).getModulePart_name());
        }
    }
}
