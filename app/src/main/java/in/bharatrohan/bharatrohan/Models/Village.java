package in.bharatrohan.bharatrohan.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Village {

    @SerializedName("villages")
    private List<Villages> villages;

    public Village(List<Villages> villages) {
        this.villages = villages;
    }

    public List<Villages> getVillages() {
        return villages;
    }

    public class Villages {
        @SerializedName("_id")
        private String id;
        @SerializedName("village_name")
        private String name;


        public Villages(String id, String name) {
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
