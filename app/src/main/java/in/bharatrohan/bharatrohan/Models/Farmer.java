package in.bharatrohan.bharatrohan.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Farmer {
    @SerializedName("farmer_name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("contact")
    private String contact;
    @SerializedName("full_address")
    private String full_address;
    @SerializedName("account_status")
    private Boolean acc_status;
    @SerializedName("dob")
    private String dob;
    @SerializedName("farms")
    private ArrayList<String> farms;
    @SerializedName("address")
    private Address address;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("alt_contact")
    private String alt_contact;
    @SerializedName("isVerified")
    private Boolean isVerified;
    @SerializedName("fe_id")
    private Fe fe;

    //contact _id

    private Response response;

    public Farmer(String name, String email, String contact, String full_address, Boolean acc_status, String dob, ArrayList<String> farms, Address address, String avatar, String alt_contact, Boolean isVerified, Fe fe, Response response) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.full_address = full_address;
        this.acc_status = acc_status;
        this.dob = dob;
        this.farms = farms;
        this.address = address;
        this.avatar = avatar;
        this.alt_contact = alt_contact;
        this.isVerified = isVerified;
        this.fe = fe;
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getAlt_contact() {
        return alt_contact;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getFull_address() {
        return full_address;
    }

    public Boolean getAcc_status() {
        return acc_status;
    }

    public String getDob() {
        return dob;
    }

    public ArrayList<String> getFarms() {
        return farms;
    }

    public Address getAddress() {
        return address;
    }


    public Fe getFe() {
        return fe;
    }

    public class Fe {
        @SerializedName("_id")
        private String id;
        @SerializedName("contact")
        private String contact;


        public Fe(String id, String contact) {
            this.id = id;
            this.contact = contact;
        }


        public String getId() {
            return id;
        }

        public String getContact() {
            return contact;
        }
    }

    public class Address {
        @SerializedName("state")
        private State state;
        @SerializedName("district")
        private District district;
        @SerializedName("tehsil")
        private Tehsil tehsil;
        @SerializedName("block")
        private Block block;
        @SerializedName("village")
        private Village village;

        public Address(State state, District district, Tehsil tehsil, Block block, Village village) {
            this.state = state;
            this.district = district;
            this.tehsil = tehsil;
            this.block = block;
            this.village = village;
        }

        public State getState() {
            return state;
        }

        public District getDistrict() {
            return district;
        }

        public Tehsil getTehsil() {
            return tehsil;
        }

        public Block getBlock() {
            return block;
        }

        public Village getVillage() {
            return village;
        }

        public class State {
            @SerializedName("state_name")
            private String state_name;

            public State(String state_name) {
                this.state_name = state_name;
            }

            public String getState_name() {
                return state_name;
            }
        }

        public class District {
            @SerializedName("district_name")
            private String district_name;

            public District(String district_name) {
                this.district_name = district_name;
            }

            public String getDistrict_name() {
                return district_name;
            }
        }

        public class Tehsil {
            @SerializedName("tehsil_name")
            private String tehsil_name;

            public Tehsil(String tehsil_name) {
                this.tehsil_name = tehsil_name;
            }

            public String getTehsil_name() {
                return tehsil_name;
            }
        }

        public class Block {
            @SerializedName("block_name")
            private String block_name;

            public Block(String block_name) {
                this.block_name = block_name;
            }

            public String getBlock_name() {
                return block_name;
            }
        }

        public class Village {
            @SerializedName("village_name")
            private String village_name;

            public Village(String village_name) {
                this.village_name = village_name;
            }

            public String getVillage_name() {
                return village_name;
            }
        }
    }

    public class Response {
        @SerializedName("token")
        private String token;

        @SerializedName("message")
        private String message;

        @SerializedName("farmerId")
        private String farmerID;

        public Response(String token, String message, String farmerID) {
            this.token = token;
            this.message = message;
            this.farmerID = farmerID;
        }

        public String getToken() {
            return token;
        }

        public String getMessage() {
            return message;
        }

        public String getFarmerID() {
            return farmerID;
        }
    }
}
