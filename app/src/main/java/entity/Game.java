package entity;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Game {

    /* ==== DECLARATION ==== */

    private String id;

    private String title;

    private String description;

    private String url_image;

    private String developer_id;

    private String publisher_id;

    private int evaluation_id;

    /* ==== CONSTRUCTOR ==== */

    public Game() {

    }

    /*public Game(String name, String description, String url_image, String developer_id, String publisher_id) {
        this.name = name;
        this.description = description;
        this.url_image = url_image;
        this.developer_id = developer_id;
        this.publisher_id = publisher_id;
    }*/

    /* ==== GETTER AND SETTER ==== */


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeveloper_id() {
        return developer_id;
    }

    public void setDeveloper_id(String developer_id) {
        this.developer_id = developer_id;
    }

    public String getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(String publisher_id) {
        this.publisher_id = publisher_id;
    }

    public int getEvaluation_id() {
        return evaluation_id;
    }

    public void setEvaluation_id(int evaluation_id) {
        this.evaluation_id = evaluation_id;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("description", description);
        result.put("developer_id", developer_id);
        result.put("publisher_id", publisher_id);
        result.put("url_image", url_image);

        return result;
    }
}
