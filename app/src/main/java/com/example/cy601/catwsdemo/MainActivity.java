package com.example.cy601.catwsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.crypto.Mac;

public class MainActivity extends AppCompatActivity {
    final String URL = "https://api.thecatapi.com/v1/images/search";

    ImageView imageView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.button) ;
        imageView=(ImageView)findViewById(R.id.imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData(URL);
            }
        });
    }

    void fetchData(String url) {
//使用 Ion library
        Ion.with(MainActivity.this)
                .load(url)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String s) {
                            processJsonData(s);

                    }
                });
    }
  //传统连接
//    private void fetchData(final String urlString) {
//        Thread thread = new Thread(new Runnable() {
//            public void run() {
//                try {
//                    URL url = new URL(urlString);
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.setConnectTimeout(30000);
//                    conn.setReadTimeout(10000);
//                    conn.setRequestMethod("GET");
//                    conn.connect();
//
//                    int responseCode = conn.getResponseCode();
//                    if (responseCode == HttpURLConnection.HTTP_OK) {
//                        InputStream input = conn.getInputStream();
//                        StringBuilder sb = new StringBuilder();
//                        while (true) {
//                            int ch = input.read();
//                            if (ch == -1)
//                                break;
//                            sb.append((char) ch);
//                        }
//                        String text = sb.toString();
//                        processJsonData(text);
//                    } else {
//                        Log.d("url", "HTTP fail, code " + responseCode);
//                    }
//                } catch (IOException ioe) {
//                    Log.wtf("url", ioe);
//                }
//            }
//        });
//        thread.start();
//    }

    private void processJsonData(String data) {
        JSONObject jsonObject = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                 jsonObject = jsonArray.getJSONObject(i);
            }

            //JSONObject json = new JSONObject(data);

            //JSONObject value = json.getJSONObject("value");

            final String url =jsonObject.getString("url");
            updateImage(url);

        } catch (JSONException e) {
            Log.wtf("json", e);
        }
    }

    private void updateImage(String url) {

        //使用Picasso library
//        Picasso.get().load(url).into(imageView);



        //使用Ion  library
        try {
            Ion.with(imageView)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    //.error(R.drawable.error_image)
                    //.animateLoad(spinAnimation)
                    //.animateIn(fadeInAnimation)
                    .load(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

