package in.bharatrohan.bharatrohan.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import in.bharatrohan.bharatrohan.R;

public class Otp extends AppCompatActivity {

    private ImageView next;
    private TextView tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        next = findViewById(R.id.next);
        tvPhone = findViewById(R.id.phone_no);


        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String alter_phone = getIntent().getStringExtra("alter_phone");
        String email = getIntent().getStringExtra("email");

        String p = "+91" + phone;

        tvPhone.setText(p);

        next.setOnClickListener(v -> {
            Intent intent = new Intent(Otp.this, SignUpFull.class);
            intent.putExtra("name", name);
            intent.putExtra("phone", phone);
            intent.putExtra("alter_name", alter_phone);
            intent.putExtra("email", email);
            startActivity(intent);
        });
    }
}
