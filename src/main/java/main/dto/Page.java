package main.dto;

import java.util.List;

/**
 * Created by Private on 5/17/2017.
 */
public class Page {

    String type;
    List<String> data;

    public Page (String type, List<String> data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
