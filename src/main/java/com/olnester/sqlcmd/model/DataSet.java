package com.olnester.sqlcmd.model;

public interface DataSet {
    Object[] getValues();

    String[] getNames();

    void put(String id, Object i);

    void updateFrom(DataSet newValue);

    Object get(String id);

    String getName();

    Object getValue();
}
