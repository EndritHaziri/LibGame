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
    List<Publisher> getAllPublisher();

    @Query("SELECT * FROM pub_table WHERE id = :idPub")
    Publisher getPubById(int idPub);

    @Query("SELECT id FROM pub_table WHERE name = :name")
    int getPubId(String name);

    /* ==== UPDATE ==== */
    @Query("UPDATE pub_table SET name = :name WHERE id = :id")
    void update(int id, String name);

    /* ==== DELETE ==== */
    @Query("DELETE FROM pub_table")
    void deleteAll();

    @Query("DELETE FROM pub_table WHERE id = :idPub")
    void deletePublisher(int idPub);
}
