package com.example.lpcouts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class HOHOvernightApplicationFragment extends Fragment {
  ListView listView;
  TextView message;
  ViewGroup noUsersAppliedRoot;
  
  @Nullable
  public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
    View view = paramLayoutInflater.inflate(2131558458, paramViewGroup, false);
    this.listView = (ListView)view.findViewById(2131361944);
    this.noUsersAppliedRoot = (ViewGroup)view.findViewById(2131361961);
    this.message = (TextView)view.findViewById(2131362064);
    this.noUsersAppliedRoot.setVisibility(View.VISIBLE);
    this.message.setText(getString(2131755130));
    return view;
  }
}
