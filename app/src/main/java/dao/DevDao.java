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
    List<Developer> getAllDeveloper();

    @Query("SELECT * FROM dev_table WHERE id = :idDev")
    Developer getDevById(int idDev);

    @Query("SELECT id FROM dev_table WHERE name = :name")
    int getIdDev(String name);

    /* ==== UPDATE ==== */
    @Query("UPDATE dev_table SET name = :name WHERE id = :id")
    void update(int id, String name);

    /* ==== DELETE ==== */
    @Query("DELETE FROM dev_table")
    void deleteAll();

    @Query("DELETE FROM dev_table WHERE id = :idDev")
    void deleteDeveloper(int idDev);
}
