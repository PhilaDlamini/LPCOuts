package com.example.lpcouts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class GuardMainFragment extends Fragment {

  //The fragment for the Guard
  public View onCreateView( LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    return paramLayoutInflater.inflate(R.layout.guard_main_fragment, paramViewGroup, false);
  }
}
