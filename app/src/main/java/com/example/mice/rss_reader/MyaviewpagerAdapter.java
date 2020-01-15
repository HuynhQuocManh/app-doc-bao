package com.example.mice.rss_reader;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyaviewpagerAdapter extends FragmentPagerAdapter {
    private final String[] TITLES = { "Giải trí", "Du lịch", "Thể thao", "Thế giới", "Pháp luật", "Giáo dục",
            "Xe ", "Khoa học" };

    public MyaviewpagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {
        //SuperAwesomeCardFragment
        return SuperAwesomeCardFragment.newInstance(position);
    }


}
