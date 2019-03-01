package com.example.burritobuddy.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.burritobuddy.BurritoPlaceMapActivity;
import com.example.burritobuddy.R;
import com.example.burritobuddy.adapter.BurritoPlacesAdapter;
import com.example.burritobuddy.model.BurritoPlace;
import com.example.burritobuddy.presenter.MainPresenterImpl;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String EXTRA_PLACE = "extra_place";

    private FusedLocationProviderClient mFusedLocationClient;

    private static final int mLocationRequestCode = 1000;

    private BurritoPlacesAdapter mAdapter;

    MainPresenterImpl mMainPresenter = new MainPresenterImpl(this);

    @BindView(R.id.placesList)
    RecyclerView mPlacesRecyclerView;

    @BindView(R.id.progressBar)
    ProgressBar mProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAdapter = new BurritoPlacesAdapter(new BurritoPlacesAdapter.OnPlaceSelectedListener() {
            @Override
            public void onPlaceSelected(BurritoPlace place) {
                Intent intent = new Intent(MainActivity.this, BurritoPlaceMapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(EXTRA_PLACE, place);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mAdapter.setData(new ArrayList<BurritoPlace>());
        mPlacesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPlacesRecyclerView.setAdapter(mAdapter);

        showProgress();


        // Make sure Google Play Services is installed an updated on this device before using its APIs.
        if(GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS){
            getCurrentLocation(this);
        } else {
            Toast.makeText(this, "Google Play Services is missing or outdated. Please install it or update to use BurritoBuddy", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void displayNearbyResults(ArrayList<BurritoPlace> placesList) {
        hideProgress();
        mAdapter.setData(placesList);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.INVISIBLE);
        mPlacesRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress() {
        mPlacesRecyclerView.setVisibility(View.GONE);
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void reportErrorToUser(String error) {

        Toast.makeText(this, "Error fetching nearby burrito places!" + error, Toast.LENGTH_SHORT).show();
    }


    // Try to get the user's current location,  make sure we are requesting the location permission first.
    private void getCurrentLocation(Context context){

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        // Check if the location permission has already been granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Request for permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    mLocationRequestCode);

        } else {
            // Location permission is already granted, let's request the nearby burrito places
            getCurrentLocationCoordinates();
        }

    }

    // Get the actual latitude and longitude coordinates of the user's current location.
    private void getCurrentLocationCoordinates(){
        try {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Log.d(TAG, "Location Coordinates ->, Lat " + location.getLatitude() + ", Lon -> " + location.getLongitude());

                        mMainPresenter.getNearbyBurritoPlaces(location.getLatitude(), location.getLongitude());

                    }
                }
            });
        } catch (SecurityException e){
            Log.e(TAG, "Error getting current location, please make sure that the right permissions are enabled");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case mLocationRequestCode: {
                // If the permission request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                  getCurrentLocationCoordinates();

                } else {
                    Toast.makeText(this, "Permission denied, please enable the location permission in order to find nearby burrito places.", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }
}
