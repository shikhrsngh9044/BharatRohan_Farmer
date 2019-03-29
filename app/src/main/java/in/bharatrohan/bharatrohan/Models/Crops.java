package in.bharatrohan.bharatrohan.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Crops {

    @SerializedName("data")
    private Data data;

    public Crops(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public class Data {

        @SerializedName("docs")
        private List<Crop> cropList;


        public Data(List<Crop> cropList) {
            this.cropList = cropList;
        }

        public List<Crop> getCropList() {
            return cropList;
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


}
