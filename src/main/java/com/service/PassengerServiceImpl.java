package com.service;

import com.DAO.PassengerDAO;
import com.exception.BadRequestException;
import com.exception.InternalServerException;
import com.exception.ObjectNotFoundException;
import com.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PassengerServiceImpl implements PassengerService {

    private final PassengerDAO passengerDAO;

    @Autowired
    public PassengerServiceImpl(PassengerDAO passengerDAO) {
        this.passengerDAO = passengerDAO;
    }

    public Passenger save(Passenger passenger) throws BadRequestException, InternalServerException {
        try {
            validatePassenger(passenger);
            passengerDAO.checkPassportCodeForUnique(passenger.getPassportCode());

            return passengerDAO.save(passenger);
        } catch (BadRequestException e) {
            throw new BadRequestException("Can`t save passenger: " + e.getMessage());
        }
    }

    public Passenger findById(long id) throws ObjectNotFoundException, InternalServerException {
        return passengerDAO.findById(id);
    }

    public Passenger update(Passenger passenger)
            throws ObjectNotFoundException, BadRequestException, InternalServerException {
        try {
            validateUpdate(passenger);

            return passengerDAO.update(passenger);
        } catch (BadRequestException e) {
            throw new BadRequestException("Can`t update passenger: " + e.getMessage());
        }
    }

    public void delete(long id) throws ObjectNotFoundException, InternalServerException {
        passengerDAO.delete(findById(id));
    }

    public List<Passenger> regularPassengers(int year)
            throws ObjectNotFoundException, BadRequestException, InternalServerException {

        //airplanes had not yet been invented
        if (year < 1903) {
            throw new BadRequestException("Can`t get regular passengers: year must be >= 1903");
        }
        if (year > Calendar.getInstance().get(Calendar.YEAR)) {
            throw new BadRequestException("Can`t get regular passengers for year that bigger than current year");
        }
        return passengerDAO.regularPassengers(year);
    }

    private void validatePassenger(Passenger passenger) throws BadRequestException {

        if (passenger.getLastName() == null || passenger.getNationality() == null
                || passenger.getDateOfBirth() == null || passenger.getPassportCode() == null) {
            throw new BadRequestException("not all fields are filed");
        }
        if (passenger.getDateOfBirth().after(new Date())) {
            throw new BadRequestException("date of birth filed incorrect");
        }
        if (passenger.getLastName().length() > 20 || passenger.getNationality().length() > 20
                || passenger.getPassportCode().length() > 20) {
            throw new BadRequestException("fields length must be <= 20");
        }
    }

    private void validateUpdate(Passenger passenger)
            throws ObjectNotFoundException, BadRequestException, InternalServerException {

        Passenger oldPassenger = findById(passenger.getId());
        validatePassenger(passenger);

        if (!oldPassenger.getPassportCode().equals(passenger.getPassportCode())) {

            passengerDAO.checkPassportCodeForUnique(passenger.getPassportCode());
        }
    }
}
