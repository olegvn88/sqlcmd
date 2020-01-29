package com.olnester.sqlcmd;

import com.olnester.sqlcmd.model.DataSet;
import com.olnester.sqlcmd.model.DataSetImpl;
import com.olnester.sqlcmd.model.JDBCDatabaseManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;

public class JDBCDatabaseManagerTest {
    private JDBCDatabaseManager manager;

    @BeforeClass
    public void setup() {
        manager = new JDBCDatabaseManager();
        manager.connect("demodb", "postgres", "admin");
    }

    @Test
    public void testGetAllTableNames() {
        String[] tableNames = manager.getTableNames();
        assertEquals("[user]", Arrays.toString(tableNames));
    }

    @Test
    public void testGetTableData() {
        //given
        manager.clear("user");

        //when
        DataSet input = new DataSetImpl();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "1111");
        manager.create(input);
        //then

        DataSet[] users = manager.getTableData("user");
        assertEquals(1, users.length);

        DataSet user = users[0];
        assertEquals("[name, password, id]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven, 1111, i3]", Arrays.toString(user.getValues()));
    }
}
