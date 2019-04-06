package in.bharatrohan.bharatrohan.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import in.bharatrohan.bharatrohan.CheckInternet;
import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;

public class RegisterFarmDialog extends AppCompatActivity {

    private Button register_farm, skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_farm_dialog);
        new CheckInternet(this).checkConnection();

        register_farm = findViewById(R.id.register_farm);
        skip = findViewById(R.id.skip);

        register_farm.setOnClickListener(v -> {
            new PrefManager(RegisterFarmDialog.this).saveKmlStatus(true);
            new PrefManager(RegisterFarmDialog.this).saveValueStatus(true);
            new PrefManager(this).saveKmlCreateStatus(false);
            Intent intent = new Intent(RegisterFarmDialog.this, RegisterFarm.class);
            intent.putExtra("phone", getIntent().getStringExtra("phone"));
            startActivity(intent);
        });

        skip.setOnClickListener(v -> startActivity(new Intent(RegisterFarmDialog.this, MainActivity.class)));

    }
}
