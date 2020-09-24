package com.lesson3.homework.DAO;

import com.lesson3.homework.exceptions.BadRequestException;
import com.lesson3.homework.exceptions.InternalServerException;
import com.lesson3.homework.model.File;
import com.lesson3.homework.model.Storage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import java.util.List;

public class FileDAOImpl extends DAO<File> implements FileDAO {

    private static final String getFilesByStorageQuery = "SELECT * FROM FILES WHERE STORAGE_ID = :storageId";
    private static final String checkFileNameQuery = "SELECT * FROM FILES WHERE NAME = :name AND STORAGE_ID = :storageId";

    public FileDAOImpl() {
        super(File.class);
    }

    public File save(Storage storage, File file) throws InternalServerException {
        try {
            file.setStorage(storage);

            return super.save(file);

        } catch (HibernateException e) {
            throw new InternalServerException("put failed: something went wrong while trying to save the file " +
                    file.getName() + "in storage " + storage.getId() + " : " + e.getMessage());
        }
    }

    public void delete(Storage storage, File file) throws InternalServerException {
        try {
            super.delete(file);

        } catch (HibernateException e) {
            throw new InternalServerException("delete failed: something went wrong while trying to delete the file " +
                    file.getId() + "in storage " + storage.getId() + " : " + e.getMessage());
        }
    }

    public File update(File file) throws InternalServerException {
        try {
            return super.update(file);

        } catch (HibernateException e) {
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
                super.update(file);
            }

            transaction.commit();
        } catch (HibernateException e) {
            throw new InternalServerException("transferAll failed: something went wrong while trying to transfer " +
                    "files from storage " + storageFrom.getId() + " to storage " + storageTo.getId() + " : "
                    + e.getMessage());
        }
    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id) throws InternalServerException {
        try {
            File file = findById(id);

            file.setStorage(storageTo);
            super.update(file);

        } catch (HibernateException | BadRequestException e) {
            throw new InternalServerException("transferFile failed: something went wrong while trying to transfer " +
                    "file " + id + "from storage " + storageFrom.getId() + " to storage " + storageTo.getId() + " : " +
                    e.getMessage());
        }
    }

    public void checkFileName(Storage storage, File file) throws BadRequestException, InternalServerException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            session.createNativeQuery(checkFileNameQuery, File.class)
                    .setParameter("name", file.getName())
                    .setParameter("storageId", storage.getId())
                    .getSingleResult();

            throw new BadRequestException("checkFileName failed: the file " + file.getId() + " with name " +
                    file.getName() + "is already exist in storage " + storage.getId());

        } catch (NoResultException e) {
            System.out.println("checkFileName: Object not found in database. Will be saved");
        } catch (HibernateException e) {
            throw new InternalServerException("checkFileName failed: something went wrong while trying to check " +
                    "file " + file.getId() + " in storage " + storage.getId());
        }
    }

    public List<File> getFilesByStorage(Storage storage) throws InternalServerException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            return session.createNativeQuery(getFilesByStorageQuery, File.class)
                    .setParameter("storageId", storage.getId())
                    .list();

        } catch (HibernateException e) {
            throw new InternalServerException("An error occurred while trying to get all files from storage " +
                    storage.getId() + " : " + e.getMessage());
        }
    }
}