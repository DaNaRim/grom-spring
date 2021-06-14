package com.controller;

import com.exceptions.BadRequestException;
import com.exceptions.InternalServerException;
import com.model.File;
import com.model.Storage;
import com.service.FileService;
import com.service.StorageService;
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

    @RequestMapping(method = RequestMethod.POST, params = {"name", "format", "size"}, produces = "text/plain")
    public @ResponseBody
    String save(@RequestParam(value = "name") String name,
                @RequestParam(value = "format") String format,
                @RequestParam(value = "size") long size) {
        try {
            File file = new File(name, format, size);

            fileService.save(file);
            return "save success";

        } catch (BadRequestException e) {

            return "save failed: " + e.getMessage();
        } catch (InternalServerException e) {

            System.err.println("save failed: " + e.getMessage());
            return "save failed: something went wrong";
        }
    }

    @RequestMapping(method = RequestMethod.GET, params = {"id"}, produces = "text/plain")
    public @ResponseBody
    String findById(@RequestParam(value = "id") long id) {
        try {
            File file = fileService.findById(id);

            return "findById success" + "\r\n" + file.toString();

        } catch (BadRequestException e) {

            return "findById failed: " + e.getMessage();
        } catch (Exception e) {

            System.err.println("findById failed: " + e.getMessage());
            return "findById failed: something went wrong";
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
            return "update failed: " + e.getMessage();
        } catch (Exception e) {

            System.err.println("update failed: " + e.getMessage());
            return "update failed: something went wrong";
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, params = {"fileId"}, produces = "text/plain")
    public @ResponseBody
    String delete(@RequestParam(value = "fileId") long fileId) {
        try {
            File file = fileService.findById(fileId);

            fileService.delete(file);
            return "delete success";

        } catch (BadRequestException e) {

            return "delete failed: " + e.getMessage();
        } catch (Exception e) {

            System.err.println("delete failed: " + e.getMessage());
            return "delete failed: something went wrong";
        }
    }

    @RequestMapping(method = RequestMethod.PUT, params = {"storageId", "fileId"}, produces = "text/plain")
    public @ResponseBody
    String put(@RequestParam(value = "storageId") long storageId,
               @RequestParam(value = "fileId") long fileId) {
        try {
            Storage storage = storageService.findById(storageId);
            File file = fileService.findById(fileId);

            fileService.put(storage, file);
            return "put success";

        } catch (BadRequestException e) {

            return "put failed: " + e.getMessage();
        } catch (Exception e) {

            System.err.println("put failed: " + e.getMessage());
            return "put failed: something went wrong";
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, params = {"storageId", "fileId"}, produces = "text/plain")
    public @ResponseBody
    String deleteFromStorage(@RequestParam(value = "storageId") long storageId,
                             @RequestParam(value = "fileId") long fileId) {
        try {
            storageService.findById(storageId);
            File file = fileService.findById(fileId);

            fileService.deleteFromStorage(storageId, file);
            return "deleteFromStorage success";

        } catch (BadRequestException e) {

            return "deleteFromStorage failed: " + e.getMessage();
        } catch (Exception e) {

            System.err.println("deleteFromStorage failed: " + e.getMessage());
            return "deleteFromStorage failed: something went wrong";
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

            return "transferAll failed: " + e.getMessage();
        } catch (Exception e) {

            System.err.println("transferAll failed: " + e.getMessage());
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
            storageService.findById(storageIdFrom);
            Storage storageTo = storageService.findById(storageIdTo);
            File file = fileService.findById(fileId);

            fileService.transferFile(storageIdFrom, storageTo, file);
            return "transferFile success";

        } catch (BadRequestException e) {

            return "transferFile failed: " + e.getMessage();
        } catch (Exception e) {

            System.err.println("transferFile failed: " + e.getMessage());
            return "transferFile failed: something went wrong";
        }
    }

}