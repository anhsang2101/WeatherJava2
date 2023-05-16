package com.example.weatherjava;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.weatherjava.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private Marker marker;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Add a marker in da nang and move the camera
        LatLng danang = new LatLng(16, 108);
        marker = mMap.addMarker(new MarkerOptions().position(danang).draggable(true).title("Marker in Da nang"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(danang));


//        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        Toast.makeText(this, "Lat" + latLng.latitude + "\nLon" + latLng.longitude, Toast.LENGTH_SHORT).show();
        mMap.clear();
        marker = mMap.addMarker(new MarkerOptions().position(latLng));

//        Dialog mDialog = new Dialog(this);
//        mDialog.setContentView(R.layout.popup);
//        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        mDialog.show();


        PopupDialog customDialog = new PopupDialog(this, latLng.latitude, latLng.longitude);
        customDialog.show();



    }

//    @Override
//    public boolean onMarkerClick(@NonNull Marker marker) {
//        Toast.makeText(this, "My position" + marker.getPosition(), Toast.LENGTH_SHORT).show();
//        return false;
//    }

//    @Override
//    public void onMarkerDrag(@NonNull Marker marker) {
//
//    }
//
//    @Override
//    public void onMarkerDragEnd(@NonNull Marker marker) {
//        Toast.makeText(this, "Lat" + marker.getPosition().latitude + "\nLon" + marker.getPosition().longitude, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onMarkerDragStart(@NonNull Marker marker) {
//
//    }
}