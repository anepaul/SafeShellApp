package com.safeshell.safeshell.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.safeshell.safeshell.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ANepaul on 4/19/16.
 */
public class FriendsFragment extends ListFragment {

    List<Friend> mFriendsList;
    ArrayAdapter<Friend> mFriendAdapter;
    // locator for display
    Random mRandom = new Random();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFriendsList = new ArrayList<>();
        mFriendAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1);
       
        // Adding friends onto the Friend's List 
        mFriendsList.add(new Friend("Anumeet", "Needs Sleep"));
        mFriendsList.add(new Friend("Matt", "Happy"));
        mFriendsList.add(new Friend("Henry", "Angry"));
        mFriendsList.add(new Friend("Stephen", "..."));
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

    private class Friend {
        String name;
        String status;
        Bitmap profile;
        LatLng location;

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
        public int getDistance(){
            // Need to work on 
            return 0; 
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer();
            sb.append(name).append(" : ").append(status);
            return sb.toString();
        }
    }
}
