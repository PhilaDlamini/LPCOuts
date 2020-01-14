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
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class HOHFragmentUsersLate extends Fragment {

  //All the member variables of the class
  ArrayList<Outs> lateUsers;
  ListView listView;
  TextView noLateUser;
  RelativeLayout noLateUserRoot;
  ViewGroup parent;
  
  @Nullable
  public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
    View view = paramLayoutInflater.inflate(2131558458, paramViewGroup, false);

    //Find all the views
    listView = (ListView)view.findViewById(2131361944);
    parent = (ViewGroup)view.findViewById(2131361975);
    noLateUserRoot = (RelativeLayout)view.findViewById(2131361961);
    noLateUser = (TextView)view.findViewById(2131362064);

    //Get all late users, together
    lateUsers = UserData.getLateUsers();
    ArrayList<Object> arrayList = new ArrayList(); //Previous generic type of String. Is that right?
    arrayList.add(getString(2131755121));
    arrayList.addAll(lateUsers);

    //If there are late users, say this and return them
    if (lateUsers.size() != 0) {
      listView.setAdapter(new UsersOutAdapter(arrayList, getContext()));
      return view;
    }

    //Else, show there are no late users
    this.noLateUserRoot.setVisibility(View.VISIBLE);
    this.noLateUser.setText(getString(2131755049));
    return view;
  }
}
