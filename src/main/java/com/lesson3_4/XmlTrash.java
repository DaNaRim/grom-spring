package com.lesson3_4;

public class XmlTrash {

    /* hibernate.cfg.xml
    <?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <property name="hibernate.connection.driver_class">oracle.jdbc.driver.OracleDriver</property>
        <property name="hibernate.connection.url">
            jdbc:oracle:thin:@gromcode-lessons.c2nwr4ze1uqa.us-east-2.rds.amazonaws.com:1521:ORCL
        </property>
        <property name="hibernate.connection.username">main</property>
        <property name="hibernate.connection.password">PyP2p02rIZ9uyMBpTBwW</property>

        <mapping class="com.lesson2.homework2.model.Item"/>

        <mapping class="com.lesson3.homework.model.Storage"/>
        <mapping class="com.lesson3.homework.model.File"/>
    </session-factory>
</hibernate-configuration>
     */


    /* spring-servlet.xml
    <?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context-4.1.xsd">

    <context:component-scan base-package="com"/>

    <bean id="orderService" class="com.lesson2.OrderService">
        <!--<constructor-arg name="name" value="value"/>-->
    </bean>

    <bean id="orderDAO" class="com.lesson2.OrderDAO"/>

    <bean id="route" class="com.lesson2.homework1.Route"/>
    <bean id="service" class="com.lesson2.homework1.Service"/>
    <bean id="step" class="com.lesson2.homework1.Step"/>

    <bean id="itemDAO" class="com.lesson2.homework2.DAO.ItemDAO"/>
    <bean id="itemService" class="com.lesson2.homework2.service.ItemService"/>

    <bean id="fileDAO" class="com.lesson3.homework.DAO.FileDAOImpl"/>
    <bean id="storageDAO" class="com.lesson3.homework.DAO.StorageDAOImpl"/>
    <bean id="fileService" class="com.lesson3.homework.service.FileServiceImpl"/>
    <bean id="storageService" class="com.lesson3.homework.service.StorageServiceImpl"/>
    <bean id="fileController" class="com.lesson3.homework.controller.FileController"/>

</beans>
     */

    /* web.xml
    <web-app>
        <servlet>
            <servlet-name>spring</servlet-name>
            <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
            <load-on-startup>1</load-on-startup>
        </servlet>

        <servlet-mapping>
            <servlet-name>spring</servlet-name>
            <url-pattern>/</url-pattern>
        </servlet-mapping>
</web-app>
     */
}
