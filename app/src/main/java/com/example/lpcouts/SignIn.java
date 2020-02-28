package com.example.lpcouts;

import android.content.Context;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.LinkedHashMap;

public class SignIn {
  private static String GUARDS_USERS_SIGNED_IN = "GUARDS Users signed in";
  private static String USERS_SIGNED_OUT = "Users signed out";
  private static Context context;
  private static DatabaseReference mGuardsUserSignedInReference, mGuardsUsersSignedInRoot, mUsersOnRegularExtension, mUsersOnSpecialExtension, userSignedOutReference;

  
  public static void assignVariables(Context paramContext) {
    context = paramContext;
    mGuardsUsersSignedInRoot = FirebaseDatabase.getInstance().getReference().child(GUARDS_USERS_SIGNED_IN);
    mGuardsUserSignedInReference = mGuardsUsersSignedInRoot.child(UserData.getData("Name"));
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(USERS_SIGNED_OUT);
    userSignedOutReference = databaseReference.child("BLOCK" + UserData.getData("Block")).child(UserData.getData("Name"));
    databaseReference = FirebaseDatabase.getInstance().getReference().child("Regular extensions");
    mUsersOnRegularExtension = databaseReference.child("BLOCK " + UserData.getData("Block")).child(UserData.getData("Name"));
    databaseReference = FirebaseDatabase.getInstance().getReference().child(SignOut.SPECIAL_EXTENSIONS);
    mUsersOnSpecialExtension = databaseReference.child("BLOCK" + UserData.getData("Block")).child(UserData.getData("Name"));
  }
  
  public static String getMonth(int month) {
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
    return linkedHashMap.get(month);
  }
  
  public static boolean isLate() {
    int i = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    if (UserExtensionActivity.isTheWeekend()) {
      if (i <= 6 || i >= 23) {
        if (UserData.isUserOnExtension()) {
          if (i > 1)
          return true;
        } 
        return true;
      } 
      return false;
    } 
    return (i <= 6 || i >= 21) ? (UserData.isUserOnExtension() ? ((i == 23 || i <= 6)) : true) : false;
  }
  
  public static void signIn() {
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int minutes = calendar.get(Calendar.MINUTE);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int month = calendar.get(Calendar.MONTH);
    int year = calendar.get(Calendar.YEAR);

    String time = hour + ":" + minutes;
    String date = getMonth(month) + " " + day + ", " + year;

    new Outs(UserData.getData("Name"), UserData.getData("Block"), time, date);
    userSignedOutReference.setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
          public void onSuccess(Void param1Void) {
            UserData.onRegularExtension(false, null);
            UserData.onSpecialExtension(false, null);
            UserData.saveStatus(context.getString(R.string.signed_in));
            mUsersOnRegularExtension.setValue(null);
            mUsersOnSpecialExtension.setValue(null);
          }
        }).addOnFailureListener(new OnFailureListener() {
          public void onFailure(@NonNull Exception param1Exception) {
            Toast.makeText(context, "Failed you to sign you in", Toast.LENGTH_SHORT).show();
          }
        });
  }
}
