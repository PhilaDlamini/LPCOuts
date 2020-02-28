package com.example.lpcouts;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class HOHMainActivity extends AppCompatActivity {

    TextView blockSupervising, hohName;
    DrawerLayout drawerLayout;
    ImageView imageView, smallProfile;
    NavigationView navigationView;
    TabLayout tabLayout;
    ViewPager viewPager;

    public void listenForDataChanges() {
        DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mUsersSignedOut = mRoot.child(SignOut.USERS_SIGNED_OUT)
                .child("BLOCK " + UserData.getData("Block"));

        DatabaseReference specialExtensions = mRoot.child(SignOut.SPECIAL_EXTENSIONS)
                .child("BLOCK " + UserData.getData("Block"));

        DatabaseReference regularExtensions = mRoot.child("Regular extensions")
                .child("BLOCK " + UserData.getData("Block"));

        DatabaseReference specialExtensionsApplications = mRoot.child("Special extensions applications")
                .child("BLOCK " + UserData.getData("Block"));

        mUsersSignedOut.addValueEventListener(new ValueEventListener() {
            @Override
            public void onCancelled(DatabaseError e) {
                Toast.makeText(getApplicationContext(), "Database error reading data change " + e,
                        Toast.LENGTH_SHORT).show();
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                //The list of all users signed out
                ArrayList<Outs> arrayList = new ArrayList();
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    Outs outs = (iterator.next()).getValue(Outs.class);
                    arrayList.add(outs);
                }
                UserData.assignSharedPreferences(HOHMainActivity.this);
                UserData.saveUsersSignedOut(arrayList);
            }
        });

        specialExtensionsApplications.addValueEventListener(new ValueEventListener() {
            public void onCancelled(DatabaseError param1DatabaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {

                //To check if THERE was an extension application
                boolean bool = false;
                ArrayList<SpecialExtension> extensionsApplication = new ArrayList();
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    extensionsApplication.add((iterator.next()).getValue(SpecialExtension.class));
                    bool = true;
                }
                UserData.assignSharedPreferences(HOHMainActivity.this);
                UserData.saveUsersAppliedForSpecialExtension(extensionsApplication);
                if (bool)
                    notifyUser("Extension application", "A user just applied for an extension");
            }
        });

        specialExtensions.addValueEventListener(new ValueEventListener() {
            public void onCancelled(DatabaseError param1DatabaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<SpecialExtension> usersOnSpecialExtension = new ArrayList();
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext())
                    usersOnSpecialExtension.add((iterator.next()).getValue(SpecialExtension.class));
                UserData.assignSharedPreferences(HOHMainActivity.this);
                UserData.saveUsersOnSpecialExtension(usersOnSpecialExtension);
            }
        });

        regularExtensions.addValueEventListener(new ValueEventListener() {
            public void onCancelled(DatabaseError param1DatabaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Extension> usersOnRegularExtension = new ArrayList();
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext())
                    usersOnRegularExtension.add((iterator.next()).getValue(Extension.class));
                UserData.assignSharedPreferences(HOHMainActivity.this);
                UserData.saveUsersOnRegularExtension(usersOnRegularExtension);
            }
        });
    }

    public void notifyUser(String paramString1, String paramString2) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, HOHExtensionsActivity.class), 0);
        Uri uri = RingtoneManager.getDefaultUri(2);
        if (Build.VERSION.SDK_INT >= 26)
            (new NotificationChannel("LPC Outs Channel ID", "LPC Outs channel", NotificationManager.IMPORTANCE_DEFAULT)).setDescription("Sends notifications");

        NotificationCompat.Builder builder = (new NotificationCompat.Builder(this))
                .setAutoCancel(true)
                .setContentTitle(paramString1)
                .setContentText(paramString2)
                .setSound(uri)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(paramString2))
                .setSmallIcon(2131230911);

        NotificationManagerCompat.from(this)
                .notify(2, builder.build());
    }

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_hohmain);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        smallProfile = (ImageView) findViewById(R.id.user_profile_pic);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        View view = navigationView.getHeaderView(0);
        imageView = (ImageView) view.findViewById(R.id.profile_picture);
        hohName = (TextView) view.findViewById(R.id.name);
        blockSupervising = (TextView) view.findViewById(R.id.block);

        getWindow().getDecorView().setSystemUiVisibility(8192); //?

        ImageSampler.assignVariables(getApplicationContext());
        ImageSampler.loadImage(smallProfile);
        ImageSampler.loadImage(imageView);
        hohName.setText(UserData.getData("Name"));
        TextView textView = blockSupervising;
        textView.setText(getString(R.string.residential_block) + " " + UserData.getData("Block"));
        tabLayout.setupWithViewPager(viewPager);
        setUpViewPager();

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            textView = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            tabLayout.getTabAt(i).setCustomView(textView);
        }

        navigationView.getMenu().getItem(0).setChecked(true);
        listenForDataChanges();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.settings:
                        Toast.makeText(HOHMainActivity.this, "Option not available yet", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.outs:
                        Toast.makeText(HOHMainActivity.this, "You are already in this activity", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.extensions:
                        Intent intent = new Intent(HOHMainActivity.this.getApplicationContext(), HOHExtensionsActivity.class);
                        HOHMainActivity.this.startActivity(intent);
                        break;
                }

                return true;
            }
        });
    }

    public void setUpViewPager() {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new HOHFragmentUsersSignedOut(), getString(R.string.out));
        pagerAdapter.addFragment(new HOHFragmentUsersOnExtension(), getString(R.string.users_on_extension));
        pagerAdapter.addFragment(new HOHFragmentUsersLate(), getString(R.string.users_late));
        viewPager.setAdapter(pagerAdapter);
    }
}
