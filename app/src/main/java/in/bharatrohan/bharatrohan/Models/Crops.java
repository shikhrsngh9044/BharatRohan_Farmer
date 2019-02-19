package in.bharatrohan.bharatrohan.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Crops {

    @SerializedName("data")
    private List<Crop> crops;

    public Crops(List<Crop> crops) {
        this.crops = crops;
    }

    public List<Crop> getCrops() {
        return crops;
    }

    public class Crop {
        @SerializedName("_id")
        private String id;
        @SerializedName("crop_name")
        private String name;

        public Crop(String id, String name) {
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
