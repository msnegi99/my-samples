package com.msnegi.tabsamples.material_design_static_tabs_icons;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.msnegi.tabsamples.R;
import com.msnegi.tabsamples.HomeActivity;
import com.msnegi.tabsamples.CallBackInterface;

public class TabIconFragment extends Fragment {

    CallBackInterface callback;
    Context contxt;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (HomeActivity) context;
        contxt = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        callback.setTitle("Tabs Icons");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_tabs_view_pager, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tab);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1").setIcon(R.drawable.ic_event));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2").setIcon(R.drawable.ic_forum));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3").setIcon(R.drawable.ic_headset));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabRippleColorResource(R.color.green);

        ViewPager viewPager = view.findViewById(R.id.pager);
        PagerAdapter adapter = new PagerAdapter(getFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

}
