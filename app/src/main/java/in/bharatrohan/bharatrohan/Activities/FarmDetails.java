package in.bharatrohan.bharatrohan.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import in.bharatrohan.bharatrohan.Apis.RetrofitClient;
import in.bharatrohan.bharatrohan.CheckInternet;
import in.bharatrohan.bharatrohan.Models.CropProblem;
import in.bharatrohan.bharatrohan.Models.Farm;
import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;
import in.bharatrohan.bharatrohan.SolutionRecyclerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FarmDetails extends AppCompatActivity {

    private TextView landName, cropName, farmStatus, mapEdit, imageWarn, solWarn;
    private ProgressBar progressBar;
    private String token, farmerId, mapImg;
    private RecyclerView recyclerView;
    private SolutionRecyclerAdapter adapter;
    private MaterialSpinner problemSpinner;
    private ArrayList<String> solutionArrayList, problemId;
    private ArrayAdapter<String> adapter1;
    private ImageView solImage, headImage;
    private boolean isFirst, isVerified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_farm_details);
        initViews();

        mapEdit.setOnClickListener(v -> {
            if (isVerified) {
                new PrefManager(FarmDetails.this).saveFarmImage(mapImg);
                Intent intent = new Intent(FarmDetails.this, ViewEditMap.class);
                intent.putExtra("verification", "true");
                startActivity(intent);
            } else {
                new PrefManager(FarmDetails.this).saveFarmImage(mapImg);
                Intent intent = new Intent(FarmDetails.this, ViewEditMap.class);
                intent.putExtra("verification", "false");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showFarmInfo(new PrefManager(FarmDetails.this).getFarmId());
    }

    private void initViews() {
        token = new PrefManager(FarmDetails.this).getToken();
        farmerId = new PrefManager(FarmDetails.this).getFarmerId();
        new CheckInternet(FarmDetails.this).checkConnection();


        headImage = findViewById(R.id.imageView12);
        Picasso.get().load(R.drawable.my_farm_header).fit().centerCrop().into(headImage);

        problemSpinner = findViewById(R.id.spinnerVisits);
        solImage = findViewById(R.id.solutionImg);
        recyclerView = findViewById(R.id.recycler);

        landName = findViewById(R.id.tvLandName);
        cropName = findViewById(R.id.tvCropName);
        farmStatus = findViewById(R.id.tvFarmStatus);
        mapEdit = findViewById(R.id.mapEdit);
        progressBar = findViewById(R.id.progressBar);
        imageWarn = findViewById(R.id.textView23);
        solWarn = findViewById(R.id.textView24);
        showFarmInfo(new PrefManager(FarmDetails.this).getFarmId());
    }


    private void showFarmInfo(String farmId) {
        Call<Farm> call = RetrofitClient.getInstance().getApi().getFarmDetail(token, farmId);

        call.enqueue(new Callback<Farm>() {
            @Override
            public void onResponse(Call<Farm> call, Response<Farm> response) {
                hideProgress();
                Farm farm = response.body();

                if (response.code() == 200) {

                    if (farm != null) {
                        if (farm.getData().getVerified() != null) {
                            isVerified = farm.getData().getVerified();

                            if (isVerified) {
                                mapEdit.setText("View Map");
                                farmStatus.setText("Verified");
                                farmStatus.setBackgroundResource(android.R.color.holo_green_dark);
                                farmStatus.setVisibility(View.VISIBLE);
                            } else {
                                mapEdit.setText("View Map & Edit Farm");
                                farmStatus.setText("Not Verified");
                                farmStatus.setBackgroundResource(android.R.color.holo_red_dark);
                                farmStatus.setVisibility(View.VISIBLE);
                            }
                        }
                        new PrefManager(FarmDetails.this).saveTempFarm(farm.getData().getFarm_name(), farm.getData().getLocation(), farm.getData().getFarm_area(), farm.getData().getMap_image(), farm.getData().getCrop().getCrop_name(), farmId, farmerId);

                        mapImg = farm.getData().getMap_image();
                        problemId = new ArrayList<>();
                        problemId.clear();
                        problemId.addAll(farm.getData().getProblemId());
                        landName.setText(farm.getData().getFarm_name());
                        landName.setVisibility(View.VISIBLE);
                        cropName.setText(farm.getData().getCrop().getCrop_name());
                        cropName.setVisibility(View.VISIBLE);
                        isFirst = true;

                        initProblemSpinner();
                    } else {
                        Toast.makeText(FarmDetails.this, "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 401) {
                    Toast.makeText(FarmDetails.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    new PrefManager(FarmDetails.this).saveLoginDetails("", "");
                    new PrefManager(FarmDetails.this).saveUserDetails("", "", "", "", "", "", "", "", "", "", "", "", "");
                    new PrefManager(FarmDetails.this).saveAvatar("");
                    new PrefManager(FarmDetails.this).saveToken("");
                    new PrefManager(FarmDetails.this).saveFarmerId("");
                    startActivity(new Intent(FarmDetails.this, Login.class));
                    finish();
                } else if (response.code() == 400) {
                    Toast.makeText(FarmDetails.this, "Error: Required values are missing!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 409) {
                    Toast.makeText(FarmDetails.this, "Conflict Occurred!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 500) {
                    Toast.makeText(FarmDetails.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Farm> call, Throwable t) {
                hideProgress();
                Toast.makeText(FarmDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void initProblemSpinner() {

        Collections.reverse(problemId);

        if (problemId.size() > 0) {

            solImage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            solWarn.setVisibility(View.GONE);
            imageWarn.setVisibility(View.GONE);

            solutionArrayList = new ArrayList<>();

            for (int i = 0; i < problemId.size(); i++) {
                solutionArrayList.add("Solution " + (i + 1));
            }

            //reversing problem tag arrayList
            Collections.reverse(solutionArrayList);

            adapter1 = new ArrayAdapter<>(FarmDetails.this, android.R.layout.simple_spinner_item, solutionArrayList);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            problemSpinner.setAdapter(adapter1);


            if (isFirst) {
                getSolution(problemId.get(0));
            }

            problemSpinner.setOnItemSelectedListener((view, position, id, item) -> {
                String problem_id = problemId.get(position);

                getSolution(problem_id);
            });

        } else {

            solutionArrayList = new ArrayList<>();

            solutionArrayList.add("N/A");
            adapter1 = new ArrayAdapter<>(FarmDetails.this, android.R.layout.simple_spinner_item, solutionArrayList);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            problemSpinner.setAdapter(adapter1);

            problemSpinner.setOnItemSelectedListener((view, position, id, item) -> Toast.makeText(FarmDetails.this, "No solutions are given", Toast.LENGTH_SHORT).show());
        }


    }

    private void getSolution(String prob_id) {
        showProgress();

        Call<CropProblem> call = RetrofitClient.getInstance().getApi().getProblemDetail(token, prob_id);

        call.enqueue(new Callback<CropProblem>() {
            @Override
            public void onResponse(Call<CropProblem> call, Response<CropProblem> response) {
                hideProgress();
                CropProblem solutionList = response.body();
                if (response.code() == 200) {
                    if (solutionList != null) {
                        // generateSolList(solutionList.getData().getSolution().getSolutionDataList());
                        adapter = new SolutionRecyclerAdapter(FarmDetails.this, solutionList.getData().getSolution().getSolutionDataList());

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FarmDetails.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                        Picasso.get().load("http://br.bharatrohan.in/" + solutionList.getData().getSolution().getSolutionImage()).into(solImage);
                    }
                } else if (response.code() == 400) {
                    Toast.makeText(FarmDetails.this, "Error: Bad Request!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 409) {
                    Toast.makeText(FarmDetails.this, "Conflict Occurred!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 500) {
                    Toast.makeText(FarmDetails.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CropProblem> call, Throwable t) {
                Toast.makeText(FarmDetails.this, "Error in Request", Toast.LENGTH_SHORT).show();
            }
        });
    }

   /* private void generateSolList(List<CropProblem.Data.Solution.SolutionData> allSolArrayList) {

        adapter = new SolutionRecyclerAdapter(FarmDetails.this, allSolArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FarmDetails.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }*/


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FarmDetails.this, FarmerFarms.class));
        finish();
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}
