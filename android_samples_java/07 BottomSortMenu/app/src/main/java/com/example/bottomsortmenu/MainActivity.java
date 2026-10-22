package com.example.bottomsortmenu;


import android.animation.Animator;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends AppCompatActivity
{
    /** Called when the activity is first created. */

    private CustomBaseAdapter mAdapter;
    private ArrayList<Item> itemlist;

    private ListView listView;
    private RelativeLayout sortView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sortView = (RelativeLayout) findViewById(R.id.sortViewLay);

        itemlist = new ArrayList<Item>();
        itemlist.add(new Item("india",R.drawable.india));
        itemlist.add(new Item("Brazil",R.drawable.brazil));
        itemlist.add(new Item("Canada",R.drawable.canada));

        // prepared arraylist and passed it to the Adapter class
        mAdapter = new CustomBaseAdapter(this, itemlist);

        // Set custom adapter to gridview
        listView = (ListView) findViewById(R.id.sortList);
        listView.setAdapter(mAdapter);

        // Implement On Item click listener
        listView.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                Toast.makeText(MainActivity.this, mAdapter.getItem(position).getCountry(), Toast.LENGTH_SHORT).show();
            }
        });

        sortView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                closeSlider();
            }
        });

        sortView.findViewById(R.id.sortViewLay).animate().y(5000f).setDuration(0).setListener(null);
        sortView.setVisibility(View.VISIBLE);
    }

    public void openSlider(){

        sortView.findViewById(R.id.sortViewLay).animate().y(0).setDuration(1000).setListener(null);
    }

    public void closeSlider(){
        sortView.findViewById(R.id.sortViewLay).animate().y(sortView.getHeight()).setDuration(1000).setListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {
            }

            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
            }

            @Override
            public void onAnimationStart(Animator animator) {
                Toast.makeText(getApplicationContext()
                        ,"onAnimationStart",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Toast.makeText(getApplicationContext()
                        ,"onAnimationEnd",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
    }

    public int dpToPx(Context context, float dp) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_cart:
                openSlider();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }


}