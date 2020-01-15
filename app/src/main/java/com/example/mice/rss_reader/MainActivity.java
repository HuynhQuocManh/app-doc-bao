package com.example.mice.rss_reader;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.mice.rss_reader.Fragment.MenuFragment;
import com.example.mice.rss_reader.Fragment.RadioFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jetradarmobile.snowfall.SnowfallView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private int mId;
    String name = "";
//    SnowfallView
    public static SnowfallView snowfallView;
    PreferenceHelper preferenceHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nv);
        bottomNavigationView = findViewById(R.id.navigation);
        snowfallView = findViewById(R.id.snowfallView);
        preferenceHelper = new PreferenceHelper(this);
        Log.d("manh2111", "onCreate: "+preferenceHelper.getHieuung());
        if (preferenceHelper.getHieuung().equals("true")){
            snowfallView.setVisibility(View.VISIBLE);
        }else {
            snowfallView.setVisibility(View.GONE);

        }

        bottomNavigationView.setOnNavigationItemSelectedListener(naListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new VNExpressCategory()).commit();
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        if (name != null) {
            new AlertDialog.Builder(this)
                    .setMessage("Chào mừng " + name + " đã đến với ứng dụng đọc báo của chúng tôi \n chúc bạn đọc báo vui vẽ ")
                    .setCancelable(false)
                    .setNegativeButton("Skip", null)
                    .show();
        }
    }

    @Override
    public void onBackPressed() {

         if (bottomNavigationView.getSelectedItemId() != R.id.action_home) {
            // selectedFragmnet = new FoodFragment();
            //  getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, selectedFragmnet).commit();
            mId = R.id.action_home;
             Log.d("actionaaaa", "onBackPressed: "+bottomNavigationView.getSelectedItemId());

            updateNavigationBarState(mId);
        } else if (bottomNavigationView.getSelectedItemId() == R.id.action_home) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }else {

         }


    }
    private void updateNavigationBarState(int id) {
        bottomNavigationView.setSelectedItemId(id);
    }


    private  BottomNavigationView.OnNavigationItemSelectedListener naListener  = new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedfragment = null;
        switch (menuItem.getItemId()){
            case R.id.action_home:
                selectedfragment= new VNExpressCategory();
                break;
            case R.id.action_video:
                selectedfragment= new VideoFragment();
                break;
            case R.id.action_radio:
                selectedfragment= new RadioFragment();
                break;
            case R.id.action_menu:
                selectedfragment= new MenuFragment();
                break;
        }

        final FragmentTransaction ft =  getSupportFragmentManager().beginTransaction();
        ft.detach(selectedfragment);
        ft.attach(selectedfragment);
        ft.replace(R.id.content_frame,selectedfragment);
        ft.commit();
        return true;
    }
};
}
