package com.lesson3.homework.homework.DAO;

public interface DAO<T>{
    T save(T entity) throws Exception;
    T findById(long id) throws Exception;
    T update (T entity) throws Exception;
    void delete(T object) throws Exception;
}
