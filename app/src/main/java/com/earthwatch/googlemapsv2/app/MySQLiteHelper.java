package com.earthwatch.googlemapsv2.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String TABLE_MARKERS = "markers";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";


    private static final String[] COLUMNS = {KEY_ID,KEY_TYPE,KEY_TITLE,KEY_DESCRIPTION,KEY_LATITUDE,KEY_LONGITUDE};


    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "MarkerDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_MARKER_TABLE = "CREATE TABLE markers ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "type TEXT, "+
                "title TEXT, "+
                "description TEXT, "+
                "latitude TEXT, " +
                "longitude TEXT)";

        // create books table
        db.execSQL(CREATE_MARKER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");

        // create fresh books table
        this.onCreate(db);
    }

    public void addMARKER(Marker marker){
        //for logging
        Log.d("addMarker", marker.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE,marker.getType());
        values.put(KEY_TITLE, marker.getTitle()); // get title
        values.put(KEY_DESCRIPTION, marker.getDescription()); // get author
        values.put(KEY_LATITUDE, marker.getLatitude()); // get latitude
        values.put(KEY_LONGITUDE, marker.getLongitude()); // get author

        // 3. insert
        db.insert(TABLE_MARKERS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Marker getMarker(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_MARKERS, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit


        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Marker marker = new Marker();
        marker.setId(Integer.parseInt(cursor.getString(0)));
        marker.setType(cursor.getString(1));
        marker.setTitle(cursor.getString(2));
        marker.setDescription(cursor.getString(3));
        marker.setLatitude(cursor.getString(4));
        marker.setLongitude(cursor.getString(5));

        //log
        Log.d("getMarker(" + id + ")", marker.toString());

        // 5. return book
        return marker;
    }


    public List<Marker> getAllMarkers() {
        List<Marker> markers = new LinkedList<Marker>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_MARKERS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Marker marker = null;
        if (cursor.moveToFirst()) {
            do {
                marker = new Marker();
                marker.setId(Integer.parseInt(cursor.getString(0)));
                marker.setType(cursor.getString(1));
                marker.setTitle(cursor.getString(2));
                marker.setDescription(cursor.getString(3));
                marker.setLatitude(cursor.getString(4));
                marker.setLongitude(cursor.getString(5));

                // Add book to books
                markers.add(marker);
            } while (cursor.moveToNext());
        }

        Log.d("getAllMarkers()", markers.toString());

        // return books
        return markers;
    }

    public int updateMarker(Marker marker) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("type", marker.getType());
        values.put("title", marker.getTitle()); // get title
        values.put("description", marker.getDescription()); // get author
        values.put("latitude",marker.getLatitude());
        values.put("longitude", marker.getLongitude());

        // 3. updating row
        int i = db.update(TABLE_MARKERS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(marker.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    public void deleteMarker(Marker marker) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_MARKERS, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(marker.getId()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteMarker", marker.toString());
    }

}
