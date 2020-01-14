package com.example.lpcouts;

import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

public class UserMainActivity extends AppCompatActivity {
  ViewGroup notConnected;
  
  public String studentWifiName = "\"LPC STUDENT\"";
  
  ViewGroup wrongWifi;
  
  public boolean isConnected() {
    return (getApplicationContext().getSystemService("connectivity")).getNetworkInfo(1).isConnected();
  }
  
  public boolean isOnStudentWifi() {
    String str = (getApplicationContext().getSystemService("wifi")).getConnectionInfo().getSSID();
    return studentWifiName.matches(str);
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131558434);
    getWindow().getDecorView().setSystemUiVisibility(8192);
    this.notConnected = (ViewGroup)findViewById(2131361964);
    this.wrongWifi = (ViewGroup)findViewById(2131362104);

    if (isConnected()) {
      if (isOnStudentWifi()) {
        getSupportFragmentManager().beginTransaction().add(2131361853, new UserMainFragment()).commit();
        return;
      } 
      this.wrongWifi.setVisibility(View.VISIBLE);
      return;
    } 
    this.notConnected.setVisibility(View.VISIBLE);
  }
}
