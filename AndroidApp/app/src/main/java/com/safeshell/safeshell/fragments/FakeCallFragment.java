package com.safeshell.safeshell.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.safeshell.safeshell.MainActivity;
import com.safeshell.safeshell.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class FakeCallFragment extends ListFragment {

    ArrayAdapter<Integer> mAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);

        // Adding friends onto the Friend's List
        mAdapter.clear();
        mAdapter.addAll(10,15,20,30,45,60);
        setListAdapter(mAdapter);
        View v = getLayoutInflater(null).inflate(R.layout.header_fakecall, (ViewGroup) view.getRootView(), false);
        getListView().addHeaderView(v);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (position > 0)
        ((MainActivity)getActivity()).showSnackBar(v, mAdapter.getItem(position-1));
    }
}
