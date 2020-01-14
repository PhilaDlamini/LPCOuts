package com.example.lpcouts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

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
    popupMenu.getMenuInflater().inflate(2131623937, popupMenu.getMenu());
    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
          public boolean onMenuItemClick(MenuItem param1MenuItem) {
            if (param1MenuItem.getItemId() == 2131362022)
              Toast.makeText(context, context.getString(2131755133), Toast.LENGTH_SHORT).show();
            return true;
          }
        });
    popupMenu.show();
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131558429);

    //Find all the views
    this.drawerLayout = (DrawerLayout)findViewById(2131361884);
    this.navigationView = (NavigationView)findViewById(2131361959);
    this.viewPager = (ViewPager)findViewById(2131362098);
    this.tabLayout = (TabLayout)findViewById(2131362055);
    this.hamburger = (ImageView)findViewById(2131361915);
    this.moreVert = (ImageView)findViewById(2131361952);
    NavigationView navigationView1 = this.navigationView;
    View view = navigationView1.getHeaderView(0);
    this.hohName = (TextView)view.findViewById(2131361956);
    this.blockSupervising = (TextView)view.findViewById(2131361835);
    this.userProfilePic = (ImageView)view.findViewById(2131361987);

    //Upload the profile picture
    ImageSampler.assignVariables(getApplicationContext());
    ImageSampler.loadImage(this.userProfilePic);

    //Display the right data for the hoh
    hohName.setText(UserData.getData("Name"));

    TextView textView = this.blockSupervising;
    textView.setText( getString(2131755154) + " " + UserData.getData("Block"));
    getWindow().getDecorView().setSystemUiVisibility(8192);  //?

    //When a navigation item is clicked
    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
      public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

          case 2131361971:
            //Send user to the HOHMainActivity
            Intent intent = new Intent(getApplicationContext(), HOHMainActivity.class);
            startActivity(intent);
            break;

          case 2131361899:
            //Tell them they are here
            Toast.makeText( HOHExtensionsActivity.this, "You are already here", 0).show();
            break;

          case 2131361856:
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
    tabLayout.setupWithViewPager(this.viewPager);

    //What does this do? //#############
    int i = 0;
    while (i < this.tabLayout.getTabCount()) {
      textView = (TextView)LayoutInflater.from((Context)this).inflate(2131558436, null);
      this.tabLayout.getTabAt(i).setCustomView((View)textView);
      i++;
    }
    //#############


    //Onclick listeners for the navigation buttons
    hamburger.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            HOHExtensionsActivity.this.drawerLayout.openDrawer(8388611);
          }
        });

    moreVert.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            HOHExtensionsActivity.showPopUpView(getApplicationContext(), moreVert);
          }
        });
  }

  //Sets up view pager for the tab appLayout
  public void setUpViewPager() {
    PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
    pagerAdapter.addFragment(new HOHSpecialExtensionApplicationFragment(), getString(2131755097));
    pagerAdapter.addFragment(new HOHOvernightApplicationFragment(), getString(2131755139));
    viewPager.setAdapter((PagerAdapter)pagerAdapter);
  }
}

