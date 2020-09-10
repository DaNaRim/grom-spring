package com.lesson3.homework.DAO;

import com.lesson3.homework.exceptions.BadRequestException;
import com.lesson3.homework.exceptions.InternalServerException;
import com.lesson3.homework.model.Storage;

public interface StorageDAO {

    Storage save(Storage storage) throws InternalServerException;

    Storage update(Storage storage) throws InternalServerException;

    Storage findById(long id) throws BadRequestException, InternalServerException;

    void delete(Storage storage) throws InternalServerException;
}
