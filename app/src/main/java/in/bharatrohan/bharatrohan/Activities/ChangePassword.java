package in.bharatrohan.bharatrohan.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import in.bharatrohan.bharatrohan.Apis.RetrofitClient;
import in.bharatrohan.bharatrohan.CheckInternet;
import in.bharatrohan.bharatrohan.Models.OtpResponse;
import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {

    private EditText editOtp, editPhone, editPass, editConfPass;
    private Button btnOtp, btnChange;
    private TextView tvResend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_change_password);
        new CheckInternet(this).checkConnection();
        init();

        btnOtp.setOnClickListener(v -> {
            String phone = editPhone.getText().toString().trim();
            sendOtp(phone);
        });

        btnChange.setOnClickListener(v -> {
            if (!validateForm()) {
                String phone = editPhone.getText().toString().trim();
                changePass(phone);
            } else {
                validateForm();
            }
        });
    }

    private void init() {

        editOtp = findViewById(R.id.editOtp);
        editPhone = findViewById(R.id.editContact);
        editPhone.setText(new PrefManager(ChangePassword.this).getContact());
        editPass = findViewById(R.id.editPass);
        editConfPass = findViewById(R.id.editConfPass);
        btnOtp = findViewById(R.id.btnOtp);
        btnChange = findViewById(R.id.btnChangePass);
        tvResend = findViewById(R.id.tvResend);

    }


    //use this thing in when we get 200 in response

/*if (getIntent().getStringExtra("activity") != null) {
            if (getIntent().getStringExtra("activity").equals("login")) {
                startActivity(new Intent(this, Login.class));
                finish();
            } else if (getIntent().getStringExtra("activity").equals("main")) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        }*/

    private void changePass(String contact) {

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().changePassReq(contact, editOtp.getText().toString().trim(), editPass.getText().toString().trim());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(ChangePassword.this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
                    new PrefManager(ChangePassword.this).saveLoginDetails("", "");
                    new PrefManager(ChangePassword.this).saveUserDetails("", "", "", "", "", "", "", "", "", "", "", "", "");
                    new PrefManager(ChangePassword.this).saveAvatar("");
                    new PrefManager(ChangePassword.this).saveToken("");
                    new PrefManager(ChangePassword.this).saveFarmerId("");
                    startActivity(new Intent(ChangePassword.this, Login.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void showChnagePassDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ChangePassword.this);
        View view = getLayoutInflater().inflate(R.layout.enter_server_name, null);
        dialogBuilder.setView(view);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.show();

        EditText message = view.findViewById(R.id.editOtp);
        Button btnOk = view.findViewById(R.id.btnOk);

        message.setText(new PrefManager(ChangePassword.this).getContact());

        btnOk.setOnClickListener(view1 -> {
            btnOtp.setVisibility(View.GONE);
            tvResend.setVisibility(View.VISIBLE);

            String strMessage = message.getText().toString().trim();


            if (!strMessage.isEmpty()) {
                sendOtp(strMessage);
                alertDialog.dismiss();
            } else {
                Toast.makeText(ChangePassword.this, "Enter you registered contact", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private boolean validateForm() {


        String phone = editPhone.getText().toString().trim();
        String pass = editPass.getText().toString().trim();
        String conf_pass = editConfPass.getText().toString().trim();
        String otp = editOtp.getText().toString().trim();

        if (phone.isEmpty()) {
            editPhone.setError("Phone no. is required!");
            editPhone.requestFocus();
            return true;
        }

        if (phone.length() < 10) {
            editPhone.setError("Phone No should be at least 10 character Long");
            editPhone.requestFocus();
            return true;
        }

        if (phone.length() > 10) {
            editPhone.setError("Phone No should be of 10 character Long");
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


        if (!conf_pass.equals(pass)) {
            editConfPass.setError("Confirm Password is not same");
            editConfPass.requestFocus();
            return true;
        }

        if (otp.isEmpty()) {
            editOtp.setError("Otp is required");
            editOtp.requestFocus();
            return true;
        }

        return false;
    }

    private void sendOtp(String contact) {
        Call<OtpResponse> call = RetrofitClient.getInstance().getApi().getOtpChangePass(contact);

        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                OtpResponse otpResponse = response.body();
                if (response.code() == 200) {
                    if (otpResponse != null) {
                        btnOtp.setVisibility(View.GONE);
                        tvResend.setVisibility(View.VISIBLE);
                        Toast.makeText(ChangePassword.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 404) {
                    Toast.makeText(ChangePassword.this, "Some error occurred!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    Toast.makeText(ChangePassword.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    new PrefManager(ChangePassword.this).saveLoginDetails("", "");
                    new PrefManager(ChangePassword.this).saveUserDetails("", "", "", "", "", "", "", "", "", "", "", "", "");
                    new PrefManager(ChangePassword.this).saveAvatar("");
                    new PrefManager(ChangePassword.this).saveToken("");
                    new PrefManager(ChangePassword.this).saveFarmerId("");
                    startActivity(new Intent(ChangePassword.this, Login.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                Toast.makeText(ChangePassword.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
