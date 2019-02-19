package in.bharatrohan.bharatrohan.Models;

import com.google.gson.annotations.SerializedName;

public class UpdateFarmer {

    @SerializedName("email")
    private String email;
    @SerializedName("farmer_name")
    private String farmer_name;
    @SerializedName("alt_contact")
    private String alt_contact;
    @SerializedName("contact")
    private String contact;
    @SerializedName("dob")
    private String dob;
    @SerializedName("full_address")
    private String fulladdress;




    public UpdateFarmer(String email, String farmer_name, String alt_contact, String contact, String dob, String fulladdress) {
        this.email = email;
        this.farmer_name = farmer_name;
        this.alt_contact = alt_contact;
        this.contact = contact;
        this.dob = dob;
        this.fulladdress = fulladdress;
    }

    public String getEmail() {
        return email;
    }

    public String getFarmer_name() {
        return farmer_name;
    }

    public String getAlt_contact() {
        return alt_contact;
    }

    public String getContact() {
        return contact;
    }

    public String getDob() {
        return dob;
    }

    public String getFulladdress() {
        return fulladdress;
    }

}
