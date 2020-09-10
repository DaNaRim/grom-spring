package com.lesson3.homework.service;

import com.lesson3.homework.exceptions.BadRequestException;
import com.lesson3.homework.exceptions.InternalServerException;
import com.lesson3.homework.model.File;
import com.lesson3.homework.model.Storage;

public interface FileService {
    File put(Storage storage, File file) throws BadRequestException, InternalServerException;

    void delete(Storage storage, File file) throws BadRequestException, InternalServerException;

    void transferAll(Storage storageFrom, Storage storageTo) throws BadRequestException, InternalServerException;

    void transferFile(Storage storageFrom, Storage storageTo, long id) throws BadRequestException,
            InternalServerException;

    File update(File file) throws BadRequestException, InternalServerException;

    File findById(long id) throws BadRequestException, InternalServerException;
}
