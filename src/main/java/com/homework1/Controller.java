package com.homework1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private Route route;
    @Autowired
    private Service service;
    @Autowired
    private Step step;

    @RequestMapping(method = RequestMethod.GET, value = "/callByBeen", produces = "text/plain")
    public @ResponseBody
    void callByBeen() {
        route.getId();
        route.getSteps();

        service.getId();
        service.getName();
        service.getParamsToCall();

        step.getId();
        step.getServiceFrom();
        step.getServiceTo();
        step.getParamsServiceFrom();
        step.getParamsServiceTo();

        System.out.println("Nice");
    }
}
