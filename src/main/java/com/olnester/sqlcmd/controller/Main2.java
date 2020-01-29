package com.olnester.sqlcmd.controller;

import java.sql.*;
import java.util.Properties;
import java.util.Random;

public class Main2 {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://192.168.1.7/demodb";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "admin");
        props.setProperty("ssl", "false");
        Connection conn = DriverManager.getConnection(url, props);

        //insert
        String sql = "insert into users" + "(name, password)" +
                "values ('Sergey', '56555')";
        insert(conn, sql);

        //select
        select(conn);

        //delete
        String sql1 = "delete from users where id > 9 and id <=100";
        delete(conn, sql1);

        //update
        String sql2 = "update users set password = ? where name='Sergey'";
        update(conn, sql2);
    }

    private static void update(Connection conn, String sql2) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sql2);
        String temp = "pass" + new Random().nextInt();
        ps.setString(1, temp);
        ps.execute();
        select(conn);
        conn.close();
    }

    private static Statement delete(Connection conn, String sql1) throws SQLException {
        Statement statement;
        statement = conn.createStatement();
        statement.executeUpdate(sql1);
        statement.close();
        return statement;
    }

    private static void insert(Connection conn, String sql) throws SQLException {
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
    }

    private static void select(Connection conn) throws SQLException {
        String sql1 = "select * from users";
        select(conn, sql1);
        return;
    }

    private static void select(Connection conn, String sql1) throws SQLException {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql1);
        while (rs.next()) {
            System.out.println("id:" + rs.getString("id"));
            System.out.println("name:" + rs.getString("name"));
            System.out.println("password:" + rs.getString("password"));
            System.out.println("-----------");
        }
        rs.close();
        st.close();
    }
}
