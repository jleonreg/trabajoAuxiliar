package es.unex.dinopedia.roomdb;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import es.unex.dinopedia.Model.Dinosaurio;
import es.unex.dinopedia.Model.HistorialCombate;
import es.unex.dinopedia.Model.Logro;
import es.unex.dinopedia.Model.Usuario;

@Database(entities = {Dinosaurio.class, HistorialCombate.class, Logro.class, Usuario.class}, version  =1)
public abstract class DinopediaDatabase extends RoomDatabase {
    private static DinopediaDatabase instance;

    public static DinopediaDatabase getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context, DinopediaDatabase.class, "JURASSICPARK.db").build();
        }
        return instance;
    }

    public abstract DinosaurioDao getDinosaurioDao();
    public abstract HistorialCombateDao getHistorialCombateDao();
    public abstract LogroDao getLogroDao();
    public abstract UsuarioDao getUsuarioDao();
}
