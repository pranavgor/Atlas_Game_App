package com.example.anonym.atlas;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String placerecieved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent2 = getIntent();
        placerecieved = intent2.getStringExtra("trial");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocationName(
                    placerecieved, 5);
        } catch (IOException e) {
            Toast.makeText(this, " Sorry,couldnt fetch the details of the place, probably because of bad connection.", Toast.LENGTH_SHORT).show();
        }
        if (addressList != null && addressList.size() > 0) {
            double lat = (double) (addressList.get(0).getLatitude());
            double lng = (double) (addressList.get(0).getLongitude());
            Toast.makeText(this, "Latitude is " + lat + " Longitude is " + lng, Toast.LENGTH_LONG).show();
            // Add a marker in the place recieved and move the camera
            LatLng placelocated = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(placelocated).title("Here's the place : " + placerecieved));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placelocated,8));
        } else {
            Toast.makeText(this, "No such place exist.", Toast.LENGTH_SHORT).show();
        }
    }
}
