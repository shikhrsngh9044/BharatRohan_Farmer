package in.bharatrohan.bharatrohan.Activities;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;

public class ChangePassword extends AppCompatActivity {

    private ImageView headImage;
    private EditText editOtp, editPhone, editPass, editConfPass;
    private Button btnOtp, btnChange;
    private TextView tvResend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_change_password);
        init();

        btnOtp.setOnClickListener(v -> showChnagePassDialog());

        btnChange.setOnClickListener(v -> {
            if (!validateForm()) {
                changePass();
            } else {
                validateForm();
            }
        });
    }

    private void init() {
        headImage = findViewById(R.id.head_image);
        Picasso.get().load(R.drawable.my_farm_header).fit().centerCrop().into(headImage);

        editOtp = findViewById(R.id.editOtp);
        editPhone = findViewById(R.id.editContact);
        editPhone.setText(new PrefManager(ChangePassword.this).getPhone());
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

    private void changePass() {

    }

    private void showChnagePassDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ChangePassword.this);
        View view = getLayoutInflater().inflate(R.layout.enter_server_name, null);
        dialogBuilder.setView(view);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.show();

        EditText message = view.findViewById(R.id.editEmail);
        Button btnOk = view.findViewById(R.id.btnOk);

        message.setText(new PrefManager(ChangePassword.this).getPhone());

        btnOk.setOnClickListener(view1 -> {
            btnOtp.setVisibility(View.GONE);
            tvResend.setVisibility(View.VISIBLE);

            String strMessage = message.getText().toString().trim();


            if (strMessage.isEmpty()) {
                message.setError("Otp is required!");
                message.requestFocus();
                return;
            }

            Toast.makeText(ChangePassword.this, "Otp Sent Successfully!", Toast.LENGTH_SHORT).show();
            // new PrefManager(ChangePassword.this).saveServerName(strMessage);
            alertDialog.dismiss();
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
}
