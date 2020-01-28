package com.olnester.sqlcmd.controller;

import com.olnester.sqlcmd.controller.command.*;
import com.olnester.sqlcmd.model.DatabaseManager;
import com.olnester.sqlcmd.model.JDBCDatabaseManager;
import com.olnester.sqlcmd.view.Console;
import com.olnester.sqlcmd.view.View;

public class Main {
    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager manager = new JDBCDatabaseManager();

        MainController controller = new MainController(view, manager, new Command[]{
                new Connect(manager, view),
                new Help(view),
                new Exit(view),
                new IsConnected(manager, view),
                new Tables(manager, view),
                new Clear(manager, view),
                new Find(manager, view),
                new Unsupported(view)
        });
        controller.run();
    }
}
