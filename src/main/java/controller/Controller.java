package controller;

import model.enums.DELETE_RESPONSE;
import service.Service;

import java.util.ArrayList;

public class Controller<T> {

    public final Service<T> service;

    public Controller(Service<T> service) {
        this.service = service;
    }

    public T findById(Integer id) {
        return service.findById(id);
    }

    public T findByField(String field, String value) {
        return service.findByField(field, value);
    }

    public ArrayList<T> getAll() {
        return service.getAll();
    }

    public DELETE_RESPONSE delete(T t) {
        return service.delete(t);
    }
}
