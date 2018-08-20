package com.android.webservices.interfaces;

import com.android.webservices.model.SitesInfo;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface SiteApiServices {

    @Headers("Cache-Control: max-age=640000")
    @GET("/get")
    Call<SitesInfo> getSiteInfo(@Query("site") String site, @Query("network") String network);
}
