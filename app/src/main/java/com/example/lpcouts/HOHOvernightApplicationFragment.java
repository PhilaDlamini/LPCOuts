package com.example.lpcouts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import androidx.core.app.Fragment;
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
    View view = paramLayoutInflater.inflate(R.layout.list_view_layout, paramViewGroup, false);
    listView = (ListView)view.findViewById(R.id.list_view);
    noUsersAppliedRoot = (ViewGroup)view.findViewById(R.id.no_users_out);
    message = (TextView)view.findViewById(R.id.text_displayed);
    noUsersAppliedRoot.setVisibility(View.VISIBLE);
    message.setText(getContext().getString(R.string.no_users_applied_for_overnight));
    return view;
  }
}
