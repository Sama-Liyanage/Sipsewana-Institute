package dao;

import entity.SuperEntity;

import java.util.List;

public interface CrudDAO<S extends SuperEntity,ID> extends SuperDAO {
    boolean save(S entity) throws Exception;
    boolean update(S entity)throws Exception;
    boolean delete(S entity)throws Exception;
    S search(S entity)throws Exception;
    List<S> getAll()throws Exception;
}
