package com.example.lpcouts;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ViewGroup verification;
    Button verify;

    public void assignVariables() {
        UserData.assignSharedPreferences(getApplicationContext());
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.verification = (ViewGroup)findViewById(R.id.verification);
        this.verify = (Button)findViewById(R.id.verify);
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
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GuardMainFragment()).commit();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
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
                                Toast.makeText(UserData.context, this.getString(R.string.failed_to_send_link) + e, Toast.LENGTH_SHORT).show();
                            }
                        });
                    MainActivity.this.verification.setVisibility(View.VISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(this, this.getString(R.string.error) + e, Toast.LENGTH_SHORT).show();
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
        ((AlarmManager) this.getSystemService(Context.ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent);
    }
}
