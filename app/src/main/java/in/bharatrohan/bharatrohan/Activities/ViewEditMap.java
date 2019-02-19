package in.bharatrohan.bharatrohan.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import in.bharatrohan.bharatrohan.Apis.RetrofitClient;
import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewEditMap extends AppCompatActivity {

    private Button verify;
    private ImageView mapImage;
    private TextView farm_name, farm_area, crop_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_map);

        verify = findViewById(R.id.verifyFarm);
        mapImage = findViewById(R.id.mapImage);
        farm_area = findViewById(R.id.tvLandArea);
        farm_name = findViewById(R.id.tvLandName);
        crop_name = findViewById(R.id.tvCropName);

        Picasso.get().load("http://bfe82c68.ngrok.io/" + new PrefManager(this).getFarmImage()).into(mapImage);

        farm_name.setText(new PrefManager(this).getFarmName());
        farm_area.setText(new PrefManager(this).getFarmArea());
        crop_name.setText(new PrefManager(this).getCropName());

        verify.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterFarm.class);
            intent.putExtra("activity", "edit");
            startActivity(intent);
        });
    }

}
