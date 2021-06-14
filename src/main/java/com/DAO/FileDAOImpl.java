package com.DAO;

import com.exceptions.BadRequestException;
import com.exceptions.InternalServerException;
import com.model.File;
import com.model.Storage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class FileDAOImpl extends DAO<File> implements FileDAO {

    private static final String TRANSFER_ALL_QUERY = "UPDATE FILES SET STORAGE_ID = :storageIdTo WHERE STORAGE_ID = :storageIdFrom";
    private static final String CHECK_STORAGE_FOR_EMPTY_QUERY = "SELECT COUNT(*) FROM FILES WHERE STORAGE_ID = :storageId";
    private static final String CHECK_FILE_NAME_QUERY = "SELECT * FROM FILES WHERE NAME = :name";
    private static final String GET_FILES_SIZE_BY_STORAGE_QUERY = "SELECT SUM(FILE_SIZE) FROM FILES WHERE STORAGE_ID = :storageId";

    public FileDAOImpl() {
        super(File.class);
    }

    public File save(File file) throws InternalServerException {
        try {
            return super.save(file);

        } catch (HibernateException e) {
            throw new InternalServerException("something went wrong while trying to save the file " + file.getName() +
                    " : " + e.getMessage());
        }
    }

    public File update(File file) throws InternalServerException {
        try {
            return super.update(file);

        } catch (HibernateException e) {
            throw new InternalServerException("something went wrong while trying to update the file " + file.getId() +
                    " : " + e.getMessage());
        }
    }

    public void delete(File file) throws InternalServerException {
        try {
            super.delete(file);

        } catch (HibernateException e) {
            throw new InternalServerException("something went wrong while trying to delete the file " + file.getId() +
                    " : " + e.getMessage());
        }
    }

    public File put(Storage storage, File file) throws InternalServerException {
        try {
            file.setStorage(storage);

            return super.update(file);

        } catch (HibernateException e) {
            throw new InternalServerException("something went wrong while trying to put the file " + file.getId() +
                    "in storage " + storage.getId() + " : " + e.getMessage());
        }
    }

    public void deleteFromStorage(File file) throws InternalServerException {
        try {
            file.setStorage(null);

            super.update(file);
        } catch (HibernateException e) {
            throw new InternalServerException("something went wrong while trying to delete the file " + file.getId() +
                    "from storage " + file.getStorage().getId() + " : " + e.getMessage());
        }
    }

    public void transferAll(long storageFromID, long storageToId) throws InternalServerException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            session.createNativeQuery(TRANSFER_ALL_QUERY)
                    .setParameter("storageIdTo", storageToId)
                    .setParameter("storageIdFrom", storageFromID)
                    .executeUpdate();

            transaction.commit();
        } catch (HibernateException e) {
            throw new InternalServerException("something went wrong while trying to transfer files from storage " +
                    storageFromID + " to storage " + storageToId + " : " + e.getMessage());
        }
    }

    public void transferFile(long storageFromId, Storage storageTo, File file) throws InternalServerException {
        try {
            file.setStorage(storageTo);
            super.update(file);

        } catch (HibernateException e) {
            throw new InternalServerException("something went wrong while trying to transfer file " + file.getId() +
                    "from storage " + storageFromId + " to storage " + storageTo.getId() + " : " + e.getMessage());
        }
    }

    public void checkFileName(String fileName) throws BadRequestException, InternalServerException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            List<File> files = session.createNativeQuery(CHECK_FILE_NAME_QUERY, File.class)
                    .setParameter("name", fileName)
                    .list();

            if (!files.isEmpty()) {
                throw new BadRequestException("The file with name " + fileName + "is already exist");
            }
        } catch (HibernateException e) {
            throw new InternalServerException("something went wrong while trying to check file name" + fileName);
        }
    }

    public void checkStorageIsEmpty(long storageId) throws BadRequestException, InternalServerException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            BigDecimal bd = (BigDecimal) session.createNativeQuery(CHECK_STORAGE_FOR_EMPTY_QUERY)
                    .setParameter("storageId", storageId)
                    .getSingleResult();

            if (bd.longValue() == 0) {
                throw new BadRequestException("Storage is Empty");
            }

        } catch (HibernateException e) {
            throw new InternalServerException("something went wrong while trying to check storage for empty" +
                    storageId + " : " + e.getMessage());
        }
    }

    public long getFilesSizeByStorageId(long StorageId) throws InternalServerException {
        try (Session session = HibernateUtil.createSessionFactory().openSession()) {

            BigDecimal bd = (BigDecimal) session.createNativeQuery(GET_FILES_SIZE_BY_STORAGE_QUERY)
                    .setParameter("storageId", StorageId)
                    .getSingleResult();

            return bd == null ? 0 : bd.longValue();

        } catch (HibernateException e) {
            throw new InternalServerException("something went wrong while trying to get size of all files from storage "
                    + StorageId + " : " + e.getMessage());
        }
    }
}