package com.service;

import com.DAO.PlaneDAO;
import com.exception.BadRequestException;
import com.exception.InternalServerException;
import com.exception.ObjectNotFoundException;
import com.model.Plane;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class PlaneServiceImpl implements PlaneService {

    private final PlaneDAO planeDAO;

    @Autowired
    public PlaneServiceImpl(PlaneDAO planeDAO) {
        this.planeDAO = planeDAO;
    }

    public Plane save(Plane plane) throws InternalServerException, BadRequestException {
        try {
            validatePlane(plane);
            planeDAO.checkPlaneCodeForUnique(plane.getCode());

            return planeDAO.save(plane);
        } catch (BadRequestException e) {
            throw new BadRequestException("Can`t save plane: " + e.getMessage());
        }
    }

    public Plane findById(long id) throws InternalServerException, ObjectNotFoundException {
        return planeDAO.findById(id);
    }

    public Plane update(Plane plane) throws InternalServerException, BadRequestException, ObjectNotFoundException {
        try {
            validateUpdate(plane);

            return planeDAO.update(plane);
        } catch (BadRequestException e) {
            throw new BadRequestException("Can`t update plane: " + e.getMessage());
        }
    }

    public void delete(long id) throws ObjectNotFoundException, InternalServerException {
        planeDAO.delete(findById(id));
    }

    public List<Plane> oldPlanes() throws ObjectNotFoundException, InternalServerException {
        return planeDAO.oldPlanes();
    }

    public List<Plane> regularPlanes(int year) throws ObjectNotFoundException, InternalServerException {
        return planeDAO.regularPlanes(year);
    }

    private void validatePlane(Plane plane) throws BadRequestException {

        if (plane.getModel() == null || plane.getCode() == null
                || plane.getYearProduced() == null || plane.getAvgFuelConsumption() == 0) {
            throw new BadRequestException("not all fields are filed");
        }
        if (plane.getYearProduced().after(new Date()) || plane.getAvgFuelConsumption() < 0) {
            throw new BadRequestException("fields are not filed correctly");
        }
        if (plane.getModel().length() > 50) {
            throw new BadRequestException("model length must be <= 50");
        }
        if (plane.getCode().length() > 20) {
            throw new BadRequestException("code length must be <= 20");
        }
    }

    private void validateUpdate(Plane plane)
            throws ObjectNotFoundException, InternalServerException, BadRequestException {

        Plane oldPlane = findById(plane.getId());
        validatePlane(plane);

        if (!oldPlane.getCode().equals(plane.getCode())) {
            planeDAO.checkPlaneCodeForUnique(plane.getCode());
        }
    }
}
