package com.lesson3.homework.controller;


import com.lesson3.homework.exceptions.BadRequestException;
import com.lesson3.homework.exceptions.InternalServerException;
import com.lesson3.homework.model.File;
import com.lesson3.homework.model.Storage;
import com.lesson3.homework.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;

public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    public File put(Storage storage, File file) throws BadRequestException, InternalServerException {
        return fileService.put(storage, file);
    }

    public void delete(Storage storage, File file) throws BadRequestException, InternalServerException {
        fileService.delete(storage, file);
    }

    public void transferAll(Storage storageFrom, Storage storageTo)
            throws BadRequestException, InternalServerException {
        fileService.transferAll(storageFrom, storageTo);
    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id)
            throws BadRequestException, InternalServerException {
        fileService.transferFile(storageFrom, storageTo, id);
    }

    public File update(File file) throws InternalServerException, BadRequestException {
        return fileService.update(file);
    }

    public File findById(long id) throws BadRequestException, InternalServerException {
        return fileService.findById(id);
    }
}