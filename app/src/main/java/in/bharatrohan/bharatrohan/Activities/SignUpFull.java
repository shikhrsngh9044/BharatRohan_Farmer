package in.bharatrohan.bharatrohan.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Calendar;

import in.bharatrohan.bharatrohan.Apis.RetrofitClient;
import in.bharatrohan.bharatrohan.CheckInternet;
import in.bharatrohan.bharatrohan.Models.Block;
import in.bharatrohan.bharatrohan.Models.District;
import in.bharatrohan.bharatrohan.Models.States;
import in.bharatrohan.bharatrohan.Models.Tehsil;
import in.bharatrohan.bharatrohan.Models.UserFarmer;
import in.bharatrohan.bharatrohan.Models.Village;
import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFull extends AppCompatActivity {

    private Button register, btnDate;
    private EditText fulladdress, pass, conf_pass, dob;
    private MaterialSpinner stateSpinner, districtSpinner, tehsilSpinner, blockSpinner, villageSpinner;
    private ArrayList<String> s_idList, s_nameList, d_idList, d_nameList, t_idList, t_nameList, b_idList, b_nameList, v_idList, v_nameList;
    ArrayAdapter<String> adapter, adapter1, adapter2, adapter3, adapter4;
    private int mYear, mMonth, mDay;
    private String state, stateId = "-SELECT STATE-", district, districtId = "-SELECT DISTRICT-", tehsil, tehsilId = "-SELECT TEHSIL-", block, blockId = "-SELECT BLOCK-", village, villageId = "-SELECT VILLAGE-";

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_full);
        new CheckInternet(this).checkConnection();


        init();
        initStateSpinner();


        stateSpinner.setOnItemSelectedListener((view, position, id, item) -> {
            stateId = s_idList.get(position);
            state = s_nameList.get(position);
            initDistrictSpinner(stateId);
        });


        districtSpinner.setOnItemSelectedListener((view, position, id, item) -> {
            districtId = d_idList.get(position);
            district = d_nameList.get(position);
            initTehsilSpinner(districtId);
        });


        tehsilSpinner.setOnItemSelectedListener((view, position, id, item) -> {
            tehsilId = t_idList.get(position);
            tehsil = t_nameList.get(position);
            initBlockSpinner(tehsilId);
        });


        blockSpinner.setOnItemSelectedListener((view, position, id, item) -> {
            blockId = b_idList.get(position);
            block = b_nameList.get(position);
            initVillageSpinner(blockId);
        });


        villageSpinner.setOnItemSelectedListener((view, position, id, item) -> {
            villageId = v_idList.get(position);
            village = v_nameList.get(position);
        });


        register.setOnClickListener(v -> {
            if (!validateForm() && !validateAddress()) {
                signUp();
            } else {
                validateForm();
                validateAddress();
            }
        });

        btnDate.setOnClickListener(v -> datePicker());
    }

    private void signUp() {


        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String alter_phone = getIntent().getStringExtra("alter_phone");
        String email = getIntent().getStringExtra("email");

        String fulladdress1 = fulladdress.getText().toString().trim();
        String pass1 = pass.getText().toString().trim();
        String conf_pass1 = conf_pass.getText().toString().trim();
        String dob1 = dob.getText().toString().trim();

        UserFarmer.Addres addres = new UserFarmer.Addres(stateId, districtId, tehsilId, blockId, villageId);

        UserFarmer userFarmer = new UserFarmer(email, pass1, name, phone, alter_phone, dob1, addres, fulladdress1);

        showProgress();

        Call<UserFarmer> call = RetrofitClient
                .getInstance()
                .getApi()
                .createUser(userFarmer);

        call.enqueue(new Callback<UserFarmer>() {
            @Override
            public void onResponse(Call<UserFarmer> call, Response<UserFarmer> response) {
                hideProgress();
                UserFarmer dr = response.body();

                if (response.code() == 201) {
                    Toast.makeText(SignUpFull.this, dr.getMessage(), Toast.LENGTH_SHORT).show();

                    new PrefManager(SignUpFull.this).saveUserDetails(state, district, tehsil, block, village, email, dob1, name, phone, fulladdress1, alter_phone, dr.getFeId(), "");
                    new PrefManager(SignUpFull.this).saveFarmerVerifyStatus(false);
                    new PrefManager(SignUpFull.this).saveFarmerId(dr.getData().getId());
                    new PrefManager(SignUpFull.this).saveToken("Bearer " + dr.getToken());

                    Intent intent = new Intent(SignUpFull.this, RegisterFarmDialog.class);
                    intent.putExtra("phone", phone);
                    intent.putExtra("password", pass1);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignUpFull.this, "User Already Exists!!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserFarmer> call, Throwable t) {
                hideProgress();
                Toast.makeText(SignUpFull.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

    }

    private boolean validateForm() {

        String pass1 = pass.getText().toString().trim();
        String conf_pass1 = conf_pass.getText().toString().trim();
        String dob1 = dob.getText().toString().trim();


        if (pass1.isEmpty()) {
            pass.setError("Password is required");
            pass.requestFocus();
            return true;
        }

        if (conf_pass1.isEmpty()) {
            conf_pass.setError("Confirm Password is required");
            conf_pass.requestFocus();
            return true;
        }

        if (pass1.length() < 4) {
            pass.setError("Password should be atleast 4 character Long");
            pass.requestFocus();
            return true;
        }

        if (!conf_pass1.equals(pass1)) {
            conf_pass.setError("Password is not same");
            conf_pass.requestFocus();
            return true;
        }

        if (dob1.isEmpty()) {
            dob.setError("DOB is required");
            dob.requestFocus();
            return true;
        }

        return false;
    }

    private void init() {

        register = findViewById(R.id.register);
        btnDate = findViewById(R.id.btnDate);

        fulladdress = findViewById(R.id.fulladdress);
        pass = findViewById(R.id.pass);
        conf_pass = findViewById(R.id.conf_pass);
        dob = findViewById(R.id.dob);
        progressBar = findViewById(R.id.progressBar);


        dob.setEnabled(false);

        stateSpinner = findViewById(R.id.stateSpinner);
        districtSpinner = findViewById(R.id.districtSpinner);
        tehsilSpinner = findViewById(R.id.tehsilSpinner);
        blockSpinner = findViewById(R.id.blockSpinner);
        villageSpinner = findViewById(R.id.villageSpinner);

        s_idList = new ArrayList<String>();
        s_nameList = new ArrayList<String>();
        d_idList = new ArrayList<String>();
        d_nameList = new ArrayList<String>();
        t_idList = new ArrayList<String>();
        t_nameList = new ArrayList<String>();
        b_idList = new ArrayList<String>();
        b_nameList = new ArrayList<String>();
        v_idList = new ArrayList<String>();
        v_nameList = new ArrayList<String>();

    }

    private void initStateSpinner() {
        showProgress();
        Call<States> call = RetrofitClient
                .getInstance()
                .getApi()
                .stateList();

        call.enqueue(new Callback<States>() {
            @Override
            public void onResponse(Call<States> call, Response<States> response) {
                hideProgress();
                States statesList = response.body();

                s_nameList.clear();
                s_idList.clear();

                if (response.code() == 200) {

                    if (statesList != null) {

                        s_nameList.add(0, "-SELECT STATE-");
                        s_idList.add(0, "-SELECT STATE-");

                        for (States.State s : statesList.getState()) {
                            s_idList.add(s.getId());
                            s_nameList.add(s.getName());
                        }


                        adapter = new ArrayAdapter<>(SignUpFull.this, android.R.layout.simple_spinner_item, s_nameList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        stateSpinner.setAdapter(adapter);
                        //progressBar.setVisibility(View.GONE);

                    } else {
                        //Toast.makeText(SignUpFull.this, "Some Error Occured Please Try Again!!", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 401) {
                    Toast.makeText(SignUpFull.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    new PrefManager(SignUpFull.this).saveLoginDetails("", "");
                    new PrefManager(SignUpFull.this).saveUserDetails("", "", "", "", "", "", "", "", "", "", "", "", "");
                    new PrefManager(SignUpFull.this).saveAvatar("");
                    new PrefManager(SignUpFull.this).saveToken("");
                    new PrefManager(SignUpFull.this).saveFarmerId("");
                    startActivity(new Intent(SignUpFull.this, Login.class));
                    finish();
                } else if (response.code() == 400) {
                    Toast.makeText(SignUpFull.this, "Error: Required values are missing!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 409) {
                    Toast.makeText(SignUpFull.this, "Conflict Occurred!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 500) {
                    Toast.makeText(SignUpFull.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<States> call, Throwable t) {
                hideProgress();
            }
        });

    }

    private void initDistrictSpinner(String id) {
        showProgress();
        Call<District> call = RetrofitClient
                .getInstance()
                .getApi()
                .districtList(id);

        call.enqueue(new Callback<District>() {
            @Override
            public void onResponse(Call<District> call, Response<District> response) {
                hideProgress();
                District districtList = response.body();

                d_nameList.clear();
                d_idList.clear();
                if (response.code() == 200) {
                    if (districtList != null) {

                        d_nameList.add(0, "-SELECT DISTRICT-");
                        d_idList.add(0, "-SELECT DISTRICT-");

                        for (District.Districts s : districtList.getDistricts()) {
                            d_idList.add(s.getId());
                            d_nameList.add(s.getName());
                        }


                        adapter1 = new ArrayAdapter<>(SignUpFull.this, android.R.layout.simple_spinner_item, d_nameList);
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        districtSpinner.setAdapter(adapter1);
                        //progressBar.setVisibility(View.GONE);

                    } else {
                        //Toast.makeText(SignUpFull.this, "Some Error Occured Please Try Again!!", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 401) {
                    Toast.makeText(SignUpFull.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    new PrefManager(SignUpFull.this).saveLoginDetails("", "");
                    new PrefManager(SignUpFull.this).saveUserDetails("", "", "", "", "", "", "", "", "", "", "", "", "");
                    new PrefManager(SignUpFull.this).saveAvatar("");
                    new PrefManager(SignUpFull.this).saveToken("");
                    new PrefManager(SignUpFull.this).saveFarmerId("");
                    startActivity(new Intent(SignUpFull.this, Login.class));
                    finish();
                } else if (response.code() == 400) {
                    Toast.makeText(SignUpFull.this, "Error: Required values are missing!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 409) {
                    Toast.makeText(SignUpFull.this, "Conflict Occurred!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 500) {
                    Toast.makeText(SignUpFull.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<District> call, Throwable t) {
                hideProgress();
            }
        });

    }

    private void initTehsilSpinner(String id) {

        showProgress();
        Call<Tehsil> call = RetrofitClient
                .getInstance()
                .getApi()
                .tehsilList(id);

        call.enqueue(new Callback<Tehsil>() {
            @Override
            public void onResponse(Call<Tehsil> call, Response<Tehsil> response) {
                hideProgress();
                Tehsil tehsilList = response.body();

                t_nameList.clear();
                t_idList.clear();

                if (response.code() == 200) {

                    if (tehsilList != null) {

                        t_nameList.add(0, "-SELECT TEHSIL-");
                        t_idList.add(0, "-SELECT TEHSIL-");

                        for (Tehsil.Tehsils s : tehsilList.getTehsils()) {
                            t_idList.add(s.getId());
                            t_nameList.add(s.getName());
                        }


                        adapter2 = new ArrayAdapter<>(SignUpFull.this, android.R.layout.simple_spinner_item, t_nameList);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        tehsilSpinner.setAdapter(adapter2);
                        //progressBar.setVisibility(View.GONE);

                    } else {
                        //Toast.makeText(SignUpFull.this, "Some Error Occured Please Try Again!!", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 401) {
                    Toast.makeText(SignUpFull.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    new PrefManager(SignUpFull.this).saveLoginDetails("", "");
                    new PrefManager(SignUpFull.this).saveUserDetails("", "", "", "", "", "", "", "", "", "", "", "", "");
                    new PrefManager(SignUpFull.this).saveAvatar("");
                    new PrefManager(SignUpFull.this).saveToken("");
                    new PrefManager(SignUpFull.this).saveFarmerId("");
                    startActivity(new Intent(SignUpFull.this, Login.class));
                    finish();
                } else if (response.code() == 400) {
                    Toast.makeText(SignUpFull.this, "Error: Required values are missing!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 409) {
                    Toast.makeText(SignUpFull.this, "Conflict Occurred!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 500) {
                    Toast.makeText(SignUpFull.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Tehsil> call, Throwable t) {
                hideProgress();
            }
        });

    }

    private void initBlockSpinner(String id) {
        showProgress();
        Call<Block> call = RetrofitClient
                .getInstance()
                .getApi()
                .blockList(id);

        call.enqueue(new Callback<Block>() {
            @Override
            public void onResponse(Call<Block> call, Response<Block> response) {
                hideProgress();
                Block blockList = response.body();

                b_nameList.clear();
                b_idList.clear();
                if (response.code() == 200) {
                    if (blockList != null) {

                        b_nameList.add(0, "-SELECT BLOCK-");
                        b_idList.add(0, "-SELECT BLOCK-");

                        for (Block.Blocks s : blockList.getBlocks()) {
                            b_idList.add(s.getId());
                            b_nameList.add(s.getName());
                        }


                        adapter3 = new ArrayAdapter<>(SignUpFull.this, android.R.layout.simple_spinner_item, b_nameList);
                        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        blockSpinner.setAdapter(adapter3);
                        //progressBar.setVisibility(View.GONE);

                    } else {
                        //Toast.makeText(SignUpFull.this, "Some Error Occured Please Try Again!!", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 401) {
                    Toast.makeText(SignUpFull.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    new PrefManager(SignUpFull.this).saveLoginDetails("", "");
                    new PrefManager(SignUpFull.this).saveUserDetails("", "", "", "", "", "", "", "", "", "", "", "", "");
                    new PrefManager(SignUpFull.this).saveAvatar("");
                    new PrefManager(SignUpFull.this).saveToken("");
                    new PrefManager(SignUpFull.this).saveFarmerId("");
                    startActivity(new Intent(SignUpFull.this, Login.class));
                    finish();
                } else if (response.code() == 400) {
                    Toast.makeText(SignUpFull.this, "Error: Required values are missing!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 409) {
                    Toast.makeText(SignUpFull.this, "Conflict Occurred!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 500) {
                    Toast.makeText(SignUpFull.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Block> call, Throwable t) {
                hideProgress();
            }
        });

    }

    private void initVillageSpinner(String id) {
        showProgress();
        Call<Village> call = RetrofitClient
                .getInstance()
                .getApi()
                .villageList(id);

        call.enqueue(new Callback<Village>() {
            @Override
            public void onResponse(Call<Village> call, Response<Village> response) {
                hideProgress();
                Village villageList = response.body();

                v_nameList.clear();
                v_idList.clear();
                if (response.code() == 200) {
                    if (villageList != null) {

                        v_nameList.add(0, "-SELECT VILLAGE-");
                        v_idList.add(0, "-SELECT VILLAGE-");

                        for (Village.Villages s : villageList.getVillages()) {
                            v_idList.add(s.getId());
                            v_nameList.add(s.getName());
                        }


                        adapter4 = new ArrayAdapter<>(SignUpFull.this, android.R.layout.simple_spinner_item, v_nameList);
                        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        villageSpinner.setAdapter(adapter4);
                        //progressBar.setVisibility(View.GONE);

                    } else {
                        //Toast.makeText(SignUpFull.this, "Some Error Occured Please Try Again!!", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 401) {
                    Toast.makeText(SignUpFull.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    new PrefManager(SignUpFull.this).saveLoginDetails("", "");
                    new PrefManager(SignUpFull.this).saveUserDetails("", "", "", "", "", "", "", "", "", "", "", "", "");
                    new PrefManager(SignUpFull.this).saveAvatar("");
                    new PrefManager(SignUpFull.this).saveToken("");
                    new PrefManager(SignUpFull.this).saveFarmerId("");
                    startActivity(new Intent(SignUpFull.this, Login.class));
                    finish();
                } else if (response.code() == 400) {
                    Toast.makeText(SignUpFull.this, "Error: Required values are missing!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 409) {
                    Toast.makeText(SignUpFull.this, "Conflict Occurred!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 500) {
                    Toast.makeText(SignUpFull.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Village> call, Throwable t) {
                hideProgress();
            }
        });

    }

    private void datePicker() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                    dob.setText(date);
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        stateSpinner.setClickable(false);
        districtSpinner.setClickable(false);
        tehsilSpinner.setClickable(false);
        blockSpinner.setClickable(false);
        villageSpinner.setClickable(false);
        fulladdress.setEnabled(false);
        pass.setEnabled(false);
        conf_pass.setEnabled(false);
        btnDate.setEnabled(false);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
        stateSpinner.setClickable(true);
        districtSpinner.setClickable(true);
        tehsilSpinner.setClickable(true);
        blockSpinner.setClickable(true);
        villageSpinner.setClickable(true);
        fulladdress.setEnabled(true);
        pass.setEnabled(true);
        conf_pass.setEnabled(true);
        btnDate.setEnabled(true);
    }

    private Boolean validateAddress() {
        if (stateId.equals("-SELECT STATE-")) {
            stateSpinner.setError("State is required!");
            stateSpinner.requestFocus();
            return true;
        }

        if (districtId.equals("-SELECT DISTRICT-")) {
            districtSpinner.setError("District is required!");
            districtSpinner.requestFocus();
            return true;
        }

        if (tehsilId.equals("-SELECT TEHSIL-")) {
            tehsilSpinner.setError("Tehsil is required!");
            tehsilSpinner.requestFocus();
            return true;
        }

        if (blockId.equals("-SELECT BLOCK-")) {
            blockSpinner.setError("Block is required!");
            blockSpinner.requestFocus();
            return true;
        }

        if (villageId.equals("-SELECT VILLAGE-")) {
            villageSpinner.setError("Village is required!");
            villageSpinner.requestFocus();
            return true;
        }

        return false;
    }

}
