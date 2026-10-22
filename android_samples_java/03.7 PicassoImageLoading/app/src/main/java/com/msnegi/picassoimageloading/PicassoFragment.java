package com.msnegi.picassoimageloading;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;

public class PicassoFragment extends Fragment {

    AppCompatImageView image1,image2,image3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_picasso, container, false);

        image1 = (AppCompatImageView) view.findViewById(R.id.image1);
        image2 = (AppCompatImageView) view.findViewById(R.id.image2);
        image3 = (AppCompatImageView) view.findViewById(R.id.image3);

        viewImageResize();

        return view;
    }

    public void viewImageResize(){
        Picasso.with(getActivity())
                .load(R.drawable.flower2)
                .resize(200, 200)
                .placeholder(R.drawable.place_holder)
                .centerInside()
                .error(R.drawable.place_holder)
                .into(image1);

        Picasso.with(getActivity())
                .load(R.drawable.flower2)
                .resize(200, 200)
                .placeholder(R.drawable.place_holder)
                .centerCrop()
                .error(R.drawable.place_holder)
                .into(image2);

        Picasso.with(getActivity())
                .load(R.drawable.flower2)
                .placeholder(R.drawable.place_holder)
                .fit()
                .error(R.drawable.place_holder)
                .into(image3);
    }
}