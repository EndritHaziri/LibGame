package dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import entity.Game;
import entity.Publisher;

@Dao
public interface PubDao {

    /* ==== INSERT ==== */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Publisher pub);

    /* ==== GET ==== */
    @Query("SELECT * FROM pub_table ORDER BY name ASC")
    LiveData<List<Publisher>> getAllPublisher();

    /* ==== DELETE ==== */
    @Query("DELETE FROM pub_table")
    void deleteAll();
}
