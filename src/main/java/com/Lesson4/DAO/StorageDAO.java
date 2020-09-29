package com.Lesson4.DAO;

import com.Lesson4.exceptions.BadRequestException;
import com.Lesson4.exceptions.InternalServerException;
import com.Lesson4.model.Storage;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageDAO {

    Storage findById(long id) throws BadRequestException, InternalServerException;

}
