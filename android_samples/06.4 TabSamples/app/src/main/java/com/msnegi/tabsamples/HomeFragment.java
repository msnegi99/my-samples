package com.msnegi.tabsamples;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.msnegi.tabsamples.material_design_static_tabs_icons.TabIconFragment;
import com.msnegi.tabsamples.material_design_tabs_viewpager.TabsViewPagerFragment;
import com.msnegi.tabsamples.rectangulartabs.RectangularTabFragment;

public class HomeFragment extends Fragment {

    Button matDesiTabViewPagerBtn, mateDesiIconBtn, rectangularTabsBtn;
    CallBackInterface callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (HomeActivity) context;
        Bundle bundle = getArguments();
    }

    @Override
    public void onResume() {
        super.onResume();
        callback.setTitle("Tabs Example");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        matDesiTabViewPagerBtn = view.findViewById(R.id.matDesiTabViewPagerBtn);
        matDesiTabViewPagerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.loadFragment(new TabsViewPagerFragment(),new Bundle(),"TabsViewPagerFragment");

            }
        });

        mateDesiIconBtn = view.findViewById(R.id.mateDesiIconBtn);
        mateDesiIconBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.loadFragment(new TabIconFragment(),new Bundle(),"TabIconFragment");
            }
        });

        rectangularTabsBtn = view.findViewById(R.id.rectangularTabsBtn);
        rectangularTabsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.loadFragment(new RectangularTabFragment(),new Bundle(),"RectangularTabFragment");
            }
        });

        return view;
    }
}
