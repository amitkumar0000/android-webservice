package com.android.webservices;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.webservices.Demo.RetrofitDemo;
import com.android.webservices.Demo.VolleyDemo;
import com.android.webservices.interfaces.SiteApiServices;
import com.android.webservices.model.SitesInfo;
import com.android.webservices.utils.HttpUrlGetAsyncTask;
import com.android.webservices.interfaces.ImageInterface;
import com.android.webservices.utils.OkHttpSyncHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ImageInterface {

    String TAG = "WebServices";
    ImageView imageView;
    SiteApiServices apiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        apiServices = RetrofitDemo.getClient().create(SiteApiServices.class);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hurlget: {
                new HttpUrlGetAsyncTask().execute("https://reqres.in/api/users/2");
                break;
            }
            case R.id.hurlpost: {
                break;
            }
            case R.id.httpGet: {
                new OkHttpSyncHandler().execute("https://reqres.in/api/users/2");
                break;
            }
            case R.id.httpPost: {
                break;
            }
            case R.id.volleyGet: {
                new VolleyDemo(this, this);
                break;
            }
            case R.id.retrofitget:{
                fetchSiteDetails();
                break;
            }

        }
    }


    @Override
    public void send(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    private void fetchSiteDetails() {
        Call<SitesInfo> call = apiServices.getSiteInfo("code","tutsplus");
        call.enqueue(new Callback<SitesInfo>() {
            @Override
            public void onResponse(Call<SitesInfo> call, Response<SitesInfo> response) {
                SitesInfo sitesInfo = response.body();
                Log.d(TAG, " Retrofit  site: " + sitesInfo.getArgs().getSite() + " network: "+ sitesInfo.getArgs().getNetwork());
            }

            @Override
            public void onFailure(Call<SitesInfo> call, Throwable t) {
                Log.e(TAG, "Got error : " + t.getLocalizedMessage());
                call.cancel();
            }
        });
    }
}
