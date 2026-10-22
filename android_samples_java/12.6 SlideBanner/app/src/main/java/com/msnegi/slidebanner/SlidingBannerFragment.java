package com.msnegi.slidebanner;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import com.msnegi.dialogbanner.R;
import com.msnegi.slidebanner.banner.pojo.OnItemClick;
import com.msnegi.slidebanner.banner.pojo.OptionItem;
import com.msnegi.slidebanner.banner.BannerSliderView;
import com.msnegi.slidebanner.banner.OptionSliderView;

import java.util.ArrayList;
import java.util.List;

public class SlidingBannerFragment extends Fragment {

    SliderLayout bannerSlider;
    OptionSliderView optionSlider;
    LinearLayout scrollContainer;

    int [] images1 = {
            R.drawable.oranges,
            R.drawable.coconut,
            R.drawable.banana,
            R.drawable.chakotha
    };

    int [] images2 = {
            R.drawable.fl1,
            R.drawable.fl2,
            R.drawable.fl3,
            R.drawable.fl4
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_sliding_banner, container, false);

        optionSlider = view.findViewById(R.id.optionSlider);
        bannerSlider = view.findViewById(R.id.bannerSlider);
        //scrollContainer = view.findViewById(R.id.container);

        loadSlider();

        return view;
    }

    private void loadSlider() {
        /*squareSlider.removeAllSliders();
        for(int i=0; i<images1.length; i++){
            squareSlider.addSlider(new ProductSquareImageSliderView(getActivity(), BaseSliderView.ScaleType.CenterInside).image(images1[i]));  //res/url/file  as parameter
        }
        squareSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = squareSlider.getCurrentPosition();
            }
        });*/

        optionSlider.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        optionSlider.setIndicatorColor(Color.WHITE, ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

        List<OptionItem> optionItems = new ArrayList();
        optionItems.add(new OptionItem().setId(1).setName("Tiles").setImgResource(images1[0]));
        optionItems.add(new OptionItem().setId(2).setName("Bathware").setImgResource(images1[1]));
        optionItems.add(new OptionItem().setId(3).setName("Catalogues").setImgResource(images1[2]));
        optionItems.add(new OptionItem().setId(4).setName("Where to buy").setImgResource(images1[3]));
        optionItems.add(new OptionItem().setId(5).setName("Media Gallery").setImgResource(R.mipmap.ic_launcher));
        optionItems.add(new OptionItem().setId(6).setName("Our Showrooms").setImgResource(R.mipmap.ic_launcher));
        optionSlider.initialize(optionItems, new OnItemClick() {
            @Override
            public void OnItemClick(Object obj, int pos) {
                Toast.makeText(getActivity(), ((OptionItem) obj).getName(), Toast.LENGTH_SHORT).show();
            }
        } ,4 );

    //------
        bannerSlider.removeAllSliders();
        for(int i=0; i<images2.length; i++){
            bannerSlider.addSlider(new BannerSliderView(getActivity(), BaseSliderView.ScaleType.CenterInside, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*if(banner.linktype.equals("tvc", true)){
                        openURL(banner.web_url)
                    }else {
                        if (banner.id != null && banner.id.isNotEmpty())
                            openProductList(banner.title, banner.web_url)
                    }*/
                }
            }).image(images2[i]));  //res/url/file  as parameter
        }
        bannerSlider.getPagerIndicator().setDefaultIndicatorColor(Color.RED, Color.BLACK);
        bannerSlider.setBackgroundColor(Color.WHITE);
        bannerSlider.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpTopx(getActivity(), 250f)));

        //------

    }

    public static int dpTopx(Context context, float val){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, val, context.getResources().getDisplayMetrics());
    }
}