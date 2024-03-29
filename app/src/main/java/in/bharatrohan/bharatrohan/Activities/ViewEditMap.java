package in.bharatrohan.bharatrohan.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import in.bharatrohan.bharatrohan.CheckInternet;
import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;

public class ViewEditMap extends AppCompatActivity {

    private Button verify;
    private ImageView mapImage;
    private TextView farm_name, farm_area, crop_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_map);
        new CheckInternet(this).checkConnection();

        verify = findViewById(R.id.verifyFarm);
        mapImage = findViewById(R.id.mapImage);
        farm_area = findViewById(R.id.tvLandArea);
        farm_name = findViewById(R.id.tvLandName);
        crop_name = findViewById(R.id.tvCropName);


        if (getIntent().getStringExtra("verification").equals("true")) {
            verify.setVisibility(View.GONE);
            findViewById(R.id.textView55).setVisibility(View.VISIBLE);
        }

        if (!new PrefManager(this).getFarmImage().equals("")) {
            Picasso.get().load("http://br.bharatrohan.in/" + new PrefManager(this).getFarmImage()).fit().centerCrop().networkPolicy(NetworkPolicy.OFFLINE).into(mapImage, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load("http://br.bharatrohan.in/" + new PrefManager(ViewEditMap.this).getFarmImage()).fit().centerCrop().into(mapImage);
                }
            });
        } else {
            Toast.makeText(ViewEditMap.this, "No Farm Image", Toast.LENGTH_SHORT).show();
        }

        farm_name.setText(new PrefManager(this).getFarmName());
        farm_area.setText(new PrefManager(this).getFarmArea());
        crop_name.setText(new PrefManager(this).getCropName());

        verify.setOnClickListener(v -> {
            showDialog();
        });
    }

    private void showDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ViewEditMap.this);
        View view = getLayoutInflater().inflate(R.layout.farm_edit_dialog, null);
        dialogBuilder.setView(view);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.show();

        Button map = view.findViewById(R.id.btnEditMap);
        Button value = view.findViewById(R.id.btnEditValues);
        Button btnOk = view.findViewById(R.id.btnBoth);

        btnOk.setOnClickListener(view1 -> {
            new PrefManager(ViewEditMap.this).saveKmlStatus(true);
            new PrefManager(ViewEditMap.this).saveValueStatus(true);
            new PrefManager(ViewEditMap.this).saveKmlCreateStatus(false);
            Intent intent = new Intent(this, RegisterFarm.class);
            intent.putExtra("activity", "edit");
            startActivity(intent);
            alertDialog.dismiss();
        });

        map.setOnClickListener(v -> {
            new PrefManager(ViewEditMap.this).saveKmlStatus(true);
            new PrefManager(ViewEditMap.this).saveValueStatus(false);
            Intent intent = new Intent(this, RegisterFarm.class);
            intent.putExtra("activity", "edit");
            startActivity(intent);
            alertDialog.dismiss();
        });

        value.setOnClickListener(v -> {
            new PrefManager(ViewEditMap.this).saveKmlStatus(false);
            new PrefManager(ViewEditMap.this).saveValueStatus(true);
            Intent intent = new Intent(this, RegisterFarm.class);
            intent.putExtra("activity", "edit");
            startActivity(intent);
            alertDialog.dismiss();
        });
    }

}
