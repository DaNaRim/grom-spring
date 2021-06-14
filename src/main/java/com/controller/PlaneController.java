package com.controller;

import com.exception.BadRequestException;
import com.exception.ObjectNotFoundException;
import com.model.Plane;
import com.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/plane")
public class PlaneController {

    private final PlaneService planeService;

    @Autowired
    public PlaneController(PlaneService planeService) {
        this.planeService = planeService;
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> save(@RequestBody Plane plane) {
        try {
            planeService.save(plane);

            return new ResponseEntity<>("Save success", HttpStatus.OK);
        } catch (BadRequestException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/findById/{id}", produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> findById(@PathVariable long id) {
        try {
            Plane plane = planeService.findById(id);

            return new ResponseEntity<>(plane.toString(), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> update(@RequestBody Plane plane) {
        try {
            planeService.update(plane);

            return new ResponseEntity<>("Update success", HttpStatus.OK);
        } catch (ObjectNotFoundException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/delete/{id}", produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> delete(@PathVariable long id) {
        try {
            planeService.delete(id);

            return new ResponseEntity<>("Delete success", HttpStatus.OK);
        } catch (ObjectNotFoundException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/oldPlanes", produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> oldPlanes() {
        //find planes older than 20 years
        try {
            List<Plane> planes = planeService.oldPlanes();

            StringBuilder sb = new StringBuilder();
            for (Plane plane : planes) {
                sb.append(plane.toString()).append("\n");
            }
            sb.delete(sb.lastIndexOf("\n"), sb.length());

            return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/regularPlanes/{year}", produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> regularPlanes(@PathVariable int year) {
        //find planes that have more than 300 flight in this year
        try {
            List<Plane> planes = planeService.regularPlanes(year);

            StringBuilder sb = new StringBuilder();
            for (Plane plane : planes) {
                sb.append(plane.toString()).append("\n");
            }
            sb.delete(sb.lastIndexOf("\n"), sb.length());

            return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
