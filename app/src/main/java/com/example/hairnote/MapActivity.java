package com.example.hairnote;

import static com.example.hairnote.BuildConfig.MAPS_API_KEY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSIONS_REQUEST_CODE = 2001;
    private static final int PERMISSION_REQUEST_ENABLE_GPS = 2002;
    private static final float DEFAULT_ZOOM = 15f;

    DrawerLayout drawerLayout;
    SupportMapFragment mapFragment;
    GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;
    Location currentLocation;
    String searchForShop = "hospital";
    double latitude = 50.068826;
    double longitude = 19.9031067;
    int proximityRadius = 10000;
    FloatingActionButton btnShowOnMap;
    Object transferData[];

    private Boolean locationPermissionsGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        drawerLayout = findViewById(R.id.drawer_layout);
        setActionBar();
        btnShowOnMap = findViewById(R.id.btnShowOnMap);

        transferData = new Object[2];
        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getLocationPermissions();
        //displayFoundShops();

        btnShowOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG,"przed czyszzceniem mapy");
                map.clear();
                Log.e(TAG,"za czyszczeniem");
                String url = getUrl(latitude, longitude, "hospital");
                Log.e(TAG,"za url");
                transferData[0] = map;
                transferData[1] = url;

                getNearbyPlaces.execute(transferData);
                Log.e(TAG,"po transferze");
                Toast.makeText(MapActivity.this, "Searching for shops", Toast.LENGTH_SHORT).show();
                Toast.makeText(MapActivity.this, "Showing found shops", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //@SuppressLint("MissingPermission")
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        Toast.makeText(this, "Mapa jest gotowa", Toast.LENGTH_SHORT).show();
        map = googleMap;

        if (locationPermissionsGranted) {
            getUserCurrentLocation();
            map.setMyLocationEnabled(true);
            //map.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    public  void initializeMap(){
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }

    public void getLocationPermissions(){
        String[] permissions = {FINE_LOCATION, COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(MapActivity.this, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(MapActivity.this, COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationPermissionsGranted = true;
                initializeMap();
            }
            else {
                ActivityCompat.requestPermissions(MapActivity.this, permissions, LOCATION_PERMISSIONS_REQUEST_CODE);
            }
        }
        else {
            ActivityCompat.requestPermissions(MapActivity.this, permissions, LOCATION_PERMISSIONS_REQUEST_CODE);
        }
    }

    public boolean isGpsEnabled(){

        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.e(TAG, "isGpsEnabled: called");
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.e(TAG, "isGpsEnabled: false");
            askToTurnOnGps();
            return false;
        }
        else {
            Log.e(TAG, "isGpsEnabled: true");
        }

        return true;
    }

    public void askToTurnOnGps(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
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
        switch (requestCode) {
            case PERMISSION_REQUEST_ENABLE_GPS:
                if (locationPermissionsGranted) {
                    initializeMap();
                }
                else {
                    getLocationPermissions();
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        locationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            locationPermissionsGranted = false;
                            return;
                        }
                    }
                    locationPermissionsGranted = true;
                    initializeMap();
                    getUserCurrentLocation();
                }
        }
    }

    public void getUserCurrentLocation(){

        if (locationPermissionsGranted) {
            @SuppressLint("MissingPermission") Task location = fusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Log.e(TAG, "onComplete: last known location was found");
                        currentLocation = (Location) task.getResult();
                        if (currentLocation != null) {
                            Log.e(TAG, "onComplete: last known location is not null");
                            LatLng latLng = new LatLng(latitude, longitude);
                            centralizeCamera(latLng,DEFAULT_ZOOM);
                        }
                        else {
                            Log.e(TAG, "onComplete: last known location is null");
                        }
                    }
                    else {
                        Log.e(TAG, "onComplete: current location is null");
                        Toast.makeText(MapActivity.this, "Niemożna pobrać obecnej lokalizacji", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void centralizeCamera(LatLng latLng, float zoom){
        Log.e(TAG, "centralizeCamera: moving camera");
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    public void setActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setTitle("Mapa");
    }

    public String getUrl(double latitude, double longitude, String string){

        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        stringBuilder.append("location=" + latitude + "," + longitude);
        stringBuilder.append("&radius=" + proximityRadius);
        stringBuilder.append("&type=" + "hospital");
        stringBuilder.append("&sensor=true");
        stringBuilder.append("&key=" + MAPS_API_KEY);

        Log.e(TAG,"url = " + stringBuilder.toString());

        return stringBuilder.toString();
    }

    public void displayFoundShops(){
        //map.clear();
        Log.e(TAG,"przed nearbyplaces");
        String url = getUrl(latitude, longitude, searchForShop);
        Object transferData[] = new Object[2];
        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
        Log.e(TAG,"za nearbyplaces");
        transferData[0] = map;
        transferData[1] = url;

        getNearbyPlaces.execute(transferData);
        Toast.makeText(MapActivity.this, "Searching for shops", Toast.LENGTH_SHORT).show();
        Toast.makeText(MapActivity.this, "Showing found shops", Toast.LENGTH_SHORT).show();

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

    public static String readUrl(String placeUrl) throws IOException {

        String Data = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

        try {
            URL url = new URL(placeUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();

            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            Data = stringBuffer.toString();
            bufferedReader.close();

        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            inputStream.close();
            httpURLConnection.disconnect();
        }
        
        return Data;

    }

    public HashMap<String, String> getNearbyPlace(JSONObject placeJSON){

        HashMap<String, String> placeMap = new HashMap<>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "-NA-";
        String longitude = "-NA-";
        String reference = "-NA-";

        try {
            if (!placeJSON.isNull("name")){
                placeName = placeJSON.getString("name");
            }
            if (!placeJSON.isNull("vicinity")){
                vicinity = placeJSON.getString("vicinity");
            }
            latitude = placeJSON.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude= placeJSON.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = placeJSON.getString("reference");

            placeMap.put("place_name", placeName);
            placeMap.put("vicinity", vicinity);
            placeMap.put("lat", latitude);
            placeMap.put("lng", longitude);
            placeMap.put("reference", reference);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return placeMap;
    }

    public List<HashMap<String, String>> getAllNearByPlaces(JSONArray jsonArray){

        int counter = jsonArray.length();
        List<HashMap<String, String>> placesList = new ArrayList<>();
        HashMap<String, String> placeMap = null;

        for (int i = 0; i < counter; i++) {
            try {
                placeMap = getNearbyPlace((JSONObject) jsonArray.get(i));
                placesList.add(placeMap);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return placesList;
    }

    public List<HashMap<String, String>> parseData(String data){

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(data);
            jsonArray = jsonObject.getJSONArray("results");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return getAllNearByPlaces(jsonArray);
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
        if(isGpsEnabled()){
            if (locationPermissionsGranted) {
                initializeMap();
            }
        }
    }


}