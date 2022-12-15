package es.unex.dinopedia.roomdb;

import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import es.unex.dinopedia.Model.HistorialCombate;

@Dao
public interface HistorialCombateDao {

    @Query("SELECT * FROM HistorialCombate")
    LiveData<List<HistorialCombate>> getAll();

    @Query("SELECT * FROM HistorialCombate")
    List<HistorialCombate> getAllList();

    @Insert (onConflict = IGNORE)
    long insert(HistorialCombate item);

    @Insert (onConflict = IGNORE)
    void insertAll(List<HistorialCombate> hc);

    @Query("DELETE FROM HistorialCombate")
    void deleteAll();

    @Update
    int update(HistorialCombate item);
}
