package com.example.mice.rss_reader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.textclassifier.TextLinks;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main2Activity extends AppCompatActivity {

    WebView webView;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        webView = (WebView) findViewById(R.id.webview);
        Intent intent = getIntent();
        String duongLink = intent.getStringExtra("link");
        String url = duongLink.replace("vnexpress.net","amp.vnexpress.net");
//        Log.d("dsdsdsd", "onCreate: ");
          webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());//click vào đường dẫn tiếp theo sẽ không bị nhảy ra khỏi ứng dụng
        //truy cập vào phần settings của webview
        WebSettings webSettings = webView.getSettings();
        //cho chức năng zoom trên trang web
        webSettings.setBuiltInZoomControls(true);
        //làm mất các phím chức năng zoom nếu có trên trang web
        webSettings.setDisplayZoomControls(false);
        //có thể sử dụng các chức năng trong trang web. VD: xem video.v.v.
        webSettings.setJavaScriptEnabled(true);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, duongLink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("string", "onResponse: " + response);
                Document document = Jsoup.parse(response);
                if (document != null) {
                    //  Element link = document.select("a").first();

                    Elements elements = document.select("article.article_detail");
                    try {
                        Log.d("sdsdsd", "onResponse: " + elements.html());
                        String aa = "";
                        Element element1 = null;
                        for (Element element : elements) {
                            aa = aa +"\n" +aa;
                        }

//
//                        webView.loadData(aa, "text/html", "UTF-8");
//                        webView.setWebViewClient(new WebViewClient());//click vào đường dẫn tiếp theo sẽ không bị nhảy ra khỏi ứng dụng
//                        //truy cập vào phần settings của webview
//                        WebSettings webSettings = webView.getSettings();
//                        //cho chức năng zoom trên trang web
//                        webSettings.setBuiltInZoomControls(true);
//                        //làm mất các phím chức năng zoom nếu có trên trang web
//                        webSettings.setDisplayZoomControls(false);
//                        //có thể sử dụng các chức năng trong trang web. VD: xem video.v.v.
//                        webSettings.setJavaScriptEnabled(true);
//                        //  loadData(yourData, "text/html", "UTF-8");
                    } catch (Exception e) {

                    }

                } else {
                    //  Log.d("string11", "onResponse: ");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("dsdsd", "onErrorResponse: " + error);
            }
        });
        requestQueue.add(stringRequest);

    }


}