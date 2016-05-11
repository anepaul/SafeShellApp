package com.safeshell.safeshell.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.safeshell.safeshell.MainActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ANepaul on 4/19/16.
 */
public class FriendsFragment extends ListFragment {

    List<Friend> mFriendsList;
    ArrayAdapter<Friend> mFriendAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFriendsList = new ArrayList<>();
        mFriendAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);
       
        // Adding friends onto the Friend's List 
        mFriendsList.add(new Friend("Anumeet", "Needs Sleep"));
        mFriendsList.add(new Friend("Matt R.", "Happy"));
        mFriendsList.add(new Friend("Henry", "Angry"));
        mFriendsList.add(new Friend("Stephen", "..."));
        mFriendsList.add(new Friend("Jon", "Chillin'"));
        mFriendsList.add(new Friend("Matt M.", "Grading"));
        mFriendAdapter.clear();
        mFriendAdapter.addAll(mFriendsList);
        setListAdapter(mFriendAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Friend friend = mFriendAdapter.getItem(position);
        MarkerOptions friendMarker = new MarkerOptions()
                .position(mFriendAdapter.getItem(position).location)
                .title(friend.name);
        ((MainActivity)getActivity()).showFriendOnMap(friendMarker);
    }

    public static class Friend {
        public String name;
        public String status;
        public Bitmap profile;
        public LatLng location;
        // locator for display
        Random mRandom = new Random();

        //Constructor for creating Friends with the name and status
        public Friend(String name, String status) {
            this.name = name;
            this.status = status;
            location = new LatLng((mRandom.nextFloat() * 180) - 90, (mRandom.nextFloat() * 360) - 180);
        }

        // Constructor for Friend with name, status, and a profile which is their profile picture
        public Friend(String name, String status, Bitmap profile) {
            this.name = name;
            this.status = status;
            this.profile = profile;
        }

        // Method to get Friend's status
        public String getStatus(){
            return this.status;
        }

        // Method to get Friend's profile
        public Bitmap getProfile(){
            return this.profile;
        }

        // Method to get distance from Friend's current location
        // From http://stackoverflow.com/questions/14394366/find-distance-between-two-points-on-map-using-google-map-api-v2
        public double calculationByDistance(LatLng Dest) {
            // Radius of earth in Km
            int Radius=6371;
            double lat1 = Dest.latitude;
            double lat2 = this.location.latitude;
            double lon1 = Dest.longitude;
            double lon2 = this.location.longitude;
            double dLat = Math.toRadians(lat2-lat1);
            double dLon = Math.toRadians(lon2-lon1);
            double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                    Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                            Math.sin(dLon/2) * Math.sin(dLon/2);
            double c = 2 * Math.asin(Math.sqrt(a));
            double valueResult= Radius*c;
            double km=valueResult/1;
            DecimalFormat newFormat = new DecimalFormat("####");
            int kmInDec =  Integer.valueOf(newFormat.format(km));
            double meter=valueResult%1000;
            int  meterInDec= Integer.valueOf(newFormat.format(meter));
            Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec + " Meter   " + meterInDec);

            return Radius * c;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer();
            sb.append(name).append(" : ").append(status);
            return sb.toString();
        }
    }
}
