package com.example.mice.rss_reader;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.SystemClock;


public class PreferenceHelper {
    private SharedPreferences app_prefs;
    private final String ishieuung = "ishieuung";
    private final String ischao = "ischao";


    private Context context;
    public PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences("hieuung", Context.MODE_PRIVATE);
        this.context = context;
    }


    public void putHieuung(String userId) {
        Editor edit = app_prefs.edit();
        edit.putString(ishieuung, userId);
        edit.apply();
    }

    public String getHieuung() {
        return app_prefs.getString(ishieuung, "true");

    }



    public void putChao(String driver_id) {
        Editor edit = app_prefs.edit();
        edit.putString(ischao, driver_id);
        edit.apply();
    }

    public String getChao() {
        return app_prefs.getString(ischao, "true");

    }













}


