package in.bharatrohan.bharatrohan.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import in.bharatrohan.bharatrohan.Apis.RetrofitClient;
import in.bharatrohan.bharatrohan.CheckInternet;
import in.bharatrohan.bharatrohan.Models.Farmer;
import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView myFarm, myRepo, registerFarm, userProfile, myHelp, moneyRecord, userImage, feDetails;
    private MaterialSpinner navHelpSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setLocale(new PrefManager(this).getUserLanguage());
        setContentView(R.layout.activity_main);
        new CheckInternet(this).checkConnection();

        init();


        navHelpSpinner.setOnItemSelectedListener((view1, position, id, item) -> {
            if (position == 1) {
                startActivity(new Intent(this, Feedback.class));
            } else if (position == 2) {
                startActivity(new Intent(this, ImpContacts.class));
            }

        });

        myFarm.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MyFarm.class)));

        myRepo.setOnClickListener(v -> Toast.makeText(MainActivity.this, "This Feature is COMING SOON!!", Toast.LENGTH_SHORT).show());

        registerFarm.setOnClickListener(v -> {
            new PrefManager(this).saveKmlStatus(true);
            new PrefManager(this).saveValueStatus(true);
            startActivity(new Intent(MainActivity.this, RegisterFarm.class));
        });

        feDetails.setOnClickListener(v -> startActivity(new Intent(this, FeProfile.class)));

        myHelp.setOnClickListener(v -> {
            startActivity(new Intent(this, HelpSupport.class));
        });

        userProfile.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, UserProfile.class)));

        moneyRecord.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MoneyRecord.class)));

    }


    private void init() {

        getFarmCount();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        myFarm = findViewById(R.id.imageView5);
        myRepo = findViewById(R.id.repository);
        myHelp = findViewById(R.id.imageView11);
        registerFarm = findViewById(R.id.registerFarm);
        userProfile = findViewById(R.id.profileUpdate);
        moneyRecord = findViewById(R.id.moneyRecord);
        feDetails = findViewById(R.id.imageView10);
        TextView usersName = findViewById(R.id.usersName);
        userImage = navigationView.getHeaderView(0).findViewById(R.id.userImage);

        // showServerDialog();

        TextView userName = navigationView.getHeaderView(0).findViewById(R.id.tvUserName);
        TextView userPhone = navigationView.getHeaderView(0).findViewById(R.id.tvUserPhone);
        userName.setText(new PrefManager(MainActivity.this).getName());
        usersName.setText(new PrefManager(MainActivity.this).getName());
        userPhone.setText(new PrefManager(MainActivity.this).getPhone());

        if (!(new PrefManager(MainActivity.this).getAvatar().equals(""))) {

            Picasso.get().load("http://br.bharatrohan.in/" + new PrefManager(MainActivity.this).getAvatar()).fit().centerCrop().networkPolicy(NetworkPolicy.OFFLINE).into(userProfile, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load("http://br.bharatrohan.in/" + new PrefManager(MainActivity.this).getAvatar()).fit().centerCrop().into(userProfile);
                }
            });


            Picasso.get().load("http://br.bharatrohan.in/" + new PrefManager(MainActivity.this).getAvatar()).fit().centerCrop().networkPolicy(NetworkPolicy.OFFLINE).into(userImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load("http://br.bharatrohan.in/" + new PrefManager(MainActivity.this).getAvatar()).fit().centerCrop().into(userImage);
                }
            });
        } else {
            Picasso.get().load(R.drawable.profile_pic).into(userProfile);
            Picasso.get().load(R.drawable.profile_pic).into(userImage);
        }


        navHelpSpinner = (MaterialSpinner) navigationView.getMenu().findItem(R.id.nav_help).getActionView();

        String title = "-" + getResources().getString(R.string.help_supp) + "-";

        ArrayList<String> helpList = new ArrayList<>();
        helpList.add(title);
        helpList.add(getResources().getString(R.string.feedback));
        helpList.add(getResources().getString(R.string.imp_contacts));
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, helpList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        navHelpSpinner.setAdapter(adapter1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(this, UserProfile.class));
        } else if (id == R.id.nav_pass) {
            Intent intent = new Intent(this, ChangePassword.class);
            intent.putExtra("activity", "main");
            startActivity(intent);
        } else if (id == R.id.nav_lang) {
            Intent intent = new Intent(this, LanguageScreen.class);
            intent.putExtra("activity", "main");
            startActivity(intent);
        } else if (id == R.id.nav_refer) {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "BharatRohan");
                String sAux = "App Short Description\n";
                sAux = sAux + "App Link";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "Choose one"));
            } catch (Exception ex) {
                //e.toString();
            }
        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_privacy) {

        } else if (id == R.id.nav_terms) {

        } else if (id == R.id.nav_logout) {
            new PrefManager(this).saveLoginDetails("", "");
            new PrefManager(this).saveUserDetails("", "", "", "", "", "", "", "", "", "", "", "", "");
            new PrefManager(this).saveAvatar("");
            new PrefManager(this).saveToken("");
            new PrefManager(this).saveFarmerId("");

            startActivity(new Intent(this, Login.class));
            finish();
        } else if (id == R.id.nav_stop) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void getFarmCount() {
        Call<Farmer> call = RetrofitClient.getInstance().getApi().getFarmerDetail(new PrefManager(MainActivity.this).getToken(), new PrefManager(this).getFarmerId());

        call.enqueue(new retrofit2.Callback<Farmer>() {
            @Override
            public void onResponse(Call<Farmer> call, Response<Farmer> response) {
                Farmer farmer = response.body();
                if (response.code() == 200) {
                    if (farmer != null) {
                        new PrefManager(MainActivity.this).saveFarmCount(farmer.getFarms().size());
                    } else {
                        Toast.makeText(MainActivity.this, "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 401) {
                    Toast.makeText(MainActivity.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    new PrefManager(MainActivity.this).saveLoginDetails("", "");
                    new PrefManager(MainActivity.this).saveUserDetails("", "", "", "", "", "", "", "", "", "", "", "", "");
                    new PrefManager(MainActivity.this).saveAvatar("");
                    new PrefManager(MainActivity.this).saveToken("");
                    new PrefManager(MainActivity.this).saveFarmerId("");
                    startActivity(new Intent(MainActivity.this, Login.class));
                    finish();
                } else if (response.code() == 400) {
                    Toast.makeText(MainActivity.this, "Error: Required values are missing!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 409) {
                    Toast.makeText(MainActivity.this, "Conflict Occurred!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 500) {
                    Toast.makeText(MainActivity.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Farmer> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getFarmCount();


        if (!(new PrefManager(MainActivity.this).getAvatar().equals(""))) {

            Picasso.get().load("http://br.bharatrohan.in/" + new PrefManager(MainActivity.this).getAvatar()).fit().centerCrop().networkPolicy(NetworkPolicy.OFFLINE).into(userProfile, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load("http://br.bharatrohan.in/" + new PrefManager(MainActivity.this).getAvatar()).fit().centerCrop().into(userProfile);
                }
            });


            Picasso.get().load("http://br.bharatrohan.in/" + new PrefManager(MainActivity.this).getAvatar()).fit().centerCrop().networkPolicy(NetworkPolicy.OFFLINE).into(userImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load("http://br.bharatrohan.in/" + new PrefManager(MainActivity.this).getAvatar()).fit().centerCrop().into(userImage);
                }
            });
        } else {
            Picasso.get().load(R.drawable.profile_pic).into(userProfile);
            Picasso.get().load(R.drawable.profile_pic).into(userImage);
        }
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
}
