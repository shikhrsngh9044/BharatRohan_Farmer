package in.bharatrohan.bharatrohan.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.bharatrohan.bharatrohan.Apis.RetrofitClient;
import in.bharatrohan.bharatrohan.CheckInternet;
import in.bharatrohan.bharatrohan.Models.Farm;
import in.bharatrohan.bharatrohan.Models.Farmer;
import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandFragment extends Fragment {

    private TextView landName, cropName, farmStatus, mapEdit;
    private ProgressBar progressBar;
    private ArrayList<String> farmList = new ArrayList<>();
    private String token, farmerId;

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

        mapEdit.setOnClickListener(v -> startActivity(new Intent(getActivity(), ViewEditMap.class)));

        return view;
    }

    private void initViews(View view) {
        landName = view.findViewById(R.id.tvLandName);
        cropName = view.findViewById(R.id.tvCropName);
        farmStatus = view.findViewById(R.id.tvFarmStatus);
        mapEdit = view.findViewById(R.id.mapEdit);
        progressBar = view.findViewById(R.id.progressBar);
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

        /*if (farmStatusBool) {
            farmStatus.setText("Verified");
            farmStatus.setBackgroundResource(android.R.color.holo_green_dark);
        } else {
            farmStatus.setText("Not Verified");
            farmStatus.setBackgroundResource(android.R.color.holo_red_dark);
        }*/
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
                        }
                       // new PrefManager(getContext()).saveTempFarm(farm.getData().getFarm_name(), farm.getData().getLocation(), farm.getData().getFarm_area(), farm.getData().getMap_image(), farm.getData().getCrop().getCrop_name(), farmId, farmerId);
                        landName.setText(farm.getData().getFarm_name());
                        landName.setVisibility(View.VISIBLE);
                        cropName.setText(farm.getData().getCrop().getCrop_name());
                        cropName.setVisibility(View.VISIBLE);
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
                        farmList = farmer.getFarms();
                        if (farmList.size() != 0) {
                            showFarmInfo(farmList.get(farmNo));
                        } else {
                            new PrefManager(getContext()).saveFarmNo(0);
                            Toast.makeText(getContext(), "No Farm is Registered yet!!", Toast.LENGTH_SHORT).show();
                        }
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
            public void onFailure(Call<Farmer> call, Throwable t) {
                hideProgress();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
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
