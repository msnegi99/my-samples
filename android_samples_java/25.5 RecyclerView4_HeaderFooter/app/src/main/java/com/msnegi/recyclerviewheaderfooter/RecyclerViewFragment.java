package com.msnegi.recyclerviewheaderfooter;

import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class RecyclerViewFragment extends Fragment {

    ListView mList;
    private static final int TOP_HEIGHT = 700;
    private View headerView;

    RecyclerView recyclerView;
    private Recycler_View_Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.recyclerview_layer,container,false);

        ArrayList<Data> list= new ArrayList<>();
        list.add(new Data(Data.HEADER_TYPE,"HEADER ROW","",0));
        list.add(new Data(Data.ITEM_TYPE,"Hi. I display a cool image too besides the omnipresent TextView.","",R.drawable.ic_action_movie));
        list.add(new Data(Data.ITEM_TYPE,"Hey. Pressing the FAB button will playback an audio file on loop.","",R.drawable.ic_action_movie));
        list.add(new Data(Data.ITEM_TYPE,"Following the destruction of Metropolis, Batman embarks on a personal vendetta against Superman ","", R.drawable.ic_action_movie));
        list.add(new Data(Data.ITEM_TYPE,"X-Men: Apocalypse is an upcoming American superhero film based on the X-Men characters that appear in Marvel Comics ","", R.drawable.ic_action_movie));
        list.add(new Data(Data.FOOTER_TYPE,"FOOTER ROW","",0));

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        adapter = new Recycler_View_Adapter(list,getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }


}
