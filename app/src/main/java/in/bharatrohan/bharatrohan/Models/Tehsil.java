package in.bharatrohan.bharatrohan.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tehsil {

    @SerializedName("tehsils")
    private List<Tehsils> tehsils;

    public Tehsil(List<Tehsils> tehsils) {
        this.tehsils = tehsils;
    }

    public List<Tehsils> getTehsils() {
        return tehsils;
    }

    public class Tehsils{
        @SerializedName("_id")
        private String id;
        @SerializedName("tehsil_name")
        private String name;


        public Tehsils(String id, String name) {
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
}
