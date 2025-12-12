package com.echessa.designdemo;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>
{
    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CardView cv;
        private Context context;

        TextView title;
        TextView desc;
        TextView price;
        ImageView icon;

        Button addToCart;

        PersonViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            cv = (CardView) itemView.findViewById(R.id.cv);

            title = (TextView) itemView.findViewById(R.id.title);
            desc = (TextView) itemView.findViewById(R.id.desc);
            price = (TextView) itemView.findViewById(R.id.price);
            icon = (ImageView) itemView.findViewById(R.id.icon);

            addToCart = (Button) itemView.findViewById(R.id.add_to_cart);

            addToCart.setOnClickListener(this);

            Log.d("MSNEGI", "------------------ PersonViewHolder ---------------------");
        }

        @Override
        public void onClick(View view) {

        }
    }

    Context context;
    List<Product> products;

    RVAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
        Log.d("MSNEGI", "------------------ RVAdapter OK---------------------");
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Log.d("MSNEGI", "------------------ onAttachedToRecyclerView OK--------------------- size : " + products.size());

    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(viewGroup.getContext(), v);

        Log.d("MSNEGI", "------------------ onCreateViewHolder ---------------------");

        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        Log.d("MSNEGI", products.get(i).gettitle() + " " + products.get(i).getdescription() + " " + "Price : $" + products.get(i).getprice());

        personViewHolder.title.setText(products.get(i).gettitle());
        personViewHolder.desc.setText(products.get(i).getdescription());
        personViewHolder.price.setText("Price : $" + products.get(i).getprice());
        personViewHolder.price.setContentDescription(products.get(i).getprice());
        personViewHolder.icon.setContentDescription("images/large/" + products.get(i).getimage());

        // personViewHolder.icon.setImageResource("images/" + products.get(i).getimage());

        try {
            AssetManager manager = context.getAssets();
            InputStream is = manager.open("images/" + products.get(i).getimage());
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            personViewHolder.icon.setImageBitmap(bitmap);

        } catch (Exception e) {
        }
        Log.d("MSNEGI", "------------------ onBindViewHolder ---------------------");
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}