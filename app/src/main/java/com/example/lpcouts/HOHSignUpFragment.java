package com.example.lpcouts;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HOHSignUpFragment extends Fragment {

  public static final String FRAGMENT_NAME = "HOH sign up fragment";
  private final int PROFILE_PICTURE = 30;
  
  EditText block, emailAccount;
  RelativeLayout cameraIcon;
  ViewGroup container;
  Button createAccount;
  LinearLayout guard, hoh, student;
  ImageView hohIcon, guardIcon, userProfilePic, studentIcon ;
  TextView hohText, guardText, studentText;
  IconClickListener iconClickListener;
  private Uri imageUri = null;
  LayoutInflater inflater;
  EditText name, password, room;
  private String nameEntered;
  CreationInProgress progress;
  RelativeLayout root;


  
  private void findViews(View paramView) {
    cameraIcon = (RelativeLayout)paramView.findViewById(2131361984);
    root = (RelativeLayout)paramView.findViewById(2131361999);
    createAccount = (Button)paramView.findViewById(2131361859);
    userProfilePic = (ImageView)paramView.findViewById(2131361987);
    emailAccount = (EditText)paramView.findViewById(2131361886);
    password = (EditText)paramView.findViewById(2131361978);
    name = (EditText)paramView.findViewById(2131361956);
    block = (EditText)paramView.findViewById(2131361835);
    student = (LinearLayout)paramView.findViewById(2131362049);
    hoh = (LinearLayout)paramView.findViewById(2131361917);
    guard = (LinearLayout)paramView.findViewById(2131361912);
    studentIcon = (ImageView)paramView.findViewById(2131362050);
    hohIcon = (ImageView)paramView.findViewById(2131361918);
    guardIcon = (ImageView)paramView.findViewById(2131361913);
    studentText = (TextView)paramView.findViewById(2131362051);
    hohText = (TextView)paramView.findViewById(2131361919);
    guardText = (TextView)paramView.findViewById(2131361914);
  }
  
  public void hideIcons() {
    this.student.setVisibility(View.GONE);
    this.guard.setVisibility(View.GONE);
    this.createAccount.setVisibility(View.VISIBLE);
  }
  
  public void hideWhenType() {

    emailAccount.addTextChangedListener(new TextWatcher() {
          public void afterTextChanged(Editable param1Editable) {}
          public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
          public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) { HOHSignUpFragment.this.hideIcons(); }
        });

    password.addTextChangedListener(new TextWatcher() {
          public void afterTextChanged(Editable param1Editable) {}
          
          public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
          
          public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) { HOHSignUpFragment.this.hideIcons(); }
        });

    block.addTextChangedListener(new TextWatcher() {
          public void afterTextChanged(Editable param1Editable) {}
          public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
          public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) { HOHSignUpFragment.this.hideIcons(); }
        });

    name.addTextChangedListener(new TextWatcher() {
          public void afterTextChanged(Editable param1Editable) {}
          public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
          public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) { HOHSignUpFragment.this.hideIcons(); }
        });
  }
  
  public void highlightHOH() {
    this.studentIcon.setImageResource(2131230940);
    this.studentText.setTextColor(getResources().getColor(2131099681));
    this.hohIcon.setImageResource(2131230870);
    this.hohText.setTextColor(getResources().getColor(2131099803));
    this.guardIcon.setImageResource(2131230866);
    this.guardText.setTextColor(getResources().getColor(2131099681));
  }
  
  public View initializeView() {
    View view;
    if (this.container != null)
      this.container.removeAllViewsInLayout(); 
    if ((getActivity().getResources().getConfiguration()).orientation == 1) {
      view = this.inflater.inflate(2131558456, this.container, false);
    } else {
      view = this.inflater.inflate(2131558457, this.container, false);
    } 
    findViews(view);
    highlightHOH();
    hideWhenType();

    root.setOnTouchListener(new View.OnTouchListener() {
          public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
            HOHSignUpFragment.this.hideIcons();
            return true;
          }
        });


    student.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) { HOHSignUpFragment.this.iconClickListener.onStudentClick(); }
        });

    cameraIcon.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            UserData.returnToFragment("HOH sign up fragment");
            HOHSignUpFragment.this.startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 30);
            HOHSignUpFragment.this.hideWhenType();
          }
        });


    guard.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) { HOHSignUpFragment.this.iconClickListener.onGuardClick(); }
        });
    hoh.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) { HOHSignUpFragment.this.showIcons(); }
        });
    student.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) { HOHSignUpFragment.this.iconClickListener.onStudentClick(); }
        });
    createAccount.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            String emailAccount = HOHSignUpFragment.this.emailAccount.getText().toString();
            String password = HOHSignUpFragment.this.password.getText().toString();
            String block = HOHSignUpFragment.this.block.getText().toString();

            if (!emailAccount.isEmpty() && !password.isEmpty() && !HOHSignUpFragment.this.nameEntered.isEmpty() && !block.isEmpty()) {
              if (SignUp.isLpcEmail(emailAccount)) {
                if (HOHSignUpFragment.this.imageUri != null) {
                  try {
                    Integer.parseInt(block);
                    HOHSignUpFragment.this.progress.onCreationInProgress();
                    SignUp.assignVariables(HOHSignUpFragment.this.getContext(), HOHSignUpFragment.this.imageUri);
                    SignUp.createAccount(new HOHAccount(HOHSignUpFragment.this.nameEntered, emailAccount, block), password, "HOH Account", HOHSignUpFragment.this.nameEntered);
                  } catch (NumberFormatException numberFormatException) {
                    Toast.makeText(HOHSignUpFragment.getContext(), HOHSignUpFragment.this.getString(2131755059), Toast.LENGTH_SHORT).show();
                  } 
                  return;
                } 
                Toast.makeText(HOHSignUpFragment.getContext(), HOHSignUpFragment.this.getString(2131755171), Toast.LENGTH_SHORT).show();
                return;
              } 
              Toast.makeText(HOHSignUpFragment.getContext(), HOHSignUpFragment.this.getString(2131755197), Toast.LENGTH_SHORT).show();
              return;
            } 
            Toast.makeText(HOHSignUpFragment.getContext(), HOHSignUpFragment.this.getString(2131755103), Toast.LENGTH_SHORT).show();
          }
        });
    return view;
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt1 == 30 && paramInt2 == -1) {
      this.imageUri = paramIntent.getData();
      ImageSampler.loadFromUri(getContext(), this.imageUri, this.userProfilePic);
    } 
  }
  
  public void onAttach(Context paramContext) {
    super.onAttach(paramContext);
    this.iconClickListener = (IconClickListener)paramContext;
    this.progress = (CreationInProgress)paramContext;
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration) {
    String email = this.emailAccount.getText().toString();
    String password = this.password.getText().toString();
    String name = this.name.getText().toString();
    String block = this.block.getText().toString();
    View view = initializeView();
    this.emailAccount.setText(email);
    this.password.setText(password);
    this.name.setText(name);
    this.block.setText(block);
    this.container.addView(view);
    super.onConfigurationChanged(paramConfiguration);
  }
  
  @Nullable
  public View onCreateView(LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
    this.inflater = paramLayoutInflater;
    this.container = paramViewGroup;
    return initializeView();
  }
  
  public void showIcons() {
    this.hoh.setVisibility(View.VISIBLE);
    this.guard.setVisibility(View.VISIBLE);
    this.student.setVisibility(View.VISIBLE);
    this.createAccount.setVisibility(View.VISIBLE);
  }
}
