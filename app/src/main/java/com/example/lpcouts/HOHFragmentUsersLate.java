package com.example.lpcouts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class HOHFragmentUsersLate extends Fragment {

  //All the member variables of the class
  ArrayList<Outs> lateUsers;
  ListView listView;
  TextView noLateUser;
  RelativeLayout noLateUserRoot;
  ViewGroup parent;
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    View view = paramLayoutInflater.inflate(R.layout.list_view_layout, paramViewGroup, false);

    //Find all the views
    listView = (ListView)view.findViewById(R.id.list_view);
    parent = (ViewGroup)view.findViewById(R.id.parent);
    noLateUserRoot = (RelativeLayout)view.findViewById(R.id.no_users_out);
    noLateUser = (TextView)view.findViewById(R.id.text_displayed);

    //Get all late users, together
    lateUsers = UserData.getLateUsers();
    ArrayList<Object> arrayList = new ArrayList(); //Previous generic type of String. Is that right?
    arrayList.add(getContext().getString(R.string.late_students));
    arrayList.addAll(lateUsers);

    //If there are late users, say this and return them
    if (lateUsers.size() != 0) {
      listView.setAdapter(new UsersOutAdapter(arrayList, getContext()));
      return view;
    }

    //Else, show there are no late users
    noLateUserRoot.setVisibility(View.VISIBLE);
    noLateUser.setText(getContext().getString(R.string.all_signed_in));
    return view;
  }
}
