package in.bharatrohan.bharatrohan.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class States {

    @SerializedName("_id")
    private String id;
    @SerializedName("state_name")
    private String name;


    public States(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
