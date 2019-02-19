package in.bharatrohan.bharatrohan.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import in.bharatrohan.bharatrohan.Apis.RetrofitClient;
import in.bharatrohan.bharatrohan.Models.LoginFarmer;
import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFarmDialog extends AppCompatActivity {

    private Button register_farm, skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_farm_dialog);

        register_farm = findViewById(R.id.register_farm);
        skip = findViewById(R.id.skip);

        register_farm.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterFarmDialog.this, RegisterFarm.class);
            intent.putExtra("phone", getIntent().getStringExtra("phone"));
            startActivity(intent);
        });

        skip.setOnClickListener(v -> startActivity(new Intent(RegisterFarmDialog.this, MainActivity.class)));

    }

    private void internalLogin(String phone, String pass) {

        LoginFarmer loginFarmer = new LoginFarmer(phone, pass);

        Call<LoginFarmer> call = RetrofitClient
                .getInstance()
                .getApi()
                .loginUser(loginFarmer);

        call.enqueue(new Callback<LoginFarmer>() {
            @Override
            public void onResponse(Call<LoginFarmer> call, Response<LoginFarmer> response) {
                LoginFarmer loginResponse = response.body();

                if (response.code() == 200) {

                    if (loginResponse != null) {

                        new PrefManager(RegisterFarmDialog.this).saveLoginDetails(phone, pass);
                        new PrefManager(RegisterFarmDialog.this).saveToken(loginResponse.getToken());

                        //Toast.makeText(RegisterFarmDialog.this, loginResponse.getToken(), Toast.LENGTH_SHORT).show();
                    }

                    startActivity(new Intent(RegisterFarmDialog.this, MainActivity.class));
                } else if (response.code() == 400) {
                    Toast.makeText(RegisterFarmDialog.this, "Validation Failed. Invalid Credentials", Toast.LENGTH_SHORT).show();
                    //Vaifation failed
                } else if (response.code() == 401) {
                    Toast.makeText(RegisterFarmDialog.this, "User not registered.Please register Yourself", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 500) {
                    Toast.makeText(RegisterFarmDialog.this, "Something went wrong.Please try Again!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginFarmer> call, Throwable t) {

            }
        });
    }
}
