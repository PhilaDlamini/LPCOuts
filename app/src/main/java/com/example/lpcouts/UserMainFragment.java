package com.example.lpcouts;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class UserMainFragment extends Fragment {
    private static final int CHANNEL_ID = 0;
    public final String CHANNEL_ID_ = "LPC Outs channel id";
    public final String CHANNEL_NAME = "LPC OUts channel name";

    TextView blockAndRoom, messageText, name, onExtension;
    View dialogView;
    ViewGroup container, done, extensionRoot, extensions, dataRoot, main, root, signIn, signOut,
            spaceFiller1, spaceFiller2, transition;
    Context context;
    CoordinatorLayout coordinatorLayout;
    RelativeLayout getExtension;
    Handler handler;
    LayoutInflater inflater;
    ImageView moreVert, profilePicImage;
    StorageReference picRoot;
    ProgressBar progressBar;
    Thread thread;
    TextView timeLeft, transitionStatusText;


    public static void showSnackView(Context paramContext, ViewGroup paramViewGroup, String paramString) {
        Snackbar snackbar = Snackbar.make(paramViewGroup, paramString, 0);
        TextView textView = (TextView) snackbar.getView().findViewById(2131362032);
        textView.setCompoundDrawablesWithIntrinsicBounds(2131230878, 0, 0, 0);
        textView.setTypeface(Typeface.createFromAsset(paramContext.getAssets(), "fonts/Raleway-Light.ttf"));
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setCompoundDrawablePadding(16);
        textView.setGravity(17);
        snackbar.show();
    }

    public String getReturnTime() {
        String str = UserData.getData("Regular extension instance");
        return (str != null) ? (new Extension(str)).getReturnTime() : (new SpecialExtension(UserData.getData("Special extension instance"))).getReturnTime();
    }

    public String getTime() {
        return UserData.isUserOnExtension() ? getReturnTime() : (UserExtensionActivity.isTheWeekend() ? getString(2131755206) : getString(2131755205));
    }

    public View initializeView() {
        final View view;
        if (this.container != null)
            this.container.removeAllViewsInLayout();
        if ((getActivity().getResources().getConfiguration()).orientation == 1) {
            view = this.inflater.inflate(2131558485, this.container, false);
        } else {
            view = this.inflater.inflate(2131558486, this.container, false);
        }
        getActivity().getWindow().setStatusBarColor(getResources().getColor(2131099810));
        profilePicImage = (ImageView) view.findViewById(2131361987);
        dataRoot = (ViewGroup) view.findViewById(2131361870);
        transition = (ViewGroup) view.findViewById(2131362080);
        name = (TextView) view.findViewById(2131361956);
        blockAndRoom = (TextView) view.findViewById(2131361836);
        root = (ViewGroup) view.findViewById(2131361999);
        signIn = (ViewGroup) view.findViewById(2131362027);
        signOut = (ViewGroup) view.findViewById(2131362028);
        main = (ViewGroup) view.findViewById(2131361945);
        getExtension = (RelativeLayout) view.findViewById(2131361907);
        timeLeft = (TextView) view.findViewById(2131362070);
        onExtension = (TextView) view.findViewById(2131361969);
        transitionStatusText = (TextView) view.findViewById(2131362083);
        extensions = (ViewGroup) view.findViewById(2131361899);
        progressBar = (ProgressBar) view.findViewById(2131361988);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(2131361858);
        extensionRoot = (ViewGroup) view.findViewById(2131361898);
        done = (ViewGroup) view.findViewById(2131361883);
        messageText = (TextView) view.findViewById(2131361949);
        spaceFiller1 = (ViewGroup) view.findViewById(2131362035);
        spaceFiller2 = (ViewGroup) view.findViewById(2131362036);
        moreVert = (ImageView) view.findViewById(2131361952);
        picRoot = FirebaseStorage.getInstance().getReference();

        handler = new Handler() {
            public void handleMessage(Message param1Message) {
                int i = param1Message.arg1;
                UserMainFragment.this.progressBar.setProgress(i);
                if (i == 60) {
                    if (UserData.getData("Status").equals(getString(2131755179))) {
                        messageText.setText(getString(2131755214));
                    } else {
                        messageText.setText(getString(2131755215));
                    }
                    spaceFiller1.setVisibility(View.INVISIBLE);
                    spaceFiller2.setVisibility(View.INVISIBLE);
                    UserMainFragment.this.dataRoot.setVisibility(View.INVISIBLE);
                    Animations.revealFromBottom(transition);
                    done.setVisibility(View.VISIBLE);
                }
            }
        };

        thread = new Thread(new ProgressUpdater());
        UserData.assignSharedPreferences(getContext());
        name.setText(UserData.getData("Name"));
        TextView textView = blockAndRoom;
        textView.setText(getString(2131755154) + " " + UserData.getData("Block") + "/" + UserData.getData("Room"));
        listenForDataChanges();
        ImageSampler.assignVariables(getContext());
        ImageSampler.loadImage(profilePicImage);

        if (UserData.getData("Status").equals(getString(2131755179))) {
            transitionStatusText.setText(getString(2131755179));

            if (UserData.isUserOnExtension()) {
                textView = this.onExtension;
                textView.setText(getString(2131755135) + "\n" + getReturnTime());
            } else {
                this.onExtension.setText(getString(2131755126));
            }
            this.signIn.setVisibility(View.INVISIBLE);
            this.timeLeft.setVisibility(View.INVISIBLE);
            this.signOut.setVisibility(View.VISIBLE);
            this.getExtension.setVisibility(View.VISIBLE);
        } else {
            this.getExtension.setVisibility(View.INVISIBLE);
            this.signOut.setVisibility(View.INVISIBLE);
            this.signIn.setVisibility(View.VISIBLE);
            this.transitionStatusText.setText(getString(2131755180));
            this.onExtension.setVisibility(View.VISIBLE);

            if (UserData.isUserOnExtension()) {
                onExtension.setText(getString(2131755135) + "\n" + getReturnTime());
            } else {
                this.onExtension.setText(getString(2131755126));
            }
            if (SignIn.isLate()) {
                this.timeLeft.setText(getString(2131755212));
            } else {
                timeLeft.setText(getString(2131755190) + "\n" + getTime());
            }
        }

        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                UserMainFragment.this.getFragmentManager().beginTransaction().replace(UserMainFragment.this.container.getId(), new UserMainFragment()).commit();
            }
        });

        moreVert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                HOHExtensionsActivity.showPopUpView(getContext(), UserMainFragment.this.moreVert);
            }
        });

        getExtension.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                if (SignOut.canSignOut()) {
                    if (!UserData.isUserOnExtension()) {
                        Intent intent = new Intent(getContext(), UserExtensionActivity.class);
                        startActivity(intent);
                        return;
                    }
                    UserMainFragment.showSnackView(getContext(), UserMainFragment.this.coordinatorLayout, this.getString(2131755050));
                    return;
                }
                UserMainFragment.showSnackView(getContext(), UserMainFragment.this.coordinatorLayout, getString(2131755092));
            }
        });
        this.signOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                SignOut.assign(getContext());
                if (SignOut.canSignOut()) {
                    SignOut.signOut();
                    Animations.slideButtonsOut(param1View, view.findViewById(2131361907), UserMainFragment.this.root);
                    thread.start();
                    return;
                }
                UserMainFragment.showSnackView(getContext(), coordinatorLayout, getString(2131755177));
            }
        });
        this.signIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                SignIn.assignVariables(getContext());
                if (!SignIn.isLate()) {
                    SignIn.signIn();
                    thread.start();
                    Animations.slideButtonsOut(param1View, view.findViewById(2131362028), root);
                    return;
                }
                showAlertDialog(dialogView);
            }
        });
        return view;
    }

    public void listenForDataChanges() {
        final DatabaseReference disApprovedExtensions = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference approvedExtensions = databaseReference2.child("Approved special extensions");
        databaseReference1 = databaseReference1.child("BLOCK " + UserData.getData("Block"));
        databaseReference2 = databaseReference2.child("DisApproved special extensions");
        databaseReference2 = databaseReference2.child("BLOCK " + UserData.getData("Block"));

        Intent intent = new Intent(getContext(), UserMainActivity.class);

        final PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
        final Uri soundUri = RingtoneManager.getDefaultUri(2);
        final NotificationManager nm = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context, "LPC Outs channel id");
        if (Build.VERSION.SDK_INT >= 26)
            notificationManager.createNotificationChannel(
                    new NotificationChannel("LPC Outs channel id", "LPC OUts channel name", NotificationManager.IMPORTANCE_DEFAULT));
        final String extensionDisApprovedMessage = getString(2131755099);
        final String extensionApprovedMessage = getString(2131755095);
        final String extensionDisApprovedTitle = getString(2131755098);
        final String extensionApprovedTitle = getString(2131755093);

        databaseReference2.addValueEventListener(new ValueEventListener() {
            public void onCancelled(@NonNull DatabaseError param1DatabaseError) {
            }

            public void onDataChange(@NonNull DataSnapshot param1DataSnapshot) {
                Iterator<DataSnapshot> iterator = param1DataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    if ((((DataSnapshot) iterator.next())
                            .getValue(SpecialExtension.class))
                            .getName().equals(UserData.getData("Name"))) {
                        Toast.makeText(UserMainFragment.this.context, "Your extension was disapproved", Toast.LENGTH_SHORT).show();
                        UserData.onSpecialExtension(false, null);
                        disApprovedExtensions.child(UserData.getData("Name")).setValue(null);
                        builder.setAutoCancel(true)
                                .setContentTitle(extensionDisApprovedTitle)
                                .setContentText(extensionDisApprovedMessage)
                                .setSound(soundUri)
                                .setContentIntent(pendingIntent
                                ).setStyle(new NotificationCompat.BigTextStyle()).bigText(extensionDisApprovedMessage)
                                .setSmallIcon(2131230911);
                        nm.notify(0, builder.build());

                        return;
                    }
                }
            }
        });

        databaseReference1.addValueEventListener(new ValueEventListener() {
            public void onCancelled(@NonNull DatabaseError param1DatabaseError) {
            }

            public void onDataChange(@NonNull DataSnapshot param1DataSnapshot) {
                Iterator<DataSnapshot> iterator = param1DataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    SpecialExtension specialExtension = ((DataSnapshot) iterator.next()).getValue(SpecialExtension.class);
                    if (specialExtension.getName().equals(UserData.getData("Name"))) {
                        UserData.onSpecialExtension(true, specialExtension);
                        Toast.makeText(getContext(), getString(2131755093), Toast.LENGTH_SHORT).show();
                        approvedExtensions.child(UserData.getData("Name")).setValue(null);
                        builder.setAutoCancel(true)
                                .setContentTitle(extensionApprovedTitle)
                                .setContentText(extensionApprovedMessage)
                                .setSound(soundUri)
                                .setContentIntent(pendingIntent)
                                .setStyle(new NotificationCompat.BigTextStyle()).bigText(extensionApprovedMessage)
                                .setSmallIcon(2131230911);
                        nm.notify(0, builder.build());
                        return;
                    }
                }
            }
        });
    }

    public void onConfigurationChanged(Configuration paramConfiguration) {
        container.addView(initializeView());
        super.onConfigurationChanged(paramConfiguration);
    }

    @Nullable
    public View onCreateView(LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        this.inflater = paramLayoutInflater;
        this.container = paramViewGroup;
        View view = initializeView();
        this.dialogView = paramLayoutInflater.inflate(2131558481, paramViewGroup, false);
        return view;
    }

    public void remindUser() {
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.putExtra("signInReminder", true);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 2, intent, 134217728);
        Date date = new Date();
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar2.setTime(date);
        calendar2.set(11, 6); //Figured out what the 11, 12 and 5 is?
        calendar2.set(12, 1);
        if (calendar2.before(calendar1))
            calendar2.add(5, 1);
        (getContext().getSystemService(Context.ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent);
    }

    public void showAlertDialog(View paramView) {
        final CheckBox remindUser = dialogView.findViewById(2131361993);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(paramView);
        builder.setPositiveButton(getString(2131755134), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                if (remindUser.isChecked())
                    remindUser();
                getFragmentManager().beginTransaction().replace(container.getId(), new UserMainFragment()).commit();
            }
        });
        builder.show();
    }

    class ProgressUpdater implements Runnable {
        public void run() {
            Looper.prepare();
            for (int i = 0; i < 61; i++) {
                Message message = Message.obtain();
                message.arg1 = i;
                UserMainFragment.this.handler.sendMessage(message);
                try {
                    Thread.sleep(25L);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
