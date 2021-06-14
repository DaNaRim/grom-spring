package com.DAO;

import com.exception.BadRequestException;
import com.exception.InternalServerException;
import com.exception.ObjectNotFoundException;
import com.model.Passenger;
import org.hibernate.HibernateException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Transactional
public class PassengerDAOImpl extends DAO<Passenger> implements PassengerDAO {

    public PassengerDAOImpl() {
        super(Passenger.class);
    }

    private static final String REGULAR_PASSENGERS_QUERY = "SELECT P.*" +
            "FROM PASSENGER P" +
            "         JOIN (SELECT PASSENGER_ID, COUNT(ID)" +
            "               FROM FLIGHT" +
            "                        JOIN PASSENGER_FLIGHT ON FLIGHT_ID = FLIGHT.ID" +
            "               WHERE EXTRACT(YEAR FROM FLIGHT.DATE_FLIGHT) = :year" +
            "               GROUP BY PASSENGER_ID" +
            "               HAVING COUNT(FLIGHT.ID) > 25)" +
            "            ON PASSENGER_ID = P.ID";

    private static final String CHECK_PASSPORT_CODE_FOR_UNIQUE_QUERY = "SELECT COUNT(*) FROM PASSENGER WHERE PASSPORT_CODE = :code";

    public List<Passenger> regularPassengers(int year) throws ObjectNotFoundException, InternalServerException {
        //find passengers that have more than 25 flights per year
        try {
            List<Passenger> passengers = entityManager.createNativeQuery(REGULAR_PASSENGERS_QUERY, Passenger.class)
                    .setParameter("year", year)
                    .getResultList();

            if (passengers == null) {
                throw new ObjectNotFoundException("There are no regular passengers in year " + year);
            }

            return passengers;
        } catch (HibernateException e) {
            throw new InternalServerException("Something went wrong while trying to find regular passengers in year "
                    + year + " : " + e.getMessage());
        }
    }

    public void checkPassportCodeForUnique(String code) throws BadRequestException, InternalServerException {
        try {
            BigDecimal bd = (BigDecimal) entityManager.createNativeQuery(CHECK_PASSPORT_CODE_FOR_UNIQUE_QUERY)
                    .setParameter("code", code)
                    .getSingleResult();

            if (bd.longValue() != 0) {
                throw new BadRequestException("passenger with passport code " + code + " already exists");
            }
        } catch (HibernateException e) {
            throw new InternalServerException("Something went wrong while trying to check passenger passport code "
                    + code + " for unique: " + e.getMessage());
        }
    }
}
