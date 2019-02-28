package com.example.burritobuddy.application;

import android.app.Application;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Custom application class that we use to initialize our Volley Request Queue. Any other initial set up logic can live here as well.
 */
public class BurritoBuddyApplication extends Application {


    private static final String TAG = BurritoBuddyApplication.class.getSimpleName();
    private RequestQueue mRequestQueue;

    private static BurritoBuddyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static BurritoBuddyApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
}
