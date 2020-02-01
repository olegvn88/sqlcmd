package com.olnester.sqlcmd.model;

import java.util.Arrays;

import static com.olnester.sqlcmd.model.ProjectProperty.*;

public class Demo {
    public static void main(String[] args) {
        JDBCDatabaseManager jdbcDatabaseManager = new JDBCDatabaseManager();
        jdbcDatabaseManager.connect(POSTGRES_DATABASE, POSTGRES_USER, POSTGRES_PASSWORD);
        String[] tables = jdbcDatabaseManager.getTableNames();
        printTablesList(tables);
//        System.out.println(jdbcDatabaseManager.getTableData("users")[0].getValues());
    }

    private static void printTablesList(String[] result) {
        Arrays.stream(result).forEach(x -> System.out.println(x));
    }
}
