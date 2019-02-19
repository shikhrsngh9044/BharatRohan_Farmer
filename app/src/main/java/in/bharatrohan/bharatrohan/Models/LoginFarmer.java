package in.bharatrohan.bharatrohan.Models;

import com.google.gson.annotations.SerializedName;

public class LoginFarmer {

    @SerializedName("contact")
    private String contact;

    @SerializedName("password")
    private String password;

    @SerializedName("message")
    private String message;

    @SerializedName("token")
    private String token;

    @SerializedName("farmerId")
    private String farmerId;



    public LoginFarmer(String contact, String password) {
        this.contact = contact;
        this.password = password;
    }

    public String getFarmerId() {
        return farmerId;
    }

    public String getToken() {
        return token;
    }

    public String getContact() {
        return contact;
    }

    public String getPassword() {
        return password;
    }

    public String getMessage() {
        return message;
    }
}
