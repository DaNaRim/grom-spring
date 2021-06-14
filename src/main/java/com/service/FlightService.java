package com.service;

import com.exception.BadRequestException;
import com.exception.InternalServerException;
import com.exception.ObjectNotFoundException;
import com.model.Filter;
import com.model.Flight;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlightService {

    Flight save(Flight flight) throws InternalServerException, ObjectNotFoundException, BadRequestException;

    Flight findById(long id) throws InternalServerException, ObjectNotFoundException;

    Flight update(Flight flight) throws InternalServerException, ObjectNotFoundException, BadRequestException;

    void delete(long id) throws ObjectNotFoundException, InternalServerException;

    List<Flight> flightsByFilter(Filter filter) throws InternalServerException, ObjectNotFoundException, BadRequestException;

    List<String> mostPopularTo() throws InternalServerException, ObjectNotFoundException;

    List<String> mostPopularFrom() throws InternalServerException, ObjectNotFoundException;
}
