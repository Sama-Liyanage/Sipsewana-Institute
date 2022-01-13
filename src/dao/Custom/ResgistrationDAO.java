package dao.Custom;

import dao.CrudDAO;
import entity.Registration;

import java.util.List;

public interface ResgistrationDAO extends CrudDAO<Registration,String> {
    List<Registration> getAllRegistration(String studentID)throws Exception;
    String searchId();

}
