package in.bharatrohan.bharatrohan;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    Context context;

    public PrefManager(Context context) {
        this.context = context;
    }

    public void savelaunchCount(int count) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("AppLaunch", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Launch", count);
        editor.apply();
    }

    public int getlaunchCount() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("AppLaunch", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("Launch", 0);
    }

    public void saveUserLanguage(String lang) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Language", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("MyLang", lang);
        editor.apply();
    }

    public String getUserLanguage() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Language", Context.MODE_PRIVATE);
        return sharedPreferences.getString("MyLang", "en");
    }

    public void saveLoginDetails(String phone, String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Phone", phone);
        editor.putString("Password", password);
        editor.apply();
    }

    public void saveUserDetails(String state, String district, String tehsil, String block, String village, String email, String dob, String name, String contact, String fullAddress, String alt_contact,String fe_id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("State", state);
        editor.putString("District", district);
        editor.putString("Tehsil", tehsil);
        editor.putString("Block", block);
        editor.putString("Village", village);
        editor.putString("Email", email);
        editor.putString("Dob", dob);
        editor.putString("Name", name);
        editor.putString("Contact", contact);
        editor.putString("FullAddress", fullAddress);
        editor.putString("AltContact", alt_contact);
        editor.putString("FeId", fe_id);
        editor.apply();
    }

    public String getFeId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("FeId", "");
    }

    public String getState() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("State", "");
    }

    public String getDistrict() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("District", "");
    }

    public String getTehsil() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Tehsil", "");
    }

    public String getBlock() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Block", "");
    }

    public String getVillage() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Village", "");
    }

    public String getEmail() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Email", "");
    }

    public String getDob() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Dob", "");
    }

    public String getName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Name", "");
    }

    public String getContact() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Contact", "");
    }

    public void saveAvatar(String avatar) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmerAvatar", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Avatar", avatar);
        editor.apply();
    }

    public String getAvatar() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmerAvatar", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Avatar", "");
    }

    public String getFullAddress() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("FullAddress", "");
    }

    public String getAltContact() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("AltContact", "");
    }


    public void saveFarmerVerifyStatus(Boolean status) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmerVerifyStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Status", status);
        editor.apply();
    }


    public Boolean getFarmerVerifyStatus() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmerVerifyStatus", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("Status", false);
    }


    public void saveFarmVerifyStatus(Boolean status) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmVerifyStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Status", status);
        editor.apply();
    }


    public Boolean getFarmVerifyStatus() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmVerifyStatus", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("Status", false);
    }

    public void saveToken(String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Tokens", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Token", token);
        editor.apply();
    }

    public String getPhone() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Phone", "");
    }

    public String getPassword() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Password", "");
    }

    public String getToken() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Tokens", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Token", "");
    }

    public void saveFarm(int farmNum, String farm_id, String farm_name, String location, String farm_area, String crop_id, String farmer_id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Farm", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("farm_id", farm_id);
        editor.putInt("FarmNum", farmNum);
        editor.putString("farm_name", farm_name);
        editor.putString("location", location);
        editor.putString("farm_area", farm_area);
        editor.putString("crop_id", crop_id);
        editor.putString("farmer_id", farmer_id);
        editor.apply();
    }

    public String getFarmerFarmId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Farm", Context.MODE_PRIVATE);
        return sharedPreferences.getString("farm_id", "");
    }


    public void saveFarmCount(int farm_count) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmDetail", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("FarmCount", farm_count);
        editor.apply();
    }


    public int getFarmerFarmCount() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmDetail", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("FarmCount", 0);
    }


    public void saveFarmerId(String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmerId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Id", id);
        editor.apply();
    }


    public String getFarmerId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmerId", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Id", "");
    }


    public void saveFarmId(String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Id", id);
        editor.apply();
    }


    public String getFarmId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmId", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Id", "");
    }


    public void saveFarmNo(int farm_count) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmNo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("No", farm_count);
        editor.apply();
    }


    public int getFarmerFarmNo() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmNo", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("No", 0);
    }

    public void saveFarmStatus(Boolean status) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Status", status);
        editor.apply();
    }

    public Boolean getFarmStatus() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FarmStatus", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("Status", false);
    }

    public void saveTempFarm(String farm_name, String location, String farm_area, String farmImage, String crop_name, String farm_id, String farmer_id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("TempFarm", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("farm_name", farm_name);
        editor.putString("location", location);
        editor.putString("farm_area", farm_area);
        editor.putString("crop_name", crop_name);
        editor.putString("farmer_id", farmer_id);
        editor.putString("farm_id", farm_id);
        editor.putString("farm_image", farmImage);
        editor.apply();
    }

    public String getFarmImage() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("TempFarm", Context.MODE_PRIVATE);
        return sharedPreferences.getString("farm_image", "");
    }

    public String getFarmName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("TempFarm", Context.MODE_PRIVATE);
        return sharedPreferences.getString("farm_name", "");
    }

    public String getFarmLocation() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("TempFarm", Context.MODE_PRIVATE);
        return sharedPreferences.getString("location", "");
    }

    public String getFarmArea() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("TempFarm", Context.MODE_PRIVATE);
        return sharedPreferences.getString("farm_area", "");
    }

    public String getCropName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("TempFarm", Context.MODE_PRIVATE);
        return sharedPreferences.getString("crop_name", "");
    }

    public String getTFarmerId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("TempFarm", Context.MODE_PRIVATE);
        return sharedPreferences.getString("farmer_id", "");
    }

    public String getTFarmId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("TempFarm", Context.MODE_PRIVATE);
        return sharedPreferences.getString("farm_id", "");
    }

    public void saveServerName(String serverName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Server", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", serverName);
        editor.apply();
    }


    public String getServerName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Server", Context.MODE_PRIVATE);
        return sharedPreferences.getString("name", "");
    }


    public void saveKmlStatus(Boolean status) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("KmlStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Status", status);
        editor.apply();
    }


    public Boolean getKmlStatus() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("KmlStatus", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("Status", false);
    }

    public void saveValueStatus(Boolean status) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ValueStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Status", status);
        editor.apply();
    }


    public Boolean getValueStatus() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ValueStatus", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("Status", false);
    }


    public void saveKmlCreateStatus(Boolean status) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("KmlCreateStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Status", status);
        editor.apply();
    }


    public Boolean getKmlCreateStatus() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("KmlCreateStatus", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("Status", false);
    }
}
