package in.bharatrohan.bharatrohan.Models;

import com.google.gson.annotations.SerializedName;

public class Farm {

    @SerializedName("data")
    private Data data;

    public Farm(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public class Data {

        @SerializedName("farm_name")
        private String farm_name;
        @SerializedName("isVerified")
        private Boolean isVerified;
        @SerializedName("map_image")
        private String map_image;
        @SerializedName("location")
        private String location;
        @SerializedName("farm_area")
        private String farm_area;
        @SerializedName("crop_id")
        private Crop crop;

        public Data(String farm_name, Boolean isVerified, String map_image, String location, String farm_area, Crop crop) {
            this.farm_name = farm_name;
            this.isVerified = isVerified;
            this.map_image = map_image;
            this.location = location;
            this.farm_area = farm_area;
            this.crop = crop;
        }

        public String getLocation() {
            return location;
        }

        public String getFarm_area() {
            return farm_area;
        }

        public String getMap_image() {
            return map_image;
        }

        public String getFarm_name() {
            return farm_name;
        }

        public Boolean getVerified() {
            return isVerified;
        }

        public Crop getCrop() {
            return crop;
        }

        public class Crop {
            @SerializedName("crop_name")
            private String crop_name;

            @SerializedName("_id")
            private String crop_id;

            public Crop(String crop_name, String crop_id) {
                this.crop_name = crop_name;
                this.crop_id = crop_id;
            }

            public String getCrop_name() {
                return crop_name;
            }

            public String getCrop_id() {
                return crop_id;
            }
        }
    }
}
