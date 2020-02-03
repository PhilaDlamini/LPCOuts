package com.example.lpcouts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class HOHExtensionsActivity extends AppCompatActivity {

  //The views for the class
  TextView blockSupervising, hohName;
  DrawerLayout drawerLayout;
  ImageView hamburger, moreVert, userProfilePic;
  NavigationView navigationView;
  TabLayout tabLayout;
  ViewPager viewPager;

  //Shows the pop up
  public static void showPopUpView(final Context context, View paramView) {
    PopupMenu popupMenu = new PopupMenu(context, paramView);
    popupMenu.getMenuInflater().inflate(R.menu.settings_menu, popupMenu.getMenu());
    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
          public boolean onMenuItemClick(MenuItem param1MenuItem) {
            if (param1MenuItem.getItemId() == R.id.settings)
              Toast.makeText(context, context.getString(R.string.not_complete), Toast.LENGTH_SHORT).show();
            return true;
          }
        });
    popupMenu.show();
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_hohextensions);

    //Find all the views
    drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
    navigationView = (NavigationView)findViewById(R.id.navigation_menu);
    viewPager = (ViewPager)findViewById(R.id.view_pager);
    tabLayout = (TabLayout)findViewById(R.id.tab_layout);
    hamburger = (ImageView)findViewById(R.id.hamburger);
    moreVert = (ImageView)findViewById(R.id.more_vert);
    View view = navigationView.getHeaderView(0);
    hohName = (TextView)view.findViewById(R.id.name);
    blockSupervising = (TextView)view.findViewById(R.id.block);
    userProfilePic = (ImageView)view.findViewById(R.id.profile_picture);

    //Upload the profile picture
    ImageSampler.assignVariables(getApplicationContext());
    ImageSampler.loadImage(userProfilePic);

    //Display the right data for the hoh
    hohName.setText(UserData.getData("Name"));
    blockSupervising.setText( getString(R.string.residential_block) + " " + UserData.getData("Block"));
    getWindow().getDecorView().setSystemUiVisibility(8192);  //?

    //When a navigation item is clicked
    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
      public boolean onNavigationItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {

          case R.id.outs:
            //Send user to the HOHMainActivity
            Intent intent = new Intent(getApplicationContext(), HOHMainActivity.class);
            startActivity(intent);
            break;

          case R.id.extensions:
            //Tell them they are here
            Toast.makeText( HOHExtensionsActivity.this, "You are already here", 0).show();
            break;

          case R.id.controls:
            Toast.makeText(HOHExtensionsActivity.this, "Service not available right now", 0).show();
            break;

        }

        return true;
      }

    });

    //Highlight the item with index 1
    navigationView.getMenu().getItem(1).setChecked(true);

    //Set up view pager
    setUpViewPager();
    tabLayout.setupWithViewPager(viewPager);

    //What does this do? //#############
    int i = 0;
    while (i < tabLayout.getTabCount()) {
      TextView textView = (TextView)LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
      tabLayout.getTabAt(i).setCustomView(textView);
      i++;
    }
    //#############


    //Onclick listeners for the navigation buttons
    hamburger.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            drawerLayout.openDrawer(Gravity.LEFT);
          }
        });

    moreVert.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            HOHExtensionsActivity.showPopUpView(getApplicationContext(), moreVert);
          }
        });
  }

  //Sets up view pager for the tab appLayout
  public void setUpViewPager() { //Change Fragment extensions.
    PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
    pagerAdapter.addFragment(new HOHSpecialExtensionApplicationFragment(), getString(R.string.extension_full));
    pagerAdapter.addFragment(new HOHOvernightApplicationFragment(), getString(R.string.overnight));
    viewPager.setAdapter(pagerAdapter);
  }
}

