package es.unex.dinopedia.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoManagerDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dinosaurio.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_TODOS =
            "CREATE TABLE " + DBContract.Dinosaurio.TABLE_NAME + " (" +
                    DBContract.Dinosaurio._ID + " INTEGER PRIMARY KEY," +
                    DBContract.Dinosaurio.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    DBContract.Dinosaurio.COLUMN_NAME_DIET+ TEXT_TYPE + COMMA_SEP +
                    DBContract.Dinosaurio.COLUMN_NAME_LIVE_IN + TEXT_TYPE + COMMA_SEP +
                    DBContract.Dinosaurio.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    DBContract.Dinosaurio.COLUMN_NAME_SPECIES + TEXT_TYPE + COMMA_SEP +
                    DBContract.Dinosaurio.COLUMN_NAME_PERIOD_NAME + TEXT_TYPE + COMMA_SEP +
                    DBContract.Dinosaurio.COLUMN_NAME_HEIGHT + TEXT_TYPE +
                     " )";

    private static final String SQL_DELETE_TODOS =
            "DROP TABLE IF EXISTS " + DBContract.Dinosaurio.TABLE_NAME;

    public ToDoManagerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TODOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TODOS);
        onCreate(db);
    }
}
