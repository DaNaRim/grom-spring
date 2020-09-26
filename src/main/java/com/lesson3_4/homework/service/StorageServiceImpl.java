package com.lesson3_4.homework.service;

import com.lesson3_4.homework.DAO.StorageDAO;
import com.lesson3_4.homework.exceptions.BadRequestException;
import com.lesson3_4.homework.exceptions.InternalServerException;
import com.lesson3_4.homework.model.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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