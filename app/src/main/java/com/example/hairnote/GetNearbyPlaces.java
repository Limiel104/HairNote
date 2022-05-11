package com.example.hairnote;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetNearbyPlaces extends AsyncTask<Object, String, String> {

    private String placeData, url;
    private GoogleMap map;

    @Override
    protected String doInBackground(Object... objects) {

        map = (GoogleMap) objects[0];
        url = (String) objects[1];

        try {
            placeData = MapActivity.readUrl(url);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return placeData;
    }

    @Override
    protected void onPostExecute(String s) {

        List<HashMap<String, String>> nearbyPlacesList = null;
        MapActivity mapActivity = new MapActivity();
        nearbyPlacesList = mapActivity.parseData(s);

        displayNearbyPlaces(nearbyPlacesList);
    }

    public void displayNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList){


        for (int i = 0; i < nearbyPlacesList.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();

            HashMap<String, String> placeMap = nearbyPlacesList.get(i);
            String placeName = placeMap.get("place_name");
            String vicinity = placeMap.get("vicinity");
            double lat = Double.parseDouble(placeMap.get("lat"));
            double lng = Double.parseDouble(placeMap.get("lng"));

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName + " : " + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            map.addMarker(markerOptions);
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            map.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    }
}
