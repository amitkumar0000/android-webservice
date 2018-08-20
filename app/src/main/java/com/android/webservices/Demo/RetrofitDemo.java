package com.android.webservices.Demo;

import com.android.webservices.interfaces.SiteApiServices;
import com.android.webservices.model.SitesInfo;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
Generally, we need 3 classes to use retrofit library –
        a. Retrofit builder class: Build an instance of retrofit that is used to send any HTTP request.
        b. Model class: This is representation of JSON in java.
        c. APIService interface: This is interface which defines possible operations. for example – send GET, POST, PUT etc. request.

***/
public class RetrofitDemo {
    static String BASE_URL = "http://httpbin.org";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }




}
