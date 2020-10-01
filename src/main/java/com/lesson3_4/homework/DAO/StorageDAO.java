package com.lesson3_4.homework.DAO;

import com.lesson3_4.homework.exceptions.BadRequestException;
import com.lesson3_4.homework.exceptions.InternalServerException;
import com.lesson3_4.homework.model.Storage;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageDAO {

    Storage findById(long id) throws BadRequestException, InternalServerException;

}
