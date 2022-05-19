package com.example.hairnote;


import static com.example.hairnote.BuildConfig.MAPS_API_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;

import java.util.ArrayList;

public class CosmeticMap2Helper extends AppCompatActivity {

    private static final String TAG = "CosmeticMap2Helper";

    ArrayList<String> shopsToLocate;
    double userLat;
    double userLng;
    DataBaseHelper dataBaseHelper;
    private GeoApiContext mGeoApiContext = null;
    int numberOfAllFound;
    ArrayList<Double> allShops;
    ArrayList<Integer> allShopIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_map2_helper);

        shopsToLocate = new ArrayList<>();
        dataBaseHelper = new DataBaseHelper(CosmeticMap2Helper.this);

        if (mGeoApiContext == null) {
            mGeoApiContext = new GeoApiContext.Builder().apiKey(MAPS_API_KEY).build();
        }

        Intent intent = getIntent();
        if (intent != null) {

            shopsToLocate = (ArrayList<String>) getIntent().getSerializableExtra("ShopsToLocateExtra");
            userLat = Double.parseDouble(shopsToLocate.get(shopsToLocate.size() - 2));
            userLng = Double.parseDouble(shopsToLocate.get(shopsToLocate.size() - 1));
            shopsToLocate.remove(shopsToLocate.size() - 1);
            shopsToLocate.remove(shopsToLocate.size() - 1);

            Log.e(TAG, shopsToLocate.toString());
            Log.e(TAG, "user position: " + userLat + " " + userLng);

            allShops = new ArrayList<>();
            allShopIds = new ArrayList<>();

            for (int i = 0; i < shopsToLocate.size(); i++) {

                ArrayList<Shop> allFoundShops = dataBaseHelper.getAllShopsFromOneBrand(shopsToLocate.get(i));
                numberOfAllFound = numberOfAllFound + allFoundShops.size();
                Log.e(TAG, allFoundShops.toString());

                for (int j = 0; j < allFoundShops.size(); j++) {

                    Log.e(TAG, "" + shopsToLocate.get(i) + " " + j);

                    DirectionsApiRequest directionsApiRequest = new DirectionsApiRequest(mGeoApiContext);
                    directionsApiRequest.alternatives(false);
                    directionsApiRequest.origin(new com.google.maps.model.LatLng(userLat, userLng));

                    int finalI = i;
                    int finalJ = j;

                    directionsApiRequest
                            .destination(new com.google.maps.model.LatLng(allFoundShops.get(j).getLat(), allFoundShops.get(j).getLng()))
                            .setCallback(new PendingResult.Callback<DirectionsResult>() {
                                @Override
                                public void onResult(DirectionsResult result) {
                                    double tempDistance = result.routes[0].legs[0].distance.inMeters;

                                    Log.e(TAG, "onResult: routes in meters: " + result.routes[0].legs[0].distance.inMeters
                                            + " dla " + finalI + " " + finalJ + " "
                                            + allFoundShops.get(finalJ).getName()
                                            + " id = " + allFoundShops.get(finalJ).getId());

                                    allShops.add(tempDistance);
                                    Log.e(TAG, "Rozmiar: " + allShops.size());
                                    allShopIds.add(allFoundShops.get(finalJ).getId());

                                    if (allShops.size() == numberOfAllFound) {
                                        Log.e(TAG, "number of shops found: " + numberOfAllFound);
                                        Intent intent1 = new Intent();
                                        intent1.putExtra("AllShopsExtra", allShops);
                                        intent1.putExtra("AllShopIdsExtra", allShopIds);
                                        setResult(RESULT_OK, intent1);
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Throwable e) {

                                }
                            });
                }
            }
        }
    }
}