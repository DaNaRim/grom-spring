package com.DAO;

import com.exception.InternalServerException;
import com.exception.ObjectNotFoundException;
import org.hibernate.HibernateException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class DAO<T> {

    private final Class<T> tClass;

    public DAO(Class<T> tClass) {
        this.tClass = tClass;
    }

    @PersistenceContext
    protected EntityManager entityManager;

    public T save(T entity) throws InternalServerException {
        try {
            entityManager.persist(entity);

            return entity;
        } catch (HibernateException e) {
            throw new InternalServerException("Something went wrong while trying to save entity " + entity.toString()
                    + " : " + e.getMessage());
        }
    }

    public T findById(long id) throws ObjectNotFoundException, InternalServerException {
        try {
            T entity = entityManager.find(this.tClass, id);

            if (entity == null) {
                throw new ObjectNotFoundException("Missed entity with id: " + id);
            }

            return entity;
        } catch (HibernateException e) {
            throw new InternalServerException("Something went wrong while trying to find entity with id: " + id + " : "
                    + e.getMessage());
        }
    }

    public T update(T entity) throws InternalServerException {
        try {
            return entityManager.merge(entity);

        } catch (HibernateException e) {
            throw new InternalServerException("Something went wrong while trying to update entity: " + e.getMessage());
        }
    }

    public void delete(T entity) throws InternalServerException {
        try {
            entityManager.remove(entityManager.merge(entity));

        } catch (HibernateException e) {
            throw new InternalServerException("Something went wrong while trying to delete entity: " + e.getMessage());
        }
    }
}
