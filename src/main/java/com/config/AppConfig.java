package com.config;

import com.DAO.FileDAO;
import com.DAO.FileDAOImpl;
import com.DAO.StorageDAO;
import com.DAO.StorageDAOImpl;
import com.controller.FileController;
import com.service.FileService;
import com.service.FileServiceImpl;
import com.service.StorageService;
import com.service.StorageServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com")
public class AppConfig {

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
