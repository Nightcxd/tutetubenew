package cn.tutetube.util;

import cn.tutetube.bean.Note;
import cn.tutetube.bean.Student;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by cxd on 2017/1/27.
 */
public class NoteUtil {
    private static SessionFactory sessionFactory;

    static {
        Configuration cfg = new Configuration();
        cfg.configure("cn/tutetube/util/hibernate.cfg.xml");
        sessionFactory = cfg.buildSessionFactory();
    }

    /*
    *保存note
     */
    public void save(Note nt) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(nt);//保存
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
    删除指定note_id的note
     */
    public void delete(String note_id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Note nt = (Note) session.get(Note.class, note_id);
            session.delete(nt);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
    更新note
     */
    public void update(Note nt) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(nt);//保存
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
    通过id获取一个note
     */
    public Note getById(String note_id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Note nt = (Note) session.get(Note.class, note_id);//获取
            tx.commit();
            return nt;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }


    /*
    查询所有note
     */
    public List<Note> findAll() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Note> list = session.createQuery("From Note").list();
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
    分页的查询
     */
    public QueryResult<Note> findAll(int firstResult, int maxResult) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            //查询总记录数量
            Long count = (Long) session.createQuery(
                    "select count(*) from Note")
                    .uniqueResult();//执行查询
            //查询一段数据
            Query query = session.createQuery("From Note");
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResult);
            List<Note> list = query.list();
            tx.commit();
            return new QueryResult<Note>(list, count);
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
    public List<Note> getNotesByType(String Note_type) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Note> st = session.createQuery("From Note where note_type=?").setString(0, Note_type.trim()).list();
            tx.commit();
            return st;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }

    }

    public static void main(String[] args) {
        NoteUtil noteUtil=new NoteUtil();
        System.out.println(noteUtil.getNotesByType("电影分享").get(0));
    }
}
