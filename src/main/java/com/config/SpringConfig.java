package com.config;

import com.lesson2.OrderDAO;
import com.lesson2.OrderService;
import com.lesson2.homework1.Route;
import com.lesson2.homework1.Service;
import com.lesson2.homework1.Step;
import com.lesson2.homework2.DAO.ItemDAO;
import com.lesson2.homework2.service.ItemService;
import com.lesson3_4.homework.DAO.FileDAO;
import com.lesson3_4.homework.DAO.FileDAOImpl;
import com.lesson3_4.homework.DAO.StorageDAO;
import com.lesson3_4.homework.DAO.StorageDAOImpl;
import com.lesson3_4.homework.controller.FileController;
import com.lesson3_4.homework.service.FileService;
import com.lesson3_4.homework.service.FileServiceImpl;
import com.lesson3_4.homework.service.StorageService;
import com.lesson3_4.homework.service.StorageServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("com")
public class SpringConfig {

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

//

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
