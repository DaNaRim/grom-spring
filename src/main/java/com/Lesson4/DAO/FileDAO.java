package com.Lesson4.DAO;

import com.Lesson4.exceptions.BadRequestException;
import com.Lesson4.exceptions.InternalServerException;
import com.Lesson4.model.File;
import com.Lesson4.model.Storage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDAO {

    File save(File file) throws InternalServerException;

    File findById(long id) throws BadRequestException, InternalServerException;

    File update(File file) throws InternalServerException;

    void delete(File file) throws InternalServerException;

    File put(Storage storage, File file) throws InternalServerException;

    void delete(Storage storage, File file) throws InternalServerException;

    void transferAll(Storage storageFrom, Storage storageTo) throws InternalServerException;

    void transferFile(Storage storageFrom, Storage storageTo, long id) throws InternalServerException;

    void checkFileName(Storage storage, File file) throws BadRequestException, InternalServerException;

    List<File> getFilesByStorage(Storage storage) throws InternalServerException;
}
