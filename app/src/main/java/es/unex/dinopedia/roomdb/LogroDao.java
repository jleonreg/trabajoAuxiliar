package es.unex.dinopedia.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import es.unex.dinopedia.Model.Logro;

@Dao
public interface LogroDao {

    @Query("SELECT * FROM Logro")
    List<Logro> getAll();

    @Query("SELECT * FROM Logro WHERE name=:titulo")
    Logro getLogro(String titulo);

    @Insert
    long insert(Logro item);

    @Query("DELETE FROM Logro")
    void deleteAll();

    @Update
    int update(Logro item);

    @Query("SELECT COUNT (id) FROM Logro")
    int count();

    @Query("SELECT * FROM Logro WHERE checked='1'")
    List<Logro> getCheck();
}
