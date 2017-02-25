package cn.tutetube.util;

import cn.tutetube.bean.Student;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by cxd on 2016/9/22.
 */
public class StudentUtil {
    private static SessionFactory sessionFactory;

    static {
        Configuration cfg = new Configuration();
        cfg.configure("cn/tutetube/util/hibernate.cfg.xml");
        sessionFactory = cfg.buildSessionFactory();
    }

    /*
    *保存用户
     */
    public void save(Student st) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(st);//保存
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
    删除指定id的用户
     */
    public void delete(String id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Student st = (Student) session.get(Student.class, id);
            session.delete(st);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
    更新用户
     */
    public void update(Student st) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(st);//保存
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
    通过id获取一个用户
     */
    public Student getById(String id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Student st = (Student) session.get(Student.class, id);//获取
            tx.commit();
            return st;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
   通过昵称获取一个用户
    */
    public int getByNickName(String nickname) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Student> list = session.createQuery("From Student where nickname=?").setString(0, nickname).list();//位置参数锁定
            int num = list.size();
            tx.commit();
            return num;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
    查询所有
     */
    public List<Student> findAll() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Student> list = session.createQuery("From Student").list();
            tx.commit();
            return list;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
    查询个人
     */
    public int check(String nickname, String password) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Student> list = session.createQuery("From Student where nickname=? and password=?").setString(0, nickname).setString(1, password).list();//位置参数锁定
            int num = list.size();
            tx.commit();
            return num;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    //通过昵称获取学生单个对象
    public List<Student> getStudentByNickName(String nickname) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Student> st= session.createQuery("From Student where nickname=?").setString(0, nickname).list();
            tx.commit();
            return st;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }

    }




    /*
    分页的查询
     */
    public QueryResult<Student> findAll(int firstResult, int maxResult) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            //查询总记录数量
            Long count = (Long) session.createQuery(
                    "select count(*) from Student")
                    .uniqueResult();//执行查询
            //查询一段数据
            Query query = session.createQuery("From Student");
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResult);
            List<Student> list = query.list();
            tx.commit();
            return new QueryResult<Student>(list, count);
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
    public Student getStudentByNicknameAndPassword(String nickname,String password){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Student> list = session.createQuery("From Student where nickname=? and password=?").setString(0, nickname.trim()).setString(1, password.trim()).list();//位置参数锁定
            tx.commit();
            return list.get(0);
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
