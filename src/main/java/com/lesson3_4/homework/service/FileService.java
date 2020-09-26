package com.lesson3_4.homework.service;

import com.lesson3_4.homework.exceptions.BadRequestException;
import com.lesson3_4.homework.exceptions.InternalServerException;
import com.lesson3_4.homework.model.File;
import com.lesson3_4.homework.model.Storage;
import org.springframework.stereotype.Service;

@Service
public interface FileService {
    File put(Storage storage, File file) throws BadRequestException, InternalServerException;

    void delete(Storage storage, File file) throws BadRequestException, InternalServerException;

    void transferAll(Storage storageFrom, Storage storageTo) throws BadRequestException, InternalServerException;

    void transferFile(Storage storageFrom, Storage storageTo, long id) throws BadRequestException,
            InternalServerException;

    File update(File file) throws BadRequestException, InternalServerException;

    File findById(long id) throws BadRequestException, InternalServerException;
}
