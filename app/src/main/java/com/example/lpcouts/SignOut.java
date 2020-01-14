package com.example.lpcouts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;

public class SignOut {
    private static String GUARDS_USERS_SIGNED_OUT = "GUARDS Users signed out";
    public static String SPECIAL_EXTENSIONS = "Special extensions";
    public static String USERS_SIGNED_OUT = "Users signed out";
    private static Context context;

    private static DatabaseReference mGuardsUserSignedOutReference, mGuardsUsersSignedOutRoot, userSignedOutReference, usersOnRegularExtension, usersOnSpecialExtension, usersSignedOutRoot;
    private static ArrayList<Outs> usersSignedOut;


    public static void GListenToUserSignOutEvent() {
        mGuardsUsersSignedOutRoot.addValueEventListener(new ValueEventListener() {
            public void onCancelled(@NonNull DatabaseError param1DatabaseError) {
            }

            public void onDataChange(@NonNull DataSnapshot param1DataSnapshot) {
                Toast.makeText(context, "Guards; a user just signed out", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void assign(Context paramContext) {
        context = paramContext;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(USERS_SIGNED_OUT);
        userSignedOutReference = databaseReference.child("BLOCK " + UserData.getData("Block")).child(UserData.getData("Name"));
        mGuardsUserSignedOutReference = FirebaseDatabase.getInstance().getReference().child(GUARDS_USERS_SIGNED_OUT).child(UserData.getData("Name"));
        usersSignedOutRoot = FirebaseDatabase.getInstance().getReference().child(USERS_SIGNED_OUT);
        mGuardsUsersSignedOutRoot = FirebaseDatabase.getInstance().getReference().child(GUARDS_USERS_SIGNED_OUT);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Regular extensions");
        usersOnRegularExtension = databaseReference.child("BLOCK " + UserData.getData("Block"));
        databaseReference = FirebaseDatabase.getInstance().getReference().child(SPECIAL_EXTENSIONS);
        usersOnSpecialExtension = databaseReference.child("BLOCK " + UserData.getData("Block"));
        usersSignedOut = new ArrayList<>();
    }

    public static boolean canSignOut() {
        int i = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        return (i > 6 && i < 21);
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

    public static void signOut() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        String time = hour + ":" + minutes;
        String date = getMonth(month) + " " + day + ", " + year;

        final Outs outs = new Outs(UserData.getData("Name"), UserData.getData("Block"), time, date);

        userSignedOutReference.setValue(outs).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> param1Task) {
                if (param1Task.isSuccessful()) {
                    UserData.saveStatus(context.getString(2131755180));
                    mGuardsUserSignedOutReference.setValue(outs)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                public void onSuccess(Void param2Void) {
                                    if (UserData.isUserOnExtension()) {
                                        if (UserData.getData("Extension type").equals("Regular extension")) {
                                            Extension extension = new Extension(UserData.getData("Regular extension instance"));
                                            usersOnRegularExtension.child(UserData.getData("Name")).setValue(extension).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                public void onSuccess(Void param3Void) {
                                                    UserData.saveStatus(context.getString(2131755180));
                                                }
                                            });
                                            return;
                                        }
                                        SpecialExtension specialExtension = new SpecialExtension(UserData.getData("Special extension instance"));
                                        usersOnSpecialExtension.child(UserData.getData("Name")).setValue(specialExtension).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            public void onSuccess(Void param3Void) {
                                                UserData.saveStatus(context.getString(2131755180));
                                            }
                                        });
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        public void onFailure(@NonNull Exception param2Exception) {
                            Toast.makeText(context, "Guards don't know you signed out", 0).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(@NonNull Exception param1Exception) {
                Toast.makeText(context, "Failed to sign you out", 0).show();
            }
        });
    }
}
