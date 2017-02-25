package cn.tutetube.util;

import cn.tutetube.bean.Note;
import cn.tutetube.bean.NoteForImages;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by cxd on 2017/1/31.
 */
public class NoteForImagesUtil {
    private static SessionFactory sessionFactory;

    static {
        Configuration cfg = new Configuration();
        cfg.configure("cn/tutetube/util/hibernate.cfg.xml");
        sessionFactory = cfg.buildSessionFactory();
    }

    /*
    *保存noteImages
     */
    public void save(NoteForImages nfi) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(nfi);//保存
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
    删除指定images_id的images
     */
    public void delete(int images_id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            NoteForImages nfi = (NoteForImages) session.get(NoteForImages.class, images_id);
            session.delete(nfi);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
    更新NoteForImages
     */
    public void update(NoteForImages nfi) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(nfi);//保存
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
    通过images_id获取一个note_images
     */
    public NoteForImages getById(int images_id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            NoteForImages nfi = (NoteForImages) session.get(NoteForImages.class, images_id);//获取
            tx.commit();
            return nfi;
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
    public List<NoteForImages> findAll() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<NoteForImages> list = session.createQuery("From NoteForImages").list();
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
    public QueryResult<NoteForImages> findAll(int firstResult, int maxResult) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            //查询总记录数量
            Long count = (Long) session.createQuery(
                    "select count(*) from NoteForImages")
                    .uniqueResult();//执行查询
            //查询一段数据
            Query query = session.createQuery("From NoteForImages");
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResult);
            List<NoteForImages> list = query.list();
            tx.commit();
            return new QueryResult<NoteForImages>(list, count);
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
    public List<NoteForImages> getNoteForImagesById(String note_id,String  id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<NoteForImages> noteForImages= session.createQuery("From NoteForImages where note_id='"+note_id.trim()+"' and id="+id+"").list();
            tx.commit();
            return noteForImages;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }

    }

}
