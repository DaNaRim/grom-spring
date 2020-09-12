package com.lesson3.homework.controller;

import com.lesson3.homework.exceptions.BadRequestException;
import com.lesson3.homework.exceptions.InternalServerException;
import com.lesson3.homework.model.Storage;
import com.lesson3.homework.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/storage")
public class StorageController {

    private final StorageService storageService;

    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping(method = RequestMethod.POST, params = {"formatsSupported", "storageCountry", "storageSize"},
            produces = "text/plain")
    public @ResponseBody
    String save(@RequestParam(value = "formatsSupported") String formatsSupported,
                @RequestParam(value = "storageCountry") String storageCountry,
                @RequestParam(value = "storageSize") long storageSize) {
        try {
            String[] formatsSupportedArray = formatsSupported.split(", ");
            Storage storage = new Storage(formatsSupportedArray, storageCountry, storageSize);

            storageService.save(storage);
            return "save success";

        } catch (BadRequestException e) {

            System.err.println(e.getMessage());
            return "save failed: " + e.getMessage();
        } catch (InternalServerException e) {

            System.err.println(e.getMessage());
            return "save failed: something went wrong";
        }
    }

    @RequestMapping(method = RequestMethod.GET, params = {"id"}, produces = "text/plain")
    public @ResponseBody
    String findById(@RequestParam(value = "id") long id) {
        try {
            Storage storage = storageService.findById(id);

            return storage.toString() + "\r\n" + "findById success";

        } catch (BadRequestException e) {

            System.err.println(e.getMessage());
            return "findById failed: " + e.getMessage();
        } catch (InternalServerException e) {

            System.err.println(e.getMessage());
            return "findById failed: something went wrong";
        }
    }

    @RequestMapping(method = RequestMethod.PUT, params = {"id", "formatsSupported", "storageCountry", "storageSize"},
            produces = "text/plain")
    public @ResponseBody
    String update(@RequestParam(value = "id") long id,
                  @RequestParam(value = "formatsSupported") String formatsSupported,
                  @RequestParam(value = "storageCountry") String storageCountry,
                  @RequestParam(value = "storageSize") long storageSize) {
        try {
            String[] formatsSupportedArray = formatsSupported.split(", ");
            Storage storage = new Storage(formatsSupportedArray, storageCountry, storageSize);

            storage.setId(id);

            storageService.update(storage);
            return "update success";

        } catch (BadRequestException e) {

            System.err.println(e.getMessage());
            return "update failed: " + e.getMessage();
        } catch (InternalServerException e) {

            System.err.println(e.getMessage());
            return "update failed: something went wrong";
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, params = {"id"}, produces = "text/plain")
    public @ResponseBody
    String delete(@RequestParam(value = "id") long id) {
        try {
            storageService.delete(id);
            return "delete success";

        } catch (BadRequestException e) {

            System.err.println(e.getMessage());
            return "delete failed: " + e.getMessage();
        } catch (InternalServerException e) {

            System.err.println(e.getMessage());
            return "delete failed: something went wrong";
        }
    }
}