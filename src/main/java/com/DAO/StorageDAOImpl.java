package com.DAO;

import com.model.Storage;

public class StorageDAOImpl extends DAO<Storage> implements StorageDAO {

    public StorageDAOImpl() {
        super(Storage.class);
    }

}