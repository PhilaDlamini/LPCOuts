package com.example.lpcouts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class HOHSpecialExtensionApplicationFragment extends Fragment {

  ListView listView;
  TextView message;
  ViewGroup noUsersAppliedRoot;
  
  @Nullable
  public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
    View view = paramLayoutInflater.inflate(2131558458, paramViewGroup, false);
    this.listView = (ListView)view.findViewById(2131361944);
    this.noUsersAppliedRoot = (ViewGroup)view.findViewById(2131361961);
    this.message = (TextView)view.findViewById(2131362064);

    ArrayList<SpecialExtension> usersAppliedForSpecialExtension = UserData.getUsersAppliedForSpecialExtension();

    if (usersAppliedForSpecialExtension.size() > 0) {
      listView.setAdapter(new SpecialExtensionApplicationAdapter(getContext(), list));
      return view;
    }

    noUsersAppliedRoot.setVisibility(View.VISIBLE);
    message.setText(getString(2131755129));
    return view;
  }
}
