package com.DAO;

import com.exception.BadRequestException;
import com.exception.InternalServerException;
import com.exception.ObjectNotFoundException;
import com.model.Item;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDAO {

    Item save(Item item) throws InternalServerException;

    Item findById(long id) throws ObjectNotFoundException, InternalServerException;

    Item update(Item item) throws InternalServerException;

    void delete(Item item) throws InternalServerException;

    void deleteByName(String name) throws InternalServerException;

    void checkItemName(String name) throws BadRequestException, InternalServerException;

    void checkItemContainsName(String name) throws BadRequestException, InternalServerException;
}
