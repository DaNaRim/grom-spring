package com.controller;

import com.exception.ObjectNotFoundException;
import com.model.Item;
import com.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/item")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> save(@RequestBody Item item) {
        try {
            itemService.save(item);

            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/findById/{id}", produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> findById(@PathVariable(value = "id") Long id) {
        try {
            itemService.findById(id);

            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (ObjectNotFoundException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> update(@RequestBody Item item) {
        try {
            itemService.update(item);

            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (ObjectNotFoundException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/delete/{id}", produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        try {
            itemService.delete(id);

            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (ObjectNotFoundException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
