package com.example.lpcouts;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class AlarmReceiver extends BroadcastReceiver {

    //Member variables for sending notifications
    private static final int CHANNEL_ID = 0;
    public final String CHANNEL_ID_ = "LPC Outs channel id";
    public final String CHANNEL_NAME = "LPC OUts channel name";

    //Fire base database references
    DatabaseReference mRoot;
    DatabaseReference specialExtensionApplications;
    DatabaseReference usersOnRegularExtension;
    DatabaseReference usersOnSpecialExtension;

    //Called when a re
    public void onReceive(Context paramContext, Intent paramIntent) {

        //If this is a reminder to sing in, when the notification reminder
        if (paramIntent.getBooleanExtra("signInReminder", false)) {
            sendNotification(paramContext, paramContext.getString(2131755175), paramContext.getString(2131755176));
            return;
        }

        //Instantiate variables for fire base reference
        this.mRoot = FirebaseDatabase.getInstance().getReference();
        this.usersOnSpecialExtension = this.mRoot.child(SignOut.SPECIAL_EXTENSIONS);
        this.usersOnRegularExtension = this.mRoot.child("Regular extensions");
        this.specialExtensionApplications = this.mRoot.child("Special extensions applications");

        if (UserData.getData("Account type").equals("User Account")) {
            UserData.onRegularExtension(false, null); //Take them off all the extensions
            UserData.onSpecialExtension(false, null);
        }

        if (UserData.getData("Account type").equals("HOH Account")) {

            //Get all users who have not signed in yet
            ArrayList<Outs> arrayList = UserData.getUsersSignedOut();
            if (arrayList.size() > 0) { //Send notification for users not signed in
                sendNotification(paramContext, paramContext.getString(2131755121), paramContext.getString(2131755120));
            } else { //Else, tell them all users are signed in?
                sendNotification(paramContext, paramContext.getString(2131755127), paramContext.getString(2131755128));
            }

            //Save the list of all users who are signed out
            UserData.saveLateUsers(arrayList);
        }

        //Remove all extensions
        this.usersOnRegularExtension.setValue(null);
        this.specialExtensionApplications.setValue(null);
        this.usersOnSpecialExtension.setValue(null);
    }

    public void sendNotification(Context paramContext, String paramString1, String paramString2) {

        PendingIntent pendingIntent = PendingIntent.getActivity(paramContext, 0, new Intent(paramContext, HOHMainActivity.class), 0);
        Uri uri = RingtoneManager.getDefaultUri(2);

        //Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(paramContext, CHANNEL_ID_);
        builder.setAutoCancel(true).setContentTitle(paramString1).setContentText(paramString2).setSound(uri).setContentIntent(pendingIntent).setStyle((NotificationCompat.Style)(new NotificationCompat.BigTextStyle()).bigText(paramString2)).setSmallIcon(2131230911);
        NotificationManager notificationManager = (NotificationManager)paramContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 26)
            notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID_, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT));
        notificationManager.notify(0, builder.build());
    }
}
