package com.apkmarvel.androidcontentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.apkmarvel.androidcontentprovider.engine.DatabaseHelper;

import java.util.HashMap;

/**
 * Created by johncarlofranco on 01/04/2017.
 */

public class UserContentProvider extends ContentProvider {
    public final String TAG = getClass().getSimpleName();
    /*privider name*/
    static final String PROVIDER_NAME = "com.apkmarvel.androidcontentprovider.UserContentProvider";

    static final String URL = "content://" + PROVIDER_NAME + "/cpuser";
    static final Uri CONTENT_URL = Uri.parse(URL);

    static final String id = "id";
    static final String firstname = "firstname";
    static final String lastname = "lastname";

    static final int uriCode = 1;

    private static HashMap<String, String> values;

    // Used to match uris with Content Providers
    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "cpuser", uriCode);
    }

    /*content provider on create*/
    @Override
    public boolean onCreate() {
        DatabaseHelper.createDatabase(getContext(), new AppDb());
        return true;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.e(TAG,"uri: "+uri.toString());
        UserTable userTable = new UserTable();
        Cursor cursor = userTable.select();
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }
    // Handles requests for the MIME type (Type of Data) of the data at the URI
    @Override
    public String getType(Uri uri) {
        // Used to match uris with Content Providers
        switch (uriMatcher.match(uri)) {
            // vnd.android.cursor.dir/cpcontacts states that we expect multiple pieces of data
            case uriCode:
                return "vnd.android.cursor.dir/cpuser";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.e(TAG,"insert uri: "+uri.toString());
        UserTable userTable = new UserTable();
        long rowID =  userTable.insert(values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URL, rowID);
            /*notifyChange to all observers*/
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowsDeleted = 0;
        // Used to match uris with Content Providers
        switch (uriMatcher.match(uri)) {
            case uriCode:
                UserTable userTable = new UserTable();
                rowsDeleted= userTable.delete(selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        // notifyChange notifies all observers that a row was updated
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }
    // Used to update a row or a selection of rows
    // Returns to number of rows updated
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowsUpdated = 0;
//
//        // Used to match uris with Content Providers
//        switch (uriMatcher.match(uri)) {
//            case uriCode:
//                // Update the row or rows of data
//                rowsUpdated = sqlDB.update(TABLE_NAME, values, selection, selectionArgs);
//                break;
//            default:
//                throw new IllegalArgumentException("Unknown URI " + uri);
//        }
//
//        // getContentResolver provides access to the content model
//        // notifyChange notifies all observers that a row was updated
//        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

}