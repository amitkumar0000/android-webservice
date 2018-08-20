package com.android.webservices.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.android.webservices.Demo.HttpUrl;

import org.json.JSONObject;

import java.net.HttpURLConnection;

public class HttpUrlGetAsyncTask extends AsyncTask<String,String,JSONObject> {
    String TAG = "WebServices";
    HttpUrl httpUrl;

    public HttpUrlGetAsyncTask(){
        httpUrl = new HttpUrl();
    }


    @Override
    protected JSONObject doInBackground(String... strings) {
        try {
            String jsonObject = httpUrl.gets(strings[0]);
            Log.d(TAG,"HTTP Get Response:: "+jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}