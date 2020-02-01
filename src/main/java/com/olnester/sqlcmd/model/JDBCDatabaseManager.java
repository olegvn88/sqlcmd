package com.olnester.sqlcmd.model;

import java.sql.*;
import java.util.Arrays;

import static com.olnester.sqlcmd.model.ProjectProperty.POSTGRES_ADDRESS;

public class JDBCDatabaseManager implements DatabaseManager {
    private Connection connection;

    @Override
    public DataSet[] getTableData(String tableName) {
        int size = getSize(tableName);
        DataSet[] result = new DataSet[size];

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int index = 0;
            while (rs.next()) {
                DataSet dataSet = new DataSet();
                result[index++] = dataSet;
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    dataSet.put(resultSetMetaData.getColumnName(i), rs.getObject(i));
                }
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private int getSize(String tableName) {
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
            String tableNames = getNameFormatted(newValue, "%s = ?,");
            String sql = "UPDATE " + tableName + " SET " + tableNames + " WHERE id = ?";
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

    private String getNameFormatted(DataSet input, String format) {
        String names = "";
        for (String name : input.getNames()) {
            names += String.format(format, name);
        }
        names = names.substring(0, names.length() - 1);
        return names;
    }

    private String getValuesFormatted(DataSet input, String format) {
        String values = "";
        for (Object value : input.getValues()) {
            values += String.format(format, value);
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }

    @Override
    public void create(DataSet input) {
        try {
            Statement statement = connection.createStatement();
            String tableNames = getNameFormatted(input, "%s,");
            String values = getValuesFormatted(input, "'%s',");
            statement.executeUpdate("INSERT INTO users (" + tableNames + ") " + "VALUES (" + values + ")");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
