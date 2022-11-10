package es.unex.dinopedia.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.unex.dinopedia.Dinosaurio;

public final class DinosaurioCRUD {

    private final ToDoManagerDbHelper mDbHelper;
    private static DinosaurioCRUD mInstance;

    private DinosaurioCRUD(Context context) {
        mDbHelper = new ToDoManagerDbHelper(context);
    }

    public static DinosaurioCRUD getInstance(Context context){
        if (mInstance == null)
            mInstance = new DinosaurioCRUD(context);

        return mInstance;
    }

    public List<Dinosaurio> getAll(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DBContract.Dinosaurio._ID,
                DBContract.Dinosaurio.COLUMN_NAME_NAME,
                DBContract.Dinosaurio.COLUMN_NAME_DIET,
                DBContract.Dinosaurio.COLUMN_NAME_LIVE_IN,
                DBContract.Dinosaurio.COLUMN_NAME_TYPE,
                DBContract.Dinosaurio.COLUMN_NAME_SPECIES,
                DBContract.Dinosaurio.COLUMN_NAME_PERIOD_NAME,
                DBContract.Dinosaurio.COLUMN_NAME_HEIGHT,
        };

        String selection = null;
        String[] selectionArgs = null;

        String sortOrder = null;

        Cursor cursor = db.query(
                DBContract.Dinosaurio.TABLE_NAME,           // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );


        ArrayList<Dinosaurio> items = new ArrayList<>();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                items.add(getDinosaurioFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    public long insert(Dinosaurio item){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBContract.Dinosaurio.COLUMN_NAME_NAME, item.getName());
        values.put(DBContract.Dinosaurio.COLUMN_NAME_DIET, item.getDiet());
        values.put(DBContract.Dinosaurio.COLUMN_NAME_LIVE_IN, item.getLivedIn());
        values.put(DBContract.Dinosaurio.COLUMN_NAME_TYPE, item.getType());
        values.put(DBContract.Dinosaurio.COLUMN_NAME_SPECIES, item.getSpecies());
        values.put(DBContract.Dinosaurio.COLUMN_NAME_PERIOD_NAME, item.getPeriodName());
        values.put(DBContract.Dinosaurio.COLUMN_NAME_HEIGHT, item.getLengthMeters());


        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DBContract.Dinosaurio.TABLE_NAME, null, values);

        return newRowId;
    }

    public void deleteAll() {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Define 'where' part of query.
        String selection = null;
        // Specify arguments in placeholder order.
        String[] selectionArgs = null;

        // Issue SQL statement.
        db.delete(DBContract.Dinosaurio.TABLE_NAME, selection, selectionArgs);
    }


    public void close(){
        if (mDbHelper!=null) mDbHelper.close();
    }

    public static Dinosaurio getDinosaurioFromCursor(Cursor cursor) {

        @SuppressLint("Range") long ID = cursor.getInt(cursor.getColumnIndex(DBContract.Dinosaurio._ID));
        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DBContract.Dinosaurio.COLUMN_NAME_NAME));
        @SuppressLint("Range") String diet = cursor.getString(cursor.getColumnIndex(DBContract.Dinosaurio.COLUMN_NAME_DIET));
        @SuppressLint("Range") String live_in = cursor.getString(cursor.getColumnIndex(DBContract.Dinosaurio.COLUMN_NAME_LIVE_IN));
        @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(DBContract.Dinosaurio.COLUMN_NAME_TYPE));
        @SuppressLint("Range") String species = cursor.getString(cursor.getColumnIndex(DBContract.Dinosaurio.COLUMN_NAME_SPECIES));
        @SuppressLint("Range") String period = cursor.getString(cursor.getColumnIndex(DBContract.Dinosaurio.COLUMN_NAME_PERIOD_NAME));
        @SuppressLint("Range") String height = cursor.getString(cursor.getColumnIndex(DBContract.Dinosaurio.COLUMN_NAME_HEIGHT));

        Dinosaurio item = new Dinosaurio(ID, name, diet, live_in, type, species, period, height);

        Log.d("DinosaurioCRUD",item.toLog());

        return item;
    }
}
