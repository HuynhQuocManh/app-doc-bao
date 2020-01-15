package com.example.mice.rss_reader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.FacebookActivity;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import okhttp3.Headers;
import okhttp3.internal.http2.Header;

public class PickNews extends AppCompatActivity {
    ShimmerTextView  textView;
    private Shimmer shimmer;
    public static Boolean ishieuung = true;
    public static Boolean ischao = true;
  PreferenceHelper preferenceHelper;



    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_news);
        textView = (ShimmerTextView) findViewById(R.id.shimmer_tv);
//        MediaPlayer song = MediaPlayer.create(this, R.raw.nhac_noen);
//        song.start();
        shimmer = new Shimmer();
        preferenceHelper = new PreferenceHelper(this);


        shimmer.start(textView);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (preferenceHelper.getChao().equals("true")) {
                    Intent intent = new Intent(PickNews.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(PickNews.this, MainActivity.class);
                    startActivity(intent);
                }
             //   shimmer.cancel();

            }
        },3000);

    }
}
;