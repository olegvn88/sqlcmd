package com.olnester.sqlcmd.model;

import java.util.Arrays;

public class InMemoryDatabaseManager { //implements DatabaseManager {

    public static final String TABLE_NAME = "user";
    private DataSet[] data = new DataSet[1000];
    private int freeIndex = 0;

    public void connect(String database, String user, String password) {

    }

    public void clear(String tableName) {
        data = new DataSet[100];
        freeIndex = 0;
    }


    public void update(String tableName, int id, DataSet newValue) {
        for (int index = 0; index < freeIndex; index++) {
//            if (data[index].get("id").equals(id)) {
//                data[index].updateFrom(newValue);
//            }
        }
    }


    public void create(DataSet input) {
        data[freeIndex] = input;
        freeIndex++;
    }


    public String[] getTableNames() {
        return new String[]{TABLE_NAME};
    }

    public DataSet[] getTableData(String tableName) {
        return (DataSet[]) Arrays.copyOf(data, freeIndex);
    }
}
