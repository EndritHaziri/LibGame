package entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import firebase.db.Firebase;

@Entity(tableName = "game_table")
public class Game {

    /* ==== DECLARATION ==== */

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "description")
    private String description;

    @NonNull
    @ColumnInfo(name = "url_image")
    private String url_image;

    @NonNull
    @ColumnInfo(name = "developer_id")
    private int developer_id;

    @NonNull
    @ColumnInfo(name = "publisher_id")
    private int publisher_id;

    @NonNull
    @ColumnInfo(name = "evaluation_id")
    private int evaluation_id;

    /* ==== CONSTRUCTOR ==== */

    public Game(String name, String description, String url_image, int developer_id, int publisher_id) {
        this.name = name;
        this.description = description;
        this.url_image = url_image;
        this.developer_id = developer_id;
        this.publisher_id = publisher_id;
    }

    /* ==== GETTER AND SETTER ==== */

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public int getDeveloper_id() {
        return developer_id;
    }

    public void setDeveloper_id(@NonNull int developer_id) {
        this.developer_id = developer_id;
    }

    @NonNull
    public int getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(@NonNull int publisher_id) {
        this.publisher_id = publisher_id;
    }

    @NonNull
    public int getEvaluation_id() {
        return evaluation_id;
    }

    public void setEvaluation_id(@NonNull int evaluation_id) {
        this.evaluation_id = evaluation_id;
    }

    @NonNull
    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(@NonNull String url_image) {
        this.url_image = url_image;
    }
}
