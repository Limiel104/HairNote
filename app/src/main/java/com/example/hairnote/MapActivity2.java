package com.example.hairnote;

import static com.example.hairnote.BuildConfig.MAPS_API_KEY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapActivity2 extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity2";

    private static final int REQUEST_CODE = 3001;
    private static final int PERMISSION_REQUEST_ENABLE_GPS = 3002;

    DrawerLayout drawerLayout;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double lat, lng;
    ImageButton btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);

        drawerLayout = findViewById(R.id.drawer_layout);
        setActionBar();

        btn1 = findViewById(R.id.imagebtn);
        btn2 = findViewById(R.id.imagebtn2);
        btn3 = findViewById(R.id.imagebtn3);
        btn4 = findViewById(R.id.imagebtn4);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(MapActivity2.this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapActivity2.this);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                stringBuilder.append("location=" + lat + "," + lng);
                stringBuilder.append("&radius=3000");
                stringBuilder.append("&type=hair_care");
                stringBuilder.append("&sensor=true");
                stringBuilder.append("&key=" + MAPS_API_KEY);

                String url = stringBuilder.toString();

                Object dataFetch[] = new Object[2];
                dataFetch[0] = mMap;
                dataFetch[1] = url;

                FetchData fetchData = new FetchData();
                fetchData.execute(dataFetch);

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                stringBuilder.append("location=" + lat + "," + lng);
                stringBuilder.append("&radius=3000");
                stringBuilder.append("&type=drugstore");
                stringBuilder.append("&sensor=true");
                stringBuilder.append("&key=" + MAPS_API_KEY);

                String url = stringBuilder.toString();

                Object dataFech[] = new Object[2];
                dataFech[0] = mMap;
                dataFech[1] = url;

                FetchData fetchData = new FetchData();
                fetchData.execute(dataFech);

            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
//                StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
//                stringBuilder.append("location=" + lat + "," + lng);
//                stringBuilder.append("&radius=3000");
//                stringBuilder.append("&type=pharmacy");
//                stringBuilder.append("&sensor=true");
//                stringBuilder.append("&key=" + MAPS_API_KEY);

                StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/textsearch/json?");
                stringBuilder.append("query=rossmann%20Krakow");
                stringBuilder.append("&key=" + MAPS_API_KEY);

                String url = stringBuilder.toString();

                Object dataFech[] = new Object[2];
                dataFech[0] = mMap;
                dataFech[1] = url;

                FetchData fetchData = new FetchData();
                fetchData.execute(dataFech);

            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();

                StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?");
                stringBuilder.append("fields=name,geometry");
                stringBuilder.append("&input=biedronka");
                stringBuilder.append("&inputtype=textquery");
                stringBuilder.append("&inputtype=textquery");
                stringBuilder.append("&key=" + MAPS_API_KEY);

                String url = stringBuilder.toString();

                Object dataFech2[] = new Object[2];
                dataFech2[0] = mMap;
                dataFech2[1] = url;

                FetchData2 fetchData2 = new FetchData2();
                fetchData2.execute(dataFech2);

            }
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //mMap = googleMap;

        Toast.makeText(this, "Mapa jest gotowa", Toast.LENGTH_SHORT).show();
        mMap = googleMap;
        getCurrentLocation();
        Log.e("Map2","onMapReady");

    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        if (!isGpsEnabled()) {

            askToTurnOnGps();
        }

        Log.e("Map2","getCurrentLocation");

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(6000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(5000);
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Toast.makeText(MapActivity2.this, "location result is = " + locationResult, Toast.LENGTH_SHORT).show();

                if (locationResult == null) {
                    Toast.makeText(MapActivity2.this, "Current location is null ", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (Location location:locationResult.getLocations()){
                    if (location != null) {
                        Toast.makeText(MapActivity2.this, "location result is = " + location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();

                    Log.e("Map2","Task");

                    LatLng latLng = new LatLng(lat, lng);
                    //mMap.addMarker(new MarkerOptions().position(latLng).title("current location"));
                    mMap.setMyLocationEnabled(true);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }
            }
        });

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (REQUEST_CODE) {
            case REQUEST_CODE:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                }
        }

    }

    public boolean isGpsEnabled(){

        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.e(TAG, "isGpsEnabled: called");
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.e(TAG, "isGpsEnabled: false");
            //askToTurnOnGps();
            return false;
        }
        else {
            Log.e(TAG, "isGpsEnabled: true");
        }

        return true;
    }

    public void askToTurnOnGps(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity2.this);
        builder.setMessage("Ta funkcja aplikacji wymaga włączonej lokalizacji do działania. Czy chcesz ją włączyć?")
                .setCancelable(false)
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent enableGps = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGps, PERMISSION_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: called");
        switch (requestCode) {
            case PERMISSION_REQUEST_ENABLE_GPS:
                getCurrentLocation();
        }
    }

    public void setActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setTitle("Mapa");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ClickMenu(View view){
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        MainActivity.closeDrawer(drawerLayout);
    }

    public void  ClickWash(View view){
        MainActivity.redirectActivity(this, MainActivity.class);
        finish();
    }

    public void ClickCosmetic(View view){
        MainActivity.redirectActivity(this, CosmeticActivity.class);
        finish();
    }

    public void ClickIngredient(View view){
        MainActivity.redirectActivity(this,IngredientActivity.class);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }

    protected void onResume() {
        super.onResume();

        if (isGpsEnabled()) {
            getCurrentLocation();
        }

    }

}