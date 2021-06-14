package com.service;

import com.DAO.FileDAO;
import com.exceptions.BadRequestException;
import com.exceptions.InternalServerException;
import com.model.File;
import com.model.Storage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class FileServiceImpl implements FileService {

    private final FileDAO fileDAO;

    @Autowired
    public FileServiceImpl(FileDAO fileDAO) {
        this.fileDAO = fileDAO;
    }

    public File save(File file) throws InternalServerException, BadRequestException {
        try {
            validateSave(file.getName(), file.getSize());

            return fileDAO.save(file);
        } catch (BadRequestException e) {
            throw new BadRequestException("Cannot save file " + file.getName() + " : " + e.getMessage());
        }
    }

    public File findById(long id) throws BadRequestException, InternalServerException {
        return fileDAO.findById(id);
    }

    public File update(File file) throws InternalServerException, BadRequestException {
        try {
            validateUpdate(file);

            return fileDAO.update(file);
        } catch (BadRequestException e) {
            throw new BadRequestException("Cannot update file " + file.getId() + " : " + e.getMessage());
        }
    }

    public void delete(File file) throws InternalServerException {
        fileDAO.delete(file);
    }

    public File put(Storage storage, File file) throws BadRequestException, InternalServerException {
        try {
            validatePut(storage, file);

            return fileDAO.put(storage, file);
        } catch (BadRequestException e) {
            throw new BadRequestException("Cannot put file in storage " + storage.getId() + " : " + e.getMessage());
        }
    }

    public void deleteFromStorage(long storageId, File file) throws BadRequestException, InternalServerException {
        try {
            validateDeleteFromStorage(storageId, file.getStorage());

            fileDAO.deleteFromStorage(file);
        } catch (BadRequestException e) {
            throw new BadRequestException("Cannot delete file " + file.getId() + " from storage " + storageId + " : " +
                    e.getMessage());
        }
    }

    public void transferAll(Storage storageFrom, Storage storageTo)
            throws BadRequestException, InternalServerException {
        try {
            validateTransferAll(storageFrom.getId(), storageFrom.getArrayFormatsSupported(), storageTo);

            fileDAO.transferAll(storageFrom.getId(), storageTo.getId());
        } catch (BadRequestException e) {
            throw new BadRequestException("Cannot transfer files from storage " + storageFrom.getId() + " to storage " +
                    storageTo.getId() + " : " + e.getMessage());
        }
    }

    public void transferFile(long storageFromId, Storage storageTo, File file)
            throws BadRequestException, InternalServerException {
        try {
            validateTransferFile(storageFromId, storageTo, file);

            fileDAO.transferFile(storageFromId, storageTo, file);
        } catch (BadRequestException e) {
            throw new BadRequestException("Cannot transfer file " + file.getId() + " from storage " +
                    storageFromId + " to storage " + storageTo.getId() + " : " + e.getMessage());
        }
    }

    private void validateSave(String fileName, long fileSize) throws BadRequestException, InternalServerException {
        if (fileName.length() > 10) throw new BadRequestException("File name length must be <= 10");
        if (fileSize <= 0) throw new BadRequestException("File size must be > 0");

        fileDAO.checkFileName(fileName);
    }

    private void validatePut(Storage storage, File file)
            throws BadRequestException, InternalServerException {

        if (file.getStorage() != null) {
            if (file.getStorage().getId() == storage.getId()) {
                throw new BadRequestException("The file is already in current storage");
            }
            throw new BadRequestException("The file is in another storage");
        }

        checkFormatAndSize(file.getFormat(), file.getSize(), storage);
    }

    private void validateDeleteFromStorage(long storageId, Storage fileStorage) throws BadRequestException {

        if (fileStorage == null) {
            throw new BadRequestException("The file is not in the storage");
        }
        if (storageId != fileStorage.getId()) {
            throw new BadRequestException("The file is not in the given storage");
        }
    }

    private void validateUpdate(File file) throws BadRequestException, InternalServerException {

        if (file.getName().length() > 10) throw new BadRequestException("File name length must be <= 10");
        if (file.getSize() <= 0) throw new BadRequestException("File size must be > 0");

        String oldName = findById(file.getId()).getName();

        if (!oldName.equals(file.getName())) {
            fileDAO.checkFileName(file.getName());
        }

        Storage fileStorage = file.getStorage();

        if (fileStorage != null) {
            checkFormatAndSize(file.getFormat(), file.getSize(), fileStorage);
        }
    }

    private void validateTransferAll(long storageFromId, String[] formatSupportedFrom, Storage storageTo)
            throws BadRequestException, InternalServerException {

        if (storageFromId == storageTo.getId()) {
            throw new BadRequestException("Transfer to the same storage");
        }

        String[] formatSupportedTo = storageTo.getArrayFormatsSupported();

        for (String formatFrom : formatSupportedFrom) {
            if (!Arrays.asList(formatSupportedTo).contains(formatFrom)) {
                throw new BadRequestException("Unsuitable format");
            }
        }

        fileDAO.checkStorageIsEmpty(storageFromId);

        long filesSizeFrom = fileDAO.getFilesSizeByStorageId(storageFromId);
        long freeSpaceTo = storageTo.getStorageSize() - fileDAO.getFilesSizeByStorageId(storageTo.getId());

        if (filesSizeFrom > freeSpaceTo) throw new BadRequestException("No storage space");
    }

    private void validateTransferFile(long storageFromId, Storage storageTo, File file)
            throws BadRequestException, InternalServerException {

        if (file.getStorage() == null) {
            throw new BadRequestException("The file is not in the storage");
        }
        if (storageFromId != file.getStorage().getId()) {
            throw new BadRequestException("The file is not in the given storage");
        }
        if (file.getStorage().getId() == storageTo.getId()) {
            throw new BadRequestException("The file is already in current storage");
        }

        checkFormatAndSize(file.getFormat(), file.getSize(), storageTo);
    }

    private void checkFormatAndSize(String fileFormat, long fileSize, Storage storage)
            throws BadRequestException, InternalServerException {

        String[] formatSupported = storage.getArrayFormatsSupported();

        if (!Arrays.asList(formatSupported).contains(fileFormat)) {
            throw new BadRequestException("Unsuitable format");
        }

        long freeSpace = storage.getStorageSize() - fileDAO.getFilesSizeByStorageId(storage.getId());

        if (freeSpace < fileSize) throw new BadRequestException("No storage space");
    }
}