package com.olnester.sqlcmd.model;

import static com.olnester.sqlcmd.model.ProjectProperty.*;

public class Demo {
    public static void main(String[] args) {
        JDBCDatabaseManager jdbcDatabaseManager = new JDBCDatabaseManager();
        jdbcDatabaseManager.connect(POSTGRES_DATABASE, POSTGRES_USER, POSTGRES_PASSWORD);
    }
}
