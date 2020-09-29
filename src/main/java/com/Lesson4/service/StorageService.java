package com.Lesson4.service;

import com.Lesson4.exceptions.BadRequestException;
import com.Lesson4.exceptions.InternalServerException;
import com.Lesson4.model.Storage;
import org.springframework.stereotype.Service;

@Service
public interface StorageService {

    Storage findById(long id) throws BadRequestException, InternalServerException;
}
