package com.Lesson4.service;

import com.Lesson4.DAO.StorageDAO;
import com.Lesson4.exceptions.BadRequestException;
import com.Lesson4.exceptions.InternalServerException;
import com.Lesson4.model.Storage;
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