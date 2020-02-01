package com.olnester.sqlcmd;

import com.olnester.sqlcmd.model.DataSet;
import com.olnester.sqlcmd.model.JDBCDatabaseManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.olnester.sqlcmd.model.ProjectProperty.*;
import static org.testng.Assert.assertEquals;

public class JDBCDatabaseManagerTest {
    private JDBCDatabaseManager manager;

    @BeforeClass
    public void setup() {
        manager = new JDBCDatabaseManager();
        manager.connect(
                POSTGRES_DATABASE, POSTGRES_USER, POSTGRES_PASSWORD);
    }

    @Test
    public void testGetAllTableNames() {

        String[] tableNames = manager.getTableNames();
        assertEquals("[users]", Arrays.toString(tableNames));
    }

    @Test
    public void testGetTableData() {
        //given
        manager.clear("users");

        //when
        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "1111");
        manager.create(input);
        //then

        DataSet[] users = manager.getTableData("users");
        assertEquals(1, users.length);

        DataSet user = users[0];
        assertEquals("[id, name, password]", Arrays.toString(user.getNames()));
        assertEquals("[13, Stiven, 1111]", Arrays.toString(user.getValues()));
    }

    @Test
    public void testUpdateValue() {
        DataSet dataSet = new DataSet();
        dataSet.put("password", "pass");
//        dataSet.put("id", 4);
        manager.update("users", 4, dataSet);
        System.out.println(Arrays.toString(manager.getTableData("users")));
    }
}
