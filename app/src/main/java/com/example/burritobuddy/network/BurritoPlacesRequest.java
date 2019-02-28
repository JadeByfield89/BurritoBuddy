package com.example.burritobuddy.network;

import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.burritobuddy.application.BurritoBuddyApplication;
import com.example.burritobuddy.constant.AppConstants;
import com.example.burritobuddy.model.BurritoPlace;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * This is a network class this is responsible for actually making the API call and returning the result to the caller.
 * In the MVP model, we either utilize this class in the Presenter, or we can delicate it to an interactor class.
 */

public class BurritoPlacesRequest {

    private static final String TAG = "BurritoPlacesRequest";
    private static final String KEY_RESULTS = "results";
    private BurritoPlacesRequestListener mListener;
    private ArrayList<BurritoPlace> mPlaceResults = new ArrayList<>();


    // This listener will notify the caller of the result of this API call. It either delivers a list of places, or an error.
    public interface BurritoPlacesRequestListener {

        void onPlacesRequestCompleted(ArrayList<BurritoPlace> burritoPlaces);

        void onPlacesRequestError(VolleyError error);
    }

    public void requestBurritoPlaces(double latitude, double longitude, final BurritoPlacesRequestListener listener){

        this.mListener = listener;
        String BURRITO_PLACES_ENDPOINT = String.format(AppConstants.GOOGLE_NEARBY_SEARCH_ENDPOINT, latitude, longitude);

        // Set up the request to fetch the JSON response which contains a list of "results"
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BURRITO_PLACES_ENDPOINT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{

                    JSONArray resultsArray = response.getJSONArray(KEY_RESULTS);

                    if(resultsArray.length() > 0 ){

                        for(int i=0; i < resultsArray.length(); i++){

                            JSONObject placeObject = resultsArray.getJSONObject(i);

                            BurritoPlace burritoPlace = new BurritoPlace();
                            burritoPlace.parseJSON(placeObject);

                            mPlaceResults.add(burritoPlace);
                        }

                        // Deliver the list of place results (BurritoPlace objects) back to the caller.
                        listener.onPlacesRequestCompleted(mPlaceResults);
                    }


                }catch(JSONException e){
                    Log.d(TAG, "Error parsing the burrito places request response!, " + e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // An error occurred while attempting to fetch, deliver the error response back to the caller.
                listener.onPlacesRequestError(error);
            }
        }
        );

        // Queue up the request and execute it
        BurritoBuddyApplication.getInstance().addToRequestQueue(request);

    }
}
