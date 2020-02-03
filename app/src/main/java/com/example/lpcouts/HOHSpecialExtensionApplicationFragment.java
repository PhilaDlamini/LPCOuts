package com.example.lpcouts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class HOHSpecialExtensionApplicationFragment extends Fragment {

  ListView listView;
  TextView message;
  ViewGroup noUsersAppliedRoot;
  
  public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
    View view = paramLayoutInflater.inflate(R.layout.list_view_layout, paramViewGroup, false);
    listView = (ListView)view.findViewById(R.id.list_view);
    noUsersAppliedRoot = (ViewGroup)view.findViewById(R.id.no_users_out);
    message = (TextView)view.findViewById(R.id.text_displayed);

    ArrayList<SpecialExtension> usersAppliedForSpecialExtension = UserData.getUsersAppliedForSpecialExtension();

    if (usersAppliedForSpecialExtension.size() > 0) {
      listView.setAdapter(new SpecialExtensionApplicationAdapter(getContext(), usersAppliedForSpecialExtension)); //Previously said list (changed to special extension thingy)
      return view;
    }

    noUsersAppliedRoot.setVisibility(View.VISIBLE);
    message.setText(getString(R.string.no_users_applied_for_extension));
    return view;
  }
}
