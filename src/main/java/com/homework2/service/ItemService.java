package com.homework2.service;


import com.homework2.DAO.ItemDAO;
import com.homework2.exception.BadRequestException;
import com.homework2.exception.InternalServerException;
import com.homework2.model.Item;
import org.springframework.beans.factory.annotation.Autowired;

public class ItemService {

    @Autowired
    private ItemDAO itemDAO;

    public Item findById(long id) throws BadRequestException, InternalServerException {
        return itemDAO.findById(id);
    }

    public Item save(Item item) throws InternalServerException, BadRequestException {
        validateItem(item);

        return itemDAO.save(item);
    }

    public Item update(Item item) throws BadRequestException, InternalServerException {
        validateItem(item);

        return itemDAO.update(item);
    }

    public void delete(long id) throws BadRequestException, InternalServerException {
        Item item = itemDAO.findById(id);

        itemDAO.delete(item);
    }

    private void validateItem(Item item) throws BadRequestException {
        if (item == null) {
            throw new BadRequestException("validateItem failed: impossible to process null item");
        }
        if (item.getName() == null || item.getName().equals("") ||
                item.getDescription() == null || item.getDescription().equals("")) {
            throw new BadRequestException("validateItem failed: not all fields are filled");
        }
        if (item.getName().length() > 50 || item.getDescription().length() > 300) {
            throw new BadRequestException("validateItem failed: fields are too long");
        }
    }
}
