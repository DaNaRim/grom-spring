package com.DAO;

import com.model.Storage;
import org.springframework.stereotype.Repository;

@Repository
public class StorageDAOImpl extends DAO<Storage> implements StorageDAO {

    public StorageDAOImpl() {
        super(Storage.class);
    }

}