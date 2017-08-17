package com.example.james.jame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.example.james.jame.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.nav_View)
    NavigationView mNavView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    MAdapter mAdapter;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (mNavView != null) {
            setupDrawerContent(mNavView);
        }

        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(mViewPager);
        }

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "This is a snackBar!", Snackbar.LENGTH_SHORT)
                        .setAction("", null)
                        .show();
            }
        });

        init();
    }

    private void init() {
        mNavView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int[] ints = new int[2];
                mNavView.getLocationOnScreen(ints);
                Log.i("onScreen.X", ints[0] + "");
                Log.i("onScreen Y", ints[1] + "");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action, menu);
        return true;
    }

    public void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_item_1: {
                        startActivity(DetailActivity.class);
                        break;
                    }
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void setupViewPager(ViewPager viewPager) {
        mAdapter = new MAdapter(HomeActivity.this.getSupportFragmentManager());
        mAdapter.addFragment(new CheeseListFragment(), "fragment1");
        mAdapter.addFragment(new CheeseListFragment(), "fragment2");
        mAdapter.addFragment(new CheeseListFragment(), "fragment3");
        viewPager.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    class MAdapter extends FragmentPagerAdapter {
        List<Fragment> mFragmentList = new ArrayList<>();
        ;
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            if (fragment != null) {
                mFragmentList.add(fragment);
                mFragmentTitles.add(title);
            }
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}
