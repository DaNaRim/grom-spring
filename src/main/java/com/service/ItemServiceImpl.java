package com.service;

import com.DAO.ItemDAO;
import com.exception.ObjectNotFoundException;
import com.model.Item;
import org.springframework.beans.factory.annotation.Autowired;

public class ItemServiceImpl implements ItemService {

    private final ItemDAO itemDAO;

    @Autowired
    public ItemServiceImpl(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public Item save(Item item) {
        return itemDAO.save(item);
    }

    public Item findById(Long id) throws ObjectNotFoundException {
        return itemDAO.findById(id);
    }

    public Item update(Item item) throws ObjectNotFoundException {
        findById(item.getId());
        return itemDAO.update(item);
    }

    public void delete(Long id) throws ObjectNotFoundException {
        itemDAO.delete(findById(id));
    }
}
