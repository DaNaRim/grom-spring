package com.service;

import com.exception.BadRequestException;
import com.exception.InternalServerException;
import com.exception.ObjectNotFoundException;
import com.model.Plane;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlaneService {

    Plane save(Plane plane) throws BadRequestException, InternalServerException;

    Plane findById(long id) throws ObjectNotFoundException, InternalServerException;

    Plane update(Plane plane) throws ObjectNotFoundException, BadRequestException, InternalServerException;

    void delete(long id) throws ObjectNotFoundException, InternalServerException;

    List<Plane> oldPlanes() throws ObjectNotFoundException, InternalServerException;

    List<Plane> regularPlanes(int year) throws ObjectNotFoundException, InternalServerException;
}
