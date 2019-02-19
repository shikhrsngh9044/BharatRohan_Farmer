package in.bharatrohan.bharatrohan.Models;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.Header;
import retrofit2.http.Path;

public class KmlModel {



    String farm_id;
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
}
