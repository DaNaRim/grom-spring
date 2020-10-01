package com.lesson3_4.homework.service;

import com.lesson3_4.homework.exceptions.BadRequestException;
import com.lesson3_4.homework.exceptions.InternalServerException;
import com.lesson3_4.homework.model.Storage;
import org.springframework.stereotype.Service;

@Service
public interface StorageService {

    Storage findById(long id) throws BadRequestException, InternalServerException;
}
