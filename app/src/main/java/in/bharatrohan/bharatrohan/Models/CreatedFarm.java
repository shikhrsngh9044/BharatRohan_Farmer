package in.bharatrohan.bharatrohan.Models;

import com.google.gson.annotations.SerializedName;

public class CreatedFarm {
    @SerializedName("_id")
    String _id;
    @SerializedName("farm_name")
    String farm_name;

    @SerializedName("location")
    String location;
    @SerializedName("farm_area")
    String farm_area;

    @SerializedName("crop_id")
    String crop_id;
    @SerializedName("farmer_id")
    String farmer_id;


    public CreatedFarm(String farm_id, String farm_name, String location, String farm_area, String crop_id, String farmer_id) {
        this._id = farm_id;
        this.farm_name = farm_name;
        this.location = location;
        this.farm_area = farm_area;
        this.crop_id = crop_id;
        this.farmer_id = farmer_id;
    }


    public String getFarm_id() {
        return _id;
    }

    public String getFarm_name() {
        return farm_name;
    }

    public String getLocation() {
        return location;
    }

    public String getFarm_area() {
        return farm_area;
    }

    public String getCrop_id() {
        return crop_id;
    }

    public String getFarmer_id() {
        return farmer_id;
    }
}
