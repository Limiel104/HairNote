package com.example.hairnote;

import static com.example.hairnote.BuildConfig.MAPS_API_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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

public class MapActivity2 extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 3001;
    private double lat, lng;
    ImageButton btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);

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
                stringBuilder.append("&radius=1000");
                stringBuilder.append("&type=atm");
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

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                stringBuilder.append("location=" + lat + "," + lng);
                stringBuilder.append("&radius=1000");
                stringBuilder.append("&type=hospital");
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
                StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                stringBuilder.append("location=" + lat + "," + lng);
                stringBuilder.append("&radius=1000");
                stringBuilder.append("&type=restaurant");
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

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                stringBuilder.append("location=" + lat + "," + lng);
                stringBuilder.append("&radius=1000");
                stringBuilder.append("&type=bank");
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
                    mMap.addMarker(new MarkerOptions().position(latLng).title("current location"));
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
}