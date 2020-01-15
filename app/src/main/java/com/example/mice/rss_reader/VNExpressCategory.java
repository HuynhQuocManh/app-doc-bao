package com.example.mice.rss_reader;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.ToxicBakery.viewpager.transforms.DrawerTransformer;
import com.ToxicBakery.viewpager.transforms.ForegroundToBackgroundTransformer;
import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.ToxicBakery.viewpager.transforms.StackTransformer;
import com.ToxicBakery.viewpager.transforms.TabletTransformer;
import com.astuetz.PagerSlidingTabStrip;

import java.io.IOException;

public class VNExpressCategory extends Fragment {

   PagerSlidingTabStrip pagerSlidingTabStrip ;
    ViewPager viewPager;
    MyaviewpagerAdapter adapter;
     public static ImageView imgrefresh;
     public  static SearchView searchView;
    MediaPlayer mediaPlayer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.category_vnexpress, container, false);
        pagerSlidingTabStrip = view.findViewById(R.id.tabs);
        viewPager =  view.findViewById(R.id.viewpager);
        imgrefresh = view.findViewById(R.id.btnRefresh);
        searchView = view.findViewById(R.id.searchView);
//        mediaPlayer = new MediaPlayer();

//        PlayNhacMp3()
        adapter = new MyaviewpagerAdapter(getActivity().getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        viewPager.setPageMargin(pageMargin);

        pagerSlidingTabStrip.setViewPager(viewPager);
       // viewPager.setPageTransformer(true, new RotateDownTransformer());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        Log.d("manh1111111", "onStart: ");
    }

    @Override
    public void onStart() {

        super.onStart();
    }
}