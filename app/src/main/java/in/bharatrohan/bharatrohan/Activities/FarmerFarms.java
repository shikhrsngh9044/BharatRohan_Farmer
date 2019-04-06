package in.bharatrohan.bharatrohan.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.bharatrohan.bharatrohan.Apis.RetrofitClient;
import in.bharatrohan.bharatrohan.LandRecyclerAdapter;
import in.bharatrohan.bharatrohan.Models.Farmer;
import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;
import retrofit2.Call;
import retrofit2.Response;

public class FarmerFarms extends AppCompatActivity {


    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_farmer_farms);
        init();
    }

    private void init() {
        ImageView headImage = findViewById(R.id.imageView8);
        Picasso.get().load(R.drawable.my_farm_header).fit().centerCrop().into(headImage);
        recyclerView = findViewById(R.id.recycler);
        getFarmCount();
    }


    private void getFarmCount() {
        Call<Farmer> call = RetrofitClient.getInstance().getApi().getFarmerDetail(new PrefManager(FarmerFarms.this).getToken(), new PrefManager(this).getFarmerId());

        call.enqueue(new retrofit2.Callback<Farmer>() {
            @Override
            public void onResponse(Call<Farmer> call, Response<Farmer> response) {
                Farmer farmer = response.body();
                if (response.code() == 200) {
                    if (farmer != null) {
                        //new PrefManager(FarmerFarms.this).saveFarmCount(farmer.getFarms().size());
                        generateLandList(farmer.getFarms());
                    } else {
                        Toast.makeText(FarmerFarms.this, "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 401) {
                    Toast.makeText(FarmerFarms.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    new PrefManager(FarmerFarms.this).saveLoginDetails("", "");
                    new PrefManager(FarmerFarms.this).saveUserDetails("", "", "", "", "", "", "", "", "", "", "", "", "");
                    new PrefManager(FarmerFarms.this).saveAvatar("");
                    new PrefManager(FarmerFarms.this).saveToken("");
                    new PrefManager(FarmerFarms.this).saveFarmerId("");
                    startActivity(new Intent(FarmerFarms.this, Login.class));
                    finish();
                } else if (response.code() == 400) {
                    Toast.makeText(FarmerFarms.this, "Bad Request!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 409) {
                    Toast.makeText(FarmerFarms.this, "Conflict Occurred!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 500) {
                    Toast.makeText(FarmerFarms.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Farmer> call, Throwable t) {
                Toast.makeText(FarmerFarms.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void generateLandList(List<Farmer.FarmerFarm> allSolArrayList) {

        LandRecyclerAdapter adapter = new LandRecyclerAdapter(FarmerFarms.this, allSolArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FarmerFarms.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
