package cn.tutetube.util.schoolutil.tute;

import cn.tutetube.bean.Module;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by cxd on 2017/2/9.
 */
public class ModuleUtil {
    private static SessionFactory sessionFactory;

    static {
        Configuration cfg = new Configuration();
        cfg.configure("cn/tutetube/util/hibernate.cfg.xml");
        sessionFactory = cfg.buildSessionFactory();
    }

    /*
    *保存模块信息
     */
    public void save(Module md) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(md);//保存
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
    删除指定id的模块信息
     */
    public void delete(String module_id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Module md = (Module) session.get(Module.class, module_id);
            session.delete(md);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
    更新模块信息
     */
    public void update(Module md) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(md);//保存
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
    通过id获取模块信息
     */
    public Module getById(String module_id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Module md = (Module) session.get(Module.class, module_id);//获取
            tx.commit();
            return md;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }


    /*
    获取所有模块信息
     */
    public List<Module> findAll() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Module> list = session.createQuery("From Module").list();
            tx.commit();
            return list;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    //    通过college_id获取所有的模块
    public List<Module> getModuleByCollegeId(String college_id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Module> modules = session.createQuery("From Module where college_id='" + college_id.trim() + "'").list();
            tx.commit();
            return modules;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
