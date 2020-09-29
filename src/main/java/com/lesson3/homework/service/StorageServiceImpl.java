package com.lesson3.homework.service;

import com.lesson3.homework.DAO.StorageDAO;
import com.lesson3.homework.exceptions.BadRequestException;
import com.lesson3.homework.exceptions.InternalServerException;
import com.lesson3.homework.model.Storage;
import org.springframework.beans.factory.annotation.Autowired;

public class StorageServiceImpl implements StorageService {

    private final StorageDAO storageDAO;

    @Autowired
    public StorageServiceImpl(StorageDAO storageDAO) {
        this.storageDAO = storageDAO;
    }

    public Storage findById(long id) throws BadRequestException, InternalServerException {
        return storageDAO.findById(id);
    }
}