/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.Customer;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * ConnectionUtil
 */
public class ConnectionUtil {
    private Connection connection;
    private String url, user, password;

    public ConnectionUtil() {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader("C:\\Users\\tinma\\Desktop\\Revature Class\\application.properties"));
            this.url = properties.getProperty("url");
            this.user = properties.getProperty("user");
            this.password = properties.getProperty("password");
            this.connection = DriverManager.getConnection(this.url, this.user, this.password);
        } catch (SQLException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public Connection getConnection() {
            System.out.println(this.connection);
        return connection;
    }

    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {

        }
    }
}