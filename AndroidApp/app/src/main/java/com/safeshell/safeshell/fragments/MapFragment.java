package com.safeshell.safeshell.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.safeshell.safeshell.R;

/**
 * Created by ANepaul on 4/18/16.
 */
public class MapFragment extends SupportMapFragment
        implements OnMapReadyCallback {
    private final String TAG = getClass().getSimpleName();
    private static final int REQUEST_LOC_PERM = 321;
    private GoogleMap mMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)) {
            getActivity().requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOC_PERM);
            return;
        }
        getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOC_PERM) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_GRANTED)
                    getMapAsync(this);
            }
        }
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

        // Add a marker in Sydney and move the camera
        LatLng umdStamp = new LatLng(38.9881238, -76.9447425);
        mMap.addMarker(new MarkerOptions().position(umdStamp).title("Destination"));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(38.9826527,-76.9379726))
                .title("Police Station")
                .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.ic_dialog_alert)));
        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);
        CircleOptions circleOptions = new CircleOptions();

        circleOptions.fillColor(Color.argb(100,255,0,0))
                .strokeColor(Color.argb(200,255,0,0))
                .radius(300.0)
                .visible(true);
        mMap.addCircle(circleOptions.center(new LatLng(38.9945048,-76.9345877)));
        mMap.addCircle(circleOptions.center(new LatLng(38.9806991,-76.9386753)));
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(umdStamp, 14.0f));
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void findFriend(MarkerOptions friend) {
        if (mMap != null) {
            mMap.addMarker(friend);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(friend.getPosition()));
        } else {
            Log.i(TAG, "findFriend: Map was not set");
        }
    }
}
