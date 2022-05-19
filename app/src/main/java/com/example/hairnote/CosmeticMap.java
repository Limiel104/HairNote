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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
import com.google.maps.GeoApiContext;

import java.util.ArrayList;
import java.util.List;

public class CosmeticMap extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "CosmeticMap";

    private static final int REQUEST_CODE = 5001;
    private static final int PERMISSION_REQUEST_ENABLE_GPS = 5002;

    DrawerLayout drawerLayout;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double lat, lng;
    ArrayList<Cosmetic> selectedCosmetics;
    private GeoApiContext mGeoApiContext = null;
    ArrayList<String> shopsToLocate;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_map);

        drawerLayout = findViewById(R.id.drawer_layout);
        setActionBar();

        dataBaseHelper = new DataBaseHelper(CosmeticMap.this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(CosmeticMap.this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(CosmeticMap.this);

        selectedCosmetics = new ArrayList<>();
        selectedCosmetics = (ArrayList<Cosmetic>) getIntent().getSerializableExtra("CosmeticsListExtra");

        shopsToLocate = new ArrayList<>();

        String s = "";
        for (int i=0; i<selectedCosmetics.size(); i++) {
            s = s + selectedCosmetics.get(i).getName() + ", ";
        }

        Log.e(TAG, s);


        if(mGeoApiContext == null) {
            mGeoApiContext = new GeoApiContext.Builder().apiKey(MAPS_API_KEY).build();
        }

        //Toast.makeText(CosmeticMap.this, "elo elo", Toast.LENGTH_SHORT).show();

        filterShops();

        String d = "";
        for (int i=0; i<shopsToLocate.size(); i++) {
            d = d + shopsToLocate.get(i)+ ", ";
        }

        Log.e(TAG, d);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        //Toast.makeText(this, "Mapa jest gotowa", Toast.LENGTH_SHORT).show();
        mMap = googleMap;
        setRoute();
        Log.e("Map2","onMapReady");
    }

    @SuppressLint("MissingPermission")
    private void setRoute(){

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
                //Toast.makeText(CosmeticMap.this, "location result is = " + locationResult, Toast.LENGTH_SHORT).show();

                if (locationResult == null) {
                    Toast.makeText(CosmeticMap.this, "Nie pobrano lokalizacji", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (Location location:locationResult.getLocations()){
                    if (location != null) {
                        //Toast.makeText(CosmeticMap.this, "location result is = " + location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();
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

                    LatLng userLatLng = new LatLng(lat, lng);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(userLatLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15));

                    //setShopMarkers(userLatLng); !!!!!!

                }
            }
        });

    }

    public void filterShops(){

        for (int i = 0; i < selectedCosmetics.size(); i++) {

            List<String> cosmeticShops =  dataBaseHelper.getAllShopBrandsFromCosmetic(selectedCosmetics.get(i).getId());

            for (int j = 0; j < cosmeticShops.size(); j++) {
                if (!shopsToLocate.contains(cosmeticShops.get(j))) {
                    shopsToLocate.add(cosmeticShops.get(j));
                }
            }
        }
    }

    public void setShopMarkers(LatLng userLatLng){

        for (int i = 0; i < shopsToLocate.size(); i++){

        }


    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (REQUEST_CODE) {
            case REQUEST_CODE:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setRoute();
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

        final AlertDialog.Builder builder = new AlertDialog.Builder(CosmeticMap.this);
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
                setRoute();
        }
    }

    public void setActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setTitle("Wybrane kosmetyki - trasa");
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
            default:
                return super.onOptionsItemSelected(item);
        }
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
            setRoute();
        }

    }

}