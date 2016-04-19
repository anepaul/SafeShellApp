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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.safeshell.safeshell.fragments.AlertDialogFragment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupTabIcons() {
        mTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        mTabLayout.getTabAt(1).setIcon(tabIcons[1]);
        mTabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFrag(new MapFragment(), "Map");
        mSectionsPagerAdapter.addFrag(PlaceholderFragment.newInstance(2), "Fake Call");
        mSectionsPagerAdapter.addFrag(PlaceholderFragment.newInstance(3), "Friends");
        viewPager.setAdapter(mSectionsPagerAdapter);
    }

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

    private void showDialog(int title, int msg, int req) {
        DialogFragment alertDialog = AlertDialogFragment.newInstance(title, msg, req);
        alertDialog.show(getSupportFragmentManager(), "dialog");
    }

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

    public void doNegativeClick(int requestCode) {
        switch (requestCode) {
            case REQUEST_EMERGENCY:
                Snackbar.make(mEmergencyFab, "Alert Canceled", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case REQUEST_NITERIDE:
                Snackbar.make(mNiteRideFab, "Alert Canceled", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            default:
                break;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
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

        @Override
        public Fragment getItem(int position) {
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
