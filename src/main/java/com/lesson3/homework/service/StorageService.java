package com.lesson3.homework.service;

import com.lesson3.homework.exceptions.BadRequestException;
import com.lesson3.homework.exceptions.InternalServerException;
import com.lesson3.homework.model.Storage;

public interface StorageService {

    Storage findById(long id) throws BadRequestException, InternalServerException;
}
