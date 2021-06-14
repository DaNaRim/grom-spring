package com.service;

import com.exceptions.BadRequestException;
import com.exceptions.InternalServerException;
import com.model.Storage;
import org.springframework.stereotype.Service;

@Service
public interface StorageService {

    Storage findById(long id) throws BadRequestException, InternalServerException;
}
