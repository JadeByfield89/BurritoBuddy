package com.example.burritobuddy.presenter;

/**
 * This interface will act as a contract between our view and business logic. MVP allows easy separation of concerns by making the Presenter a powerful
 * "middleman".
 */

public interface MainPresenter {

    void getNearbyBurritoPlaces(double latitude, double longitude);

}
