package service;

import model.enums.DELETE_RESPONSE;
import repository.Repository;

import javax.persistence.RollbackException;
import java.util.ArrayList;

public class Service<T> {

    public Repository<T> repository;

    public Service(Repository<T> repository) {
        this.repository = repository;
    }

    public T findById(Integer id) {
        return repository.findById(id);
    }

    public T findByField(String field, String value) {
        return repository.findByField(field, value);
    }

    public ArrayList<T> getAll() {
        return repository.getAll();
    }

    public DELETE_RESPONSE delete(T t) {
        if (t != null) {
            try {
                repository.delete(t);
                return DELETE_RESPONSE.SUCCESS;
            } catch (RollbackException rollbackException) {
                return DELETE_RESPONSE.NOT_FOUND;
            }
        }
        //should never be caught
        else return DELETE_RESPONSE.INVALID_INPUT;
    }
}
