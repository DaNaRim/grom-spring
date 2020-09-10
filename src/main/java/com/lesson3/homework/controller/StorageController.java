package com.lesson3.homework.controller;

import com.lesson3.homework.exceptions.BadRequestException;
import com.lesson3.homework.exceptions.InternalServerException;
import com.lesson3.homework.model.Storage;
import com.lesson3.homework.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;

public class StorageController {

    private final StorageService storageService;

    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    public Storage save(Storage storage) throws InternalServerException {
        return storageService.save(storage);
    }

    public void delete(long id) throws BadRequestException, InternalServerException {
        storageService.delete(id);
    }

    public Storage update(Storage storage) throws InternalServerException, BadRequestException {
        return storageService.update(storage);
    }

    public Storage findById(long id) throws BadRequestException, InternalServerException {
        return storageService.findById(id);
    }
}