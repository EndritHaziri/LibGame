package dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import entity.Developer;
import entity.Game;

@Dao
public interface DevDao {

    /* ==== INSERT ==== */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Developer game);

    /* ==== GET ==== */
    @Query("SELECT * FROM dev_table ORDER BY name ASC")
    LiveData<List<Developer>> getAllDeveloper();

    /* ==== DELETE ==== */
    @Query("DELETE FROM dev_table")
    void deleteAll();
}
