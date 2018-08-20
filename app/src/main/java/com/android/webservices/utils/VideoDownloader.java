package com.android.webservices.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class VideoDownloader extends AsyncTask<Void, Long, Boolean> {

    Context context;
    public VideoDownloader(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        OkHttpClient client = new OkHttpClient();
        String url = "http://myamazingvideo.mp4";
        Call call = client.newCall(new Request.Builder().url(url).get().build());

        try {
            Response response = call.execute();
            if (response.code() == 200 || response.code() == 201) {

                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    Log.d("", responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                InputStream inputStream = null;
                try {
                    inputStream = response.body().byteStream();

                    byte[] buff = new byte[1024 * 4];
                    long downloaded = 0;
                    long target = response.body().contentLength();
                    File mediaFile = new File(context.getCacheDir(), "mySuperVideo.mp4");
                    OutputStream output = new FileOutputStream(mediaFile);

                    publishProgress(0L, target);
                    while (true) {
                        int readed = inputStream.read(buff);

                        if (readed == -1) {
                            break;
                        }
                        output.write(buff, 0, readed);
                        //write buff
                        downloaded += readed;
                        publishProgress(downloaded, target);
                        if (isCancelled()) {
                            return false;
                        }
                    }

                    output.flush();
                    output.close();

                    return downloaded == target;
                } catch (IOException ignore) {
                    return false;
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);


    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);


    }
}
