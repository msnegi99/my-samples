package com.msnegi.websearchvolley.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.msnegi.websearchvolley.R;

/**
 * This is a slider with a description TextView.
 */
public class BannerSliderView extends BaseSliderView{
    private ScaleType scaleType;
    private View.OnClickListener clickListener;
    public BannerSliderView(Context context, ScaleType scaleType, View.OnClickListener clickListener) {
        super(context);
        this.scaleType = scaleType;
        this.clickListener = clickListener;
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.view_round_corner_banner_item,null);
        ImageView target = (ImageView)v.findViewById(R.id.daimajia_slider_image);
        setScaleType(scaleType);
        bindEventAndShow(v, target);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(v);
            }
        });
        return v;
    }
}
