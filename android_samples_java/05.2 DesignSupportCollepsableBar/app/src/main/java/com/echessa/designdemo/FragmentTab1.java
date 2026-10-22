package com.echessa.designdemo;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.List;

public class FragmentTab1 extends Fragment
{
    private RecyclerView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.fragmenttab1, container, false);

        listView = (RecyclerView) view.findViewById(R.id.list_tab1);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        listView.setLayoutManager(llm);
        listView.setHasFixedSize(true);

        List<Product> product = null;

        try
        {
            XMLPullParserHandler parser = new XMLPullParserHandler();
            product = parser.parse(getActivity().getAssets().open("veg_pizza_tab_1.xml"));
            //ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(this,R.layout.list_item, product);
            //CustomBaseAdapter adapter = new CustomBaseAdapter(this, product);

            Log.d("MSNEGI", "product size : " + product.size());

            RVAdapter adapter = new RVAdapter(getActivity().getApplicationContext(), product);

            //listView.setAdapter(adapter);
            listView.setAdapter(adapter);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return view;
    }

}
