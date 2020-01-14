package com.example.lpcouts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.util.Calendar;

public class UserExtensionActivity extends AppCompatActivity implements RegularExtensionFragment.onGetExtensionClicked {
  public static final String BLOCK = "BLOCK";
  public static final String REGULAR_EXTENSIONS = "Regular extensions";
  public static final String SPECIAL_EXTENSION_APPLICATIONS = "Special extensions applications";
  View contaier;
  
  public static boolean isTheWeekend() {
    return (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) >= 6);
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131558428);
    tcontaier = findViewById(2131361853);
    getWindow().getDecorView().setSystemUiVisibility(8192);

    if (isTheWeekend()) {
      getSupportFragmentManager().beginTransaction().add(2131361853, new RegularExtensionFragment()).commit();
      return;
    } 
    getSupportFragmentManager().beginTransaction().add(2131361853, new SpecialExtensionFragment()).commit();
  }
  
  public void onGetExtensionClicked() {
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(67108864);
    startActivity(intent);
  }
}
