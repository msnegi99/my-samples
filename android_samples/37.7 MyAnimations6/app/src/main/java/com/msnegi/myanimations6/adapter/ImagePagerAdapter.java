package com.msnegi.myanimations6.adapter;

import com.msnegi.myanimations6.fragment.ImageFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import static com.msnegi.myanimations6.adapter.ImageData.IMAGE_DRAWABLES;

public class ImagePagerAdapter extends FragmentStatePagerAdapter {

  public ImagePagerAdapter(Fragment fragment) {
    // Note: Initialize with the child fragment manager.
    super(fragment.getChildFragmentManager());
  }

  @Override
  public int getCount() {
    return IMAGE_DRAWABLES.length;
  }

  @Override
  public Fragment getItem(int position) {
    return ImageFragment.newInstance(IMAGE_DRAWABLES[position]);
  }
}
