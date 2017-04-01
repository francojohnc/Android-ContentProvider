package com.apkmarvel.androidcontentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public final String TAG = getClass().getSimpleName();

    // The URL used to target the content provider
    ContentResolver resolver;
    static final Uri CONTENT_URL = Uri.parse("content://com.apkmarvel.androidcontentprovider.UserContentProvider/cpuser");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resolver = getContentResolver();
    }

    public void click(View view) {
        // Get the name supplied
        // Stores a key value pair
        ContentValues values = new ContentValues();
        values.put(UserContentProvider.firstname, "John");
        values.put(UserContentProvider.lastname, "franco");
        // Provides access to other applications Content Providers
        Uri uri = getContentResolver().insert(UserContentProvider.CONTENT_URL, values);
        Log.e(TAG,"uri "+uri.toString());
        Toast.makeText(getBaseContext(), "New Contact Added", Toast.LENGTH_LONG) .show();
    }
    public void getContacts(){
        // Projection contains the columns we want
        String[] projection = new String[]{"id", "firstname"};
        // Pass the URL, projection and I'll cover the other options below
        Cursor cursor = resolver.query(CONTENT_URL, projection, null, null, null);
        String contactList = "";
        // Cycle through and display every row of data
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("firstname"));
                contactList = contactList + id + " : " + name + "\n";
            }while (cursor.moveToNext());
        }
        Log.e(TAG,"contactList "+contactList);
    }
    public void getdata(View view) {
        getContacts();
    }
    public void deleteContact(View view) {
        String idToDelete = "1";
        long idDeleted = resolver.delete(CONTENT_URL,"id = ? ", new String[]{idToDelete});
        getContacts();
    }

}
