package es.unex.dinopedia.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.unex.dinopedia.Dinosaurio;

@Database(entities = {Dinosaurio.class}, version  =1)
public abstract class DinosaurioDatabase extends RoomDatabase {
    private static DinosaurioDatabase instance;

    public static DinosaurioDatabase getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context, DinosaurioDatabase.class, "JURASSICPARK.db").build();
        }
        return instance;
    }

    public abstract DinosaurioDao getDao();
}
