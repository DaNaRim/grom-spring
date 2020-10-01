package com.lesson3_4.homework;

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
@ComponentScan(basePackages = {"com.lesson3_4"})
public class SpringConfig4 {

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
}
