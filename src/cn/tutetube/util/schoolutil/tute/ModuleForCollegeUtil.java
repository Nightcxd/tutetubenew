package cn.tutetube.util.schoolutil.tute;

import cn.tutetube.bean.ModuleForCollege;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by cxd on 2017/2/25.
 */
public class ModuleForCollegeUtil {
    private static SessionFactory sessionFactory;

    static {
        Configuration cfg = new Configuration();
        cfg.configure("cn/tutetube/util/hibernate.cfg.xml");
        sessionFactory = cfg.buildSessionFactory();
    }

    /*
    *保存模块内信息
     */
    public void save(ModuleForCollege mb) {
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
    public void delete(String mc_id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            ModuleForCollege mp = (ModuleForCollege) session.get(ModuleForCollege.class, mc_id);
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
    public void update(ModuleForCollege mp) {
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
    public ModuleForCollege getById(String mc_id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            ModuleForCollege ut = (ModuleForCollege) session.get(ModuleForCollege.class, mc_id);//获取
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
    public List<ModuleForCollege> findAll() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<ModuleForCollege> list = session.createQuery("From ModuleForCollege").list();
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
    public ModuleForCollege getByModuleIdAndCollegeId(String module_id,String college_id){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            ModuleForCollege moduleParts = (ModuleForCollege) session.createQuery("From ModuleForCollege where college_id='" + college_id.trim() + "' and module_id='"+module_id.trim()+"'").list().get(0);
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
        ModuleForCollegeUtil moduleForCollegeUtil=new ModuleForCollegeUtil();
        System.out.println(moduleForCollegeUtil.getByModuleIdAndCollegeId("xydt","jxgcxy").getModule_name());
    }
}
