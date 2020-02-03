package com.example.lpcouts;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SignUpActivity extends AppCompatActivity implements IconClickListener, CreationInProgress {
  private final int PHOTO_PERMISSIONS = 2;
  ImageView backToCreate;
  Context context;
  ViewGroup creationInProgress, errorRoot, settingUp, container;

  
  public boolean havePermission() {
    return (ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0); }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_sign_up);

    creationInProgress = (ViewGroup)findViewById(R.id.creation_in_progress);
    container = (ViewGroup)findViewById(R.id.fragment_container);
    backToCreate = (ImageView)findViewById(R.id.back_to_creation);
    errorRoot = (ViewGroup)findViewById(R.id.error_root);
    settingUp = (ViewGroup)findViewById(R.id.setting_up);
    context = getApplicationContext();
    getWindow().setStatusBarColor(getResources().getColor(R.color.amber_700));

    if (UserData.shouldReturn()) {
      String str = UserData.getData("Fragment");
      if (str.equals("Guard sign up fragment")) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new GuardSignUpFragment()).commit();
        UserData.resetFragments();
      } 
      if (str.equals("HOH sign up fragment")) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new HOHSignUpFragment()).commit();
        UserData.resetFragments();
      } 
    } else {
      getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new UserSignUpFragment()).commit();
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
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GuardSignUpFragment()).commit();
  }
  
  public void onHOHClick() {
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HOHSignUpFragment()).commit();
  }
  
  public void onRequestPermissionsResult(int paramInt, @NonNull String[] paramArrayOfString, @NonNull int[] paramArrayOfint) {
    if (paramInt != PHOTO_PERMISSIONS)
      return; 
    if (paramArrayOfint.length > 0 && paramArrayOfint[0] == 0)
      paramInt = paramArrayOfint[1]; 
  }
  
  public void onStudentClick() {
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserSignUpFragment()).commit();
  }
}
