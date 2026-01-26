package com.msnegi.myviewpagerimagegallery;

import android.app.Activity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends Activity
{
  private PageIndicator mPageIndicator;

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImageAdapter adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter);

        mPageIndicator = (PageIndicator) findViewById(R.id.indicator);
        mPageIndicator.setViewPager(viewPager);

      viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
      {

          // This method will be invoked when a new page becomes selected.
          @Override
          public void onPageSelected(int position)
          {
              mPageIndicator.updateIndicator(position);
          }

          // This method will be invoked when the current page is scrolled
          @Override
          public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
              // Code goes here
          }

          // Called when the scroll state changes:
          // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
          @Override
          public void onPageScrollStateChanged(int state) {
              // Code goes here
          }
      });


  }

}
