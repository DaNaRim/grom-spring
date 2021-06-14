package com.DAO;

import com.exceptions.BadRequestException;
import com.exceptions.InternalServerException;
import com.model.Storage;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageDAO {

    Storage findById(long id) throws BadRequestException, InternalServerException;

}
