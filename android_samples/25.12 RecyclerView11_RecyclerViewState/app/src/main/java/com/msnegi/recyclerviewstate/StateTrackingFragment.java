package com.msnegi.recyclerviewstate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StateTrackingFragment extends Fragment {

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_state_tracking, container, false);

        ArrayList<Data> list = new ArrayList<Data>();
        list.add(new Data("title1",false));
        list.add(new Data("title2",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));
        list.add(new Data("title",false));

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(list, getActivity());
        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        SpacesItemDecoration decoration = new SpacesItemDecoration(15);
        recyclerView.addItemDecoration(decoration);

        return view;
    }
}