package com.example.burritobuddy;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.burritobuddy.model.BurritoPlace;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;

public class BurritoPlaceMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @BindView(R.id.placeTitle)
    TextView mPlaceTitle;

    @BindView(R.id.placeInfo)
    TextView mPlaceInfo;

    @BindView(R.id.placeAddress)
    TextView mPlaceAddress;

    @BindView(R.id.back)
    ImageButton mBackButton;

    private BurritoPlace mPlace;
    private double mLatitude;
    private double mLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burrito_place_map);
        ButterKnife.bind(this);


        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if(getIntent().hasExtra("extra_place")){
            Bundle bundle = getIntent().getExtras();
            mPlace = (BurritoPlace) bundle.getSerializable("extra_place");

            mPlaceTitle.setText(mPlace.getPlaceName());
            mPlaceAddress.setText(mPlace.getPlaceAddress());
            mLatitude = mPlace.getPlaceLatitude();
            mLongitude = mPlace.getPlaceLongitude();

            String priceLevel = "";
            switch(mPlace.getPriceLevel()){
                case 1:
                    priceLevel = "$";
                    break;


                case 2:
                    priceLevel = "$$";
                    break;

                case 3:
                    priceLevel = "$$$";

                    break;

                case 4:
                    priceLevel = "$$$$";

                    break;
            }

            mPlaceInfo.setText(priceLevel);

        }
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

        // Add a marker at the selected burrito place and move the camera
        LatLng burritoPlace = new LatLng(mLatitude, mLongitude);
        mMap.addMarker(new MarkerOptions().position(burritoPlace).title("Selected Burrito Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_map_marker)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(burritoPlace));
        googleMap.setMinZoomPreference(16.0f);
    }
}
