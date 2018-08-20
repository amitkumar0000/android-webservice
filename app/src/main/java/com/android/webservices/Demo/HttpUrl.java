package com.android.webservices.Demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.cert.Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class HttpUrl {
    HttpsURLConnection httpURLConnection;
    String POST = "POST";
    String GET = "GET";
    String TAG = "HTTPDEMO";


    public HttpUrl() {

    }

    private void connect(String url) {
        try {
            URL urlObj = new URL(url);
            httpURLConnection = (HttpsURLConnection) urlObj.openConnection();
            printConInfo(httpURLConnection);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }

    }

    private void printConInfo(HttpsURLConnection con) {
        if (con != null) {

            try {

                Log.d("Response Code : ", "" + con.getResponseCode());
                Log.d("Cipher Suite : ", con.getCipherSuite());

                Certificate[] certs = con.getServerCertificates();
                for (Certificate cert : certs) {
                    Log.d("Cert Type : ", cert.getType());
                    Log.d("Cert Hash Code : ", "" + cert.hashCode());
                    Log.d("Cert Key Algorithm : "
                            , cert.getPublicKey().getAlgorithm());
                    Log.d("Cert  Key Format : "
                            , cert.getPublicKey().getFormat());
                }

            } catch (SSLPeerUnverifiedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void post(String url, JSONObject jsonObject) {
        try {
            connect(url);
            httpURLConnection.setRequestMethod(POST);
            httpURLConnection.getOutputStream().write((jsonObject.toString()).getBytes());

            int status = httpURLConnection.getResponseCode();
            if(status == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String text = "";
                StringBuilder re = new StringBuilder();
                while((text=in.readLine())!=null)
                    re.append(text);
                Log.d(TAG,"Write Response:: "+ re);
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }
    }

    public String gets(String requestUrl) {
        URL url = null;
        StringBuilder result = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) url.openConnection();
            connection.setConnectTimeout(8 * 1000);
            connection.setRequestProperty("User-Agent", "Hello");
            connection.setRequestMethod("GET");

            connection.setDefaultUseCaches(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String text;
            result = new StringBuilder();
            while ((text = in.readLine()) != null)
                result.append(text);

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public JSONObject get(String url) throws IOException {
        JSONObject jsonObject = null;
        StringBuilder buffer = null;

        try {
            connect(url);
            byte[] bytes = new byte[1024];
            buffer = new StringBuilder();
            httpURLConnection.setRequestMethod(GET);

            InputStream inputStream = httpURLConnection.getInputStream();
            while (inputStream.read(bytes) != -1) {
                buffer.append(new String(bytes));
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            InputStream in = httpURLConnection.getErrorStream();
            StringBuilder errorMsg = new StringBuilder();
            byte[] b = new byte[1024];
            try {
                while (in.read(b) != -1) {
                    errorMsg.append(new String(b));
                }
                Log.d(TAG, errorMsg.toString());
            } catch (IOException e1) {

            }
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }

        try {
            jsonObject = new JSONObject(buffer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


    public Bitmap downloadImage(String url) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        connect(url);

        try {
            InputStream in = httpURLConnection.getInputStream();
            byte[] b = new byte[1024];
            while (in.read(b) != -1) {
                baos.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }

        return BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length);
    }
}
