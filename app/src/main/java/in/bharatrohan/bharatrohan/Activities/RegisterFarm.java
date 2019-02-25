package in.bharatrohan.bharatrohan.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import in.bharatrohan.bharatrohan.Apis.RetrofitClient;
import in.bharatrohan.bharatrohan.Models.Crops;
import in.bharatrohan.bharatrohan.Models.District;
import in.bharatrohan.bharatrohan.Models.FarmID;
import in.bharatrohan.bharatrohan.Models.FarmResponse;
import in.bharatrohan.bharatrohan.Models.States;
import in.bharatrohan.bharatrohan.Models.UserFarmer;
import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFarm extends AppCompatActivity {

    private ImageView open_map;
    private EditText landName, landArea, landLocation;
    private Button register;
    FarmResponse farmResponse;
    private FarmID farmID = new FarmID();
    private ProgressBar mProgressBar;
    private MaterialSpinner cropsSpinner;
    private ArrayList<String> c_idList, c_nameList;
    private ArrayAdapter<String> adapter1;
    private String crop, cropId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_farm);

        mProgressBar = findViewById(R.id.progressBar);

        register = findViewById(R.id.register);

        open_map = findViewById(R.id.open_map);

        cropsSpinner = findViewById(R.id.crops);

        c_idList = new ArrayList<>();
        c_nameList = new ArrayList<>();

        c_nameList.add(0, "-SELECT CROP-");
        c_idList.add(0, "-SELECT CROP-");


        landName = findViewById(R.id.land_name);
        landArea = findViewById(R.id.land_area);
        landLocation = findViewById(R.id.land_location);

        if (getIntent().getStringExtra("activity") != null) {
            if (getIntent().getStringExtra("activity").equals("edit")) {
                landArea.setText(new PrefManager(RegisterFarm.this).getFarmArea());
                landName.setText(new PrefManager(RegisterFarm.this).getFarmName());
                landLocation.setText(new PrefManager(RegisterFarm.this).getFarmLocation());

                if (!new PrefManager(RegisterFarm.this).getKmlStatus() && new PrefManager(RegisterFarm.this).getValueStatus()) {
                    open_map.setEnabled(false);
                    open_map.setClickable(false);
                    Toast.makeText(this, "You will be able to update values only", Toast.LENGTH_SHORT).show();
                }

                if (new PrefManager(RegisterFarm.this).getKmlStatus() && !new PrefManager(RegisterFarm.this).getValueStatus()) {
                    landArea.setEnabled(false);
                    landName.setEnabled(false);
                    landLocation.setEnabled(false);
                    landArea.setClickable(false);
                    landName.setClickable(false);
                    landLocation.setClickable(false);
                    Toast.makeText(this, "You will be able to update Map only", Toast.LENGTH_SHORT).show();
                }
            }
        }

        initCropSpinner();

        cropsSpinner.setOnItemSelectedListener((view, position, id, item) -> {
            showProgress();
            crop = c_nameList.get(position);
            cropId = c_idList.get(position);
            hideProgress();
        });

        open_map.setOnClickListener(v -> {

            if (getIntent().getStringExtra("activity") != null) {
                if (getIntent().getStringExtra("activity").equals("edit")) {
                    if (!new PrefManager(RegisterFarm.this).getKmlStatus() && new PrefManager(RegisterFarm.this).getValueStatus()) {
                        Toast.makeText(this, "Cannot not update map.As u have choose to update values only", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Intent intent = new Intent(RegisterFarm.this, MapsActivity.class);
                intent.putExtra("landName", landName.getText().toString().trim());
                intent.putExtra("phone", getIntent().getStringExtra("phone"));
                startActivity(intent);
            }

        });

        register.setOnClickListener(v ->
        {

            if (getIntent().getStringExtra("activity") != null) {
                if (getIntent().getStringExtra("activity").equals("edit")) {
                    if (new PrefManager(RegisterFarm.this).getKmlStatus() && new PrefManager(RegisterFarm.this).getValueStatus()) {
                        updateFarm();
                        updateKml();
                        startActivity(new Intent(RegisterFarm.this, MainActivity.class));
                        finish();
                    }

                    if (!new PrefManager(RegisterFarm.this).getKmlStatus() && new PrefManager(RegisterFarm.this).getValueStatus()) {
                        updateFarm();
                        startActivity(new Intent(RegisterFarm.this, MainActivity.class));
                        finish();
                    }

                    if (new PrefManager(RegisterFarm.this).getKmlStatus() && !new PrefManager(RegisterFarm.this).getValueStatus()) {
                        updateKml();
                        startActivity(new Intent(RegisterFarm.this, MainActivity.class));
                        finish();
                    }
                }
            } else {
                if (new PrefManager(RegisterFarm.this).getKmlStatus() && new PrefManager(RegisterFarm.this).getValueStatus()) {
                    if (new PrefManager(RegisterFarm.this).getKmlCreateStatus()) {
                        createFarm();
                    } else {
                        Toast.makeText(RegisterFarm.this, "First mark your land on map!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
    }


    private void createFarm() {
        if (!validateValues()) {

            showProgress();

            Call<FarmResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .createFarm(new PrefManager(this).getToken(), landName.getText().toString().trim(), landLocation.getText().toString().trim(), landArea.getText().toString().trim() + "acres", cropId, new PrefManager(this).getFarmerId());

            call.enqueue(new Callback<FarmResponse>() {
                @Override
                public void onResponse(Call<FarmResponse> call, Response<FarmResponse> response) {
                    hideProgress();
                    farmResponse = response.body();

                    if (response.code() == 201) {

                        if (farmResponse != null) {
                            new PrefManager(RegisterFarm.this).saveFarm(1, farmResponse.getCreatedFarm().getFarm_id(), farmResponse.getCreatedFarm().getFarm_name(), farmResponse.getCreatedFarm().getLocation(), farmResponse.getCreatedFarm().getFarm_area(), farmResponse.getCreatedFarm().getCrop_id(), farmResponse.getCreatedFarm().getFarmer_id());
                            uploadKml();
                        }

                    } else if (response.code() == 401) {
                        hideProgress();
                        Toast.makeText(RegisterFarm.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<FarmResponse> call, Throwable t) {
                    hideProgress();
                }
            });
        } else {
            validateValues();
        }
    }

    private void updateFarm() {
        if (!validateValues()) {

            showProgress();

            Call<ResponseBody> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .updateFarm(new PrefManager(this).getToken(), new PrefManager(this).getTFarmId(), landName.getText().toString().trim(), landLocation.getText().toString().trim(), landArea.getText().toString().trim(), cropId);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    hideProgress();

                    if (response.code() == 200) {
                        Toast.makeText(RegisterFarm.this, "Farm Updated", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {
                        hideProgress();
                        Toast.makeText(RegisterFarm.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    hideProgress();
                }
            });
        } else {
            validateValues();
        }
    }

    private void uploadKml() {
        showProgress();
        File kml = new File(this.getFilesDir() + "/LandKmls/" + "Land_" + new PrefManager(RegisterFarm.this).getPhone() + ".kml");
        File image = new File(this.getFilesDir(), "/LandImage/" + "Land_" + new PrefManager(RegisterFarm.this).getPhone() + ".jpg");

        RequestBody requestBodyImage = RequestBody.create(MediaType.parse("image/*"), image);
        MultipartBody.Part uploadImage = MultipartBody.Part.createFormData("file", image.getName(), requestBodyImage);

        RequestBody requestBodykml = RequestBody.create(MediaType.parse("application/vnd.google-earth.kml"), kml);
        MultipartBody.Part kmlUpload = MultipartBody.Part.createFormData("file", kml.getName(), requestBodykml);
        //RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), kml.getName());


        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .uploadKml(new PrefManager(RegisterFarm.this).getToken(), new PrefManager(RegisterFarm.this).getFarmerFarmId(), kmlUpload, uploadImage);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                hideProgress();
                if (response.code() == 201) {
                    Toast.makeText(RegisterFarm.this, "Farm Created and Files Uploaded", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterFarm.this, WelcomeActivity.class));
                    finish();

                } else if (response.code() == 409) {
                    Toast.makeText(RegisterFarm.this, "Farm Created but Files not Uploaded", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideProgress();
                Toast.makeText(RegisterFarm.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void updateKml() {
        showProgress();
        File kml = new File(this.getFilesDir() + "/LandKmls/" + "Land_" + new PrefManager(RegisterFarm.this).getPhone() + ".kml");
        File image = new File(this.getFilesDir(), "/LandImage/" + "Land_" + new PrefManager(RegisterFarm.this).getPhone() + ".jpg");

        RequestBody requestBodyImage = RequestBody.create(MediaType.parse("image/*"), image);
        MultipartBody.Part uploadImage = MultipartBody.Part.createFormData("file", image.getName(), requestBodyImage);

        RequestBody requestBodykml = RequestBody.create(MediaType.parse("application/vnd.google-earth.kml"), kml);
        MultipartBody.Part kmlUpload = MultipartBody.Part.createFormData("file", kml.getName(), requestBodykml);
        //RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), kml.getName());

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .uploadKml(new PrefManager(RegisterFarm.this).getToken(), new PrefManager(RegisterFarm.this).getTFarmId(), kmlUpload, uploadImage);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                hideProgress();
                if (response.code() == 201) {
                    Toast.makeText(RegisterFarm.this, "Farm Updated Successfully!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 409) {
                    Toast.makeText(RegisterFarm.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideProgress();
                Toast.makeText(RegisterFarm.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initCropSpinner() {
        Call<Crops> call = RetrofitClient
                .getInstance()
                .getApi()
                .cropList();

        call.enqueue(new Callback<Crops>() {
            @Override
            public void onResponse(Call<Crops> call, Response<Crops> response) {

                Crops cropsList = response.body();


                if (cropsList != null) {

                    for (Crops.Crop s : cropsList.getCrops()) {
                        c_idList.add(s.getId());
                        c_nameList.add(s.getName());
                    }


                    adapter1 = new ArrayAdapter<>(RegisterFarm.this, android.R.layout.simple_spinner_item, c_nameList);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cropsSpinner.setAdapter(adapter1);
                }

            }

            @Override
            public void onFailure(Call<Crops> call, Throwable t) {

            }
        });
    }

    private void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        cropsSpinner.setEnabled(false);
        open_map.setEnabled(false);
        landName.setEnabled(false);
        landArea.setEnabled(false);
        landLocation.setEnabled(false);
        register.setEnabled(false);
    }

    private void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
        cropsSpinner.setEnabled(true);
        open_map.setEnabled(true);
        landName.setEnabled(true);
        landArea.setEnabled(true);
        landLocation.setEnabled(true);
        register.setEnabled(true);
    }

    private boolean validateValues() {


        String landname = landName.getText().toString().trim();
        String landarea = landArea.getText().toString().trim();
        String landlocation = landLocation.getText().toString().trim();

        if (landname.isEmpty()) {
            landName.setError("Land Name is required!");
            landName.requestFocus();
            return true;
        }

        if (landarea.isEmpty()) {
            landArea.setError("Land Area is required!");
            landArea.requestFocus();
            return true;
        }

        if (landlocation.isEmpty()) {
            landLocation.setError("Land Location is required!");
            landLocation.requestFocus();
            return true;
        }

        return false;
    }

}
