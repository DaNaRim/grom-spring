package com.DAO;

import com.exception.BadRequestException;
import com.exception.InternalServerException;
import com.exception.ObjectNotFoundException;
import com.model.Plane;
import org.hibernate.HibernateException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@Transactional
public class PlaneDAOImpl extends DAO<Plane> implements PlaneDAO {

    public PlaneDAOImpl() {
        super(Plane.class);
    }

    private static final String OLD_PLANES_QUERY = "SELECT * FROM PLANE WHERE YEAR_PRODUCED <= TO_DATE(:date, 'MM.yyyy') ORDER BY YEAR_PRODUCED";
    private static final String REGULAR_PLANES_QUERY = "SELECT P.*" +
            "FROM PLANE P" +
            "         JOIN (SELECT COUNT(ID), PLANE_ID" +
            "               FROM FLIGHT" +
            "               WHERE EXTRACT(YEAR FROM DATE_FLIGHT) = :year" +
            "               GROUP BY PLANE_ID" +
            "               HAVING COUNT(ID) >= 300) ON P.ID = PLANE_ID";

    private static final String CHECK_PLANE_CODE_FOR_UNIQUE_QUERY = "SELECT COUNT(*) FROM PLANE WHERE CODE = :code";

    public List<Plane> oldPlanes() throws InternalServerException, ObjectNotFoundException {
        //find planes older than 20 years
        try {
            Calendar calendar = Calendar.getInstance();
            String oldDate = calendar.get(Calendar.MONTH) + "." + (calendar.get(Calendar.YEAR) - 20);

            List<Plane> planes = entityManager.createNativeQuery(OLD_PLANES_QUERY, Plane.class)
                    .setParameter("date", oldDate)
                    .getResultList();

            if (planes == null) {
                throw new ObjectNotFoundException("All planes are younger than 20 years");
            }

            return planes;
        } catch (HibernateException e) {
            throw new InternalServerException("Something went wrong while trying to find old planes: "
                    + e.getMessage());
        }
    }

    public List<Plane> regularPlanes(int year) throws ObjectNotFoundException, InternalServerException {
        //find planes that have more than 300 flight in this year
        try {
            List<Plane> planes = entityManager.createNativeQuery(REGULAR_PLANES_QUERY, Plane.class)
                    .setParameter("year", year)
                    .getResultList();

            if (planes == null) {
                throw new ObjectNotFoundException("There is no regular planes in year " + year);
            }

            return planes;
        } catch (HibernateException e) {
            throw new InternalServerException("Something went wrong while trying to find regular planes in year "
                    + year + " : " + e.getMessage());
        }
    }

    public void checkPlaneCodeForUnique(String code) throws BadRequestException, InternalServerException {
        try {
            BigDecimal bd = (BigDecimal) entityManager.createNativeQuery(CHECK_PLANE_CODE_FOR_UNIQUE_QUERY)
                    .setParameter("code", code)
                    .getSingleResult();

            if (bd.longValue() != 0) {
                throw new BadRequestException("plane with code " + code + " already exists");
            }
        } catch (HibernateException e) {
            throw new InternalServerException("Something went wrong while trying to check plane code "
                    + code + " fore unique: " + e.getMessage());
        }
    }
}
