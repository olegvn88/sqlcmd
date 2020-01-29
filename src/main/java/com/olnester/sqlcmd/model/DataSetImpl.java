package com.olnester.sqlcmd.model;

public class DataSetImpl implements DataSet {

    private String name;
    private Object value;

    private int freeIndex = 0;
    public DataSet[] data;

    public DataSetImpl() {
    }

    public DataSetImpl(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Object[] getValues() {
        Object[] result = new Object[freeIndex];
        for (int i = 0; i < freeIndex; i++) {
            result[i] = data[i].getValue();
        }
        return result;
    }

    public String[] getNames() {
        String[] result = new String[freeIndex];
        for (int i = 0; i < freeIndex; i++) {
            result[i] = data[i].getName();
        }
        return result;
    }

    public void put(String id, Object i) {
        data[freeIndex++] = new DataSetImpl(name, value);
    }

    @Override
    public void updateFrom(DataSet newValue) {
        String[] names = newValue.getNames();
        Object[] values = newValue.getValues();
        for (int index = 0; index < names.length; index++) {
            String name = names[index];
            Object value = values[index];
            this.put(name, value);
        }
    }

    @Override
    public Object get(String id) {
        for (int i = 0; i < freeIndex; i++) {
            if (data[i].getName().equals(name)) {
                return data[i].getValue();
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
