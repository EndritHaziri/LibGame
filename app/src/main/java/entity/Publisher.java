package entity;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Publisher {

    /* ==== DECLARATION ==== */

    @NonNull
    private int id;

    private String name;

    /* ==== CONSTRUCTOR ==== */

    public Publisher(String name) {
        this.name = name;
    }

    /* ==== GETTER AND SETTER ==== */
    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);

        return result;
    }
}
