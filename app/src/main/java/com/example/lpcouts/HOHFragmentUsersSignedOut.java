package com.example.lpcouts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;

public class HOHFragmentUsersSignedOut extends Fragment {

  //All the member variables for the fragment
  int mLastVisibleItem;
  TextView noExtensions;
  RelativeLayout noUsersOnExtension;
  ViewGroup parent;
  ListView usersSignedOutList;
  
  public ArrayList<Object> getData(ArrayList<Outs> paramArrayList) {

    //The lists
    ArrayList<Outs> arrayList2 = new ArrayList();
    ArrayList<String> arrayList = new ArrayList();
    ArrayList<Outs> arrayList1 = new ArrayList();

    int i = 0; //What does all this code below do really?
    while (!paramArrayList.isEmpty()) {
      Object object2 = new StringBuilder();
      object2.append("Size of users signed out ");
      object2.append(paramArrayList.size());
      Log.e("HOHFragment", object2.toString());
      if (!i) {
        arrayList.add(getString(R.string.today));
      } else if (i == 1) {
        arrayList.add(getString(R.string.yesterday));
      } else {
        arrayList.add(getDate(i));
      } 
      for (Outs outs : paramArrayList) {
        if (outs.getDate().equals(getDate(i))) {
          if (i)
            arrayList2.add(outs); 
          arrayList1.add(outs);
        } 
      } 
      if (!arrayList1.isEmpty()) {
        Collections.sort(arrayList1);
        arrayList.addAll(arrayList1);
        paramArrayList.removeAll(arrayList1);
        ArrayList arrayList3 = new ArrayList();
      } else {
        arrayList.remove(getDate(i));
        if (!i)
          arrayList.remove(getString(R.string.today));
        object2 = arrayList1;
        if (i == 1) {
          arrayList.remove(getString(R.string.yesterday));
          object2 = arrayList1;
        } 
      } 
      i++;
      Object object1 = object2;
    } 
    UserData.assignSharedPreferences(getContext());
    UserData.saveLateUsers(arrayList2);
    return (ArrayList)arrayList;
  }
  
  public String getDate(int paramInt) {

    //Don't understand even what paramInt is here (??)
    long currentTimeMillis = System.currentTimeMillis();
    long l2 = (86400000 * paramInt);

    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(currentTimeMillis - l2);

    LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>();
    linkedHashMap.put(0, "Jan");
    linkedHashMap.put(1, "Feb");
    linkedHashMap.put(2, "Mar");
    linkedHashMap.put(3, "Apr");
    linkedHashMap.put(4, "May");
    linkedHashMap.put(5, "Jun");
    linkedHashMap.put(6, "Jul");
    linkedHashMap.put(7, "Aug");
    linkedHashMap.put(8, "Sep");
    linkedHashMap.put(9, "Oct");
    linkedHashMap.put(10, "Nov");
    linkedHashMap.put(11, "Dec");

    paramInt = calendar.get(Calendar.DAY_OF_MONTH);
    int month = calendar.get(Calendar.MONTH);
    int year = calendar.get(Calendar.YEAR);

    return linkedHashMap.get(month) + " " + paramInt + ", " + year;
  }
  

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    View view = paramLayoutInflater.inflate(R.layout.list_view_layout, paramViewGroup, false);

    //Find all views
    usersSignedOutList = (ListView)view.findViewById(R.id.list_view);
    parent = (ViewGroup)view.findViewById(R.id.parent);
    noUsersOnExtension = (RelativeLayout)view.findViewById(R.id.no_users_out);
    noExtensions = (TextView)view.findViewById(R.id.text_displayed);

    //So we know the scroll position
    usersSignedOutList.setOnScrollListener(new AbsListView.OnScrollListener() {
          public void onScroll(AbsListView param1AbsListView, int param1Int1, int param1Int2, int param1Int3) {}
          
          public void onScrollStateChanged(AbsListView absListView, int param1Int) { //What is paramInt??
            param1Int = absListView.getFirstVisiblePosition();
            if (param1Int < mLastVisibleItem) {
              Toast.makeText(getContext(), "Scrolling downards", Toast.LENGTH_SHORT).show();
            } else if (param1Int > mLastVisibleItem) {
              Toast.makeText(getContext(), "Scrolling upwards", Toast.LENGTH_SHORT).show();
            } 
            mLastVisibleItem = param1Int;
          }
        });

    ArrayList<Object> arrayList = getData(UserData.getUsersSignedOut());
    if (arrayList.size() != 0) {
      usersSignedOutList.setAdapter(new UsersOutAdapter(arrayList, getContext()));
      return view;
    } 

    noUsersOnExtension.setVisibility(View.VISIBLE);
    noExtensions.setText(getString(R.string.no_users_out));
    return view;
  }
}
