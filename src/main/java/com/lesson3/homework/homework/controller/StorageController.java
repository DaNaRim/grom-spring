package com.lesson3.homework.homework.controller;

import com.lesson3.homework.homework.Exceptions.BadRequestException;
import com.lesson3.homework.homework.Exceptions.InternalServerException;
import com.lesson3.homework.homework.model.Storage;
import com.lesson3.homework.homework.service.StorageService;

public class StorageController {

    public static Storage save(Storage storage) throws InternalServerException {
        return StorageService.save(storage);
    }

    public static void delete(long id) throws BadRequestException, InternalServerException {
        StorageService.delete(id);
    }

    public static Storage update(Storage storage) throws InternalServerException, BadRequestException {
        return StorageService.update(storage);
    }

    public static Storage findById(long id) throws BadRequestException, InternalServerException {
        return StorageService.findById(id);
    }
}