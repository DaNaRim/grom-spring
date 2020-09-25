package com.lesson3.homework.controller;

import com.lesson3.homework.exceptions.BadRequestException;
import com.lesson3.homework.exceptions.InternalServerException;
import com.lesson3.homework.model.File;
import com.lesson3.homework.model.Storage;
import com.lesson3.homework.service.FileService;
import com.lesson3.homework.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/file")
public class FileController {

    private final FileService fileService;
    private final StorageService storageService;

    @Autowired
    public FileController(FileService fileService, StorageService storageService) {
        this.fileService = fileService;
        this.storageService = storageService;
    }

    @RequestMapping(method = RequestMethod.POST, params = {"storageId", "name", "format", "size"},
            produces = "text/plain")
    public @ResponseBody
    String put(@RequestParam(value = "storageId") long storageId,
               @RequestParam(value = "name") String name,
               @RequestParam(value = "format") String format,
               @RequestParam(value = "size") long size) {
        try {
            Storage storage = storageService.findById(storageId);

            File file = new File(name, format, size);

            fileService.put(storage, file);
            return "put success";

        } catch (BadRequestException e) {

            System.err.println(e.getMessage());
            return "put failed: " + e.getMessage();
        } catch (InternalServerException e) {

            System.err.println(e.getMessage());
            return "put failed: something went wrong";
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, params = {"storageId", "fileId"}, produces = "text/plain")
    public @ResponseBody
    String delete(@RequestParam(value = "storageId") long storageId,
                  @RequestParam(value = "fileId") long fileId) {
        try {
            Storage storage = storageService.findById(storageId);
            File file = fileService.findById(fileId);

            fileService.delete(storage, file);
            return "delete success";

        } catch (BadRequestException e) {

            System.err.println(e.getMessage());
            return "delete failed: " + e.getMessage();
        } catch (InternalServerException e) {

            System.err.println(e.getMessage());
            return "delete failed: something went wrong";
        }
    }

    @RequestMapping(method = RequestMethod.PUT, params = {"storageIdFrom", "storageIdTo"}, produces = "text/plain")
    public @ResponseBody
    String transferAll(@RequestParam(value = "storageIdFrom") long storageIdFrom,
                       @RequestParam(value = "storageIdTo") long storageIdTo) {
        try {
            Storage storageFrom = storageService.findById(storageIdFrom);
            Storage storageTo = storageService.findById(storageIdTo);

            fileService.transferAll(storageFrom, storageTo);
            return "transferAll success";

        } catch (BadRequestException e) {

            System.err.println(e.getMessage());
            return "transferAll failed: " + e.getMessage();
        } catch (InternalServerException e) {

            System.err.println(e.getMessage());
            return "transferAll failed: something went wrong";
        }
    }

    @RequestMapping(method = RequestMethod.PUT, params = {"storageIdFrom", "storageIdTo", "fileId"},
            produces = "text/plain")
    public @ResponseBody
    String transferFile(@RequestParam(value = "storageIdFrom") long storageIdFrom,
                        @RequestParam(value = "storageIdTo") long storageIdTo,
                        @RequestParam(value = "fileId") long fileId) {
        try {
            Storage storageFrom = storageService.findById(storageIdFrom);
            Storage storageTo = storageService.findById(storageIdTo);

            fileService.transferFile(storageFrom, storageTo, fileId);
            return "transferFile success";

        } catch (BadRequestException e) {

            System.err.println(e.getMessage());
            return "transferFile failed: " + e.getMessage();
        } catch (InternalServerException e) {

            System.err.println(e.getMessage());
            return "transferFile failed: something went wrong";
        }
    }

    @RequestMapping(method = RequestMethod.PUT, params = {"id", "name", "format", "size"}, produces = "text/plain")
    public @ResponseBody
    String update(@RequestParam(value = "id") long id,
                  @RequestParam(value = "name") String name,
                  @RequestParam(value = "format") String format,
                  @RequestParam(value = "size") long size) {
        try {
            File file = fileService.findById(id);
            file.setName(name);
            file.setFormat(format);
            file.setSize(size);

            fileService.update(file);
            return "update success";

        } catch (BadRequestException e) {

            System.err.println(e.getMessage());
            return "update failed: " + e.getMessage();
        } catch (InternalServerException e) {

            System.err.println(e.getMessage());
            return "update failed: something went wrong";
        }
    }

    @RequestMapping(method = RequestMethod.GET, params = {"id"}, produces = "text/plain")
    public @ResponseBody
    String findById(@RequestParam(value = "id") long id) {
        try {
            File file = fileService.findById(id);

            return file.toString() + "\r\n" + "findById success";

        } catch (BadRequestException e) {

            System.err.println(e.getMessage());
            return "findById failed: " + e.getMessage();
        } catch (InternalServerException e) {

            System.err.println(e.getMessage());
            return "findById failed: something went wrong";
        }
    }
}