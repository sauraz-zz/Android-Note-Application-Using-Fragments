package beer.com.mynotes.Model;

import java.io.Serializable;

/**
 * Created by 1405473 on 18-04-2017.
 */

public class Note implements Serializable {
    private String date;
    private String text;
    private long id;

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public long getId() {
        return this.id;
    }

}