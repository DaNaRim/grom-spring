package com.lesson3.homework.service;

import com.lesson3.homework.exceptions.BadRequestException;
import com.lesson3.homework.exceptions.InternalServerException;
import com.lesson3.homework.model.Storage;

public interface StorageService {
    Storage save(Storage storage) throws InternalServerException;

    void delete(long id) throws BadRequestException, InternalServerException;

    Storage update(Storage storage) throws BadRequestException, InternalServerException;

    Storage findById(long id) throws BadRequestException, InternalServerException;
}
