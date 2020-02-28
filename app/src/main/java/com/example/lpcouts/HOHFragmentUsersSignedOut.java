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

    public ArrayList<Object> getData(ArrayList<Outs> usersSignedOut) {

        ArrayList<Object> sortedUsers = new ArrayList();

        //Sort all users signed out according to date
        for (int i = 0; i < usersSignedOut.size(); i++) {

            if (i == 0) {
                sortedUsers.add(getString(R.string.today));
            } else if (i == 1) {
                sortedUsers.add(getString(R.string.yesterday));
            } else {
                sortedUsers.add(getDate(i));
            }

            //Temporary to hold all the outs belonging to this date
            ArrayList<Outs> tempList = new ArrayList<>();

            for (Outs out : usersSignedOut) {
                if (out.getDate().equals(getDate(i))) {
                    tempList.add(out);
                }
            }

            if (!tempList.isEmpty()) {

                //Sort the temp list by name
                Collections.sort(tempList);

                //Add to sorted list
                sortedUsers.addAll(tempList);
            } else {
                if (i == 0) {
                    sortedUsers.remove(getString(R.string.today));
                } else if (i == 1) {
                    sortedUsers.remove(getString(R.string.yesterday));
                } else {
                    sortedUsers.remove(getDate(i));
                }
            }
        }


//    UserData.assignSharedPreferences(getContext());
//    UserData.saveLateUsers(arrayList2); didn't get what it did
        return sortedUsers;
    }

    public String getDate(int numOfDaysBack) {

        //Don't understand even what paramInt is here (??)
        long currentTimeMillis = System.currentTimeMillis();
        long numOfDayInMillis = (86400000 * numOfDaysBack);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis - numOfDayInMillis);

        LinkedHashMap<Integer, String> monthMap = new LinkedHashMap<>();
        monthMap.put(0, "Jan");
        monthMap.put(1, "Feb");
        monthMap.put(2, "Mar");
        monthMap.put(3, "Apr");
        monthMap.put(4, "May");
        monthMap.put(5, "Jun");
        monthMap.put(6, "Jul");
        monthMap.put(7, "Aug");
        monthMap.put(8, "Sep");
        monthMap.put(9, "Oct");
        monthMap.put(10, "Nov");
        monthMap.put(11, "Dec");

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        return monthMap.get(month) + " " + day + ", " + year;
    }


    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View view = paramLayoutInflater.inflate(R.layout.list_view_layout, paramViewGroup, false);

        //Find all views
        usersSignedOutList = (ListView) view.findViewById(R.id.list_view);
        parent = (ViewGroup) view.findViewById(R.id.parent);
        noUsersOnExtension = (RelativeLayout) view.findViewById(R.id.no_users_out);
        noExtensions = (TextView) view.findViewById(R.id.text_displayed);

        //So we know the scroll position
        usersSignedOutList.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScroll(AbsListView param1AbsListView, int param1Int1, int param1Int2, int param1Int3) {
            }

            public void onScrollStateChanged(AbsListView absListView, int param1Int) { //What is paramInt??
                param1Int = absListView.getFirstVisiblePosition();
                if (param1Int < mLastVisibleItem) {
                    Toast.makeText(getContext(), "Scrolling downwards", Toast.LENGTH_SHORT).show();
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
