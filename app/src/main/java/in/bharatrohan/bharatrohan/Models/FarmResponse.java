package in.bharatrohan.bharatrohan.Models;

import com.google.gson.annotations.SerializedName;

public class FarmResponse {

    @SerializedName("message")
    String message;

    @SerializedName("createdFarm")
    CreatedFarm createdFarm;

    public FarmResponse(String message, CreatedFarm createdFarm) {
        this.message = message;
        this.createdFarm = createdFarm;
    }

    public String getMessage() {
        return message;
    }

    public CreatedFarm getCreatedFarm() {
        return createdFarm;
    }
}
