package com.example.lpcouts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;

public class HOHFragmentUsersOnExtension extends Fragment {

  //All the views for this fragment
  ListView listView;
  ArrayList<Object> mData;
  TextView noExtensions;
  RelativeLayout noUsersOnExtension;
  
  public void addListViewData() {
    mData = new ArrayList();

    if (UserExtensionActivity.isTheWeekend()) {

      //Find users on regular extension
      mData.add(getContext().getString(R.string.regular_extension));
      ArrayList<Extension> usersOnRegularExtension = UserData.getUsersOnRegularExtension();

      if (usersOnRegularExtension.size() != 0) {
        Collections.sort(usersOnRegularExtension);
        mData.addAll(usersOnRegularExtension);
      } else {
        mData.remove(getString(R.string.regular_extension));
      } 
    } else {

      //It's not the weekend. Check for special extension
      ArrayList<SpecialExtension> usersOnSpecialExtention = new ArrayList();
      mData.add(getString(R.string.special_extension));
      usersOnSpecialExtention.addAll(UserData.getUsersOnSpecialExtension());

      if (usersOnSpecialExtention.size() != 0) {
        Collections.sort(usersOnSpecialExtention);
        mData.addAll(usersOnSpecialExtention);
      } else {
        mData.remove(getString(R.string.special_extension));
      } 
    }

    //Check if there is anybody on extension. If yes, show then, otherwise, show the message that no user is on extension
    if (mData.size() != 0) {
      UsersOnExtensionsAdapter usersOnExtensionsAdapter = new UsersOnExtensionsAdapter(mData, getContext());
      listView.setAdapter((ListAdapter)usersOnExtensionsAdapter);
      return;
    }

    noUsersOnExtension.setVisibility(View.VISIBLE);
    noExtensions.setText(getString(R.string.no_users_on_extension));
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    View view = paramLayoutInflater.inflate(R.layout.no_users_on_extension, paramViewGroup, false);
    noUsersOnExtension = (RelativeLayout)view.findViewById(R.id.no_users_out);
    listView = (ListView)view.findViewById(R.id.list_view);
    noExtensions = (TextView)view.findViewById(R.id.text_displayed);
    addListViewData();
    return view;
  }
}
