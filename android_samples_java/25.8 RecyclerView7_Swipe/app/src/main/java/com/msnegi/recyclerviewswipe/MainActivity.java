package com.msnegi.recyclerviewswipe;

import android.graphics.Color;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    int [] images = {
            R.drawable.avacado,
            R.drawable.banana,
            R.drawable.chakotha,
            R.drawable.chikku,
            R.drawable.coconut,
            R.drawable.custard_apple,
            R.drawable.dates,
            R.drawable.lychees,
            R.drawable.mango,
            R.drawable.mulberry,
            R.drawable.muskmelon,
            R.drawable.oranges,
            R.drawable.papayas,
            R.drawable.pears,
            R.drawable.star_fruit,
            R.drawable.watermelon
    };
    RecyclerViewAdapter adapter;
    LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Data> list = new ArrayList<Data>();
        list.add(new Data("avacado","avacado description", images[0]));
        list.add(new Data("banana","banana description",images[1]));
        list.add(new Data("chakotha","chakotha description",images[2]));
        list.add(new Data("chikku","chikku description",images[3]));
        list.add(new Data("coconut","coconut description",images[4]));
        list.add(new Data("custard_apple","custard_apple description",images[5]));
        list.add(new Data("dates","dates description",images[6]));
        list.add(new Data("lychees","lychees description",images[7]));
        list.add(new Data("mango","mango description",images[8]));
        list.add(new Data("mulberry","mulberry description",images[9]));
        list.add(new Data("muskmelon","muskmelon description",images[10]));
        list.add(new Data("oranges","oranges description",images[11]));
        list.add(new Data("papayas","papayas description",images[12]));
        list.add(new Data("pears","pears description",images[13]));
        list.add(new Data("star_fruit","star_fruit description",images[14]));

        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new RecyclerViewAdapter(list, this);
        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        SpacesItemDecoration decoration = new SpacesItemDecoration(15);
        recyclerView.addItemDecoration(decoration);

        enableSwipeToDeleteAndUndo();
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
                final Data item = adapter.getData().get(position);

                adapter.removeItem(position);

                Snackbar snackbar = Snackbar.make(mainLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        adapter.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }
}
