package com.lesson5;

import com.lesson5.homework.ItemController5;
import com.lesson5.homework.ItemDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.lesson5"})
public class SpringConfig5 {

    @Bean
    public DAO dao() {
        return new DAO();
    }

    @Bean
    public TestController testController() {
        return new TestController(dao());
    }

    @Bean
    public ItemDAO itemDAO() {
        return new ItemDAO();
    }

    @Bean
    public ItemController5 itemController5() {
        return new ItemController5(itemDAO());
    }
}
