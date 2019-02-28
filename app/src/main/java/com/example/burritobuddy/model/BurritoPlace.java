package com.example.burritobuddy.model;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Simple POJO class that represents an individual burrito place result, and that stores all of the fields that we
 * will need to render in the UI.
 */

public class BurritoPlace implements Serializable {

    private static final String TAG = BurritoPlace.class.getSimpleName();
    private static final String KEY_GEOMETRY = "geometry";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";
    private static final String KEY_NAME = "name";
    private static final String KEY_PRICE_LEVEL = "price_level";
    private static final String KEY_VICINITY = "vicinity";

    private String mPlaceName;
    private int mPriceLevel;
    private String mPlaceAddress;
    private double mLatitude;
    private double mLongitude;

    public BurritoPlace(){

    }

    public void parseJSON(JSONObject jsonObject){

        if (jsonObject == null) {
            Log.e(TAG, "Error creating a BurritoPlace object because the provided json object is null.");
        }

        else {

            try {
                if (jsonObject.has(KEY_GEOMETRY)) {
                    JSONObject geometryObject = jsonObject.getJSONObject(KEY_GEOMETRY);

                    if(geometryObject.has(KEY_LOCATION)){
                        JSONObject locationObject = geometryObject.getJSONObject(KEY_LOCATION);

                        mLatitude = locationObject.getDouble(KEY_LAT);
                        mLongitude = locationObject.getDouble(KEY_LNG);
                    }

                }

                if(jsonObject.has(KEY_NAME)){
                    mPlaceName = jsonObject.getString(KEY_NAME);
                }

                if(jsonObject.has(KEY_PRICE_LEVEL)){
                    mPriceLevel = jsonObject.getInt(KEY_PRICE_LEVEL);
                }

                if(jsonObject.has(KEY_VICINITY)){
                    mPlaceAddress = jsonObject.getString(KEY_VICINITY);
                }

            }catch (JSONException e){
                Log.d(TAG, "Error creating a BurritoPlace object!");
            }
        }
    }

    public int getPriceLevel(){
        return mPriceLevel;
    }

    public String getPlaceName(){
        return mPlaceName;
    }

    public String getPlaceAddress(){
        return mPlaceAddress;
    }

    public double getPlaceLatitude(){
        return mLatitude;
    }

    public double getPlaceLongitude(){
        return mLongitude;
    }


}
