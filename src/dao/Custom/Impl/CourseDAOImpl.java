package dao.Custom.Impl;

import dao.Custom.CourseDAO;
import entity.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.FactoryConfiguration;

import java.io.Serializable;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {

    private SessionFactory sessionFactory;

    public CourseDAOImpl(){
        this.sessionFactory = FactoryConfiguration.getSessionFactory();
    }

    @Override
    public boolean save(Course entity) throws Exception {
        Session session = FactoryConfiguration.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Serializable save = session.save(entity);
        transaction.commit();
        return save !=null;
    }

    @Override
    public boolean update(Course entity) throws Exception {
        try{
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
            return true;
        }catch (Exception e){
        }
        return false;
    }

    @Override
    public boolean delete(Course entity) throws Exception {
        try{
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
            return true;
        }catch (Exception e){
        }
        return false;    }

    @Override
    public Course search(Course entity) throws Exception {
        try{
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Course course = session.find(Course.class, entity.getC_id());
            transaction.commit();
            return course;
        }catch (Exception e){
        }
        return null;
    }

    @Override
    public List<Course> getAll() throws Exception {
        try{
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            List<Course> courseList = session.createNativeQuery("SELECT * FROM course",Course.class).list();
            transaction.commit();
            return courseList;
        }catch (Exception e){
        }
        return null;
    }
}
