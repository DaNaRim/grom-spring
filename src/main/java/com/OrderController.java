package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET, value = "/orderSave", produces = "text/plain")
    public @ResponseBody
    String test() {
        orderService.save(null);
        return "ok";
    }

//    public Order save(Order order) {
//        orderService.test(0, 1111);
//
//        return orderService.save(order);
//    }

//    private OrderService getOrderService() {
//        if (orderService == null) {
//            orderService = new OrderService();
//        }
//        return orderService;
//    }
}
