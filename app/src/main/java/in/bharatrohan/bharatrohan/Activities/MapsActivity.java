package in.bharatrohan.bharatrohan.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import in.bharatrohan.bharatrohan.CheckInternet;
import in.bharatrohan.bharatrohan.Parser;
import in.bharatrohan.bharatrohan.PrefManager;
import in.bharatrohan.bharatrohan.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private FusedLocationProviderClient mClient;
    private Boolean mLocationPermissionGranted = false;
    private static final int LOC_PER_REQ_CODE = 123;
    private Polygon shape;
    private ArrayList<Marker> markers = new ArrayList<Marker>();
    private Button reset, select, save;
    private int counter = 0;
    private ArrayList<Double> lats, longs;
    private Parser kmlParser = new Parser();
    private XmlSerializer xmlSerializer;
    FileOutputStream fileos;
    private String landName, phone;
    private ConstraintLayout constr;

    private TextView instruction;
    private LinearLayout functions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        new CheckInternet(this).checkConnection();


        lats = new ArrayList<>();
        longs = new ArrayList<>();

        reset = findViewById(R.id.reset);
        select = findViewById(R.id.select);
        save = findViewById(R.id.save);
        instruction = findViewById(R.id.tvIns);
        functions = findViewById(R.id.functionLayout);

        constr = findViewById(R.id.constr);


        select.setOnClickListener(v -> {
            select.setVisibility(View.GONE);
            save.setVisibility(View.VISIBLE);
            reset.setVisibility(View.VISIBLE);
            drawPolygon();
        });

        reset.setOnClickListener(v -> {
            reset.setVisibility(View.GONE);
            save.setVisibility(View.GONE);
            select.setVisibility(View.VISIBLE);
            functions.setVisibility(View.GONE);
            instruction.setVisibility(View.VISIBLE);
            if (markers != null) {
                for (Marker marker : markers) {
                    marker.remove();
                }
                markers.clear();
            }

            if (shape != null && counter != 0) {
                shape.remove();
                shape = null;
                counter = 0;
            }

        });

        save.setOnClickListener(v -> {

            createKml();
            takeScreenshot(MapsActivity.this);
            new PrefManager(MapsActivity.this).saveKmlCreateStatus(true);
            finish();


        });

        if (googleServicesAvailable()) {
        }

        checkPermissions();


    }

    private void createKml() {

        StringBuilder sb = new StringBuilder();
        try {

            landName = getIntent().getStringExtra("landName");
            phone = getIntent().getStringExtra("phone");

            File directory = new File(this.getFilesDir(), "LandKmls");
            if (!directory.exists()) {
                directory.mkdir();
            }
            File kmlfile = new File(directory, "Land_" + new PrefManager(MapsActivity.this).getPhone() + ".kml");

            FileOutputStream fileos = new FileOutputStream(kmlfile);
            xmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
            xmlSerializer.setOutput(fileos, "UTF-8");
            xmlSerializer.startDocument(null, null);
            xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            xmlSerializer.startTag(null, "kml");
            xmlSerializer.attribute(null, "xmlns", "http://www.opengis.net/kml/2.2");
            xmlSerializer.attribute(null, "xmlns:gx", "http://www.google.com/kml/ext/2.2");
            xmlSerializer.attribute(null, "xmlns:kml", "http://www.opengis.net/kml/2.2");
            xmlSerializer.attribute(null, "xmlns:atom", "http://www.w3.org/2005/Atom");

            xmlSerializer.startTag(null, "Document");

            xmlSerializer.startTag(null, "name");
            xmlSerializer.text("Land");
            xmlSerializer.endTag(null, "name");

            //Style1
            xmlSerializer.startTag(null, "Style");
            xmlSerializer.attribute(null, "id", "s_ylw-pushpin_hl");

            xmlSerializer.startTag(null, "IconStyle");

            xmlSerializer.startTag(null, "scale");
            xmlSerializer.text("1.3");
            xmlSerializer.endTag(null, "scale");


            xmlSerializer.startTag(null, "Icon");

            xmlSerializer.startTag(null, "href");
            xmlSerializer.text("http://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png");
            xmlSerializer.endTag(null, "href");

            xmlSerializer.endTag(null, "Icon");


            xmlSerializer.startTag(null, "hotSpot");
            xmlSerializer.attribute(null, "x", "20");
            xmlSerializer.attribute(null, "y", "2");
            xmlSerializer.attribute(null, "xunits", "pixels");
            xmlSerializer.attribute(null, "xunits", "pixels");
            xmlSerializer.endTag(null, "hotSpot");


            xmlSerializer.endTag(null, "IconStyle");

            xmlSerializer.endTag(null, "Style");

            //Style1


            //Style2
            xmlSerializer.startTag(null, "Style");
            xmlSerializer.attribute(null, "id", "s_ylw-pushpin");


            xmlSerializer.startTag(null, "IconStyle");

            xmlSerializer.startTag(null, "scale");
            xmlSerializer.text("1.1");
            xmlSerializer.endTag(null, "scale");


            xmlSerializer.startTag(null, "Icon");

            xmlSerializer.startTag(null, "href");
            xmlSerializer.text("http://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png");
            xmlSerializer.endTag(null, "href");

            xmlSerializer.endTag(null, "Icon");


            xmlSerializer.startTag(null, "hotSpot");
            xmlSerializer.attribute(null, "x", "20");
            xmlSerializer.attribute(null, "y", "2");
            xmlSerializer.attribute(null, "xunits", "pixels");
            xmlSerializer.attribute(null, "xunits", "pixels");
            xmlSerializer.endTag(null, "hotSpot");


            xmlSerializer.endTag(null, "IconStyle");

            xmlSerializer.endTag(null, "Style");

            //Style2


            //StyleMAp
            xmlSerializer.startTag(null, "StyleMap");
            xmlSerializer.attribute(null, "id", "m_ylw-pushpin");


            xmlSerializer.startTag(null, "Pair");

            xmlSerializer.startTag(null, "key");
            xmlSerializer.text("normal");
            xmlSerializer.endTag(null, "key");

            xmlSerializer.startTag(null, "styleUrl");
            xmlSerializer.text("#s_ylw-pushpin");
            xmlSerializer.endTag(null, "styleUrl");

            xmlSerializer.endTag(null, "Pair");


            xmlSerializer.startTag(null, "Pair");

            xmlSerializer.startTag(null, "key");
            xmlSerializer.text("highlight");
            xmlSerializer.endTag(null, "key");

            xmlSerializer.startTag(null, "styleUrl");
            xmlSerializer.text("#s_ylw-pushpin_hl");
            xmlSerializer.endTag(null, "styleUrl");

            xmlSerializer.endTag(null, "Pair");


            xmlSerializer.endTag(null, "StyleMap");

            //StyleMap


            //Placemark
            xmlSerializer.startTag(null, "Placemark");
            xmlSerializer.startTag(null, "name");
            xmlSerializer.text("" + landName);
            xmlSerializer.endTag(null, "name");

            xmlSerializer.startTag(null, "styleUrl");
            xmlSerializer.text("#m_ylw-pushpin");
            xmlSerializer.endTag(null, "styleUrl");

            xmlSerializer.startTag(null, "Polygon");


            xmlSerializer.startTag(null, "tessellate");
            xmlSerializer.text("1");
            xmlSerializer.endTag(null, "tessellate");

            xmlSerializer.startTag(null, "outerBoundaryIs");

            xmlSerializer.startTag(null, "LinearRing");

            xmlSerializer.startTag(null, "coordinates");
            sb.setLength(0);

            /*This is the part where I get the size of the path arraylist
             * and start printing the coordinates for my path*/

            int size = lats.size();
            for (int x = 0; x < size; x++) {

                sb.append(Double.toString((Double) longs.get(x)));
                sb.append(",");
                sb.append(Double.toString((Double) lats.get(x)));
                sb.append(",0\n");
            }

            sb.append(Double.toString((Double) longs.get(0)));
            sb.append(",");
            sb.append(Double.toString((Double) lats.get(0)));
            sb.append(",0");
            String s = sb.toString();

            xmlSerializer.text(s);
            xmlSerializer.endTag(null, "coordinates");

            xmlSerializer.endTag(null, "LinearRing");

            xmlSerializer.endTag(null, "outerBoundaryIs");

            xmlSerializer.endTag(null, "Polygon");

            xmlSerializer.endTag(null, "Placemark");


            xmlSerializer.endTag(null, "Document");
            xmlSerializer.endTag(null, "kml");
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            fileos.close();

            //String absolutePath = getBaseContext().getFileStreamPath("Land_" + phone + ".kml").getAbsolutePath();

            //Toast.makeText(this, absolutePath, Toast.LENGTH_SHORT).show();

        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error in Generating Kml File.Please Try Again!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void takeScreenshot(Context ctx) {

        landName = getIntent().getStringExtra("landName");
        phone = getIntent().getStringExtra("phone");


        GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {

            Bitmap snapShot;

            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                snapShot = bitmap;

                File directory = new File(ctx.getFilesDir(), "LandImage");
                if (!directory.exists()) {
                    directory.mkdir();
                }

                File imagefile = new File(directory, "Land_" + new PrefManager(MapsActivity.this).getPhone() + ".jpg");

                try {
                    FileOutputStream fileos = new FileOutputStream(imagefile);
                    snapShot.compress(Bitmap.CompressFormat.JPEG, 75, fileos);
                    fileos.flush();
                    fileos.close();
                    MediaStore.Images.Media.insertImage(getContentResolver(), snapShot, "Screen", "screen");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        mMap.snapshot(callback);
    }


    private void drawPolygon() {
        if (counter >= 3) {
            PolygonOptions options = new PolygonOptions()
                    .fillColor(0x330000FF)
                    .strokeWidth(2)
                    .strokeColor(Color.RED);

            for (int i = 0; i < counter; i++) {
                options.add(markers.get(i).getPosition());
            }

            shape = mMap.addPolygon(options);
        } else {
            Toast.makeText(MapsActivity.this, "Mark at least 3 points", Toast.LENGTH_SHORT).show();
        }
    }

    private void setMarker(double lat, double lng) {

        counter = counter + 1;

        if (counter == 1) {
            instruction.setVisibility(View.GONE);
        }

        if (counter >= 3) {
            functions.setVisibility(View.VISIBLE);
        }

        MarkerOptions options = new MarkerOptions()
                .draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(new LatLng(lat, lng));

        markers.add(mMap.addMarker(options));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        mClient = LocationServices.getFusedLocationProviderClient(this);

        getDeviceLocation();

        mMap.setOnMapLongClickListener(latLng -> {
            MapsActivity.this.setMarker(latLng.latitude, latLng.longitude);

            lats.add(latLng.latitude);
            longs.add(latLng.longitude);
        });
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getDeviceLocation() {
        FusedLocationProviderClient mClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionGranted) {
                mClient.getLastLocation().addOnSuccessListener(this, location -> {
                    if (location != null) {
                        goToLocationZoom(new LatLng(location.getLatitude(), location.getLongitude()));

                        Toast.makeText(MapsActivity.this, "Location: " + location.getLatitude() + "," + location.getLongitude(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MapsActivity.this, "Unable to get Current Location", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } catch (SecurityException e) {
            Toast.makeText(this, "Something went wrong" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void goToLocationZoom(LatLng location) {
        mMap.addMarker(new MarkerOptions().position(location).title("Your Current Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, (float) 15));
    }

    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't connect to play services", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {

        if (location == null) {
            Toast.makeText(this, "Can't get current location", Toast.LENGTH_SHORT).show();
        } else {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            goToLocationZoom(latLng);

        }
    }


    private void checkPermissions() {

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    initMap();
                } else {
                    ActivityCompat.requestPermissions(this, permissions, LOC_PER_REQ_CODE);
                }
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOC_PER_REQ_CODE);
            }

        } else {
            ActivityCompat.requestPermissions(this, permissions, LOC_PER_REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        mLocationPermissionGranted = false;
        switch (requestCode) {

            case LOC_PER_REQ_CODE: {

                if (grantResults.length > 0) {

                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    initMap();
                }
            }
        }

        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
