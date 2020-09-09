package com.lesson3.homework.homework.controller;


import com.lesson3.homework.homework.Exceptions.BadRequestException;
import com.lesson3.homework.homework.Exceptions.InternalServerException;
import com.lesson3.homework.homework.model.File;
import com.lesson3.homework.homework.model.Storage;
import com.lesson3.homework.homework.service.FileService;

public class FileController {

    public static File put(Storage storage, File file) throws BadRequestException, InternalServerException {
        return FileService.put(storage, file);
    }

    public static void delete(Storage storage, File file) throws BadRequestException, InternalServerException {
        FileService.delete(storage, file);
    }

    public static void transferAll(Storage storageFrom, Storage storageTo)
            throws BadRequestException, InternalServerException {
        FileService.transferAll(storageFrom, storageTo);
    }

    public static void transferFile(Storage storageFrom, Storage storageTo, long id)
            throws BadRequestException, InternalServerException {
        FileService.transferFile(storageFrom, storageTo, id);
    }

    public static File update(File file) throws InternalServerException, BadRequestException {
        return FileService.update(file);
    }

    public static File findById(long id) throws BadRequestException, InternalServerException {
        return FileService.findById(id);
    }
}