package com.example.lpcouts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentPagerAdapter {
  ArrayList<String> mFragmentTitles = new ArrayList<>();
  ArrayList<Fragment> mFragments = new ArrayList<>();
  
  public PagerAdapter(FragmentManager paramFragmentManager) {
    super(paramFragmentManager);
  }
  
  public void addFragment(Fragment paramFragment, String paramString) {
    mFragments.add(paramFragment);
    mFragmentTitles.add(paramString);
  }
  
  public int getCount() {
    return mFragments.size();
  }
  
  public Fragment getItem(int paramInt) {
    return mFragments.get(paramInt);
  }
  
  @Nullable
  public CharSequence getPageTitle(int paramInt) {
    return mFragmentTitles.get(paramInt);
  }
}
