package com.msnegi.myviewpagerimagegallery;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageAdapter extends PagerAdapter
{
	Context context;
    public int[] GalImages = new int[]
    {
        R.drawable.one,
        R.drawable.two,
        R.drawable.three
    };

    ImageAdapter(Context context){
    	this.context=context;
    }

    @Override
    public int getCount() {
      return GalImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
      return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
      ImageView imageView = new ImageView(context);
      imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
      imageView.setImageResource(GalImages[position]);

      ((ViewPager) container).addView(imageView, 0);

      return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
      ((ViewPager) container).removeView((ImageView) object);
    }
  }
