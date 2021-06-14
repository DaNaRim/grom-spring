package com.DAO;

import com.exception.BadRequestException;
import com.exception.InternalServerException;
import com.exception.ObjectNotFoundException;
import com.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerDAO {

    Passenger save(Passenger passenger) throws InternalServerException;

    Passenger findById(long id) throws ObjectNotFoundException, InternalServerException;

    Passenger update(Passenger passenger) throws InternalServerException;

    void delete(Passenger passenger) throws InternalServerException;

    List<Passenger> regularPassengers(int year) throws ObjectNotFoundException, InternalServerException;

    void checkPassportCodeForUnique(String code) throws BadRequestException, InternalServerException;
}
