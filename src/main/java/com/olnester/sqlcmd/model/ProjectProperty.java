package com.olnester.sqlcmd.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProjectProperty {
    public static final String POSTGRES_ADDRESS = ProjectProperty.getProperty("postgres_address");
    public static final String POSTGRES_DATABASE = ProjectProperty.getProperty("postgres_database");
    public static final String POSTGRES_USER = ProjectProperty.getProperty("postgres_user");
    public static final String POSTGRES_PASSWORD = ProjectProperty.getProperty("postgres_password");

    static String getProperty(String name) {
        Properties properties = new Properties();
        try (InputStream input = ProjectProperty.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                return "Sorry, unable to find config.properties";
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return properties.getProperty(name);
    }
}