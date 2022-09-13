package com.example.demomysql.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DBConnectionConfig {

    @Bean
    public Connection createConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbl01_person", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
