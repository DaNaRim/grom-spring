package com.DAO;

import com.exceptions.BadRequestException;
import com.exceptions.InternalServerException;
import com.model.File;
import com.model.Storage;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDAO {

    File save(File file) throws InternalServerException;

    File findById(long id) throws BadRequestException, InternalServerException;

    File update(File file) throws InternalServerException;

    void delete(File file) throws InternalServerException;

    File put(Storage storage, File file) throws InternalServerException;

    void deleteFromStorage(File file) throws InternalServerException;

    void transferAll(long storageFromID, long storageToId) throws InternalServerException;

    void transferFile(long storageFromId, Storage storageTo, File file) throws InternalServerException;

    void checkFileName(String name) throws BadRequestException, InternalServerException;

    void checkStorageIsEmpty(long storageId) throws BadRequestException, InternalServerException;

    long getFilesSizeByStorageId(long storageId) throws InternalServerException;
}
