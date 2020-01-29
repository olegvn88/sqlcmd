package com.olnester.sqlcmd.model;

public interface DatabaseManager {
    void connect(String database, String user, String password);

    void clear(String tableName);

    void update(String tableName, int id, DataSet newValue);

    void create(DataSet input);

    String[] getTableNames();

    DataSet[] getTableData(String tableName);
}
