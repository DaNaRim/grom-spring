package com.DAO;

import com.exception.InternalServerException;
import com.exception.ObjectNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Filter;
import com.model.Flight;
import com.model.Plane;
import org.hibernate.HibernateException;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Transactional
public class FlightDAOImpl extends DAO<Flight> implements FlightDAO {

    public FlightDAOImpl() {
        super(Flight.class);
    }

    private static final String MOST_POPULAR_TO_QUERY = "SELECT CITY_TO " +
            "FROM (SELECT CITY_TO FROM FLIGHT GROUP BY CITY_TO ORDER BY COUNT(ID) DESC)" +
            "WHERE ROWNUM <= 10";
    private static final String MOST_POPULAR_FROM_QUERY = "SELECT CITY_FROM " +
            "FROM (SELECT CITY_FROM FROM FLIGHT GROUP BY CITY_FROM ORDER BY COUNT(ID) DESC)" +
            "WHERE ROWNUM <= 10";


    @Override
    public Flight save(Flight entity) throws InternalServerException {

        if (entity.getDateFlight() == null) {
            entity.setDateFlight(new Date());
        }

        return super.save(entity);
    }

    @Override
    public Flight update(Flight entity) throws InternalServerException {
        try {
            if (entity.getDateFlight() == null) {

                Flight oldFlight = findById(entity.getId());

                entity.setDateFlight(oldFlight.getDateFlight());
            }

            return super.update(entity);
        } catch (ObjectNotFoundException e) {
            throw new InternalServerException(e.getMessage());
        }
    }

    public List<Flight> flightsByFilter(Filter filter) throws ObjectNotFoundException, InternalServerException {
        try {
            List<Flight> flights = entityManager.createQuery(getFlightCriteriaQuery(filter))
                    .getResultList();

            if (flights == null) {
                throw new ObjectNotFoundException("There are no flights with this filter parameters");
            }

            return flights;
        } catch (HibernateException e) {
            throw new InternalServerException("Something went wrong while trying to find flights by filter: "
                    + filter.toString() + " : " + e.getMessage());
        }
    }


    public List<String> mostPopularTo() throws ObjectNotFoundException, InternalServerException {
        //find top 10 most popular cities to which they flew
        try {
            List<String> cities = entityManager.createNativeQuery(MOST_POPULAR_TO_QUERY)
                    .getResultList();

            if (cities == null) {
                throw new ObjectNotFoundException("There are no flights yet");
            }

            return cities;
        } catch (HibernateException e) {
            throw new InternalServerException("Something went wrong while trying to get most popular cities to which "
                    + "they flew: " + e.getMessage());
        }
    }

    public List<String> mostPopularFrom() throws ObjectNotFoundException, InternalServerException {
        //find top 10 most popular cities from which they flew
        try {
            List<String> cities = entityManager.createNativeQuery(MOST_POPULAR_FROM_QUERY)
                    .getResultList();

            if (cities == null) {
                throw new ObjectNotFoundException("There are no flights yet");
            }

            return cities;
        } catch (HibernateException e) {
            throw new InternalServerException("Something went wrong while trying to get most popular cities from which "
                    + "they flew: " + e.getMessage());
        }
    }

    private CriteriaQuery<Flight> getFlightCriteriaQuery(Filter filter) {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> filterParams = objectMapper.convertValue(filter, Map.class);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Flight> criteria = builder.createQuery(Flight.class);

        Root<Flight> flightRoot = criteria.from(Flight.class);
        Join<Flight, Plane> planeJoin = flightRoot.join("plane");

        Predicate predicate = builder.conjunction();

        for (String param : filterParams.keySet()) {
            if (filterParams.get(param) != null) {

                if (param.equals("dateFrom")) {
                    Calendar calendar = Calendar.getInstance();

                    if (filter.getDateTo() != null) {
                        predicate = builder.and(predicate,
                                builder.greaterThanOrEqualTo(flightRoot.get("dateFlight"), filter.getDateFrom()));

                        calendar.setTime(filter.getDateTo());
                        calendar.add(Calendar.DAY_OF_YEAR, 1);

                        predicate = builder.and(predicate,
                                builder.lessThanOrEqualTo(flightRoot.get("dateFlight"), calendar.getTime()));
                    } else {
                        predicate = builder.and(predicate,
                                builder.greaterThanOrEqualTo(flightRoot.get("dateFlight"), filter.getDateFrom()));

                        calendar.setTime(filter.getDateFrom());
                        calendar.add(Calendar.DAY_OF_YEAR, 1);

                        predicate = builder.and(predicate,
                                builder.lessThan(flightRoot.get("dateFlight"), calendar.getTime()));
                    }
                    continue;
                }
                if (param.equals("dateTo")) continue;
                if (param.equals("planeModel")) {
                    predicate = builder.and(predicate, builder.equal(planeJoin.get("model"), filterParams.get(param)));
                    continue;
                }

                predicate = builder.and(predicate, builder.equal(flightRoot.get(param), filterParams.get(param)));
            }
        }
        return criteria.select(flightRoot).where(predicate);

        /*
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Flight> criteria = builder.createQuery(Flight.class);

        Root<Flight> flightRoot = criteria.from(Flight.class);
        Join<Flight, Plane> planeJoin = flightRoot.join("plane");

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getDateFrom() != null) {
            Calendar calendar = Calendar.getInstance();

            if (filter.getDateTo() != null) {
                predicates.add(builder.greaterThanOrEqualTo(flightRoot.get("dateFlight"), filter.getDateFrom()));

                calendar.setTime(filter.getDateTo());
                calendar.add(Calendar.DAY_OF_YEAR, 1);

                predicates.add(builder.lessThanOrEqualTo(flightRoot.get("dateFlight"), calendar.getTime()));
            } else {
                predicates.add(builder.greaterThanOrEqualTo(flightRoot.get("dateFlight"), filter.getDateFrom()));

                calendar.setTime(filter.getDateFrom());
                calendar.add(Calendar.DAY_OF_YEAR, 1);

                predicates.add(builder.lessThan(flightRoot.get("dateFlight"), calendar.getTime()));
            }
        }
        if (filter.getCityFrom() != null) {
            predicates.add(builder.equal(flightRoot.get("cityFrom"), filter.getCityFrom()));
        }
        if (filter.getCityTo() != null) {
            predicates.add(builder.equal(flightRoot.get("cityTo"), filter.getCityTo()));
        }
        if (filter.getPlaneModel() != null) {
            predicates.add(builder.equal(planeJoin.get("model"), filter.getPlaneModel()));
        }

        return criteria.select(flightRoot).where(predicates.toArray(new Predicate[0]));
         */
    }
}
