package com.service;

import com.exception.ObjectNotFoundException;
import com.model.Item;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {

    Item save(Item item);

    Item findById(Long id) throws ObjectNotFoundException;

    Item update(Item item) throws ObjectNotFoundException;

    void delete(Long id) throws ObjectNotFoundException;
}
