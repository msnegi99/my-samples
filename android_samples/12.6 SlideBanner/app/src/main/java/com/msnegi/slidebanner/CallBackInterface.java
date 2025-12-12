package com.msnegi.slidebanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public interface CallBackInterface {
    public void setTitle(String title);
    public void loadFragment(Fragment fragment, Bundle bundle, String tag);
    public void onFragmentRemoved();
}
