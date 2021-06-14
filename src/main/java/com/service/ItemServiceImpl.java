package com.service;

import com.DAO.ItemDAO;
import com.exception.BadRequestException;
import com.exception.InternalServerException;
import com.exception.ObjectNotFoundException;
import com.model.Item;
import org.springframework.beans.factory.annotation.Autowired;

public class ItemServiceImpl implements ItemService {

    private final ItemDAO itemDAO;

    @Autowired
    public ItemServiceImpl(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public Item save(Item item) throws BadRequestException, InternalServerException {
        try {
            validateItem(item);
            itemDAO.checkItemName(item.getName());

            return itemDAO.save(item);
        } catch (BadRequestException e) {
            throw new BadRequestException("Can`t save item " + item.getName() + " : " + e.getMessage());
        }
    }

    public Item findById(long id) throws ObjectNotFoundException, InternalServerException {
        return itemDAO.findById(id);
    }

    public Item update(Item item) throws ObjectNotFoundException, BadRequestException, InternalServerException {
        try {
            validateUpdate(item);

            return itemDAO.update(item);
        } catch (BadRequestException e) {
            throw new BadRequestException("Can`t update item " + item.getId() + " : " + e.getMessage());
        }
    }

    public void delete(long id) throws ObjectNotFoundException, InternalServerException {
        itemDAO.delete(findById(id));
    }

    public void deleteByName(String name) throws BadRequestException, InternalServerException {
        try {
            itemDAO.checkItemContainsName(name);

            itemDAO.deleteByName(name);
        } catch (BadRequestException e) {
            throw new BadRequestException("Can`t delete items with name " + name + " : " + e.getMessage());
        }
    }

    private void validateItem(Item item) throws BadRequestException {

        if (item.getName().length() > 50) {
            throw new BadRequestException("name length must be <= 50");
        }
        if (item.getDescription().length() > 200) {
            throw new BadRequestException("description length must be <= 200");
        }
    }

    private void validateUpdate(Item item)
            throws ObjectNotFoundException, BadRequestException, InternalServerException {

        Item oldItem = findById(item.getId());
        validateItem(item);

        if (!item.getName().equals(oldItem.getName())) {
            itemDAO.checkItemName(item.getName());
        }

        item.setDateCreated(oldItem.getDateCreated());
    }
}
