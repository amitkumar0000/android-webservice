package com.android.webservices.Demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.webservices.interfaces.ImageInterface;
import com.android.webservices.utils.VolleyRequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

//StringRequest
//ImageRequest
//JsonRequest

public class VolleyDemo {
    String stringUrl = "http://httpbin.org/html";
    String jsonUrl = "http://httpbin.org/get?site=code&network=tutsplus";
//    String imagUrl = "http://i.imgur.com/Nwk25LA.jpg";
    String imagUrl = "http://api.learn2crack.com/android/images/donut.png";

    String TAG = "WebServices";



    // Request a string response
    StringRequest stringRequest;
    JsonObjectRequest jsonRequest;
    ImageRequest imgRequest;
    Context context;
    ImageInterface listener;

    public VolleyDemo(Context context,ImageInterface listener){
        this.context = context;
        this.listener = listener;

        createStringRequest();
        createJsonRequest();
        createImageRequest();

        VolleyRequestQueue.getInstance(context).getRequestQueue().add(stringRequest);
        VolleyRequestQueue.getInstance(context).getRequestQueue().add(jsonRequest);
        VolleyRequestQueue.getInstance(context).getRequestQueue().add(imgRequest);
    }

    private void createStringRequest() {
        stringRequest = new StringRequest(Request.Method.GET, stringUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Result handling
                        Log.d(TAG,response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                Log.d(TAG,"Something went wrong!");
                error.printStackTrace();

            }
        });
    }

    private void createJsonRequest(){
        jsonRequest = new JsonObjectRequest
                (Request.Method.GET, jsonUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String site = response.getString("site"),
                                    network = response.getString("network");
                            Log.d(TAG,"Site: "+site+" Network: "+network);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

    }

    private void createImageRequest(){
        imgRequest = new ImageRequest(imagUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        listener.send(response);
                    }
                }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        imgRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    protected void onStop() {
        VolleyRequestQueue.getInstance(context).getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                // do I have to cancel this?
                return true; // -> always yes
            }
        });
    }
}
