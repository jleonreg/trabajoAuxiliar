package es.unex.dinopedia.roomdb;

import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import es.unex.dinopedia.Model.Dinosaurio;

@Dao
public interface DinosaurioDao {

    @Query("SELECT * FROM Dinosaurio")
    LiveData<List<Dinosaurio>> getAll();

    @Query("SELECT name FROM Dinosaurio")
    LiveData<List<String>> getNombres();

    @Query("SELECT * FROM Dinosaurio WHERE diet=:opcion OR periodName=:opcion")
    LiveData<List<Dinosaurio>> getOpciones(String opcion);

    @Query("SELECT * FROM Dinosaurio WHERE id=:ID")
    Dinosaurio getDinosaurioId(long ID);

    @Query("SELECT * FROM Dinosaurio WHERE name=:nombre")
    Dinosaurio getDinosaurioString(String nombre);

    @Insert (onConflict = IGNORE)
    long insert(Dinosaurio item);

    @Insert (onConflict = IGNORE)
    void insertAll(List<Dinosaurio> dino);

    @Query("DELETE FROM Dinosaurio")
    void deleteAll();

    @Update
    int update(Dinosaurio item);

    @Query("SELECT COUNT (id) FROM Dinosaurio")
    int count();

    @Query("SELECT * FROM Dinosaurio WHERE favorite='1'")
    LiveData<List<Dinosaurio>> getFavorito();

    @Query("UPDATE Dinosaurio SET favorite='0'")
    void quitarFavorite();
}
