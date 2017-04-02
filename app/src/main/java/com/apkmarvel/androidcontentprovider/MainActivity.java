package com.apkmarvel.androidcontentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.apkmarvel.androidcontentprovider.table.OrderTable;
import com.apkmarvel.androidcontentprovider.table.UserTable;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public final String TAG = getClass().getSimpleName();

    // The URL used to target the content provider
    ContentResolver resolver;
    static final Uri CONTENT_URL_USER = Uri.parse("content://com.apkmarvel.androidcontentprovider.UserContentProvider/cpuser");
    static final Uri CONTENT_URL_ORDER = Uri.parse("content://com.apkmarvel.androidcontentprovider.UserContentProvider/cporder");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resolver = getContentResolver();
        getSupportLoaderManager().initLoader(UserContentProvider.URI_CODE_ORDER, null, this);
        getSupportLoaderManager().initLoader(UserContentProvider.URI_CODE_USER, null, this);
    }

    public void addUser(View view) {
        ContentValues values = new ContentValues();
        values.put(UserTable.FIRSTNAME, "John");
        values.put(UserTable.LASTNAME, "franco");
        // Provides access to other applications Content Providers
        Uri uri = getContentResolver().insert(UserContentProvider.CONTENT_URL_USER, values);
        Log.e(TAG, "uri " + uri.toString());
    }

    public void getuser() {
        // Projection contains the columns we want
        String[] projection = new String[]{"id", "firstname"};
        // Pass the URL, projection and I'll cover the other options below
        Cursor cursor = resolver.query(CONTENT_URL_USER, projection, null, null, null);
        String contactList = "";
        // Cycle through and display every row of data
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("firstname"));
                contactList = contactList + id + " : " + name + "\n";
            } while (cursor.moveToNext());
        }
        Log.e(TAG, "contactList " + contactList);
    }

    public void getUser(View view) {
        getuser();
    }

    public void deleteUser(View view) {
        String idToDelete = "1";
        long idDeleted = resolver.delete(CONTENT_URL_USER, "id = ? ", new String[]{idToDelete});
        getuser();
    }
    /*listner you can do this on separate app*/

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.e(TAG, "Loader");
        String[] projection;
        switch (id) {
            case UserContentProvider.URI_CODE_USER:
                projection = new String[]{"id", "firstname"};
                return new CursorLoader(this, CONTENT_URL_USER, projection, null, null, null);
            case UserContentProvider.URI_CODE_ORDER:
                projection = new String[]{"id", "name", "price"};
                return new CursorLoader(this, CONTENT_URL_ORDER, projection, null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.e(TAG, "onLoadFinished " + loader.getId());
        String listData = "";
        switch (loader.getId()) {
            case UserContentProvider.URI_CODE_USER:
                while (data.moveToNext()) {
                    String id = data.getString(data.getColumnIndex("id"));
                    String name = data.getString(data.getColumnIndex("firstname"));
                    listData = listData + id + " : " + name + "\n";
                }
                break;
            case UserContentProvider.URI_CODE_ORDER:
                while (data.moveToNext()) {
                    String id = data.getString(data.getColumnIndex("id"));
                    String name = data.getString(data.getColumnIndex("name"));
                    listData = listData + id + " : " + name + "\n";
                }
                break;
        }
        Log.e(TAG, "listData " + listData);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.e(TAG, "onLoaderReset");
    }

    public void addOrder(View view) {
        ContentValues values = new ContentValues();
        values.put(OrderTable.PRICE, "20");
        values.put(OrderTable.NAME, "burger");
        // Provides access to other applications Content Providers
        Uri uri = getContentResolver().insert(UserContentProvider.CONTENT_URL_ORDER, values);
        Log.e(TAG, "uri " + uri.toString());
    }

    public void getOrder(View view) {
        getOrder();
    }

    public void deleteOrder(View view) {
        String idToDelete = "1";
        long idDeleted = resolver.delete(CONTENT_URL_ORDER, "id = ? ", new String[]{idToDelete});
        getOrder();
    }

    public void getOrder() {
        // Projection contains the columns we want
        String[] projection = new String[]{"id", "name", "price"};
        // Pass the URL, projection and I'll cover the other options below
        Cursor cursor = resolver.query(CONTENT_URL_ORDER, projection, null, null, null);
        String list = "";
        // Cycle through and display every row of data
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                list = list + id + " : " + name + "\n" + price + "\n";
            } while (cursor.moveToNext());
        }
        Log.e(TAG, "list " + list);
    }
}
