package com.controller;

import com.exception.BadRequestException;
import com.exception.ObjectNotFoundException;
import com.model.Filter;
import com.model.Flight;
import com.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/flight")
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> save(@RequestBody Flight flight) {
        try {
            flightService.save(flight);

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
            Flight flight = flightService.findById(id);

            return new ResponseEntity<>(flight.toString(), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> update(@RequestBody Flight flight) {
        try {
            flightService.update(flight);

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
            flightService.delete(id);

            return new ResponseEntity<>("Delete success", HttpStatus.OK);
        } catch (ObjectNotFoundException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/flightsByFilter", produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> flightsByFilter(@RequestBody Filter filter) {
        try {
            List<Flight> flights = flightService.flightsByFilter(filter);

            StringBuilder sb = new StringBuilder();
            for (Flight flight : flights) {
                sb.append(flight.toString()).append("\n");
            }
            sb.delete(sb.lastIndexOf("\n"), sb.length());

            return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/mostPopularTo", produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> mostPopularTo() {
        //find top 10 most popular cities to which they flew
        try {
            List<String> cities = flightService.mostPopularTo();

            return new ResponseEntity<>(cities.toString(), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/mostPopularFrom", produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> mostPopularFrom() {
        //find top 10 most popular cities from which they flew
        try {
            List<String> cities = flightService.mostPopularFrom();

            return new ResponseEntity<>(cities.toString(), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
