package com.android.webservices.Demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OKHttp {
    OkHttpClient client;
    Request request;
    public String url= "https://reqres.in/api/users/2";


    public OKHttp(){
        client = new OkHttpClient();
        request = new Request.Builder()
                .url("http://www.vogella.com/index.html")
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response =  client.newCall(request).execute();
                    ResponseBody message = response.body();
                    InputStream in = message.byteStream();
                    BufferedInputStream bufferedInputStrea = new BufferedInputStream(in);
                    Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStrea);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
