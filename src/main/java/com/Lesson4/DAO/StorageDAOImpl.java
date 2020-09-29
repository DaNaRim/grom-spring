package com.Lesson4.DAO;

import com.Lesson4.model.Storage;
import org.springframework.stereotype.Repository;

@Repository
public class StorageDAOImpl extends DAO<Storage> implements StorageDAO {

    public StorageDAOImpl() {
        super(Storage.class);
    }

}