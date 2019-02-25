package in.bharatrohan.bharatrohan.Activities;

import android.content.BroadcastReceiver;
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

    public static LandFragment newInstance() {
        return new LandFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.land_fragment_layout, container, false);

        initViews(view);


        if (new PrefManager(getContext()).getFarmStatus()) {
            farmStatus.setText("Verified");
            farmStatus.setBackgroundResource(android.R.color.holo_green_dark);
        } else {
            farmStatus.setText("Not Verified");
            farmStatus.setBackgroundResource(android.R.color.holo_red_dark);
        }

        mapEdit.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ViewEditMap.class));
        });

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
        /*showFarmInfo();
        Toast.makeText(getActivity(), "OnResume Called", Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && isResumed()) {
            if (new PrefManager(getActivity()).getFarmerFarmCount() == 1) {
                new PrefManager(getActivity()).saveFarmNo(0);
            }
            getFarmId(new PrefManager(getActivity()).getFarmerFarmNo());
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
        //showProgress();
        Call<Farm> call = RetrofitClient.getInstance().getApi().getFarmDetail(new PrefManager(getActivity()).getToken(), farmId);

        call.enqueue(new Callback<Farm>() {
            @Override
            public void onResponse(Call<Farm> call, Response<Farm> response) {
                hideProgress();
                Farm farm = response.body();
                if (farm != null) {
                    if (farm.getData().getVerified() != null) {
                        new PrefManager(getContext()).saveFarmStatus(farm.getData().getVerified());
                    }
                    new PrefManager(getContext()).saveTempFarm(farm.getData().getFarm_name(), farm.getData().getLocation(), farm.getData().getFarm_area(), farm.getData().getMap_image(), farm.getData().getCrop().getCrop_name(), farmId, new PrefManager(getActivity()).getFarmerId());
                    landName.setText(farm.getData().getFarm_name());
                    cropName.setText(farm.getData().getCrop().getCrop_name());
                } else {
                    Toast.makeText(getActivity(), "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Farm> call, Throwable t) {
                hideProgress();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getFarmId(int farmNo) {

        showProgress();
        Call<Farmer> call = RetrofitClient.getInstance().getApi().getFarmerDetail(new PrefManager(getActivity()).getToken(), new PrefManager(getActivity()).getFarmerId());

        call.enqueue(new Callback<Farmer>() {
            @Override
            public void onResponse(Call<Farmer> call, Response<Farmer> response) {
                Farmer farmer = response.body();

                if (farmer != null) {
                    farmList = farmer.getFarms();
                    if (farmList.size() != 0) {
                        showFarmInfo(farmList.get(farmNo));
                    } else {
                        new PrefManager(getActivity()).saveFarmNo(0);
                        Toast.makeText(getActivity(), "No Farm is Registered yet!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Farmer> call, Throwable t) {
                hideProgress();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
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
