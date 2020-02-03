package com.example.lpcouts;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import androidx.annotation.NonNull;

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
        usersDatabaseReference = FirebaseDatabase.getInstance().getReference().child(USERS);
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
                                Toast.makeText(context, context.getString(R.string.error) + " " + param2Exception.getMessage() , Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthWeakPasswordException) {
                    Toast.makeText(context, context.getString(R.string.stronger_password), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(context, context.getString(R.string.incorrect_email), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (e instanceof FirebaseAuthUserCollisionException) {
                    Toast.makeText(context, context.getString(R.string.email_in_use), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static boolean isLpcEmail(String paramString) {
        return paramString.split("@")[1].toLowerCase().trim().equals(LPC_EMAIL);
    }

    public static void saveData(final Uri imageUri, User user, final String accountType, final String nameEntered) {
        if (accountType.equals(USER_ACCOUNT)) {
            usersDatabaseReference = FirebaseDatabase.getInstance().getReference().child(USERS);
        } else if (accountType.equals(HOH_ACCOUNT)) {
            usersDatabaseReference = FirebaseDatabase.getInstance().getReference().child(HOHACCOUNTS);
        } else {
            usersDatabaseReference = FirebaseDatabase.getInstance().getReference().child(GUARDACCOUNTS);
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
                        UserData.saveStatus(context.getString(R.string.signed_in));
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
                Toast.makeText(context, context.getString(R.string.error) + "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
