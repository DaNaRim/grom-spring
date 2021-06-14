package com.config;

import com.DAO.*;
import com.controller.FlightController;
import com.controller.PassengerController;
import com.controller.PlaneController;
import com.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com"})
@EnableWebMvc
public class AppConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@gromcode-lessons.c2nwr4ze1uqa.us-east-2.rds.amazonaws.com:1521:ORCL");
        dataSource.setUsername("main");
        dataSource.setPassword("PyP2p02rIZ9uyMBpTBwW");

        return dataSource;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public FlightDAO flightDAO() {
        return new FlightDAOImpl();
    }

    @Bean
    public FlightService flightService() {
        return new FlightServiceImpl(flightDAO(), planeService(), passengerService());
    }

    @Bean
    public FlightController flightController() {
        return new FlightController(flightService());
    }

    @Bean
    public PassengerDAO passengerDAO() {
        return new PassengerDAOImpl();
    }

    @Bean
    public PassengerService passengerService() {
        return new PassengerServiceImpl(passengerDAO());
    }

    @Bean
    public PassengerController passengerController() {
        return new PassengerController(passengerService());
    }

    @Bean
    public PlaneDAO planeDAO() {
        return new PlaneDAOImpl();
    }

    @Bean
    public PlaneService planeService() {
        return new PlaneServiceImpl(planeDAO());
    }

    @Bean
    public PlaneController planeController() {
        return new PlaneController(planeService());
    }
}
