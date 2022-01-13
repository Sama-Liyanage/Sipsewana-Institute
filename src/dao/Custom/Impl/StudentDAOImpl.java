package dao.Custom.Impl;

import dao.CrudDAO;
import dao.Custom.StudentDAO;
import entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.FactoryConfiguration;

import java.io.Serializable;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    private SessionFactory sessionFactory;

    public StudentDAOImpl(){
        this.sessionFactory = FactoryConfiguration.getSessionFactory();
    }

    @Override
    public boolean save(Student entity) throws Exception {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Serializable save = session.save(entity);
        session.getTransaction().commit();
        return save !=null;
    }

    @Override
    public boolean update(Student entity) throws Exception {
        try{
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            return true;
        }catch (Exception e) {
        }
        return false;
    }

    @Override
    public boolean delete(Student entity) throws Exception {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction();
            return true;
        }catch (Exception e){
        }
        return false;
    }

    @Override
    public Student search(Student entity) throws Exception {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Student student = session.find(Student.class, entity.getS_id());
            return student;
        }catch (Exception e) {
        }
        return null;
    }



    @Override
    public List<Student> getAll() throws Exception {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            List<Student> studentList = session.createNativeQuery("SELECT * FROM student",Student.class).list();
            session.getTransaction().commit();
            return studentList;
        }catch (Exception e){
        }
        return null;
    }
}
