package com.service;

import com.DAO.StorageDAO;
import com.exceptions.BadRequestException;
import com.exceptions.InternalServerException;
import com.model.Storage;
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