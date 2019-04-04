package in.bharatrohan.bharatrohan.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Objects;

import in.bharatrohan.bharatrohan.Apis.RetrofitClient;
import in.bharatrohan.bharatrohan.CheckInternet;
import in.bharatrohan.bharatrohan.FileUtils;
import in.bharatrohan.bharatrohan.Models.Responses;
import in.bharatrohan.bharatrohan.Models.UpdateFarmer;
import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfile extends AppCompatActivity {


    private static final String TAG = UserProfile.class.getSimpleName();
    private static final int REQUEST_CODE = 6384;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 124;


    private ImageView profileP;
    private ProgressBar progressBar;
    private TextView name, contact, email, address, dob, fulladress, altcontact, updatePicLabel;
    private Button btnUpdateOp, btnUpdate, btnCancel;
    private ConstraintLayout constraintLayout;
    private EditText editName, editContact, editAltContact, editEmail, editFullAddress, editDob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_profile);
        new CheckInternet(this).checkConnection();

        init();

        String address1 = new PrefManager(this).getVillage() + "," + new PrefManager(this).getBlock() + "," + new PrefManager(this).getTehsil() + "," + new PrefManager(this).getDistrict() + "," + new PrefManager(this).getState();

        name.setText(new PrefManager(this).getName());
        contact.setText(new PrefManager(this).getContact());
        email.setText(new PrefManager(this).getEmail());
        fulladress.setText(new PrefManager(this).getFullAddress());
        altcontact.setText(new PrefManager(this).getAltContact());
        address.setText(address1);
        dob.setText(new PrefManager(this).getDob());


        btnUpdateOp.setOnClickListener(v -> {

            String name1 = name.getText().toString().trim();
            String contact1 = contact.getText().toString().trim();
            String alt_contact1 = altcontact.getText().toString().trim();
            String email1 = email.getText().toString().trim();
            String fulladdress1 = fulladress.getText().toString().trim();
            String dob1 = dob.getText().toString().trim();

            visibilityGone();

            editName.setText(name1);
            editContact.setText(contact1);
            editAltContact.setText(alt_contact1);
            editEmail.setText(email1);
            editFullAddress.setText(fulladdress1);
            editDob.setText(dob1);
        });

        btnUpdate.setOnClickListener(v -> {
            if (!validateValues()) {
                updateFarmer();
            } else {
                validateValues();
            }

        });

        btnCancel.setOnClickListener(v -> {
            visibilityVisible();
        });

        updatePicLabel.setOnClickListener(v -> {
            if (askForPermission()) {
                showChooser();
            }
        });


    }

    private void init() {
        profileP = findViewById(R.id.profileImage);


        if (!new PrefManager(this).getAvatar().equals("")) {
            Picasso.get().load("http://br.bharatrohan.in/" + new PrefManager(this).getAvatar()).fit().centerCrop().noFade().networkPolicy(NetworkPolicy.OFFLINE).into(profileP, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load("http://br.bharatrohan.in/" + new PrefManager(UserProfile.this).getAvatar()).fit().centerCrop().noFade().into(profileP);
                }
            });
        } else {
            Picasso.get().load(R.drawable.profile_pic).into(profileP);
        }

        //id = findViewById(R.id.framerId);
        name = findViewById(R.id.tvName);
        contact = findViewById(R.id.tvContact);
        email = findViewById(R.id.tvEmail);
        address = findViewById(R.id.tvAddress);
        dob = findViewById(R.id.tvDob);
        fulladress = findViewById(R.id.tvFullAdress);
        altcontact = findViewById(R.id.tvAltContact);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdateOp = findViewById(R.id.btnUpdateOp);
        btnCancel = findViewById(R.id.btnCancel);
        constraintLayout = findViewById(R.id.constraint);
        updatePicLabel = findViewById(R.id.textView39);
        progressBar = findViewById(R.id.progressBar);

        editName = findViewById(R.id.editName);
        editContact = findViewById(R.id.editContact);
        editAltContact = findViewById(R.id.editAltContact);
        editEmail = findViewById(R.id.editOtp);
        editFullAddress = findViewById(R.id.editFullAddress);
        editDob = findViewById(R.id.editDob);
    }

    private void visibilityGone() {
        btnUpdateOp.setVisibility(View.GONE);
        name.setVisibility(View.GONE);
        altcontact.setVisibility(View.GONE);
        email.setVisibility(View.GONE);
        fulladress.setVisibility(View.GONE);
        dob.setVisibility(View.GONE);
        editName.setVisibility(View.VISIBLE);
        editAltContact.setVisibility(View.VISIBLE);
        editEmail.setVisibility(View.VISIBLE);
        editFullAddress.setVisibility(View.VISIBLE);
        editDob.setVisibility(View.VISIBLE);
        constraintLayout.setVisibility(View.VISIBLE);
    }

    private void visibilityVisible() {
        btnUpdateOp.setVisibility(View.VISIBLE);
        name.setVisibility(View.VISIBLE);
        altcontact.setVisibility(View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        fulladress.setVisibility(View.VISIBLE);
        dob.setVisibility(View.VISIBLE);

        editName.setVisibility(View.GONE);
        editAltContact.setVisibility(View.GONE);
        editEmail.setVisibility(View.GONE);
        editFullAddress.setVisibility(View.GONE);
        editDob.setVisibility(View.GONE);
        constraintLayout.setVisibility(View.GONE);
    }

    private Boolean validateValues() {


        String name = editName.getText().toString().trim();
        String contact = editContact.getText().toString().trim();
        String altcontact = editAltContact.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String fulladdress = editFullAddress.getText().toString().trim();
        String dob = editDob.getText().toString().trim();

        if (name.isEmpty()) {
            editName.setError("Name is required");
            editName.requestFocus();
            return true;
        }

        if (contact.isEmpty()) {
            editContact.setError("Contact is required");
            editContact.requestFocus();
            return true;
        }

        if (!altcontact.isEmpty() && altcontact.length() < 10) {
            editAltContact.setError("Phone must be of at least length 10");
            editAltContact.requestFocus();
            return true;
        }

        if (email.isEmpty()) {
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return true;
        }

        if (fulladdress.isEmpty()) {
            editFullAddress.setError("Full Address is required");
            editFullAddress.requestFocus();
            return true;
        }

        if (dob.isEmpty()) {
            editDob.setError("Dob is required");
            editDob.requestFocus();
            return true;
        }

        return false;
    }

    private void updateFarmer() {
        showProgress();
        String name = editName.getText().toString().trim();
        String contact = editContact.getText().toString().trim();
        String altcontact = editAltContact.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String fulladdress = editFullAddress.getText().toString().trim();
        String dob = editDob.getText().toString().trim();

        UpdateFarmer updateFarmer = new UpdateFarmer(email, name, altcontact, contact, dob, fulladdress);


        Call<Responses.ProfileResponse> call = RetrofitClient.getInstance().getApi().updateFarmerDetail(new PrefManager(UserProfile.this).getToken(), new PrefManager(UserProfile.this).getFarmerId(), updateFarmer);

        call.enqueue(new Callback<Responses.ProfileResponse>() {
            @Override
            public void onResponse(Call<Responses.ProfileResponse> call, Response<Responses.ProfileResponse> response) {
                visibilityVisible();
                hideProgress();
                Responses.ProfileResponse profileResponse = response.body();

                if (response.code() == 200) {
                    if (profileResponse != null) {
                        new PrefManager(UserProfile.this).saveFarmerId("");
                        Toast.makeText(UserProfile.this, profileResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UserProfile.this, Login.class));
                        finish();
                    }
                } else if (response.code() == 401) {
                    Toast.makeText(UserProfile.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    new PrefManager(UserProfile.this).saveLoginDetails("", "");
                    new PrefManager(UserProfile.this).saveUserDetails("", "", "", "", "", "", "", "", "", "", "", "", "");
                    new PrefManager(UserProfile.this).saveAvatar("");
                    new PrefManager(UserProfile.this).saveToken("");
                    new PrefManager(UserProfile.this).saveFarmerId("");
                    startActivity(new Intent(UserProfile.this, Login.class));
                    finish();
                } else if (response.code() == 400) {
                    Toast.makeText(UserProfile.this, "Error: Bad Request!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 409) {
                    Toast.makeText(UserProfile.this, "Conflict Occurred!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 500) {
                    Toast.makeText(UserProfile.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Responses.ProfileResponse> call, Throwable t) {
                visibilityVisible();
                hideProgress();
            }
        });


    }


    private void showChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);
    }

    private void updateProfilePic(Uri imageUri) {
//Uri imageUri
        showProgress();


        File file = FileUtils.getFile(this, imageUri);
        //Objects.requireNonNull(getContentResolver().getType(imageUri))

        //File file = new File(this.getFilesDir() + "/ProfileImage/" + "Pic_" + new PrefManager(UserProfile.this).getPhone() + ".jpg");

        RequestBody requestFile = RequestBody.create(MediaType.parse(Objects.requireNonNull(getContentResolver().getType(imageUri))), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part profilePic = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);

        Call<Responses.AvatarResponse> call = RetrofitClient.getInstance().getApi().updateAvatar(new PrefManager(UserProfile.this).getToken(), new PrefManager(UserProfile.this).getFarmerId(), profilePic);

        call.enqueue(new Callback<Responses.AvatarResponse>() {
            @Override
            public void onResponse(Call<Responses.AvatarResponse> call, Response<Responses.AvatarResponse> response) {
                hideProgress();
                Responses.AvatarResponse avatarResponse = response.body();
                if (response.code() == 200) {

                    if (avatarResponse != null) {

                        new PrefManager(UserProfile.this).saveAvatar(avatarResponse.getAvatar());

                        //Toast.makeText(UserProfile.this, new PrefManager(UserProfile.this).getAvatar(), Toast.LENGTH_SHORT).show();
                        Picasso.get().load("http://br.bharatrohan.in" + new PrefManager(UserProfile.this).getAvatar()).networkPolicy(NetworkPolicy.OFFLINE).into(profileP, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                //Toast.makeText(UserProfile.this, "Didn't got Pic", Toast.LENGTH_SHORT).show();
                                Picasso.get().load("http://br.bharatrohan.in" + new PrefManager(UserProfile.this).getAvatar()).into(profileP);
                            }
                        });

                        Toast.makeText(UserProfile.this, avatarResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else if (response.code() == 401) {
                    Toast.makeText(UserProfile.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    new PrefManager(UserProfile.this).saveLoginDetails("", "");
                    new PrefManager(UserProfile.this).saveUserDetails("", "", "", "", "", "", "", "", "", "", "", "", "");
                    new PrefManager(UserProfile.this).saveAvatar("");
                    new PrefManager(UserProfile.this).saveToken("");
                    new PrefManager(UserProfile.this).saveFarmerId("");
                    startActivity(new Intent(UserProfile.this, Login.class));
                    finish();
                } else if (response.code() == 400) {
                    Toast.makeText(UserProfile.this, "Error: Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 409) {
                    Toast.makeText(UserProfile.this, "Conflict Occurred!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 500) {
                    Toast.makeText(UserProfile.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Responses.AvatarResponse> call, Throwable t) {
                hideProgress();
                Toast.makeText(UserProfile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                final Uri uri = data.getData();
                Log.i(TAG, "Uri = " + uri.toString());
                updateProfilePic(uri);

                /*CropImage.activity(uri).setAspectRatio(1, 1)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setBorderLineColor(Color.RED)
                        .setGuidelinesColor(Color.GREEN)
                        .setMinCropWindowSize(500, 500)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setFixAspectRatio(true)
                        .setScaleType(CropImageView.ScaleType.FIT_CENTER)
                        .setAutoZoomEnabled(true)
                        .start(UserProfile.this);*/
            }
        }
        /*if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {

                File directory = new File(this.getFilesDir(), "ProfileImage");
                if (!directory.exists()) {
                    directory.mkdir();
                }

                File imagefile = new File(directory, "Pic_" + new PrefManager(UserProfile.this).getPhone() + ".jpg");

                Uri resultUri = result.getUri();
                File thumb_filePath = new File(Objects.requireNonNull(resultUri.getPath()));

                Bitmap thumb_bitmap = null;
                try {
                    thumb_bitmap = new Compressor(this)
                            .setMaxHeight(150)
                            .setMaxWidth(150)
                            .setQuality(75)
                            .compressToBitmap(thumb_filePath);


                    FileOutputStream fileos = new FileOutputStream(imagefile);
                    //assert thumb_bitmap != null;
                    thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileos);
                    fileos.flush();
                    fileos.close();
                    MediaStore.Images.Media.insertImage(getContentResolver(), thumb_bitmap, "Profile Pic", "profile pic user");
                } catch (IOException e) {
                    e.printStackTrace();
                }


                *//*try {

                } catch (IOException e) {
                    e.printStackTrace();
                }*//*

                File filePath = new File(this.getFilesDir() + "/ProfileImage");
                File newFile = new File(filePath,"Pic_" + new PrefManager(UserProfile.this).getPhone() + ".jpg");

                Uri imageUri = FileProvider.getUriForFile(this,"in.bharatrohan.fileprovider",newFile);

                updateProfilePic(imageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }*/

        //updateProfilePic(uri);

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    private boolean askForPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            int hasCallPermission = ContextCompat.checkSelfPermission(UserProfile.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            if (hasCallPermission != PackageManager.PERMISSION_GRANTED) {
                // Ask for permission
                // need to request permission
                if (ActivityCompat.shouldShowRequestPermissionRationale(UserProfile.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // explain
                    showMessageOKCancel(
                            (dialogInterface, i) -> ActivityCompat.requestPermissions(UserProfile.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    REQUEST_CODE_ASK_PERMISSIONS));
                    // if denied then working here
                } else {
                    // Request for permission
                    ActivityCompat.requestPermissions(UserProfile.this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_ASK_PERMISSIONS);
                }

                return false;
            } else {
                // permission granted and calling function working
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    showChooser();
                } else {
                    // Permission Denied
                    Toast.makeText(UserProfile.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfile.this);
        final AlertDialog dialog = builder.setMessage("You need to grant access to Read External Storage")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(arg0 -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                    ContextCompat.getColor(UserProfile.this, android.R.color.holo_blue_light));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                    ContextCompat.getColor(UserProfile.this, android.R.color.holo_red_light));
        });

        dialog.show();

    }
}
