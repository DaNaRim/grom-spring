package com.service;

import com.exception.BadRequestException;
import com.exception.InternalServerException;
import com.exception.ObjectNotFoundException;
import com.model.Item;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {

    Item save(Item item) throws BadRequestException, InternalServerException;

    Item findById(long id) throws ObjectNotFoundException, InternalServerException;

    Item update(Item item) throws ObjectNotFoundException, BadRequestException, InternalServerException;

    void delete(long id) throws ObjectNotFoundException, InternalServerException;

    void deleteByName(String name) throws BadRequestException, InternalServerException;
}
