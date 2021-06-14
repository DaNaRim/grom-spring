package com.controller;

import com.exception.BadRequestException;
import com.exception.ObjectNotFoundException;
import com.model.Passenger;
import com.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/passenger")
public class PassengerController {

    private final PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> save(@RequestBody Passenger passenger) {
        try {
            passengerService.save(passenger);

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
            Passenger passenger = passengerService.findById(id);

            return new ResponseEntity<>(passenger.toString(), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> update(@RequestBody Passenger passenger) {
        try {
            passengerService.update(passenger);

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
            passengerService.delete(id);

            return new ResponseEntity<>("Delete success", HttpStatus.OK);
        } catch (ObjectNotFoundException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/regularPassengers/{year}", produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> regularPassengers(@PathVariable int year) {
        //find passengers that have more than 25 flights per year
        try {
            List<Passenger> passengers = passengerService.regularPassengers(year);

            StringBuilder sb = new StringBuilder();
            for (Passenger passenger : passengers) {
                sb.append(passenger.toString()).append("\n");
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
}
