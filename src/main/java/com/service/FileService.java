package com.service;

import com.exceptions.BadRequestException;
import com.exceptions.InternalServerException;
import com.model.File;
import com.model.Storage;

public interface FileService {

    File save(File file) throws InternalServerException, BadRequestException;

    File findById(long id) throws BadRequestException, InternalServerException;

    File update(File file) throws BadRequestException, InternalServerException;

    void delete(File file) throws InternalServerException;

    File put(Storage storage, File file) throws BadRequestException, InternalServerException;

    void deleteFromStorage(long storageId, File file) throws BadRequestException, InternalServerException;

    void transferAll(Storage storageFrom, Storage storageTo) throws BadRequestException, InternalServerException;

    void transferFile(long storageFromId, Storage storageTo, File file) throws BadRequestException,
            InternalServerException;

}
