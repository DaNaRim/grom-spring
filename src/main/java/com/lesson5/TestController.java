package com.lesson5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/saveItem")
public class TestController {

    private final DAO dao;

    @Autowired
    public TestController(DAO dao) {
        this.dao = dao;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "text/plan")
    public @ResponseBody
    String saveOrder() {
        Item item = new Item();
        item.setDescription("spring basics spring gromcode");
        dao.save(item);
        return "ok";
    }
}
