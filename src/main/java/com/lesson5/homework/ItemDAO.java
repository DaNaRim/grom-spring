package com.lesson5.homework;

import com.lesson5.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class ItemDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public Item save(Item item) {
        entityManager.persist(item);
        return item;
    }

    public Item findById(Long id) throws BadRequestException {
        Item item = entityManager.find(Item.class, id);

        if (item == null) {
            throw new BadRequestException("Missed item with id: " + id);
        }
        return item;
    }

    public Item update(Item item) {
        return entityManager.merge(item);
    }

    public void delete(Long id) throws BadRequestException {
        entityManager.remove(findById(id));
    }
}
