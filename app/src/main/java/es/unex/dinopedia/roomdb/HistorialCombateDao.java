package es.unex.dinopedia.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import es.unex.dinopedia.Model.HistorialCombate;

@Dao
public interface HistorialCombateDao {

    @Query("SELECT * FROM HistorialCombate")
    List<HistorialCombate> getAll();

    @Insert
    long insert(HistorialCombate item);

    @Query("DELETE FROM HistorialCombate")
    void deleteAll();

    @Update
    int update(HistorialCombate item);
}
