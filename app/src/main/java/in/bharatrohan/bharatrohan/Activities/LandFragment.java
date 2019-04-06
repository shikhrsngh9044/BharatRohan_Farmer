package in.bharatrohan.bharatrohan.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import in.bharatrohan.bharatrohan.Apis.RetrofitClient;
import in.bharatrohan.bharatrohan.CheckInternet;
import in.bharatrohan.bharatrohan.Models.CropProblem;
import in.bharatrohan.bharatrohan.Models.Farm;
import in.bharatrohan.bharatrohan.Models.Farmer;
import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;
import in.bharatrohan.bharatrohan.SolutionRecyclerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandFragment extends Fragment {

    private TextView landName, cropName, farmStatus, mapEdit, imageWarn, solWarn;
    private ProgressBar progressBar;
    private ArrayList<String> farmList = new ArrayList<>();
    private String token, farmerId;
    private boolean isVerified;
    private RecyclerView recyclerView;
    private SolutionRecyclerAdapter adapter;
    private MaterialSpinner problemSpinner;
    private ArrayList<String> solutionArrayList;
    private ArrayList<String> problemId;
    private ArrayAdapter<String> adapter1;
    private String mapImg;
    private ImageView solImage;
    private boolean isFirst;

    public static LandFragment newInstance() {
        return new LandFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        token = new PrefManager(getContext()).getToken();
        farmerId = new PrefManager(getContext()).getFarmerId();
        new CheckInternet(getContext()).checkConnection();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.land_fragment_layout, container, false);

        initViews(view);

        mapEdit.setOnClickListener(v -> {
            if (isVerified) {
                new PrefManager(getContext()).saveFarmImage(mapImg);
                Intent intent = new Intent(getActivity(), ViewEditMap.class);
                intent.putExtra("verification", "true");
                startActivity(intent);
            } else {
                new PrefManager(getContext()).saveFarmImage(mapImg);
                Intent intent = new Intent(getActivity(), ViewEditMap.class);
                intent.putExtra("verification", "false");
                startActivity(intent);
            }
        });

        return view;
    }

    private void initViews(View view) {
        problemSpinner = view.findViewById(R.id.spinnerVisits);
        solImage = view.findViewById(R.id.solutionImg);
        recyclerView = view.findViewById(R.id.recycler);

        landName = view.findViewById(R.id.tvLandName);
        cropName = view.findViewById(R.id.tvCropName);
        farmStatus = view.findViewById(R.id.tvFarmStatus);
        mapEdit = view.findViewById(R.id.mapEdit);
        progressBar = view.findViewById(R.id.progressBar);
        imageWarn = view.findViewById(R.id.textView23);
        solWarn = view.findViewById(R.id.textView24);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (new PrefManager(getActivity()).getFarmerFarmCount() == 1) {
            new PrefManager(getActivity()).saveFarmNo(0);
        }
        getFarmId(new PrefManager(getActivity()).getFarmerFarmNo());
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && isResumed()) {
            if (new PrefManager(getContext()).getFarmerFarmCount() == 1) {
                new PrefManager(getContext()).saveFarmNo(0);
            }
            getFarmId(new PrefManager(getContext()).getFarmerFarmNo());
        }


    }

    private void setFarmVerifyStatus(Boolean farmStatusBool) {
        if (farmStatusBool) {
            farmStatus.setText("Verified");
            farmStatus.setBackgroundResource(android.R.color.holo_green_dark);
            farmStatus.setVisibility(View.VISIBLE);
        } else {
            farmStatus.setText("Not Verified");
            farmStatus.setBackgroundResource(android.R.color.holo_red_dark);
            farmStatus.setVisibility(View.VISIBLE);
        }
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
                            // new PrefManager(getContext()).saveFarmStatus(farm.getData().getVerified());
                            setFarmVerifyStatus(farm.getData().getVerified());
                            isVerified = farm.getData().getVerified();

                            if (isVerified) {
                                mapEdit.setText("View Map");
                            } else {
                                mapEdit.setText("View Map & Edit Farm");
                            }
                        }
                        if (getContext() != null)
                            new PrefManager(getContext()).saveTempFarm(farm.getData().getFarm_name(), farm.getData().getLocation(), farm.getData().getFarm_area(), farm.getData().getMap_image(), farm.getData().getCrop().getCrop_name(), farmId, farmerId);

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
                        Toast.makeText(getContext(), "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 401) {
                    Toast.makeText(getContext(), "Token Expired", Toast.LENGTH_SHORT).show();
                    new PrefManager(getContext()).saveLoginDetails("", "");
                    new PrefManager(getContext()).saveUserDetails("", "", "", "", "", "", "", "", "", "", "", "", "");
                    new PrefManager(getContext()).saveAvatar("");
                    new PrefManager(getContext()).saveToken("");
                    new PrefManager(getContext()).saveFarmerId("");
                    startActivity(new Intent(getActivity(), Login.class));
                    getActivity().finish();
                } else if (response.code() == 400) {
                    Toast.makeText(getContext(), "Error: Required values are missing!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 409) {
                    Toast.makeText(getContext(), "Conflict Occurred!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 500) {
                    Toast.makeText(getContext(), "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Farm> call, Throwable t) {
                hideProgress();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getFarmId(int farmNo) {

        showProgress();
        Call<Farmer> call = RetrofitClient.getInstance().getApi().getFarmerDetail(token, farmerId);

        call.enqueue(new Callback<Farmer>() {
            @Override
            public void onResponse(Call<Farmer> call, Response<Farmer> response) {
                Farmer farmer = response.body();
                if (response.code() == 200) {
                    if (farmer != null) {
                        /*farmList = farmer.getFarms();
                        if (farmList.size() != 0) {
                            showFarmInfo(farmList.get(farmNo));
                        } else {
                            new PrefManager(getContext()).saveFarmNo(0);
                            Toast.makeText(getContext(), "No Farm is Registered yet!!", Toast.LENGTH_SHORT).show();
                        }*/
                    } else {
                        Toast.makeText(getContext(), "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 401) {
                    Toast.makeText(getContext(), "Token Expired", Toast.LENGTH_SHORT).show();
                    new PrefManager(getContext()).saveLoginDetails("", "");
                    new PrefManager(getContext()).saveUserDetails("", "", "", "", "", "", "", "", "", "", "", "", "");
                    new PrefManager(getContext()).saveAvatar("");
                    new PrefManager(getContext()).saveToken("");
                    new PrefManager(getContext()).saveFarmerId("");
                    startActivity(new Intent(getActivity(), Login.class));
                    getActivity().finish();
                } else if (response.code() == 400) {
                    Toast.makeText(getContext(), "Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 409) {
                    Toast.makeText(getContext(), "Conflict Occurred!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 500) {
                    Toast.makeText(getContext(), "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Farmer> call, Throwable t) {
                hideProgress();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initProblemSpinner() {

        solImage.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        solWarn.setVisibility(View.GONE);
        imageWarn.setVisibility(View.GONE);

        Collections.reverse(problemId);

        if (problemId.size() > 0) {
            solutionArrayList = new ArrayList<>();

            for (int i = 0; i < problemId.size(); i++) {
                solutionArrayList.add("Solution " + (i + 1));
            }

            //reversing problem tag arrayList
            Collections.reverse(solutionArrayList);

            adapter1 = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, solutionArrayList);
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
            solImage.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.GONE);
            solWarn.setVisibility(View.VISIBLE);
            imageWarn.setVisibility(View.VISIBLE);

            solutionArrayList = new ArrayList<>();

            solutionArrayList.add("N/A");
            adapter1 = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, solutionArrayList);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            problemSpinner.setAdapter(adapter1);

            problemSpinner.setOnItemSelectedListener((view, position, id, item) -> Toast.makeText(getContext(), "No solutions are given", Toast.LENGTH_SHORT).show());
        }


    }


    private void getSolution(String prob_id) {
        showProgress();

        Call<CropProblem> call = RetrofitClient.getInstance().getApi().getProblemDetail(new PrefManager(getContext()).getToken(), prob_id);

        call.enqueue(new Callback<CropProblem>() {
            @Override
            public void onResponse(Call<CropProblem> call, Response<CropProblem> response) {
                hideProgress();
                CropProblem solutionList = response.body();
                if (response.code() == 200) {
                    if (solutionList != null) {
                        generateSolList(solutionList.getData().getSolution().getSolutionDataList());
                        //Toast.makeText(getContext(), solutionList.getData().getSolution().getSolutionDataList().toString(), Toast.LENGTH_SHORT).show();
                        Picasso.get().load("http://br.bharatrohan.in/" + solutionList.getData().getSolution().getSolutionImage()).into(solImage);
                    }
                } else if (response.code() == 400) {
                    Toast.makeText(getContext(), "Error: Bad Request!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 409) {
                    Toast.makeText(getContext(), "Conflict Occurred!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 500) {
                    Toast.makeText(getContext(), "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CropProblem> call, Throwable t) {

            }
        });
    }

    private void generateSolList(List<CropProblem.Data.Solution.SolutionData> allSolArrayList) {

        adapter = new SolutionRecyclerAdapter(getActivity(), allSolArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
