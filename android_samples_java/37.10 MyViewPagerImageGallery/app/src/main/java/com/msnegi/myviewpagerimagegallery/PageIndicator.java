package com.msnegi.myviewpagerimagegallery;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PageIndicator extends LinearLayout implements ViewPager.OnPageChangeListener
{
    public static final int DEFAULT_INDICATOR_SPACING = 3;

    private int mActivePosition = -1;
    private int mIndicatorSpacing;
    private ViewPager mViewPager;

    Context context;

    public PageIndicator(Context context) {
        this(context, null);
    }

    public PageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageIndicator(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init()
    {
        setOrientation(HORIZONTAL);

        if (!(getLayoutParams() instanceof FrameLayout.LayoutParams))
        {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.BOTTOM | Gravity.START;
            setLayoutParams(params);
        }
    }

    public void setViewPager(ViewPager pager)
    {
        mViewPager = pager;
       //pager.addOnPageChangeListener(this);

        if (mViewPager != null)
        {
            addIndicator(mViewPager.getAdapter().getCount());
        }
    }

    private void removeIndicator() {
        removeAllViews();
    }

    private void addIndicator(int count)
    {
        removeIndicator();
        if (count <= 0) return;
        for (int i = 0; i < count; i++)
        {
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = mIndicatorSpacing;
            params.rightMargin = mIndicatorSpacing;

            ImageView img = new ImageView(getContext());
            img.setImageResource(R.drawable.circle_indicator_stroke);

            int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_small);
            img.setPadding(padding, padding, padding, padding);

            addView(img, params);
        }
        updateIndicator(mViewPager.getCurrentItem());
    }

    public void updateIndicator(int position)
    {
        if (mActivePosition != position)
        {
            if (mActivePosition == -1)
            {
                ((ImageView) getChildAt(position)).setImageResource(R.drawable.circle_indicator_solid);
                mActivePosition = position;
                return;
            }

            ((ImageView) getChildAt(mActivePosition)).setImageResource(R.drawable.circle_indicator_stroke);
            ((ImageView) getChildAt(position)).setImageResource(R.drawable.circle_indicator_solid);

            mActivePosition = position;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position) {
        updateIndicator(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

