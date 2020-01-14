package com.example.lpcouts;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class SignUp {
    public static final String GUARDACCOUNTS = "Guard Accounts";
    public static final String GUARD_ACCOUNT = "Guard Account";
    public static final String HOHACCOUNTS = "HOH Accounts";
    public static final String HOH_ACCOUNT = "HOH Account";
    private static final String LPC_EMAIL = "lpcuwc.edu.hk";
    public static final String PROFILE_PICTURES = "Profile Pictures";
    public static final String USERS = "Users";
    public static final String USER_ACCOUNT = "User Account";
    private static Context context;
    private static FirebaseAuth firebaseAuth;
    public static Uri imageUri;
    private static DatabaseReference specificUser;
    private static DatabaseReference usersDatabaseReference;

    public static void assignVariables(Context paramContext, Uri paramUri) {
        context = paramContext;
        usersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        firebaseAuth = FirebaseAuth.getInstance();
        imageUri = paramUri;
    }

    public static void createAccount(final User user, final String password, final String accountType, final String nameEntered) {
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    public void onSuccess(AuthResult param1AuthResult) {
                        firebaseAuth.signInWithEmailAndPassword(user.getEmail(), password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            public void onSuccess(AuthResult param2AuthResult) {
                                SignUp.saveData(SignUp.imageUri, user, accountType, nameEntered);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            public void onFailure(@NonNull Exception param2Exception) {
                                Toast.makeText(context, context.getString(2131755089) + " " + param2Exception.getMessage() , Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthWeakPasswordException) {
                    Toast.makeText(context, context.getString(2131755186), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(context, context.getString(2131755118), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (e instanceof FirebaseAuthUserCollisionException) {
                    Toast.makeText(context, context.getString(2131755088), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static boolean isLpcEmail(String paramString) {
        return paramString.split("@")[1].toLowerCase().trim().equals("lpcuwc.edu.hk");
    }

    public static void saveData(final Uri imageUri, User user, final String accountType, final String nameEntered) {
        if (accountType.equals("User Account")) {
            usersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        } else if (accountType.equals("HOH Account")) {
            usersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("HOH Accounts");
        } else {
            usersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Guard Accounts");
        }
        specificUser = usersDatabaseReference.child(user.getFullName());
        usersDatabaseReference.addValueEventListener(new ValueEventListener() {
            public void onCancelled(DatabaseError param1DatabaseError) {
            }

            public void onDataChange(DataSnapshot param1DataSnapshot) {
                Iterator<DataSnapshot> iterator = param1DataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    User user =  ((DataSnapshot) iterator.next()).getValue(User.class);
                    if (user.getFullName().equals(nameEntered)) {
                        UserData.assignSharedPreferences(context);
                        UserData.saveEmail(user.getEmail());
                        UserData.saveBlock(user.getBlock());
                        UserData.saveUserName(user.getFullName());
                        UserData.saveAccountType(accountType);
                        UserData.saveRoom(user.getRoom());
                        UserData.saveStatus(context.getString(2131755179));
                        return;
                    }
                }
            }
        });

        specificUser.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            public void onSuccess(Void param1Void) {
                new Upload(context).execute(imageUri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, context.getString(2131755089) + "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
