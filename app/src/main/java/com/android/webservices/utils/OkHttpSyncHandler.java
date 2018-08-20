package com.android.webservices.utils;

import android.os.AsyncTask;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpSyncHandler extends AsyncTask<String,String,String> {

    String TAG = "WebServices";
    OkHttpClient client;

    public OkHttpSyncHandler(){
        client = new OkHttpClient();
    }

    @Override
    protected String doInBackground(String...params) {

        Request.Builder builder = new Request.Builder();
        builder.url(params[0]);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d(TAG," Response:"+s);
    }
}
