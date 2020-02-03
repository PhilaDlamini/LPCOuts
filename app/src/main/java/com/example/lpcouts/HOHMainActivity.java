package com.example.lpcouts;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import java.util.ArrayList;

public class HOHMainActivity extends AppCompatActivity {

  TextView blockSupervising, hohName;
  DrawerLayout drawerLayout;
  ImageView imageView, smallProfile;
  NavigationView navigationView;
  TableLayout tabLayout;
  ViewPager viewPager;
  
  public void listenForDataChanges() {
    DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUsersSignedOut = mRoot.child(SignOut.USERS_SIGNED_OUT);

    mUsersSignedOut = mUsersSignedOut.child("BLOCK " + UserData.getData("Block"));

    DatabaseReference specialExtensions = mRoot.child(SignOut.SPECIAL_EXTENSIONS)
            .child("BLOCK " + UserData.getData("Block"));

    DatabaseReference regularExtensions = mRoot.child("Regular extensions")
            .child("BLOCK " + UserData.getData("Block"));

    DatabaseReference specialExtensionsApplications = mRoot.child("Special extensions applications")
            .child("BLOCK " + UserData.getData("Block"));

    mUsersSignedOut.addValueEventListener(new ValueEventListener() {
          public void onCancelled(DatabaseError param1DatabaseError) {
            Toast.makeText(getApplicationContext(), "Database error reading data change " + param1DatabaseError,
                    Toast.LENGTH_SHORT).show();
          }
          
          public void onDataChange(dataSnapshot param1DataSnapshot) {
            ArrayList<Outs> arrayList = new ArrayList();

            Iterator<DataSnapshot> iterator = param1DataSnapshot.getChildren().iterator();
            while (iterator.hasNext()) {
              Outs outs = ((DataSnapshot)iterator.next()).getValue(Outs.class);
              Log.e("HOHMainActivity", outs.toString());
              arrayList.add(outs);
            } 
            UserData.assignSharedPreferences(HOH.getApplicationContext());
            UserData.saveUsersSignedOut(arrayList);
          }
        });

    specialExtensionsApplications.addValueEventListener(new ValueEventListener() {
          public void onCancelled(DatabaseError param1DatabaseError) {}
          
          public void onDataChange(DataSnapshot param1DataSnapshot) {
            boolean bool = false;
            ArrayList<SpecialExtension> arrayList = new ArrayList();
            Iterator<DataSnapshot> iterator = param1DataSnapshot.getChildren().iterator();
            while (iterator.hasNext()) {
              arrayList.add(((DataSnapshot)iterator.next()).getValue(SpecialExtension.class));
              bool = true;
            } 
            UserData.assignSharedPreferences(HOHMainActivity.this);
            UserData.saveUsersAppliedForSpecialExtension(arrayList);
            if (bool)
              notifyUser("Extension application", "A user just applied for an extension");
          }
        });

    specialExtensions.addValueEventListener(new ValueEventListener() {
          public void onCancelled(DatabaseError param1DatabaseError) {}
          
          public void onDataChange(DataSnapshot param1DataSnapshot) {
            ArrayList<SpecialExtension> arrayList = new ArrayList();
            Iterator<DataSnapshot> iterator = param1DataSnapshot.getChildren().iterator();
            while (iterator.hasNext())
              arrayList.add(((DataSnapshot)iterator.next()).getValue(SpecialExtension.class));
            UserData.assignSharedPreferences(HOHMainActivity.this);
            UserData.saveUsersOnSpecialExtension(arrayList);
          }
        });

    regularExtensions.addValueEventListener(new ValueEventListener() {
          public void onCancelled(DatabaseError param1DatabaseError) {}
          
          public void onDataChange(DataSnapshot param1DataSnapshot) {
            ArrayList<Extension> arrayList = new ArrayList();
            Iterator<DataSnapshot> iterator = param1DataSnapshot.getChildren().iterator();
            while (iterator.hasNext())
              arrayList.add(((DataSnapshot)iterator.next()).getValue(Extension.class));
            UserData.assignSharedPreferences(HOHMainActivity.this);
            UserData.saveUsersOnRegularExtension(arrayList);
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
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_hohmain);

    drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
    viewPager = (ViewPager)findViewById(R.id.view_pager);
    smallProfile = (ImageView)findViewById(R.id.user_profile_pic);
    tabLayout = (TabLayout)findViewById(R.id.tab_layout);
    navigationView = (NavigationView)findViewById(R.id.navigation_menu);
    View view = navigationView.getHeaderView(0);
    imageView = (ImageView)view.findViewById(R.id.profile_picture);
    hohName = (TextView)view.findViewById(R.id.name);
    blockSupervising = (TextView)view.findViewById(R.id.block);

    getWindow().getDecorView().setSystemUiVisibility(8192);

    ImageSampler.assignVariables(getApplicationContext());
    ImageSampler.loadImage(smallProfile);
    ImageSampler.loadImage(imageView);
    hohName.setText(UserData.getData("Name"));
    TextView textView = blockSupervising;
    StringBuilder stringBuilder = new StringBuilder();
    textView.setText(getString(R.string.residential_block) + " " + UserData.getData("Block"));
    tabLayout.setupWithViewPager(viewPager); //What?!!!
    setUpViewPager();

    for (int i = 0; i < tabLayout.getTabCount(); i++) {
      textView = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
      tabLayout.getTabAt(i).setCustomView(textView);
    } 
    this.navigationView.getMenu().getItem(0).setChecked(true);
    listenForDataChanges();

    this.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
          public boolean onNavigationItemSelected(MenuItem menuItem) {

            switch (menuItem.getItemId()) {

              case R.id.settings:
                Toast.makeText(HOHMainActivity.this, "Option not available yet", 0).show();
                break;

              case R.id.outs:
                Toast.makeText(HOHMainActivity.this, "You are already in this activity", 0).show();
                break;

              case R.string.extensions:
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
