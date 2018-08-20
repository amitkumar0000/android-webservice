package com.android.webservices.utils;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import okhttp3.OkHttpClient;

public class VolleyRequestQueue {

    static VolleyRequestQueue mInstance;
    RequestQueue requestQueue;
    // Instantiate the cache
    Cache cache;

    // Set up the network to use HttpURLConnection as the HTTP client.
    Network network;

    private VolleyRequestQueue(Context context){
        network = new BasicNetwork(new HurlStack());

        cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
        requestQueue = new RequestQueue(cache,network);

//        requestQueue = Volley.newRequestQueue(context);
        requestQueue.start();

    }

    public static VolleyRequestQueue getInstance(Context context){
        if(mInstance == null){
            mInstance = new VolleyRequestQueue(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
