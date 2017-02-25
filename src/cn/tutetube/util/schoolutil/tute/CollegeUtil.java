package cn.tutetube.util.schoolutil.tute;

import cn.tutetube.bean.College;
import cn.tutetube.bean.ModuleForCollege;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by cxd on 2017/2/9.
 */
public class CollegeUtil {
    private static SessionFactory sessionFactory;

    static {
        Configuration cfg = new Configuration();
        cfg.configure("cn/tutetube/util/hibernate.cfg.xml");
        sessionFactory = cfg.buildSessionFactory();
    }

    /*
    *保存学院信息
     */
    public void save(College cl) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(cl);//保存
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
    删除指定id的学院信息
     */
    public void delete(String college_id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            College cl = (College) session.get(College.class, college_id);
            session.delete(cl);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
    更新学院信息
     */
    public void update(College cl) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(cl);//保存
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
    通过id获取学院信息
     */
    public College getById(String college_id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            College cl = (College) session.get(College.class, college_id);//获取
            tx.commit();
            return cl;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }


    /*
    查询所有学院信息
     */
    public List<College> findAll() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<College> list = session.createQuery("From College ").list();
            tx.commit();
            return list;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public College getByCollegeName(String college_name) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            College college = (College) session.createQuery("From College where college_name='" + college_name.trim() + "'").list().get(0);
            tx.commit();
            return college;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        CollegeUtil cu=new CollegeUtil();
        System.out.println(cu.getByCollegeName("信息技术工程学院").getCollege_id());
    }
}
