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
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CosmeticMap2 extends AppCompatActivity implements OnMapReadyCallback{

    private static final String TAG = "CosmeticMap2";

    private static final int REQUEST_CODE = 5001;
    private static final int PERMISSION_REQUEST_ENABLE_GPS = 5002;
    private static final int REQUEST_DISTANCE_CALCULATION = 5003;

    DrawerLayout drawerLayout;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double lat, lng;
    ArrayList<Cosmetic> selectedCosmetics;
    private GeoApiContext mGeoApiContext = null;
    ArrayList<String> shopsToLocate;
    DataBaseHelper dataBaseHelper;
    private boolean lock = false;
    LatLng userLatLng;
    ArrayList<Shop> finalShops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_map2);

        drawerLayout = findViewById(R.id.drawer_layout);
        setActionBar();

        dataBaseHelper = new DataBaseHelper(CosmeticMap2.this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(CosmeticMap2.this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(CosmeticMap2.this);

        selectedCosmetics = new ArrayList<>();
        selectedCosmetics = (ArrayList<Cosmetic>) getIntent().getSerializableExtra("CosmeticsListExtra");

        shopsToLocate = new ArrayList<>();

        if(mGeoApiContext == null) {
            mGeoApiContext = new GeoApiContext.Builder().apiKey(MAPS_API_KEY).build();
        }

        filterShops();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;
        calculateDistances();
        Log.e("Map2","onMapReady");
    }

    @SuppressLint("MissingPermission")
    private void calculateDistances(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        if (!isGpsEnabled()) {

            askToTurnOnGps();
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(6000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(5000);
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {

                if (locationResult == null) {
                    Toast.makeText(CosmeticMap2.this, "Nie pobrano lokalizacji", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (Location location:locationResult.getLocations()){
                    if (location != null) {

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

                    userLatLng = new LatLng(lat, lng);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(userLatLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 13));

                    if(lock == false) {
                        lock = true;
                        String latSend = String.valueOf(userLatLng.latitude);
                        String lngSend = String.valueOf(userLatLng.longitude);
                        shopsToLocate.add(latSend);
                        shopsToLocate.add(lngSend);

                        Intent intent = new Intent(CosmeticMap2.this, CosmeticMap2Helper.class);
                        intent.putExtra("ShopsToLocateExtra", shopsToLocate);
                        startActivityForResult(intent, REQUEST_DISTANCE_CALCULATION);

                    }
                }
            }
        });

    }

    public void filterShops(){

        for (int i = 0; i < selectedCosmetics.size(); i++) {
            selectedCosmetics.get(i).setShopList(dataBaseHelper.getAllShopBrandsFromCosmetic(selectedCosmetics.get(i).getId()));
        }

        for (int i = 0; i < selectedCosmetics.size(); i++) {
            Log.e(TAG, String.valueOf(selectedCosmetics.get(i).getShopList()));
        }

        ArrayList<Cosmetic> toFoundCos = (ArrayList<Cosmetic>) selectedCosmetics.clone();
        ArrayList<Integer> foundCos = new ArrayList<>();
        boolean finish = false;

        while (!finish){

            String foundShop = findBestMatch(toFoundCos);
            shopsToLocate.add(foundShop);
            toFoundCos.clear();
            Log.e(TAG, "ello");

            for (int i = 0; i < selectedCosmetics.size(); i++) {
                List<String> list = selectedCosmetics.get(i).getShopList();

                if (!foundCos.contains(selectedCosmetics.get(i).getId())) {
                    for (int j = 0; j < list.size(); j++) {
                        if (list.get(j).equals(foundShop)) {
                            Log.e(TAG, "tak " + list.get(j) + " " + foundShop);
                            foundCos.add(selectedCosmetics.get(i).getId());
                        } else {
                            Log.e(TAG, "nie " + list.get(j) + " " + foundShop);
                        }
                    }
                }

            }

            Log.e(TAG, "Rozmiar: " + foundCos.size());

            if (selectedCosmetics.size() != foundCos.size()) {
                Log.e(TAG, "jeszcze nie");
                Log.e(TAG,"Juz znalezione:" + foundCos.toString());

                for (int i=0; i<selectedCosmetics.size(); i++){
                    if (!foundCos.contains(selectedCosmetics.get(i).getId())){
                        toFoundCos.add(selectedCosmetics.get(i));
                    }
                }

            } else {
                Log.e(TAG, "juz tak");
                finish = true;
            }

            for (int g=0; g<toFoundCos.size(); g++){
                Log.e(TAG, "Zostalo do znalezienia: " + toFoundCos.toString());
            }


        }

        for (int i=0; i<shopsToLocate.size(); i++) {
            Log.e(TAG, "Wszystkie sklepy " + shopsToLocate.get(i).toString());
        }

    }

    public String findBestMatch(ArrayList<Cosmetic> selectedCosmetics2){

        HashMap<String, Integer> test = new HashMap<>();
        test.put("Hebe", 0);
        test.put("Rossmann", 0);
        test.put("Natura", 0);
        test.put("Pigment", 0);
        test.put("Ziaja dla Ciebie", 0);

        String returnShop = "";

        for (int i =0; i<selectedCosmetics2.size(); i++) {
            for (int j = 0; j<selectedCosmetics2.get(i).getShopList().size(); j++) {

                String shopName = selectedCosmetics2.get(i).getShopList().get(j);

                if (shopName.equals("Hebe")){
                    test.put("Hebe", test.get("Hebe") + 1);
                }
                else if (shopName.equals("Rossmann")){
                    test.put("Rossmann", test.get("Rossmann") + 1);
                }
                else if (shopName.equals("Pigment")){
                    test.put("Pigment", test.get("Pigment") + 1);
                }
                else if (shopName.equals("Natura")){
                    test.put("Natura", test.get("Natura") + 1);
                }
                else if (shopName.equals("Ziaja dla Ciebie")){
                    test.put("Ziaja dla Ciebie", test.get("Ziaja dla Ciebie") + 1);
                }
            }
        }

        int maxValueInMap=(Collections.max(test.values()));
        for (Map.Entry<String, Integer> entry : test.entrySet()) {
            if (entry.getValue()==maxValueInMap) {
                returnShop = entry.getKey();
            }
        }

        Log.e(TAG, returnShop + "    ret ");
        return returnShop;

    }

    public  void filterReturnedDistance(ArrayList<Double> allDistanceList, ArrayList<Integer> allIdList){

        finalShops = new ArrayList<>();

        for (int i = 0; i < shopsToLocate.size()-2; i++) { //bo tam jest jeszcze userLocation
            finalShops.add(new Shop(-1,"default","default",0,0));
        }

        int k = 0;

        for (int i = 0; i < shopsToLocate.size()-2; i++) {

            double minDistance = 100000000;
            int numberOfShops = dataBaseHelper.getNumberOfAllShopsFromOneBrand(shopsToLocate.get(i));
            Log.e(TAG, "numer sklepow dla marki " + shopsToLocate.get(i) + ": " + numberOfShops);

            for (int j = 0; j < numberOfShops; j++) {
                if(allDistanceList.get(k) < minDistance) {
                    Shop shop = dataBaseHelper.findShop(allIdList.get(k));
                    finalShops.set(i, shop);
                    minDistance = allDistanceList.get(k);
                    Log.e(TAG, "distance k = " + k + " " + "meters = " + allDistanceList.get(k));
                }
                k++;
            }
        }

        Log.e(TAG, finalShops.toString());
        setShopMarkers();

    }

    public void setShopMarkers(){

        MarkerOptions userMarkerOptions = new MarkerOptions();
        userMarkerOptions.position(userLatLng);
        userMarkerOptions.title("Twoja pozycja");

        mMap.addMarker(userMarkerOptions);

        for (int i = 0; i < finalShops.size(); i++) {

            Log.e(TAG, "punkt: " + i + " lat = " + finalShops.get(i).getLat() + " lng = " + finalShops.get(i).getLng() + " name = " + finalShops.get(i).getName() + " id = " + finalShops.get(i).getId());

            LatLng latLng = new LatLng(finalShops.get(i).getLat(), finalShops.get(i).getLng());

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title(finalShops.get(i).getName());
            markerOptions.position(latLng);
            markerOptions.snippet(finalShops.get(i).getAddress());

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
        }

        finalShops.add(0,new Shop(-1, "Twoja pozycja", "Twoj adres", userLatLng.latitude, userLatLng.longitude));
        Log.e(TAG, finalShops.toString());

        for (int i = 0; i < finalShops.size()-1; i++) {

            LatLng latLngOrigin = new LatLng(finalShops.get(i).getLat(), finalShops.get(i).getLng());
            LatLng latLngDest = new LatLng(finalShops.get(i+1).getLat(), finalShops.get(i+1).getLng());

            calculateRoutesForMarkers(latLngOrigin, latLngDest, finalShops.get(i).getName(), finalShops.get(i+1).getName());
        }
    }

    public  void  calculateRoutesForMarkers(LatLng latLngOrigin, LatLng latLngDest, String origin, String dest){

        DirectionsApiRequest directionsApiRequest = new DirectionsApiRequest(mGeoApiContext);
        directionsApiRequest.alternatives(false);

        directionsApiRequest.origin(new com.google.maps.model.LatLng(latLngOrigin.latitude, latLngOrigin.longitude));
        directionsApiRequest.destination(new com.google.maps.model.LatLng(latLngDest.latitude, latLngDest.longitude)).setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                Log.e(TAG, "kalkulacja dla " + origin + " " + dest + ": " + result.routes[0].legs[0].distance);
                setPolylinesForRoutes(result);
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    public void setPolylinesForRoutes(final DirectionsResult directionsResult){
        
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                for (DirectionsRoute route: directionsResult.routes) {
                    List<com.google.maps.model.LatLng> path = PolylineEncoding.decode(route.overviewPolyline.getEncodedPath());

                    List<LatLng> newPath = new ArrayList<>();

                    for (com.google.maps.model.LatLng latLng: path) {
                        newPath.add(new LatLng(latLng.lat, latLng.lng));
                    }

                    Polyline polyline = mMap.addPolyline(new PolylineOptions().addAll(newPath));
                    polyline.setColor(R.color.col5);
                    polyline.setClickable(true);
                    polyline.setZIndex(1);
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
                    calculateDistances();
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

        final AlertDialog.Builder builder = new AlertDialog.Builder(CosmeticMap2.this);
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
                calculateDistances();
            case REQUEST_DISTANCE_CALCULATION:
                if (resultCode == RESULT_OK) {
                    ArrayList<Double> allDistances = (ArrayList<Double>) data.getSerializableExtra("AllShopsExtra");
                    ArrayList<Integer> allShopIds = (ArrayList<Integer>) data.getSerializableExtra("AllShopIdsExtra");
                    Log.e(TAG, "dostalam dystanse: " + allDistances.toString());
                    Log.e(TAG, "dostalam id: " + allShopIds.toString());

                    filterReturnedDistance(allDistances, allShopIds);
                }
        }
    }

    public void setActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setTitle("Wybrane kosmetyki - trasa2");
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
            calculateDistances();
        }

    }

}