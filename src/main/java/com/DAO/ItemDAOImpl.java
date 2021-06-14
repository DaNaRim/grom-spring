package com.DAO;

import com.exception.ObjectNotFoundException;
import com.model.Item;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class ItemDAOImpl implements ItemDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public Item save(Item item) {
        entityManager.persist(item);
        return item;
    }

    public Item findById(Long id) throws ObjectNotFoundException {
        Item item = entityManager.find(Item.class, id);

        if (item == null) {
            throw new ObjectNotFoundException("Missed item with id: " + id);
        }
        return item;
    }

    public Item update(Item item) {
        return entityManager.merge(item);
    }

    public void delete(Item item) {
        entityManager.remove(entityManager.merge(item));
    }
}
