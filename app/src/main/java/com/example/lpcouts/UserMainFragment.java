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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
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
        Snackbar snackbar = Snackbar.make(paramViewGroup, paramString, Snackbar.LENGTH_LONG);
        TextView textView =  snackbar.getView().findViewById(R.id.snackbar_text);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.info, 0, 0, 0);
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
        return UserData.isUserOnExtension() ? getReturnTime() : (UserExtensionActivity.isTheWeekend() ? getString(R.string.weekend_return_time) : getString(R.string.weekday_return_time));
    }

    public View initializeView() {
        final View view;
        if (this.container != null)
            this.container.removeAllViewsInLayout();
        if ((getActivity().getResources().getConfiguration()).orientation == 1) {
            view = this.inflater.inflate(R.layout.user_fragment, this.container, false);
        } else {
            view = this.inflater.inflate(R.layout.user_fragment_land, this.container, false);
        }
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.white));

        profilePicImage = (ImageView) view.findViewById(R.id.profile_picture);
        dataRoot = (ViewGroup) view.findViewById(R.id.data_root);
        transition = (ViewGroup) view.findViewById(R.id.transition_occured);
        name = (TextView) view.findViewById(R.id.name);
        blockAndRoom = (TextView) view.findViewById(R.id.block_and_room);
        root = (ViewGroup) view.findViewById(R.id.root);
        signIn = (ViewGroup) view.findViewById(R.id.sign_in);
        signOut = (ViewGroup) view.findViewById(R.id.sign_out);
        main = (ViewGroup) view.findViewById(R.id.main);
        getExtension = (RelativeLayout) view.findViewById(R.id.get_extension);
        timeLeft = (TextView) view.findViewById(R.id.time_left);
        onExtension = (TextView) view.findViewById(R.id.on_extension);
        transitionStatusText = (TextView) view.findViewById(R.id.transition_status);
        extensions = (ViewGroup) view.findViewById(R.id.extensions);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
        extensionRoot = (ViewGroup) view.findViewById(R.id.extension_root);
        done = (ViewGroup) view.findViewById(R.id.done);
        messageText = (TextView) view.findViewById(R.id.message_text);
        spaceFiller1 = (ViewGroup) view.findViewById(R.id.space_filler1);
        spaceFiller2 = (ViewGroup) view.findViewById(R.id.space_filler2);
        moreVert = (ImageView) view.findViewById(R.id.more_vert);
        picRoot = FirebaseStorage.getInstance().getReference();

        handler = new Handler() {
            public void handleMessage(Message param1Message) {
                int i = param1Message.arg1;
                UserMainFragment.this.progressBar.setProgress(i);
                if (i == 60) {
                    if (UserData.getData("Status").equals(getString(R.string.signed_in))) {
                        messageText.setText(getString(R.string.you_signed_in));
                    } else {
                        messageText.setText(getString(R.string.you_signed_out));
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
        textView.setText(getString(R.string.residential_block) + " " + UserData.getData("Block") + "/" + UserData.getData("Room"));
        listenForDataChanges();
        ImageSampler.assignVariables(getContext());
        ImageSampler.loadImage(profilePicImage);

        if (UserData.getData("Status").equals(getString(R.string.signed_in))) {
            transitionStatusText.setText(getString(R.string.signed_in));

            if (UserData.isUserOnExtension()) {
                textView = this.onExtension;
                textView.setText(getString(R.string.on_extension) + "\n" + getReturnTime());
            } else {
                this.onExtension.setText(getString(R.string.no_extension));
            }

            signIn.setVisibility(View.INVISIBLE);
            timeLeft.setVisibility(View.INVISIBLE);
            signOut.setVisibility(View.VISIBLE);
            getExtension.setVisibility(View.VISIBLE);
        } else {
            getExtension.setVisibility(View.INVISIBLE);
            signOut.setVisibility(View.INVISIBLE);
            signIn.setVisibility(View.VISIBLE);
            transitionStatusText.setText(getString(R.string.signed_out));
            onExtension.setVisibility(View.VISIBLE);

            if (UserData.isUserOnExtension()) {
                onExtension.setText(getString(R.string.on_extension) + "\n" + getReturnTime());
            } else {
                this.onExtension.setText(getString(R.string.no_extension));
            }
            if (SignIn.isLate()) {
                this.timeLeft.setText(getString(R.string.you_are_late));
            } else {
                timeLeft.setText(getString(R.string.time_of_return) + "\n" + getTime());
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
                    UserMainFragment.showSnackView(getContext(), UserMainFragment.this.coordinatorLayout, getContext().getString(R.string.already_on_extension));
                    return;
                }
                UserMainFragment.showSnackView(getContext(), UserMainFragment.this.coordinatorLayout, getString(R.string.extension_application_not_allowed));
            }
        });
        this.signOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                SignOut.assign(getContext());
                if (SignOut.canSignOut()) {
                    SignOut.signOut();
                    Animations.slideButtonsOut(param1View, view.findViewById(R.id.get_extension), UserMainFragment.this.root);
                    thread.start();
                    return;
                }
                UserMainFragment.showSnackView(getContext(), coordinatorLayout, getString(R.string.sign_out_not_allowed));
            }
        });
        this.signIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                SignIn.assignVariables(getContext());
                if (!SignIn.isLate()) {
                    SignIn.signIn();
                    thread.start();
                    Animations.slideButtonsOut(param1View, view.findViewById(R.id.sign_out), root);
                    return;
                }
                showAlertDialog(dialogView);
            }
        });
        return view;
    }

    public void listenForDataChanges() {
        final DatabaseReference disApprovedExtensions = FirebaseDatabase.getInstance().getReference()
                .child("BLOCK " + UserData.getData("Block"))
                .child("DisApproved special extensions");
        final DatabaseReference approvedExtensions = FirebaseDatabase.getInstance().getReference()
                .child("Approved special extensions")
                .child("BLOCK " + UserData.getData("Block"));

        Intent intent = new Intent(getContext(), UserMainActivity.class);

        final PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
        final Uri soundUri = RingtoneManager.getDefaultUri(2);
        final NotificationManager nm = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context, CHANNEL_ID_);
        if (Build.VERSION.SDK_INT >= 26)
            nm.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID_, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT));
        final String extensionDisApprovedMessage = getString(R.string.extension_not_approved_message);
        final String extensionApprovedMessage = getString(R.string.extension_approved_message);
        final String extensionDisApprovedTitle = getString(R.string.extension_not_approved);
        final String extensionApprovedTitle = getString(R.string.extension_approved);

        disApprovedExtensions.addValueEventListener(new ValueEventListener() {
            public void onCancelled(@NonNull DatabaseError param1DatabaseError) {
            }

            public void onDataChange(@NonNull DataSnapshot param1DataSnapshot) {
                Iterator<DataSnapshot> iterator = param1DataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    if (((iterator.next())
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
                                ).setStyle((new NotificationCompat.BigTextStyle()).bigText(extensionDisApprovedMessage))
                                .setSmallIcon(R.drawable.path);
                        nm.notify(CHANNEL_ID, builder.build());

                        return;
                    }
                }
            }
        });

        approvedExtensions.addValueEventListener(new ValueEventListener() {
            public void onCancelled(@NonNull DatabaseError param1DatabaseError) {
            }

            public void onDataChange(@NonNull DataSnapshot param1DataSnapshot) {
                Iterator<DataSnapshot> iterator = param1DataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    SpecialExtension specialExtension = ((DataSnapshot) iterator.next()).getValue(SpecialExtension.class);
                    if (specialExtension.getName().equals(UserData.getData("Name"))) {
                        UserData.onSpecialExtension(true, specialExtension);
                        Toast.makeText(getContext(), getString(R.string.extension_approved), Toast.LENGTH_SHORT).show();
                        approvedExtensions.child(UserData.getData("Name")).setValue(null);
                        builder.setAutoCancel(true)
                                .setContentTitle(extensionApprovedTitle)
                                .setContentText(extensionApprovedMessage)
                                .setSound(soundUri)
                                .setContentIntent(pendingIntent)
                                .setStyle((new NotificationCompat.BigTextStyle()).bigText(extensionApprovedMessage))
                                .setSmallIcon(R.drawable.path);
                        nm.notify(CHANNEL_ID, builder.build());
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
        inflater = paramLayoutInflater;
        container = paramViewGroup;
        View view = initializeView();
        dialogView = paramLayoutInflater.inflate(R.layout.signed_in_late, paramViewGroup, false);
        return view;
    }

    public void remindUser() {
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.putExtra("signInReminder", true);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Date date = new Date();
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar2.setTime(date);
        calendar2.set(Calendar.HOUR_OF_DAY, 6); //Figured out what the 11, 12 and 5 is? (hour of day / minute)
        calendar2.set(Calendar.MINUTE, 1);
        if (calendar2.before(calendar1))
            calendar2.add(Calendar.DAY_OF_MONTH, 1); //The right 5?

        ((AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent);
    }

    public void showAlertDialog(View paramView) {
        final CheckBox remindUser = dialogView.findViewById(R.id.remind_me);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(paramView);
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
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
