package com.homework2.controller;

import com.homework2.exception.BadRequestException;
import com.homework2.exception.InternalServerException;
import com.homework2.model.Item;
import com.homework2.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/test")
public class ItemController extends HttpServlet {

    @Autowired
    private ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            long id = Long.parseLong(req.getParameter("id"));
            Item item = itemService.findById(id);

            resp.getWriter().println(item.toString());
            resp.getWriter().println("Get success");

        } catch (BadRequestException | InternalServerException e) {

            resp.getWriter().println("Get failed");
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            Item item = new Item(
                    req.getParameter("name"),
                    req.getParameter("description")
            );

            itemService.save(item);
            resp.getWriter().println("Post success");

        } catch (BadRequestException | InternalServerException e) {

            resp.getWriter().println("Post failed");
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            Item item = new Item(
                    req.getParameter("name"),
                    req.getParameter("description")
            );
            long id = Long.parseLong(req.getParameter("id"));
            item.setId(id);

            itemService.update(item);
            resp.getWriter().println("Put success");

        } catch (BadRequestException | InternalServerException e) {

            resp.getWriter().println("Put failed");
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            itemService.delete(id);

            resp.getWriter().println("Delete success");

        } catch (InternalServerException | BadRequestException e) {

            resp.getWriter().println("Delete failed");
            System.err.println(e.getMessage());
        }
    }

}
