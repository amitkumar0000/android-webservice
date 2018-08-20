package com.android.webservices.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.webservices.MainActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//https://developers.google.com/web/fundamentals/performance/optimizing-content-efficiency/http-caching
//Max age :- to determine maxage after which cache need revalidation
//ETAG :- Tag sent as part of request to determine change in content in server
//Cache Control:

//OKHTTP Benefit
    // Connection Pooling
    //HTTP/2 Support
    //Async OkHttp use ThreadPool and Blocking Queue to serve request
    //Request Cancellation


public class OkHttpAsyncHandler {
    OkHttpClient okHttpClient;
    String TAG = "WebServices";
    int cacheSize = 10*1024*1024;
    Context context;

    public OkHttpAsyncHandler(Context context) {
        this.context = context;
        okHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(context.getCacheDir(),cacheSize))
                .addInterceptor(new ForceCacheInterceptor())
                //context.getCachedir() = /data/data/your.application.package/cache (this is a absolute path)
                //Environment.getConDownloadCacheDirectory() = /cache (this is relative path)
                .build();
    }

    public void run(String url) {
        try {
            URL u = new URL(url);
            RequestBody formBody = new FormBody.Builder()
                    .add("message", "Your message")
                    .build();

            Request request = new Request
                    .Builder()
                    .url(u)
                    .patch(formBody)
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();


            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Request request1 = call.request();
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String myResponse = response.body().string();
                    Log.d(TAG," Response:: "+myResponse);
                }
            });

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    class ForceCacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            if (!isNetworkAvailable()) {
                builder.cacheControl(CacheControl.FORCE_CACHE);
            }

            return chain.proceed(builder.build());
        }
    }

    boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
