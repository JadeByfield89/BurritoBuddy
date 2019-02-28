package com.example.burritobuddy.constant;

/**
 * Utility class used to store all app constants. Things like API keys, endpoint urls, file names, etc.
 */
public class AppConstants {

    private static final String GOOGLE_MAPS_API_KEY = "AIzaSyDsY0nmEkSkBNqvqEQqgiX4oxOo_TMRYZs";
    public static final String GOOGLE_NEARBY_SEARCH_ENDPOINT = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%s,%s&radius=1000&types=food&name=burrito&key=" + GOOGLE_MAPS_API_KEY;
}
