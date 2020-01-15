package com.example.mice.rss_reader.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mice.rss_reader.MainActivity;
import com.example.mice.rss_reader.PickNews;
import com.example.mice.rss_reader.PreferenceHelper;
import com.example.mice.rss_reader.R;


public class MenuFragment extends Fragment {

    Switch switchtuyet, switchfacebook;
    PreferenceHelper preferenceHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment=
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        switchtuyet = v.findViewById(R.id.switchtuyet);
        switchfacebook = v.findViewById(R.id.switchfacebook);
        preferenceHelper = new PreferenceHelper(getActivity());
        if (preferenceHelper.getChao().equals("true")){
            switchfacebook.setChecked(true);
        } else {
            switchfacebook.setChecked(false);
        }
        if (preferenceHelper.getHieuung().equals("true")) {
            switchtuyet.setChecked(true);
            MainActivity.snowfallView.setVisibility(View.VISIBLE);
        } else {
            MainActivity.snowfallView.setVisibility(View.GONE);
            switchtuyet.setChecked(false);
        }
        preferenceHelper = new PreferenceHelper(getActivity());
        switchtuyet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    preferenceHelper.putHieuung("true");
                    MainActivity.snowfallView.setVisibility(View.VISIBLE);Toast.makeText(getActivity(), "dsdsd", Toast.LENGTH_SHORT).show();
                    MainActivity.snowfallView.setVisibility(View.VISIBLE);
                } else {
                    preferenceHelper.putHieuung("false");
                    MainActivity.snowfallView.setVisibility(View.GONE);
                }
            }
        });
        switchfacebook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    preferenceHelper.putChao("true");
                } else {
                    preferenceHelper.putChao("false");
                }
            }
        });
        return v;
    }
}
