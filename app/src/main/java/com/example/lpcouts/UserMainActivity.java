package com.example.lpcouts;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;

public class UserMainActivity extends AppCompatActivity {
  ViewGroup notConnected;
  
  public String studentWifiName = "\"LPC STUDENT\"";
  
  ViewGroup wrongWifi;
  
  public boolean isConnected() {
    return (getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(1).isConnected();
  }
  
  public boolean isOnStudentWifi() {
    String str = (getApplicationContext().getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getSSID();
    return studentWifiName.matches(str);
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_user_main);
    notConnected = (ViewGroup)findViewById(R.id.not_connected);
    wrongWifi = (ViewGroup)findViewById(R.id.wrong_wifi);

    if (isConnected()) {
      if (isOnStudentWifi()) {
        getSupportFragmentManager().beginTransaction().add(R.id.container, new UserMainFragment()).commit();
        return;
      } 
      this.wrongWifi.setVisibility(View.VISIBLE);
      return;
    } 
    this.notConnected.setVisibility(View.VISIBLE);
  }
}
