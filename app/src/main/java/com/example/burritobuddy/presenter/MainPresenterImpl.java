package com.example.burritobuddy.presenter;

import android.util.Log;
import com.android.volley.VolleyError;
import com.example.burritobuddy.model.BurritoPlace;
import com.example.burritobuddy.network.BurritoPlacesRequest;
import com.example.burritobuddy.view.MainView;

import java.util.ArrayList;

public class MainPresenterImpl implements MainPresenter {

    private static final String TAG = MainPresenterImpl.class.getSimpleName();
    private MainView mMainView;


    public MainPresenterImpl(MainView mainView){
        this.mMainView = mainView;
    }


    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void getNearbyBurritoPlaces(double latitude, double longitude) {

        BurritoPlacesRequest burritoPlacesRequest = new BurritoPlacesRequest();
        burritoPlacesRequest.requestBurritoPlaces(latitude, longitude, new BurritoPlacesRequest.BurritoPlacesRequestListener() {
            @Override
            public void onPlacesRequestCompleted(ArrayList<BurritoPlace> burritoPlaces) {
                // We have a list of places, notify our main view that it should update
                mMainView.displayNearbyResults(burritoPlaces);
            }

            @Override
            public void onPlacesRequestError(VolleyError error) {

                // Oops, we ran into an error, notify our main view so that the UI updates and lets the user know.
                mMainView.reportErrorToUser(error.getMessage());
            }
        });
    }

}
