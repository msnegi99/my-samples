package com.msnegi.websearchvolley.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.msnegi.websearchvolley.R;

public class ProductSquareImageSliderView extends BaseSliderView{
    private ScaleType scaleType;
    public ProductSquareImageSliderView(Context context, ScaleType scaleType) {
        super(context);
        this.scaleType = scaleType;
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.view_product_square_image_item,null);
        ImageView target = v.findViewById(R.id.daimajia_slider_square_image);
        setScaleType(scaleType);
        bindEventAndShow(v, target);
        return v;
    }
}
