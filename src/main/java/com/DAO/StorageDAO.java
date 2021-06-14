package com.DAO;

import com.exceptions.BadRequestException;
import com.exceptions.InternalServerException;
import com.model.Storage;

public interface StorageDAO {

    Storage findById(long id) throws BadRequestException, InternalServerException;

}
