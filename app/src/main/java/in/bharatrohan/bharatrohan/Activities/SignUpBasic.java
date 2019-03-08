package in.bharatrohan.bharatrohan.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageView;

import in.bharatrohan.bharatrohan.CheckInternet;
import in.bharatrohan.bharatrohan.R;

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
}
