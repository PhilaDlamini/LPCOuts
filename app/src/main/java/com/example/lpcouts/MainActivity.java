package com.example.lpcouts;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ViewGroup verification;
    Button verify;

    public void assignVariables() {
        UserData.assignSharedPreferences(getApplicationContext());
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.verification = (ViewGroup)findViewById(2131362095);
        this.verify = (Button)findViewById(2131362096);
    }

    public void loadUserInfo() {
        String str = UserData.getData("Account type");
        if (str.equals("User Account")) {
            startActivity(new Intent(this, UserMainActivity.class));
            return;
        }
        if (str.equals("HOH Account")) {
            startActivity(new Intent(getApplicationContext(), HOHMainActivity.class));
            return;
        }
        getSupportFragmentManager().beginTransaction().replace(2131361906, new GuardMainFragment()).commit();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(2131558431);
        getWindow().getDecorView().setSystemUiVisibility(8192);
        assignVariables();
        refresh();
        this.verify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.APP_EMAIL");
                MainActivity.this.startActivity(intent);
            }
        });
    }

    public void onStart() {
        super.onStart();
        final FirebaseUser user = this.firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.reload().addOnSuccessListener(new OnSuccessListener<Void>() {
                public void onSuccess(Void param1Void) {
                    if (user.isEmailVerified()) {
                        MainActivity.this.verification.setVisibility(View.GONE);
                        MainActivity.this.loadUserInfo();
                        return;
                    }
                    if (!UserData.linkSent())
                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            public void onSuccess(Void param2Void) { UserData.linkSent(true); }
                        }).addOnFailureListener(new OnFailureListener() {
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UserData.context, this.getString(2131755102) + e, 0).show();
                            }
                        });
                    MainActivity.this.verification.setVisibility(0);
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(this, this.getString(2131755089) + e, 0).show();
                }
            });
            return;
        }
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
    }

    public void refresh() {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, new Intent(this, AlarmReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
        Date date = new Date();
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar2.setTime(date);
        calendar2.set(Calendar.HOUR_OF_DAY, 1);
        calendar2.set(Calendar.MINUTE, 1);

        if (calendar2.before(calendar1))
            calendar2.add(Calendar.DAY_OF_MONTH, 1);
        ((AlarmManager) context.getSystemService("alarm")).set(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent);
    }
}
