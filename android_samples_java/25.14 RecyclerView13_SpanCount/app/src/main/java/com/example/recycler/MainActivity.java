package com.example.recycler;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView image_back;
    ArrayList<Integer> viewType = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        recyclerView = findViewById(R.id.recyclerView);
        image_back = findViewById(R.id.image_back);

        for(int i =0;i<24 ; i++){
            int vvyu = i%6;
            if(vvyu==0){
                viewType.add(3);
            }else if(vvyu == 1 ||  vvyu == 2){
                viewType.add(2);
            }else if(vvyu == 3 || vvyu == 4 || vvyu ==5){
                viewType.add(1);
            }
        }

        Log.e( "onCreate: ",viewType.size()+" " );

        //Picasso.get().load("https://images.pexels.com/photos/556665/pexels-photo-556665.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(image_back);

        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //int vvyu = i%6;
                if(position==0){
                   return 3;
                }else if(position == 1 ||  position == 2){
                   return 2;
                }else if(position == 3 || position == 4 || position ==5){
                   return 1;
                }else {
                   return 1;
                }
            }
        });

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new Adapter(getApplicationContext(), viewType));

    }
}
