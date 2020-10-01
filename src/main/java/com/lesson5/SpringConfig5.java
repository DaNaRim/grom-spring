package com.lesson5;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.lesson3_4"})
public class SpringConfig5 {

    @Bean
    public DAO dao() {
        return new DAO();
    }

    @Bean
    public TestController testController() {
        return new TestController(dao());
    }
}
