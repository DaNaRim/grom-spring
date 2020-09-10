package com.lesson3.homework.service;

import com.lesson3.homework.DAO.FileStorageFacade;
import com.lesson3.homework.DAO.StorageDAO;
import com.lesson3.homework.exceptions.BadRequestException;
import com.lesson3.homework.exceptions.InternalServerException;
import com.lesson3.homework.model.File;
import com.lesson3.homework.model.Storage;
import org.springframework.beans.factory.annotation.Autowired;

public class StorageServiceImpl implements StorageService {

    private final FileStorageFacade fileStorageFacade;
    private final StorageDAO storageDAO;

    @Autowired
    public StorageServiceImpl(FileStorageFacade fileStorageFacade, StorageDAO storageDAO) {
        this.fileStorageFacade = fileStorageFacade;
        this.storageDAO = storageDAO;
    }


    public Storage save(Storage storage) throws InternalServerException {
        return storageDAO.save(storage);
    }

    public void delete(long id) throws BadRequestException, InternalServerException {
        try {
            Storage storage = findById(id);
            fileStorageFacade.delete(storage);
        } catch (BadRequestException e) {
            throw new BadRequestException("Cannot delete storage " + id + " : " + e.getMessage());
        }
    }

    public Storage update(Storage storage) throws InternalServerException, BadRequestException {
        try {
            findById(storage.getId());
            checkFormatSupported(storage);
            return storageDAO.update(storage);
        } catch (BadRequestException e) {
            throw new BadRequestException("Cannot update storage " + storage.getId() + " : " + e.getMessage());
        }
    }

    public Storage findById(long id) throws BadRequestException, InternalServerException {
        return storageDAO.findById(id);
    }

    private void checkFormatSupported(Storage storage) throws BadRequestException {
        try {
            for (File file : storage.getFiles()) {
                checkFileFormat(storage, file);
            }
        } catch (BadRequestException e) {
            throw new BadRequestException("Files from this storage have a format that is no longer available");
        }
    }

    private void checkFileFormat(Storage storage, File file) throws BadRequestException {
        for (String str : storage.getTRFormatsSupported()) {
            if (file.getFormat().equals(str)) return;
        }
        throw new BadRequestException("Unsuitable format");
    }
}