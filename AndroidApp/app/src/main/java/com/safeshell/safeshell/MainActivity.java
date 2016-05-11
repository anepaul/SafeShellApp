package com.safeshell.safeshell;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.gms.maps.model.MarkerOptions;
import com.safeshell.safeshell.fragments.AlertDialogFragment;
import com.safeshell.safeshell.fragments.FakeCallFragment;
import com.safeshell.safeshell.fragments.FriendsFragment;
import com.safeshell.safeshell.fragments.MapFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final int REQUEST_EMERGENCY = 0;
    private static final int REQUEST_NITERIDE = 1;

    private Boolean mAreFabsOpen = false;
    private FloatingActionButton mFab, mEmergencyFab, mNiteRideFab;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

     /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private int[] tabIcons = {
            R.drawable.map,
            R.drawable.ic_tab_call,
            R.drawable.account_multiple
    };
        /* initialize contents */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        // Set up the Tab layout 
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();

        mFab = (FloatingActionButton)findViewById(R.id.fab);
        mEmergencyFab = (FloatingActionButton)findViewById(R.id.fab1);
        mNiteRideFab = (FloatingActionButton)findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        mFab.setOnClickListener(this);
        mEmergencyFab.setOnClickListener(this);
        mNiteRideFab.setOnClickListener(this);

        /*FloatingActionButton mFab = (FloatingActionButton) findViewById(R.id.mFab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }
/* responds to features */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fab:
                animateFAB();
                break;
            case R.id.fab1:
                showDialog(R.string.call_niteride,R.string.msg_niteride, REQUEST_NITERIDE);
                break;
            case R.id.fab2:
                showDialog(R.string.call_emergency, R.string.msg_emergency, REQUEST_EMERGENCY);
                break;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            View v = findViewById(R.id.search);
            if (null != v) {
                if (v.getVisibility() == View.GONE) {
                    v.clearFocus();
                    v.setVisibility(View.VISIBLE);
                    item.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
                } else {
                    v.clearFocus();
                    v.setVisibility(View.GONE);
                    item.setIcon(android.R.drawable.ic_menu_search);
                }

            }
            v.setVisibility(View.VISIBLE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
/* do icon layout*/
    private void setupTabIcons() {
        mTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        mTabLayout.getTabAt(1).setIcon(tabIcons[1]);
        mTabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }
/* main view */
    private void setupViewPager(ViewPager viewPager) {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFrag(new MapFragment(), "Map");
        mSectionsPagerAdapter.addFrag(new FakeCallFragment(), "Fake Call");
        mSectionsPagerAdapter.addFrag(new FriendsFragment(), "Friends");
        viewPager.setAdapter(mSectionsPagerAdapter);
    }
/* anim`ation for the buttons */
    public void animateFAB(){
        if(mAreFabsOpen){
            mFab.startAnimation(rotate_backward);
            mEmergencyFab.startAnimation(fab_close);
            mNiteRideFab.startAnimation(fab_close);
            mEmergencyFab.setClickable(false);
            mNiteRideFab.setClickable(false);
            mAreFabsOpen = false;
            } else {
            mFab.startAnimation(rotate_forward);
            mEmergencyFab.startAnimation(fab_open);
            mNiteRideFab.startAnimation(fab_open);
            mEmergencyFab.setClickable(true);
            mNiteRideFab.setClickable(true);
            mAreFabsOpen = true;
            }
    }
/* dialog displayer */
    private void showDialog(int title, int msg, int req) {
        DialogFragment alertDialog = AlertDialogFragment.newInstance(title, msg, req);
        alertDialog.show(getSupportFragmentManager(), "dialog");
    }
/* click handler for the main */
    public void doPositiveClick(int requestCode) {
        switch (requestCode) {
            case REQUEST_EMERGENCY:
                Snackbar.make(mEmergencyFab, R.string.rsp_emergency, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case REQUEST_NITERIDE:
                Snackbar.make(mNiteRideFab, R.string.rsp_niteride, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            default:
                break;
        }

    }
/* click handler for the main */
    public void doNegativeClick(int requestCode) {
        switch (requestCode) {
            case REQUEST_EMERGENCY:
                Snackbar.make(mEmergencyFab, "Alert Canceled", Snackbar.LENGTH_LONG).show();
                break;
            case REQUEST_NITERIDE:
                Snackbar.make(mNiteRideFab, "Alert Canceled", Snackbar.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
/* goes to the friends tab */
    public void showFriendOnMap(MarkerOptions friendMarker) {
        MapFragment mapFragment = (MapFragment) mSectionsPagerAdapter.getItem(0);
        mapFragment.findFriend(friendMarker);
        mViewPager.setCurrentItem(0, true);
    }
/* creates the snack bar */
    public void showSnackBar(View v, int time) {
        Snackbar.make(v, "You will recieve a fake call in " + time + " seconds", Snackbar.LENGTH_LONG).show();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        /* high level abstration for item */
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
/* high level abstration for count */
        @Override
        public int getCount() {
            return mFragmentList.size();
        }
/* high level abstration for frag */
        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
/* high level abstration for title */
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
