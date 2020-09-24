package com.lesson3.homework.service;

import com.lesson3.homework.DAO.FileDAO;
import com.lesson3.homework.exceptions.BadRequestException;
import com.lesson3.homework.exceptions.InternalServerException;
import com.lesson3.homework.model.File;
import com.lesson3.homework.model.Storage;
import org.springframework.beans.factory.annotation.Autowired;

public class FileServiceImpl implements FileService {

    private final FileDAO fileDAO;

    @Autowired
    public FileServiceImpl(FileDAO fileDAO) {
        this.fileDAO = fileDAO;
    }


    public File put(Storage storage, File file) throws BadRequestException, InternalServerException {
        try {
            checkFileFormat(storage, file);
            checkSize(storage, file);
            fileDAO.checkFileName(storage, file);

            return fileDAO.save(storage, file);
        } catch (BadRequestException e) {
            throw new BadRequestException("Cannot put file in storage " + storage.getId() + " : " + e.getMessage());
        }
    }

    public void delete(Storage storage, File file) throws BadRequestException, InternalServerException {
        try {
            fileDAO.findById(file.getId());
            checkStorage(storage, file);

            fileDAO.delete(storage, file);
        } catch (BadRequestException e) {
            throw new BadRequestException("Cannot delete file " + file.getId() + " from storage " + storage.getId() +
                    " : " + e.getMessage());
        }
    }

    public void transferAll(Storage storageFrom, Storage storageTo)
            throws BadRequestException, InternalServerException {
        try {
            storageFrom.setFiles(fileDAO.getFilesByStorage(storageFrom));

            checkStorages(storageFrom, storageTo);
            checkFilesFormat(storageFrom, storageTo);
            checkSize(storageFrom, storageTo);
            checkFiles(storageFrom, storageTo);

            fileDAO.transferAll(storageFrom, storageTo);
        } catch (BadRequestException e) {
            throw new BadRequestException("Cannot transfer files from storage " + storageFrom.getId() + " to storage " +
                    storageTo.getId() + " : " + e.getMessage());
        }
    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id)
            throws BadRequestException, InternalServerException {
        try {
            storageFrom.setFiles(fileDAO.getFilesByStorage(storageFrom));

            File file = fileDAO.findById(id);
            checkStorages(storageFrom, storageTo);
            checkFileFormat(storageTo, file);
            checkSize(storageTo, file);
            fileDAO.checkFileName(storageTo, file);

            fileDAO.transferFile(storageFrom, storageTo, id);
        } catch (BadRequestException e) {
            throw new BadRequestException("Cannot transfer file " + id + " from storage " + storageFrom.getId() +
                    " to storage " + storageTo.getId() + " : " + e.getMessage());
        }
    }

    public File update(File file) throws InternalServerException, BadRequestException {
        try {
            findById(file.getId());

            Storage storage = file.getStorage();
            checkFileFormat(storage, file);
            checkSize(storage, file);
            fileDAO.checkFileName(storage, file);

            return fileDAO.update(file);
        } catch (BadRequestException e) {
            throw new BadRequestException("Cannot update file " + file.getId() + " : " + e.getMessage());
        }
    }

    public File findById(long id) throws BadRequestException, InternalServerException {
        return fileDAO.findById(id);
    }

    private void checkFileFormat(Storage storage, File file) throws BadRequestException {
        for (String str : storage.getArrayFormatsSupported()) {
            if (file.getFormat().equals(str)) return;
        }
        throw new BadRequestException("Unsuitable format");
    }

    private void checkFilesFormat(Storage storageFrom, Storage storageTo) throws BadRequestException {
        for (File file : storageFrom.getFiles()) {
            checkFileFormat(storageTo, file);
        }
    }

    private void checkSize(Storage storage, File file) throws BadRequestException {

        if (getFreeSpace(storage) < file.getSize()) throw new BadRequestException("No storage space");
    }

    private void checkSize(Storage storageFrom, Storage storageTo) throws BadRequestException {
        long filesSize = storageFrom.getStorageSize() - getFreeSpace(storageFrom);
        if (filesSize > getFreeSpace(storageTo)) {
            throw new BadRequestException("No storage space");
        }
    }

    private void checkFiles(Storage storageFrom, Storage storageTo) throws BadRequestException,
            InternalServerException {
        for (File file : storageFrom.getFiles()) {
            fileDAO.checkFileName(storageTo, file);
        }
    }

    private void checkStorages(Storage storage1, Storage storage2) throws BadRequestException {
        if (storage1.getFiles().isEmpty()) {
            throw new BadRequestException("Nothing to transfer");
        }
        if (storage1.getId() == storage2.getId()) {
            throw new BadRequestException("Transfer to the same storage");
        }
    }

    private void checkStorage(Storage storage, File file) throws BadRequestException {
        if (file.getStorage().getId() != storage.getId()) {
            throw new BadRequestException("The file is not in the given storage");
        }
    }

    private long getFreeSpace(Storage storage) {
        long freeSpace = storage.getStorageSize();

        for (File file : storage.getFiles()) {
            freeSpace -= file.getSize();
        }
        return freeSpace;
    }
}