package com.lesson2.homework2.controller;

import com.lesson2.homework2.exception.BadRequestException;
import com.lesson2.homework2.exception.InternalServerException;
import com.lesson2.homework2.model.Item;
import com.lesson2.homework2.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.GET, params = {"id"}, produces = "text/plain")
    public @ResponseBody
    String doGet(@RequestParam(value = "id") String id) {
        try {
            Item item = itemService.findById(Long.parseLong(id));

            return item.toString() + "\r\n" + "Get success";

        } catch (BadRequestException | InternalServerException e) {

            System.err.println(e.getMessage());
            return "Get failed";
        }
    }


    @RequestMapping(method = RequestMethod.POST, params = {"name", "description"},
            produces = "text/plain")
    public @ResponseBody
    String doPost(@RequestParam(value = "name") String name,
                  @RequestParam(value = "description") String description) {
        try {
            Item item = new Item(name, description);

            itemService.save(item);
            return "Post success";

        } catch (BadRequestException | InternalServerException e) {

            System.err.println(e.getMessage());
            return "Post failed";
        }
    }

    @RequestMapping(method = RequestMethod.PUT, params = {"id", "name", "description"},
            produces = "text/plain")
    public @ResponseBody
    String doPut(@RequestParam(value = "id") String id,
                 @RequestParam(value = "name") String name,
                 @RequestParam(value = "description") String description) {
        try {
            Item item = new Item(name, description);

            item.setId(Long.parseLong(id));

            itemService.update(item);
            return "Put success";

        } catch (BadRequestException | InternalServerException e) {
            System.err.println(e.getMessage());
            return "Put failed";

        }
    }

    @RequestMapping(method = RequestMethod.DELETE, params = {"id"}, produces = "text/plain")
    public @ResponseBody
    String doDelete(@RequestParam(value = "id") String id) {
        try {
            itemService.delete(Long.parseLong(id));

            return "Delete success";

        } catch (InternalServerException | BadRequestException e) {
            System.err.println(e.getMessage());
            return "Delete failed";
        }
    }

}
