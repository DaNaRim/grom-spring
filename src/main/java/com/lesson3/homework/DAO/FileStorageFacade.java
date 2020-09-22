package com.lesson3.homework.DAO;

import com.lesson3.homework.exceptions.InternalServerException;
import com.lesson3.homework.model.File;
import com.lesson3.homework.model.Storage;

public interface FileStorageFacade {

    File put(Storage storage, File file) throws InternalServerException;

    File update(File file) throws InternalServerException;

    void delete(Storage storage, File file) throws InternalServerException;

    void transferAll(Storage storageFrom, Storage storageTo) throws InternalServerException;

    void transferFile(Storage storageFrom, Storage storageTo, long id) throws InternalServerException;
}
