package com.lesson5.homework;

import com.lesson5.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/item")
public class ItemController5 {

    private final ItemDAO itemDAO;

    @Autowired
    public ItemController5(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = "test/plain")
    public @ResponseBody
    ResponseEntity<String> save(@RequestBody Item item) {
        try {
            itemDAO.save(item);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findById", params = {"id"}, produces = "test/plain")
    public @ResponseBody
    ResponseEntity<String> findById(@RequestParam(name = "id") Long id) {
        try {
            itemDAO.findById(id);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = "test/plain")
    public @ResponseBody
    ResponseEntity<String> update(@RequestBody Item item) {
        try {
            itemDAO.update(item);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete", params = {"id"}, produces = "test/plain")
    public @ResponseBody
    ResponseEntity<String> delete(@RequestParam(name = "id") Long id) {
        try {
            itemDAO.delete(id);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
