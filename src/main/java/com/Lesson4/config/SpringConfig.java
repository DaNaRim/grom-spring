package com.Lesson4.config;

import com.lesson2.OrderDAO;
import com.lesson2.OrderService;
import com.lesson2.homework1.Route;
import com.lesson2.homework1.Service;
import com.lesson2.homework1.Step;
import com.lesson2.homework2.DAO.ItemDAO;
import com.lesson2.homework2.service.ItemService;
import com.lesson3.homework.DAO.FileDAO;
import com.lesson3.homework.DAO.FileDAOImpl;
import com.lesson3.homework.DAO.StorageDAO;
import com.lesson3.homework.DAO.StorageDAOImpl;
import com.lesson3.homework.controller.FileController;
import com.lesson3.homework.service.FileService;
import com.lesson3.homework.service.FileServiceImpl;
import com.lesson3.homework.service.StorageService;
import com.lesson3.homework.service.StorageServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("com")
public class SpringConfig {

    //4
    @Bean
    public com.Lesson4.DAO.FileDAO fileDAO2() {
        return new com.Lesson4.DAO.FileDAOImpl();
    }

    @Bean
    public com.Lesson4.DAO.StorageDAO storageDAO2() {
        return new com.Lesson4.DAO.StorageDAOImpl();
    }

    @Bean
    public com.Lesson4.service.FileService fileService2() {
        return new com.Lesson4.service.FileServiceImpl(fileDAO2());
    }

    @Bean
    public com.Lesson4.service.StorageService storageService2() {
        return new com.Lesson4.service.StorageServiceImpl(storageDAO2());
    }

    @Bean
    public com.Lesson4.controller.FileController fileController2() {
        return new com.Lesson4.controller.FileController(fileService2(), storageService2());
    }


    //3
    @Bean
    public FileDAO fileDAO() {
        return new FileDAOImpl();
    }

    @Bean
    public StorageDAO storageDAO() {
        return new StorageDAOImpl();
    }

    @Bean
    public FileService fileService() {
        return new FileServiceImpl(fileDAO());
    }

    @Bean
    public StorageService storageService() {
        return new StorageServiceImpl(storageDAO());
    }

    @Bean
    public FileController fileController() {
        return new FileController(fileService(), storageService());
    }

//2

    @Bean
    public OrderService orderService() {
        return new OrderService();
    }

    @Bean
    public OrderDAO orderDAO() {
        return new OrderDAO();
    }

    @Bean
    public Route route() {
        return new Route();
    }

    @Bean
    public Service service() {
        return new Service();
    }

    @Bean
    public Step step() {
        return new Step();
    }

    @Bean
    public ItemDAO itemDAO() {
        return new ItemDAO();
    }

    @Bean
    public ItemService itemService() {
        return new ItemService();
    }

}
