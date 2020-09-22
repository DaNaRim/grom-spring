package com.lesson3.homework.DAO;

import com.lesson3.homework.exceptions.BadRequestException;
import com.lesson3.homework.exceptions.InternalServerException;
import com.lesson3.homework.model.File;
import com.lesson3.homework.model.Storage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;


public class FileStorageFacadeImpl implements FileStorageFacade {

    private final FileDAO fileDAO;
    private final StorageDAO storageDAO;

    @Autowired
    public FileStorageFacadeImpl(FileDAO fileDAO, StorageDAO storageDAO) {
        this.fileDAO = fileDAO;
        this.storageDAO = storageDAO;
    }

    public File put(Storage storage, File file) throws InternalServerException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            file.setStorage(storage);
            fileDAO.save(file);

            storage.setFreeSpace(storage.getFreeSpace() - file.getSize());
            storageDAO.update(storage);

            transaction.commit();
            return file;
        } catch (HibernateException e) {
            throw new InternalServerException("put failed: something went wrong while trying to save the file " +
                    file.getName() + "in storage " + storage.getId() + " : " + e.getMessage());
        }
    }

    public void delete(Storage storage, File file) throws InternalServerException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            file.setStorage(storage);
            fileDAO.delete(file);

            storage.setFreeSpace(storage.getFreeSpace() + file.getSize());
            storageDAO.update(storage);

            transaction.commit();
        } catch (HibernateException e) {
            throw new InternalServerException("delete failed: something went wrong while trying to delete the file " +
                    file.getId() + "in storage " + storage.getId() + " : " + e.getMessage());
        }
    }

    public File update(File file) throws InternalServerException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            fileDAO.update(file);

            File currentFileVersion = fileDAO.findById(file.getId());
            Storage currentStorage = currentFileVersion.getStorage();
            Storage newStorage = file.getStorage();

            if (currentStorage.getId() == newStorage.getId()) {

                currentStorage.setFreeSpace(currentStorage.getFreeSpace() + file.getSize() -
                        currentFileVersion.getSize());
                storageDAO.update(currentStorage);
            } else {
                currentStorage.setFreeSpace(currentStorage.getFreeSpace() + currentFileVersion.getSize());
                newStorage.setFreeSpace(currentStorage.getFreeSpace() - file.getSize());

                storageDAO.update(currentStorage);
                storageDAO.update(newStorage);
            }

            transaction.commit();
            return file;
        } catch (HibernateException | BadRequestException e) {
            throw new InternalServerException("update failed: something went wrong while trying to update the file " +
                    file.getId() + " : " + e.getMessage());
        }
    }

    public void transferAll(Storage storageFrom, Storage storageTo) throws InternalServerException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            for (File file : storageFrom.getFiles()) {
                file.setStorage(storageTo);
                fileDAO.update(file);
            }

            storageTo.setFreeSpace(storageTo.getFreeSpace() - storageFrom.getStorageSize() +
                    storageFrom.getFreeSpace());
            storageDAO.update(storageTo);

            storageFrom.setFreeSpace(storageFrom.getStorageSize());
            storageDAO.update(storageFrom);

            transaction.commit();
        } catch (HibernateException e) {
            throw new InternalServerException("transferAll failed: something went wrong while trying to transfer " +
                    "files from storage " + storageFrom.getId() + " to storage " + storageTo.getId() + " : "
                    + e.getMessage());
        }
    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id) throws InternalServerException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            File file = fileDAO.findById(id);

            file.setStorage(storageTo);
            fileDAO.update(file);

            storageFrom.setFreeSpace(storageFrom.getFreeSpace() + file.getSize());
            storageDAO.update(storageFrom);

            storageTo.setFreeSpace(storageTo.getFreeSpace() - file.getSize());
            storageDAO.update(storageTo);

            transaction.commit();
        } catch (HibernateException | BadRequestException e) {
            throw new InternalServerException("transferFile failed: something went wrong while trying to transfer " +
                    "file " + id + "from storage " + storageFrom.getId() + " to storage " + storageTo.getId() + " : " +
                    e.getMessage());
        }
    }
}
