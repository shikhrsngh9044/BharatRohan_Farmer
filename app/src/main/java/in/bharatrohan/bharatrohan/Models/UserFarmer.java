package in.bharatrohan.bharatrohan.Models;


import com.google.gson.annotations.SerializedName;

public class UserFarmer {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String pass;

    @SerializedName("farmer_name")
    private String f_name;

    @SerializedName("contact")
    private String contact;

    @SerializedName("alt_contact")
    private String alt_contact;

    @SerializedName("dob")
    private String dob;

    @SerializedName("address")
    private Addres address;

    @SerializedName("full_address")
    private String full_address;

    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Data data;

    @SerializedName("token")
    private String token;

    @SerializedName("fe_id")
    private String feId;

    public UserFarmer(String email, String pass, String f_name, String contact, String alt_contact, String dob, Addres address, String full_address) {
        this.email = email;
        this.pass = pass;
        this.f_name = f_name;
        this.contact = contact;
        this.alt_contact = alt_contact;
        this.dob = dob;
        this.address = address;
        this.full_address = full_address;
    }

    public String getFeId() {
        return feId;
    }

    public Data getData() {
        return data;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getF_name() {
        return f_name;
    }

    public String getContact() {
        return contact;
    }

    public String getAlt_contact() {
        return alt_contact;
    }

    public String getFull_address() {
        return full_address;
    }

    public String getDob() {
        return dob;
    }

    public String getToken() {
        return token;
    }

    public Addres getAddress() {
        return address;
    }

    public static class Addres {

        @SerializedName("state")
        private String state;

        @SerializedName("district")
        private String district;

        @SerializedName("tehsil")
        private String tehsil;

        @SerializedName("block")
        private String block;

        @SerializedName("village")
        private String village;


        public Addres(String state, String district, String tehsil, String block, String village) {
            this.state = state;
            this.district = district;
            this.tehsil = tehsil;
            this.block = block;
            this.village = village;
        }
    }


    public static class Data {
        @SerializedName("_id")
        private String id;

        public String getId() {
            return id;
        }

        public Data(String id) {
            this.id = id;
        }
    }

    public UserFarmer(String message, Data data, String token) {
        this.message = message;
        this.data = data;
        this.token = token;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
