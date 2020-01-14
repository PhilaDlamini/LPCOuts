package com.example.lpcouts;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SignUpActivity extends AppCompatActivity implements IconClickListener, CreationInProgress {
  private final int PHOTO_PERMISSIONS = 2;
  ImageView backToCreate;
  Context context;
  ViewGroup creationInProgress, errorRoot, settingUp, container;

  
  public boolean havePermission() {
    return (ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0); }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131558433);

    creationInProgress = (ViewGroup)findViewById(2131361860);
    container = (ViewGroup)findViewById(2131361906);
    backToCreate = (ImageView)findViewById(2131361832);
    errorRoot = (ViewGroup)findViewById(2131361891);
    settingUp = (ViewGroup)findViewById(2131362021);
    context = getApplicationContext();
    getWindow().setStatusBarColor(getResources().getColor(2131099676));

    if (UserData.shouldReturn()) {
      String str = UserData.getData("Fragment");
      if (str.equals("Guard sign up fragment")) {
        getSupportFragmentManager().beginTransaction().add(2131361906, new GuardSignUpFragment()).commit();
        UserData.resetFragments();
      } 
      if (str.equals("HOH sign up fragment")) {
        getSupportFragmentManager().beginTransaction().add(2131361906, new HOHSignUpFragment()).commit();
        UserData.resetFragments();
      } 
    } else {
      getSupportFragmentManager().beginTransaction().add(2131361906, new UserSignUpFragment()).commit();
    } 
    if (!havePermission())
      ActivityCompat.requestPermissions(this, new String[] { "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE" }, 2);

    this.backToCreate.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            SignUpActivity.this.container.setVisibility(View.VISIBLE);
            SignUpActivity.this.creationInProgress.setVisibility(View.GONE);
          }
        });
  }
  
  public void onCreationInProgress() {
    this.creationInProgress.setVisibility(View.VISIBLE);
    this.container.setVisibility(View.GONE);
    this.errorRoot.setVisibility(View.GONE);
    this.settingUp.setVisibility(View.VISIBLE);
  }
  
  public void onGuardClick() {
    getSupportFragmentManager().beginTransaction().replace(2131361906, new GuardSignUpFragment()).commit();
  }
  
  public void onHOHClick() {
    getSupportFragmentManager().beginTransaction().replace(2131361906, new HOHSignUpFragment()).commit();
  }
  
  public void onRequestPermissionsResult(int paramInt, @NonNull String[] paramArrayOfString, @NonNull int[] paramArrayOfint) {
    if (paramInt != 2)
      return; 
    if (paramArrayOfint.length > 0 && paramArrayOfint[0] == 0)
      paramInt = paramArrayOfint[1]; 
  }
  
  public void onStudentClick() {
    getSupportFragmentManager().beginTransaction().replace(2131361906, new UserSignUpFragment()).commit();
  }
}
