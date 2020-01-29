package com.olnester.sqlcmd.model;

public class APp {
    public static void main(String[] args) {
        JDBCDatabaseManager jdbcDatabaseManager = new JDBCDatabaseManager();
        jdbcDatabaseManager.connect("demodb", "postgres", "admin");
    }
}
