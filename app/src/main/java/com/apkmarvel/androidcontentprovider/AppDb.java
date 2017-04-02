

package com.apkmarvel.androidcontentprovider;


import com.apkmarvel.androidcontentprovider.engine.EngineDatabase;
import com.apkmarvel.androidcontentprovider.table.OrderTable;
import com.apkmarvel.androidcontentprovider.table.UserTable;

public class AppDb extends EngineDatabase {

    public final static String DB_NAME = "vpn_db.db";
    public final static int DB_VERSION = 1;
    public AppDb() {
        super(DB_NAME, DB_VERSION);
        addTable(new UserTable());
        addTable(new OrderTable());
    }
}