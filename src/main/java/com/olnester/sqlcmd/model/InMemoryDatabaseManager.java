package com.olnester.sqlcmd.model;

import java.util.Arrays;

public class InMemoryDatabaseManager implements DatabaseManager {

    public static final String TABLE_NAME = "user";
    private DataSet[] data = new DataSetImpl[1000];
    private int freeIndex = 0;

    @Override
    public void connect(String database, String user, String password) {

    }

    @Override
    public void clear(String tableName) {
        data = new DataSet[1000];
        freeIndex = 0;
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        for (int index = 0; index < freeIndex; index++) {
            if (data[index].get("id").equals(id)) {
                data[index].updateFrom(newValue);
            }
        }
    }

    @Override
    public void create(DataSet input) {
        data[freeIndex] = input;
        freeIndex++;
    }

    @Override
    public String[] getTableNames() {
        return new String[]{TABLE_NAME};
    }

    @Override
    public DataSet[] getTableData(String tableName) {
        return Arrays.copyOf(data, freeIndex);
    }
}
