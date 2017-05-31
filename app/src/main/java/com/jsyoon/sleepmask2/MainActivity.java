package com.jsyoon.sleepmask2;

import android.content.SharedPreferences;
import android.support.annotation.BoolRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    // Debug Tag
    private static final String TAG = "MainActivity";

    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    // Setting values
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Main onCreateView");

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);

        // viewPager를 TAB 과 연결
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Boolean setting1 = sharedPreferences.getBoolean("setting1",true);
        Boolean setting2 = sharedPreferences.getBoolean("setting2",true);
        Boolean setting3 = sharedPreferences.getBoolean("setting3",true);

        String colorkey = sharedPreferences.getString("color1", getString(R.string.color_red));
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.edm);
        tabLayout.getTabAt(1).setIcon(R.drawable.edm);
        tabLayout.getTabAt(2).setIcon(R.drawable.edm);
        tabLayout.getTabAt(3).setIcon(R.drawable.edm);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new TabFragment1(), getString(R.string.tab1_title));
        adapter.addFrag(new TabFragment2(), getString(R.string.tab2_title));
        adapter.addFrag(new TabFragment3(), getString(R.string.tab3_title));
        adapter.addFrag(new TabFragDbg(), getString(R.string.tab4_title));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(TAG, "getItem " + position);
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
