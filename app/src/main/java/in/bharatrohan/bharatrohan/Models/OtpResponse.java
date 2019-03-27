package in.bharatrohan.bharatrohan.Models;

import com.google.gson.annotations.SerializedName;

public class OtpResponse {
    @SerializedName("message")
    String message;

    @SerializedName("otp")
    String otp;

    @SerializedName("error")
    String error;

    @SerializedName("data")
    String data;

    public OtpResponse(String message, String otp, String error, String data) {
        this.message = message;
        this.otp = otp;
        this.error = error;
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public String getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getOtp() {
        return otp;
    }
}
