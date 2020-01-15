package com.example.mice.rss_reader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
String idvideo ="";
YouTubePlayerView youTubePlayerView;
    String Api_KEY = "AIzaSyDT0OJgnNx5Nw3WRN8Yt2hhBHabvpH88r8";
    int Requet_video = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.playyoutube);
        Intent intent = getIntent();
        idvideo = intent.getStringExtra("idvideo");
        youTubePlayerView.initialize(Api_KEY,this );
        // không load lại trang khi xoay màn hình để trong manifest
        //android:configChanges="orientation|screenSize"
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Requet_video){
            youTubePlayerView.initialize(Api_KEY,this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(idvideo);
        // tu dong xoay ngang

        youTubePlayer.setFullscreen(true );
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
if (youTubeInitializationResult.isUserRecoverableError()){
    youTubeInitializationResult.getErrorDialog(this,Requet_video);
}else {
    Toast.makeText(this, "Video bị lỗi ", Toast.LENGTH_SHORT).show();

}
    }

}
