package com.lesson3_4.homework.DAO;

import com.lesson3_4.homework.model.Storage;
import org.springframework.stereotype.Repository;

@Repository
public class StorageDAOImpl extends DAO<Storage> implements StorageDAO {

    public StorageDAOImpl() {
        super(Storage.class);
    }

}