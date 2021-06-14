package com.service;

import com.exception.BadRequestException;
import com.exception.InternalServerException;
import com.exception.ObjectNotFoundException;
import com.model.Passenger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PassengerService {

    Passenger save(Passenger passenger) throws BadRequestException, InternalServerException;

    Passenger findById(long id) throws ObjectNotFoundException, InternalServerException;

    Passenger update(Passenger passenger) throws ObjectNotFoundException, BadRequestException, InternalServerException;

    void delete(long id) throws ObjectNotFoundException, InternalServerException;

    List<Passenger> regularPassengers(int year) throws ObjectNotFoundException, BadRequestException, InternalServerException;
}
