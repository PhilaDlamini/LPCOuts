package com.example.lpcouts;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;

public class PagerAdapter extends FragmentPagerAdapter {
  ArrayList<String> mFragmentTitles = new ArrayList<String>();
  ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
  
  public PagerAdapter(FragmentManager paramFragmentManager) {
    super(paramFragmentManager);
  }
  
  public void addFragment(Fragment paramFragment, String paramString) {
    this.mFragments.add(paramFragment);
    this.mFragmentTitles.add(paramString);
  }
  
  public int getCount() {
    return this.mFragments.size();
  }
  
  public Fragment getItem(int paramInt) {
    return this.mFragments.get(paramInt);
  }
  
  @Nullable
  public CharSequence getPageTitle(int paramInt) {
    return this.mFragmentTitles.get(paramInt);
  }
}
