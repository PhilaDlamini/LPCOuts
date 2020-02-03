package com.example.lpcouts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserFragmentExtendOptions extends Fragment {
  @Nullable
  public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
    return paramLayoutInflater.inflate(R.layout.extend_options, paramViewGroup, false);
  }
}

