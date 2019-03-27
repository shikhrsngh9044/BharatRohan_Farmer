package in.bharatrohan.bharatrohan.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import in.bharatrohan.bharatrohan.Apis.RetrofitClient;
import in.bharatrohan.bharatrohan.CheckInternet;
import in.bharatrohan.bharatrohan.Models.Farmer;
import in.bharatrohan.bharatrohan.Models.LoginFarmer;
import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText editPhone, editPass;
    private Button login;
    private TextView signUpScreen;
    private ProgressBar progressBar;
    private TextView tvForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLocale(new PrefManager(Login.this).getUserLanguage());
        setContentView(R.layout.activity_login);
        new CheckInternet(this).checkConnection();

        progressBar = findViewById(R.id.progressBar);
        editPhone = findViewById(R.id.phone);
        editPass = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signUpScreen = findViewById(R.id.signUpScreen);
        tvForgot = findViewById(R.id.tvForget);

        if (!(new PrefManager(this).getFarmerId().equals(""))) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        login.setOnClickListener(v -> {
            if (!validateForm()) {
                UserLogin();
            } else {
                validateForm();
            }
        });

        tvForgot.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChangePassword.class);
            intent.putExtra("activity", "login");
            startActivity(intent);
        });


        signUpScreen.setOnClickListener(v -> startActivity(new Intent(Login.this, SignUpBasic.class)));
    }


    private void UserLogin() {

        String phone = editPhone.getText().toString().trim();
        String pass = editPass.getText().toString().trim();

        LoginFarmer loginFarmer = new LoginFarmer(phone, pass);

        showProgress();

        Call<LoginFarmer> call = RetrofitClient
                .getInstance()
                .getApi()
                .loginUser(loginFarmer);

        call.enqueue(new Callback<LoginFarmer>() {
            @Override
            public void onResponse(Call<LoginFarmer> call, Response<LoginFarmer> response) {

                hideProgress();
                LoginFarmer loginResponse = response.body();

                if (response.isSuccessful()) {

                    if (response.code() == 200) {

                        if (loginResponse != null) {

                            new PrefManager(Login.this).saveLoginDetails(phone, pass);
                            new PrefManager(Login.this).saveToken("Bearer " + loginResponse.getToken());
                            new PrefManager(Login.this).saveFarmerId(loginResponse.getFarmerId());
                            saveFarmerDetails();
                            Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                } else if (response.code() == 400) {
                    Toast.makeText(Login.this, "Validation Failed. Invalid Credentials", Toast.LENGTH_SHORT).show();
                    //Vaifation failed
                } else if (response.code() == 401) {
                    Toast.makeText(Login.this, "User not registered.Please register Yourself", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 500) {
                    Toast.makeText(Login.this, "Something went wrong.Please try Again!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginFarmer> call, Throwable t) {
                hideProgress();
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    private boolean validateForm() {


        String phone = editPhone.getText().toString().trim();
        String pass = editPass.getText().toString().trim();

        if (phone.isEmpty()) {
            editPhone.setError("Phone no. is required!");
            editPhone.requestFocus();
            return true;
        }

        if (phone.length() != 10) {
            editPhone.setError("Phone must be 10 character Long");
            editPhone.requestFocus();
            return true;
        }

        if (pass.isEmpty()) {
            editPass.setError("Password is required");
            editPass.requestFocus();
            return true;
        }

        if (pass.length() < 4) {
            editPass.setError("Password should be at least 4 character Long");
            editPass.requestFocus();
            return true;
        }

        return false;
    }


    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        editPhone.setEnabled(false);
        editPass.setEnabled(false);
        login.setEnabled(false);
        signUpScreen.setEnabled(false);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
        editPhone.setEnabled(true);
        editPass.setEnabled(true);
        login.setEnabled(true);
        signUpScreen.setEnabled(true);
    }

    private void saveFarmerDetails() {
        Call<Farmer> call = RetrofitClient.getInstance().getApi().getFarmerDetail(new PrefManager(this).getToken(), new PrefManager(this).getFarmerId());

        call.enqueue(new Callback<Farmer>() {
            @Override
            public void onResponse(Call<Farmer> call, Response<Farmer> response) {
                hideProgress();
                Farmer farmer = response.body();

                if (response.code() == 200) {

                    if (farmer != null) {
                        new PrefManager(Login.this).saveUserDetails(farmer.getAddress().getState().getState_name(), farmer.getAddress().getDistrict().getDistrict_name(), farmer.getAddress().getTehsil().getTehsil_name(), farmer.getAddress().getBlock().getBlock_name(), farmer.getAddress().getVillage().getVillage_name(), farmer.getEmail(), farmer.getDob(), farmer.getName(), farmer.getContact(), farmer.getFull_address(), farmer.getAlt_contact(), farmer.getFe().getId(), farmer.getFe().getContact());
                        new PrefManager(Login.this).saveAvatar(farmer.getAvatar());


                        if (farmer.getAcc_status()) {
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        } else {
                            startActivity(new Intent(Login.this, UnSplash.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(Login.this, "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 400) {
                    Toast.makeText(Login.this, "Error: Required values are missing!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 409) {
                    Toast.makeText(Login.this, "Conflict Occurred!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 500) {
                    Toast.makeText(Login.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Farmer> call, Throwable t) {
                hideProgress();
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
}
