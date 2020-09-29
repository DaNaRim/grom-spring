package com.lesson3.homework.DAO;

import com.lesson3.homework.model.File;
import com.lesson3.homework.model.Storage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();

                properties.put(Environment.DRIVER, "oracle.jdbc.driver.OracleDriver");
                properties.put(Environment.URL,
                        "jdbc:oracle:thin:@gromcode-lessons.c2nwr4ze1uqa.us-east-2.rds.amazonaws.com:1521:ORCL");
                properties.put(Environment.USER, "main");
                properties.put(Environment.PASS, "PyP2p02rIZ9uyMBpTBwW");

                configuration.setProperties(properties);

                configuration.addAnnotatedClass(Storage.class);
                configuration.addAnnotatedClass(File.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
