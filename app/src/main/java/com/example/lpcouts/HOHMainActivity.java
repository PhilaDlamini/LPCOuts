package com.example.lpcouts;

import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

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
          public void onCancelled(@NonNull DatabaseError param1DatabaseError) {
            Toast.makeText(HOHMainActivity.getApplicationContext(), "Database error reading data change " + param1DatabaseError,
                    Toast.LENGTH_SHORT).show();
          }
          
          public void onDataChange(@NonNull DataSnapshot param1DataSnapshot) {
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
          public void onCancelled(@NonNull DatabaseError param1DatabaseError) {}
          
          public void onDataChange(@NonNull DataSnapshot param1DataSnapshot) {
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
              HOHMainActivity.this.notifyUser("Extension application", "A user just applied for an extension"); 
          }
        });

    specialExtensions.addValueEventListener(new ValueEventListener() {
          public void onCancelled(@NonNull DatabaseError param1DatabaseError) {}
          
          public void onDataChange(@NonNull DataSnapshot param1DataSnapshot) {
            ArrayList<SpecialExtension> arrayList = new ArrayList();
            Iterator<DataSnapshot> iterator = param1DataSnapshot.getChildren().iterator();
            while (iterator.hasNext())
              arrayList.add(((DataSnapshot)iterator.next()).getValue(SpecialExtension.class));
            UserData.assignSharedPreferences(HOHMainActivity.this);
            UserData.saveUsersOnSpecialExtension(arrayList);
          }
        });

    regularExtensions.addValueEventListener(new ValueEventListener() {
          public void onCancelled(@NonNull DatabaseError param1DatabaseError) {}
          
          public void onDataChange(@NonNull DataSnapshot param1DataSnapshot) {
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
      (new NotificationChannel("LPC Outs Channel ID", "LPC Outs channel", 3)).setDescription("Sends notifications"); 

    NotificationCompat.Builder builder = (new NotificationCompat.Builder(this))
            .setAutoCancel(true)
            .setContentTitle(paramString1)
            .setContentText(paramString2)
            .setSound(uri)
            .setContentIntent(pendingIntent)
            .setStyle(new NotificationCompat.BigTextStyle())
            .bigText(paramString2)
            .setSmallIcon(2131230911);

    NotificationManagerCompat.from(this)
            .notify(2, builder.build());
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131558430);

    drawerLayout = (DrawerLayout)findViewById(2131361884);
    viewPager = (ViewPager)findViewById(2131362098);
    smallProfile = (ImageView)findViewById(2131362094);
    tabLayout = (TabLayout)findViewById(2131362055);
    navigationView = (NavigationView)findViewById(2131361959);
    View view = navigationView.getHeaderView(0);
    imageView = (ImageView)view.findViewById(2131361987);
    hohName = (TextView)view.findViewById(2131361956);
    blockSupervising = (TextView)view.findViewById(2131361835);

    getWindow().getDecorView().setSystemUiVisibility(8192);
    ImageSampler.assignVariables(getApplicationContext());
    ImageSampler.loadImage(smallProfile);
    ImageSampler.loadImage(imageView);
    hohName.setText(UserData.getData("Name"));
    TextView textView = blockSupervising;
    StringBuilder stringBuilder = new StringBuilder();
    textView.setText(getString(2131755154) + " " + UserData.getData("Block"));
    tabLayout.setupWithViewPager(viewPager);
    setUpViewPager();

    for (int i = 0; i < this.tabLayout.getTabCount(); i++) {
      textView = LayoutInflater.from(this).inflate(2131558436, null);
      tabLayout.getTabAt(i).setCustomView(textView);
    } 
    this.navigationView.getMenu().getItem(0).setChecked(true);
    listenForDataChanges();

    this.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
          public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {

              case 2131362022:
                Toast.makeText(HOHMainActivity.this, "Option not available yet", 0).show();
                break;

              case 2131361971:
                Toast.makeText(HOHMainActivity.this, "You are already in this activity", 0).show();
                break;

              case 2131361899:
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
    pagerAdapter.addFragment(new HOHFragmentUsersSignedOut(), getString(2131755137));
    pagerAdapter.addFragment(new HOHFragmentUsersOnExtension(), getString(2131755199));
    pagerAdapter.addFragment(new HOHFragmentUsersLate(), getString(2131755198));
    viewPager.setAdapter(pagerAdapter);
  }
}
