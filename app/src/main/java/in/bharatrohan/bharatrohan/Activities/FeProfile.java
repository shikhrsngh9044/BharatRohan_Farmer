package in.bharatrohan.bharatrohan.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import in.bharatrohan.bharatrohan.Apis.RetrofitClient;
import in.bharatrohan.bharatrohan.Models.FeDetails;
import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeProfile extends AppCompatActivity {

    private TextView tvName, tvPhone, tvEmail, tvAddress, tvState, tvDistrict, tvTehsil, tvBlock, tvCall;
    private ImageView profilePic;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fe_profile);
        init();
        getFeDetails();

        tvCall.setOnClickListener(v -> {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:" + tvPhone.getText().toString().trim()));
            startActivity(dialIntent);
        });
    }

    private void init() {
        progressBar = findViewById(R.id.progressBar);
        tvName = findViewById(R.id.tvUavName);
        tvPhone = findViewById(R.id.tvUavPhone);
        tvEmail = findViewById(R.id.tvUavEmail);
        tvAddress = findViewById(R.id.tvUavAddress);
        tvState = findViewById(R.id.tvState);
        tvDistrict = findViewById(R.id.tvDistrict);
        tvTehsil = findViewById(R.id.tvTehsil);
        tvBlock = findViewById(R.id.tvBlock);
        tvCall = findViewById(R.id.tvCall);
    }

    private void getFeDetails() {

        showProgress();
        Call<FeDetails> call = RetrofitClient.getInstance().getApi().getFeDetail(new PrefManager(this).getToken(), new PrefManager(this).getFeId());

        call.enqueue(new Callback<FeDetails>() {
            @Override
            public void onResponse(Call<FeDetails> call, Response<FeDetails> response) {
                hideProgress();
                FeDetails details = response.body();

                if (response.code() == 200) {
                    if (details != null) {
                        tvName.setText(details.getName());
                        tvPhone.setText(details.getContact());
                        tvEmail.setText(details.getEmail());
                        tvAddress.setText(details.getAddress());
                        tvState.setText(details.getJobLocation().getState().getState_name());
                        tvDistrict.setText(details.getJobLocation().getDistrict().getDistrict_name());
                        tvTehsil.setText(details.getJobLocation().getTehsil().getTehsil_name());
                        tvBlock.setText(details.getJobLocation().getBlock().getBlock_name());
                    }
                } else if (response.code() == 401) {
                    startActivity(new Intent(FeProfile.this, Login.class));
                    finish();
                } else {
                    Toast.makeText(FeProfile.this, "Some error occurred. Please try again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<FeDetails> call, Throwable t) {
                hideProgress();
                Toast.makeText(FeProfile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}
