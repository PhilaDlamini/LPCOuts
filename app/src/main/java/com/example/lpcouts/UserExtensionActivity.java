package com.example.lpcouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
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
    setContentView(R.layout.activity_extension);
    contaier = findViewById(R.id.container);

    if (isTheWeekend()) {
      getSupportFragmentManager().beginTransaction().add(R.id.container, new RegularExtensionFragment()).commit();
      return;
    } 
    getSupportFragmentManager().beginTransaction().add(R.id.container, new SpecialExtensionFragment()).commit();
  }
  
  public void getExtensionClicked() {
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
  }
}
