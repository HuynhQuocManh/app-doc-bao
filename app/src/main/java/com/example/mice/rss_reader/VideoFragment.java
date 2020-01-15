package com.example.mice.rss_reader;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mice.rss_reader.Adapter.AdapterVideo;
import com.example.mice.rss_reader.modal.VideoYoutube;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class VideoFragment extends Fragment {

    String Api_KEY = "AIzaSyDT0OJgnNx5Nw3WRN8Yt2hhBHabvpH88r8";
    String IDPLAY = "PLy-oKo0s3hYnJrMsPkoyvBjt1XQiZAwVY";
    String url = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + IDPLAY + "&key=" + Api_KEY;
    RequestQueue requestQueue;
    VideoYoutube videoYoutube;
    ArrayList<VideoYoutube> arrayvideo;
    RecyclerView listvideo;
    AdapterVideo adapterVideo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_video, container, false);
        Log.d("dsdsdsds", "onCreateView: " + url);
        listvideo = v.findViewById(R.id.listvideo);
        requestQueue = Volley.newRequestQueue(getActivity());
        arrayvideo = new ArrayList<>();
        getDATA();
        return v;
    }

    private void getDATA() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArrays = response.getJSONArray("items");
                            for (int i = 0; i < jsonArrays.length(); i++) {

                                JSONObject jsonObject = jsonArrays.getJSONObject(i);
                                JSONObject snippet =  jsonObject.getJSONObject("snippet");
                                String title = snippet.getString("title");
                                JSONObject thumbnails = snippet.getJSONObject("thumbnails");

                                JSONObject jsondefault = thumbnails.getJSONObject("medium");
                                String urlimg = jsondefault.getString("url");
                                JSONObject resourceId = snippet.getJSONObject("resourceId");
                                Log.d("manh211", "onResponse: "+thumbnails.toString());
                                String idview = resourceId.getString("videoId");
                                Log.d("manh211", "onResponse: "+title);
                                Log.d("manh211", "onResponse: "+urlimg);
                                Log.d("manh211", "onResponse: "+idview);
                                arrayvideo.add(new VideoYoutube(title,idview,urlimg,""));
                            }
                            listvideo.setHasFixedSize(true);
                            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

                            listvideo.setLayoutManager(linearLayoutManager);
                            DividerItemDecoration decoration = new DividerItemDecoration(getActivity(),linearLayoutManager.getOrientation());
                            listvideo.addItemDecoration(decoration);
                            adapterVideo = new AdapterVideo(getActivity(),arrayvideo);
                            listvideo.setAdapter(adapterVideo);




                        } catch (JSONException e) {
                            Log.d("dsdsdsd", "onResponse: "+e);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred\\

                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }


}
