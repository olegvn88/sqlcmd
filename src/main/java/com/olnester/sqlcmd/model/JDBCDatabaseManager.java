package com.olnester.sqlcmd.model;

import java.sql.*;
import java.util.Arrays;

import static com.olnester.sqlcmd.model.ProjectProperty.POSTGRES_ADDRESS;

public class JDBCDatabaseManager implements DatabaseManager {
    private Connection connection;

    @Override
    public DataSet[] getTableData(String tableName) {
        DataSetImpl[] dataSet = new DataSetImpl[]{};
        try {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
            if (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSet;
    }

    public int getSize(String tableName) {
        int size = 0;
        try {
        Statement statement = connection.createStatement();
        ResultSet rsCount = statement.executeQuery("SELECT COUNT(*) FROM " + tableName);
            rsCount.next();
            size = rsCount.getInt(1);
            rsCount.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return size;
    }

    public String[] getTableNames() {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT table_name FROM information_schema.tables" +
                    " WHERE table_schema = 'public' ORDER BY table_name;");
            String[] tables = new String[100];
            int index = 0;
            while (rs.next()) {
                tables[index++] = rs.getString("table_name");
            }
            tables = Arrays.copyOf(tables, index, String[].class);
            rs.close();
            statement.close();
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    @Override
    public void connect(String database, String user, String password) {
        try {
            String url = "jdbc:postgresql://" + POSTGRES_ADDRESS + "/" + database;
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(String.format("Can't make connection for database : %s user: %s", database, user));
            e.printStackTrace();
            connection = null;
        }
    }

    @Override
    public void clear(String tableName) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM " + tableName);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        try {
            String tableNames = getNameFormated(newValue, "%s = ?,");
            String sql = "UPDATE " + tableName + " SET " + tableNames + " WHERE id > 5 and id < 10";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int index = 1;
            for (Object value : newValue.getValues()) {
                preparedStatement.setObject(index, value);
                index++;
            }
            preparedStatement.setInt(index, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getNameFormated(DataSet newValue, String format) {
        String string = "";
        for (String name : newValue.getNames()) {
            string += String.format(format, name);
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }

    @Override
    public void create(DataSet input) {
        try {
            Statement statement = connection.createStatement();
            String tableNames = getNameFormated(input, "%s,");
            String values = getValuesFormated(input, "'%s'");
            statement.executeUpdate("INSERT INTO user (" + tableNames + ") " + "VALUES (" + values + ")");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getValuesFormated(DataSet input, String format) {
        String values = "";
        for (Object value : input.getValues()) {
            values += String.format(format, value);
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }
}
