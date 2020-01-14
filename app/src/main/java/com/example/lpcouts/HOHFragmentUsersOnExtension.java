package com.example.lpcouts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;

public class HOHFragmentUsersOnExtension extends Fragment {

  //All the views for this fragment
  ListView listView;
  ArrayList<Object> mData;
  TextView noExtensions;
  RelativeLayout noUsersOnExtension;
  
  public void addListViewData() {
    this.mData = new ArrayList();

    if (UserExtensionActivity.isTheWeekend()) {

      //Find users on regular extension
      mData.add(getString(2131755151));
      ArrayList<Extension> usersOnRegularExtension = UserData.getUsersOnRegularExtension();

      if (usersOnRegularExtension.size() != 0) {
        Collections.sort(usersOnRegularExtension);
        this.mData.addAll(usersOnRegularExtension);
      } else {
        this.mData.remove(getString(2131755151));
      } 
    } else {

      //It's not the weekend. Check for special extension
      ArrayList<SpecialExtension> usersOnSpecialExtention = new ArrayList();
      this.mData.add(getString(2131755182));
      usersOnSpecialExtention.addAll(UserData.getUsersOnSpecialExtension());

      if (usersOnSpecialExtention.size() != 0) {
        Collections.sort(usersOnSpecialExtention);
        this.mData.addAll(usersOnSpecialExtention);
      } else {
        this.mData.remove(getString(2131755182));
      } 
    }

    //Check if there is anybody on extension. If yes, show then, otherwise, show the message that no user is on extension
    if (mData.size() != 0) {
      UsersOnExtensionsAdapter usersOnExtensionsAdapter = new UsersOnExtensionsAdapter(mData, getContext());
      listView.setAdapter((ListAdapter)usersOnExtensionsAdapter);
      return;
    }

    this.noUsersOnExtension.setVisibility(View.VISIBLE);
    this.noExtensions.setText(getString(2131755131));
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    View view = paramLayoutInflater.inflate(2131558458, paramViewGroup, false);
    this.noUsersOnExtension = (RelativeLayout)view.findViewById(2131361961);
    this.listView = (ListView)view.findViewById(2131361944);
    this.noExtensions = (TextView)view.findViewById(2131362064);
    addListViewData();
    return view;
  }
}
