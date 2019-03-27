package in.bharatrohan.bharatrohan.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import in.bharatrohan.bharatrohan.Apis.RetrofitClient;
import in.bharatrohan.bharatrohan.CheckInternet;
import in.bharatrohan.bharatrohan.Models.OtpResponse;
import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpBasic extends AppCompatActivity {

    private ImageView next;
    private EditText name1, phone1, alter_phone1, email1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        new CheckInternet(this).checkConnection();

        next = findViewById(R.id.next);
        name1 = findViewById(R.id.name);
        phone1 = findViewById(R.id.phone);
        alter_phone1 = findViewById(R.id.alter_phone);
        email1 = findViewById(R.id.email);


        next.setOnClickListener(v -> {

            if (!validateValues()) {
                sendOtp();
                Intent intent = new Intent(SignUpBasic.this, Otp.class);
                intent.putExtra("name", name1.getText().toString().trim());
                intent.putExtra("phone", phone1.getText().toString().trim());
                intent.putExtra("alter_name", alter_phone1.getText().toString().trim());
                intent.putExtra("email", email1.getText().toString().trim());
                startActivity(intent);
            } else {
                validateValues();
            }

        });
    }

    private boolean validateValues() {


        String name = name1.getText().toString().trim();
        String phone = phone1.getText().toString().trim();
        String alter_phone = alter_phone1.getText().toString().trim();
        String email = email1.getText().toString().trim();

        if (name.isEmpty()) {
            name1.setError("Name is required");
            name1.requestFocus();
            return true;
        }

        if (name.length() < 3) {
            name1.setError("Name length must be at least 3");
            name1.requestFocus();
            return true;
        }

        if (phone.isEmpty()) {
            phone1.setError("Phone is required");
            phone1.requestFocus();
            return true;
        }

        if (phone.length() != 10) {
            phone1.setError("Phone must be of 10 characters long!");
            phone1.requestFocus();
            return true;
        }

        if (!alter_phone.isEmpty() && alter_phone.length() != 10) {
            alter_phone1.setError(" Alter Phone must be of 10 characters long!");
            alter_phone1.requestFocus();
            return true;
        }

        if (email.isEmpty()) {
            email1.setError("Email is required");
            email1.requestFocus();
            return true;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email1.setError("Enter a valid Email!!");
            email1.requestFocus();
            return true;
        }

        return false;
    }

    private void sendOtp() {
        Call<OtpResponse> call = RetrofitClient.getInstance().getApi().getOtp(phone1.getText().toString().trim());

        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                OtpResponse otpResponse = response.body();
                if (response.code() == 200) {
                    if (otpResponse != null) {
                        new PrefManager(SignUpBasic.this).saveOtp(otpResponse.getOtp());
                        Toast.makeText(SignUpBasic.this, otpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 400) {

                    if (otpResponse != null) {
                        Toast.makeText(SignUpBasic.this, "400 " + otpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else if (response.code() == 404) {

                    if (otpResponse != null) {
                        Toast.makeText(SignUpBasic.this, "404 " + otpResponse.getMessage() + "  " + otpResponse.getError(), Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 502) {
                    if (otpResponse != null) {
                        Toast.makeText(SignUpBasic.this, "502 " + otpResponse.getMessage() + "  " + otpResponse.getError(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpBasic.this, "Error Code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                Toast.makeText(SignUpBasic.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
