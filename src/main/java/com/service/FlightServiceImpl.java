package com.service;

import com.DAO.FlightDAO;
import com.exception.BadRequestException;
import com.exception.InternalServerException;
import com.exception.ObjectNotFoundException;
import com.model.Filter;
import com.model.Flight;
import com.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class FlightServiceImpl implements FlightService {

    private final FlightDAO flightDAO;
    private final PlaneService planeService;
    private final PassengerService passengerService;

    @Autowired
    public FlightServiceImpl(FlightDAO flightDAO, PlaneService planeService, PassengerService passengerService) {
        this.flightDAO = flightDAO;
        this.planeService = planeService;
        this.passengerService = passengerService;
    }

    public Flight save(Flight flight) throws ObjectNotFoundException, BadRequestException, InternalServerException {
        try {
            validateFlight(flight);

            return flightDAO.save(flight);
        } catch (BadRequestException e) {
            throw new BadRequestException("Can`t save flight: " + e.getMessage());
        }
    }

    public Flight findById(long id) throws ObjectNotFoundException, InternalServerException {
        return flightDAO.findById(id);
    }

    public Flight update(Flight flight) throws ObjectNotFoundException, BadRequestException, InternalServerException {
        try {
            findById(flight.getId());
            validateFlight(flight);

            return flightDAO.update(flight);
        } catch (BadRequestException e) {
            throw new BadRequestException("Can`t update flight: " + e.getMessage());
        }
    }

    public void delete(long id) throws ObjectNotFoundException, InternalServerException {
        flightDAO.delete(findById(id));
    }

    public List<Flight> flightsByFilter(Filter filter)
            throws ObjectNotFoundException, BadRequestException, InternalServerException {
        try {
            validateFilter(filter);

            return flightDAO.flightsByFilter(filter);
        } catch (BadRequestException e) {
            throw new BadRequestException("Can`t find flights by filter: " + e.getMessage());
        }
    }

    public List<String> mostPopularTo() throws ObjectNotFoundException, InternalServerException {
        return flightDAO.mostPopularTo();
    }

    public List<String> mostPopularFrom() throws ObjectNotFoundException, InternalServerException {
        return flightDAO.mostPopularFrom();
    }

    private void validateFlight(Flight flight)
            throws ObjectNotFoundException, BadRequestException, InternalServerException {

        if (flight.getPassengers() == null || flight.getPlane() == null
                || flight.getCityFrom() == null || flight.getCityTo() == null) {
            throw new BadRequestException("not all fields are filed");
        }
        if (flight.getCityFrom().length() > 20 || flight.getCityTo().length() > 20) {
            throw new BadRequestException("fields length must be <= 20");
        }
        if (flight.getCityFrom().equals(flight.getCityTo())) {
            throw new BadRequestException("flight to the same city");
        }
        if (flight.getDateFlight() != null && flight.getDateFlight().after(new Date())) {
            throw new BadRequestException("date filled incorrect");
        }

        planeService.findById(flight.getPlane().getId());

        for (Passenger passenger : flight.getPassengers()) {
            passengerService.findById(passenger.getId());
        }
    }

    private void validateFilter(Filter filter) throws BadRequestException {
        if (filter.getDateFrom() == null && filter.getDateTo() == null
                && filter.getCityFrom() == null && filter.getCityTo() == null
                && filter.getPlaneModel() == null) {
            throw new BadRequestException("filter is empty");
        }
        if (filter.getDateFrom() == null && filter.getDateTo() != null) {
            throw new BadRequestException("if dateTo is filed dateFrom must be filed too");
        }
        if (filter.getCityFrom() != null && filter.getCityFrom().equals(filter.getCityTo())) {
            throw new BadRequestException("flight to the same city");
        }
        if (filter.getDateFrom() != null && filter.getDateFrom().after(new Date())
                || filter.getDateTo() != null && filter.getDateTo().after(new Date())) {
            throw new BadRequestException("date filed incorrect");
        }
    }
}
