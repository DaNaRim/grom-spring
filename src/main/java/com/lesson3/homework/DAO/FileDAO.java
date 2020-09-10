package com.lesson3.homework.DAO;

import com.lesson3.homework.exceptions.BadRequestException;
import com.lesson3.homework.exceptions.InternalServerException;
import com.lesson3.homework.model.File;
import com.lesson3.homework.model.Storage;

public interface FileDAO {

    File save(File file) throws InternalServerException;

    File findById(long id) throws BadRequestException, InternalServerException;

    File update(File file) throws InternalServerException;

    void delete(File file) throws InternalServerException;

    void checkFileName(Storage storage, File file) throws BadRequestException, InternalServerException;


}
