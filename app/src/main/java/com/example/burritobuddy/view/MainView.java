package com.example.burritobuddy.view;

import com.example.burritobuddy.model.BurritoPlace;

import java.util.ArrayList;

public interface MainView {

    void displayNearbyResults(ArrayList<BurritoPlace> placesList);

    void showProgress();

    void hideProgress();

    void reportErrorToUser(String error);
}
