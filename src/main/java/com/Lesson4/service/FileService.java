package com.Lesson4.service;

import com.Lesson4.exceptions.BadRequestException;
import com.Lesson4.exceptions.InternalServerException;
import com.Lesson4.model.File;
import com.Lesson4.model.Storage;
import org.springframework.stereotype.Service;

@Service
public interface FileService {

    File save(File file) throws InternalServerException;

    File findById(long id) throws BadRequestException, InternalServerException;

    File update(File file) throws BadRequestException, InternalServerException;

    void delete(File file) throws InternalServerException;

    File put(Storage storage, File file) throws BadRequestException, InternalServerException;

    void delete(Storage storage, File file) throws BadRequestException, InternalServerException;

    void transferAll(Storage storageFrom, Storage storageTo) throws BadRequestException, InternalServerException;

    void transferFile(Storage storageFrom, Storage storageTo, long id) throws BadRequestException,
            InternalServerException;

}
