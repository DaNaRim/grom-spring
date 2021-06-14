package com.DAO;

import com.exception.InternalServerException;
import com.exception.ObjectNotFoundException;
import com.model.Filter;
import com.model.Flight;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightDAO {

    Flight save(Flight flight) throws InternalServerException;

    Flight findById(long id) throws ObjectNotFoundException, InternalServerException;

    Flight update(Flight flight) throws InternalServerException;

    void delete(Flight flight) throws InternalServerException;

    List<Flight> flightsByFilter(Filter filter) throws ObjectNotFoundException, InternalServerException;

    List<String> mostPopularTo() throws ObjectNotFoundException, InternalServerException;

    List<String> mostPopularFrom() throws ObjectNotFoundException, InternalServerException;
}
