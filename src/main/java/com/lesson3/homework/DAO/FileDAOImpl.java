package com.lesson3.homework.DAO;

import com.lesson3.homework.exceptions.BadRequestException;
import com.lesson3.homework.exceptions.InternalServerException;
import com.lesson3.homework.model.File;
import com.lesson3.homework.model.Storage;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.List;

public class FileDAOImpl extends DAO<File> implements FileDAO {

    private static final String getFilesByStorageQuery = "SELECT * FROM FILES WHERE STORAGE_ID = :storageId";
    private static final String checkFileNameQuery = "SELECT * FROM FILES WHERE NAME = :name AND STORAGE_ID = :storageId";

    public FileDAOImpl() {
        super(File.class);
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