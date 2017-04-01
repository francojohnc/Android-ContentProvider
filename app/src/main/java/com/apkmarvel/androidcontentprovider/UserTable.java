package com.apkmarvel.androidcontentprovider;

import com.apkmarvel.androidcontentprovider.engine.QueryBuilder;
import com.apkmarvel.androidcontentprovider.engine.Table;

/**
 * Created by johncarlofranco on 01/04/2017.
 */

public class UserTable extends Table {
    static final String TABLE_NAME = "tbl_user";
    @Override
    public String getTableStructure() {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.setTableName(TABLE_NAME);
        queryBuilder.setClass(User.class);
        queryBuilder.setPrimaryKey("id");
        String query = queryBuilder.build();
        return query;
    }
    @Override
    public String getName() {
        return TABLE_NAME;
    }
}
