package com.msnegi.basicnestedrecyclerview;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.msnegi.basicnestedrecyclerview.R;
import com.msnegi.basicnestedrecyclerview.adapter.OuterAdapter;
import com.msnegi.basicnestedrecyclerview.model.Inner;
import com.msnegi.basicnestedrecyclerview.model.Outer;

import java.util.ArrayList;
import java.util.List;

public class NestedRecyclerViewFragment extends Fragment {

    RecyclerView rvMain;
    private OuterAdapter outerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nested_recycler_view, container, false);
        rvMain = view.findViewById(R.id.rv_main);

        setupRv();
        showList();

        return view;
    }

    private void showList() {
        List<Inner> inners = new ArrayList<Inner>();
        inners.add(new Inner(R.mipmap.ic_launcher, "msnegi98"));
        inners.add(new Inner(R.mipmap.ic_launcher, "kamal98"));
        inners.add(new Inner(R.mipmap.ic_launcher, "mahender98"));

        //for (int i = 0; i < 100; i++) { // 2 * 100 = 200 outer
            outerAdapter.addOuter(new Outer("Bank", inners));
            outerAdapter.addOuter(new Outer("Cards", inners));
            outerAdapter.addOuter(new Outer("E-Mails", inners));
            outerAdapter.addOuter(new Outer("Social", inners));
            outerAdapter.addOuter(new Outer("Work", inners));
            outerAdapter.addOuter(new Outer("Others", inners));
        //}
    }

    private void setupRv() {
        rvMain.setHasFixedSize(true);
        rvMain.setLayoutManager(new LinearLayoutManager(getActivity()));
        outerAdapter = new OuterAdapter();
        rvMain.setAdapter(outerAdapter);
    }
}