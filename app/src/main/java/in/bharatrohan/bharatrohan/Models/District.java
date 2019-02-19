package in.bharatrohan.bharatrohan.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class District {

    @SerializedName("districts")
    List<Districts> districts;

    public District(List<Districts> districts) {
        this.districts = districts;
    }

    public List<Districts> getDistricts() {
        return districts;
    }

    public class Districts {
        @SerializedName("_id")
        private String id;
        @SerializedName("district_name")
        private String name;


        public Districts(String id, String name) {
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
