package com.DAO;

import com.exception.BadRequestException;
import com.exception.InternalServerException;
import com.exception.ObjectNotFoundException;
import com.model.Plane;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaneDAO {

    Plane save(Plane plane) throws InternalServerException;

    Plane findById(long id) throws ObjectNotFoundException, InternalServerException;

    Plane update(Plane plane) throws InternalServerException;

    void delete(Plane plane) throws InternalServerException;

    List<Plane> oldPlanes() throws ObjectNotFoundException, InternalServerException;

    List<Plane> regularPlanes(int year) throws ObjectNotFoundException, InternalServerException;

    void checkPlaneCodeForUnique(String code) throws BadRequestException, InternalServerException;
}
