package in.bharatrohan.bharatrohan.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView myFarm, myRepo, registerFarm, userProfile, moneyRecord, userImage;
    private MaterialSpinner navHelpSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        navHelpSpinner.setOnItemSelectedListener((view1, position, id, item) -> {
            if (position == 1) {
                Toast.makeText(this, "Selected : Feedback", Toast.LENGTH_SHORT).show();
            } else if (position == 2) {
                Toast.makeText(this, "Selected : Contact", Toast.LENGTH_SHORT).show();
            } else if (position == 3) {
                Toast.makeText(this, "Selected : FE", Toast.LENGTH_SHORT).show();
            }

        });

        myFarm.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MyFarm.class)));

        myRepo.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "This Feature is COMING SOON!!", Toast.LENGTH_SHORT).show();
        });

        registerFarm.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RegisterFarm.class)));

        userProfile.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, UserProfile.class)));

        moneyRecord.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MoneyRecord.class)));

    }


    private void init() {
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
        registerFarm = findViewById(R.id.registerFarm);
        userProfile = findViewById(R.id.profileUpdate);
        moneyRecord = findViewById(R.id.moneyRecord);
        TextView usersName = findViewById(R.id.usersName);
        userImage = navigationView.getHeaderView(0).findViewById(R.id.userImage);

        showServerDialog();

        TextView userName = navigationView.getHeaderView(0).findViewById(R.id.tvUserName);
        TextView userPhone = navigationView.getHeaderView(0).findViewById(R.id.tvUserPhone);


        if (!(new PrefManager(MainActivity.this).getAvatar().equals(""))) {
            Picasso.get().load(new PrefManager(MainActivity.this).getAvatar()).networkPolicy(NetworkPolicy.OFFLINE).into(userImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load(R.drawable.profile_pic).into(userImage);
                }
            });
        }

        userName.setText(new PrefManager(MainActivity.this).getName());
        usersName.setText(new PrefManager(MainActivity.this).getName());
        userPhone.setText(new PrefManager(MainActivity.this).getPhone());


        navHelpSpinner = (MaterialSpinner) navigationView.getMenu().findItem(R.id.nav_help).getActionView();

        ArrayList<String> helpList = new ArrayList<>();
        helpList.add("-Help and Support-");
        helpList.add("Feedback and Complaints");
        helpList.add("Imp Contacts");
        helpList.add("Change FE");
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
            // Handle the camera action
        } else if (id == R.id.nav_pass) {

        } else if (id == R.id.nav_refer) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_privacy) {

        } else if (id == R.id.nav_terms) {

        } else if (id == R.id.nav_logout) {
            new PrefManager(this).saveLoginDetails("", "");
            new PrefManager(this).saveUserDetails("", "", "", "", "", "", "", "", "", "", "");
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

    private void showServerDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.enter_server_name, null);
        dialogBuilder.setView(view);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.show();

        EditText message = view.findViewById(R.id.editEmail);
        Button btnOk = view.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(view1 -> {
            String strMessage = message.getText().toString().trim();


            if (strMessage.isEmpty()) {
                message.setError("Server name is required!");
                message.requestFocus();
                return;
            }

            new PrefManager(MainActivity.this).saveServerName(strMessage);
            alertDialog.dismiss();
        });
    }
}
