package com.example.lpcouts;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import androidx.fragment.app.Fragment;

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
    cameraIcon = (RelativeLayout)paramView.findViewById(R.id.pick_photo);
    root = (RelativeLayout)paramView.findViewById(R.id.root);
    createAccount = (Button)paramView.findViewById(R.id.create_account);
    userProfilePic = (ImageView)paramView.findViewById(R.id.profile_picture);
    emailAccount = (EditText)paramView.findViewById(R.id.email);
    password = (EditText)paramView.findViewById(R.id.password);
    name = (EditText)paramView.findViewById(R.id.name);
    block = (EditText)paramView.findViewById(R.id.block);
    student = (LinearLayout)paramView.findViewById(R.id.student);
    hoh = (LinearLayout)paramView.findViewById(R.id.hoh);
    guard = (LinearLayout)paramView.findViewById(R.id.guard);
    studentIcon = (ImageView)paramView.findViewById(R.id.student_icon);
    hohIcon = (ImageView)paramView.findViewById(R.id.hoh_icon);
    guardIcon = (ImageView)paramView.findViewById(R.id.guard_icon);
    studentText = (TextView)paramView.findViewById(R.id.student_text);
    hohText = (TextView)paramView.findViewById(R.id.hoh_text);
    guardText = (TextView)paramView.findViewById(R.id.guard_text);
  }
  
  public void hideIcons() {
    student.setVisibility(View.GONE);
    guard.setVisibility(View.GONE);
    createAccount.setVisibility(View.VISIBLE);
  }
  
  public void hideWhenType() {

    emailAccount.addTextChangedListener(new TextWatcher() {
          public void afterTextChanged(Editable param1Editable) {}
          public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
          public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
            hideIcons();
          }
        });

    password.addTextChangedListener(new TextWatcher() {
          public void afterTextChanged(Editable param1Editable) {}
          
          public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
          
          public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
            hideIcons();
          }
        });

    block.addTextChangedListener(new TextWatcher() {
          public void afterTextChanged(Editable param1Editable) {}
          public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
          public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
            hideIcons();
          }
        });

    name.addTextChangedListener(new TextWatcher() {
          public void afterTextChanged(Editable param1Editable) {}
          public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
          public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
            hideIcons();
          }
        });
  }
  
  public void highlightHOH() {
    studentIcon.setImageResource(R.drawable.student_outline);
    studentText.setTextColor(getContext().getResources().getColor(R.color.black));
    hohIcon.setImageResource(R.drawable.hoh_green);
    hohText.setTextColor(getContext().getResources().getColor(R.color.teal_400));
    guardIcon.setImageResource(R.drawable.guard);
    guardText.setTextColor(getContext().getResources().getColor(R.color.black));
  }
  
  public View initializeView() {
    View view;
    if (this.container != null)
      this.container.removeAllViewsInLayout(); 
    if ((getActivity().getResources().getConfiguration()).orientation == 1) {
      view = this.inflater.inflate(R.layout.hoh_sign_up_fragment, container, false);
    } else {
      view = this.inflater.inflate(R.layout.hoh_sign_up_fragment_land, container, false);
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
            UserData.returnToFragment(FRAGMENT_NAME);
            startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), PROFILE_PICTURE);
            hideWhenType();
          }
        });


    guard.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            iconClickListener.onGuardClick();
          }
        });

    hoh.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            showIcons();
          }
        });

    student.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            iconClickListener.onStudentClick();
          }
        });

    createAccount.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            String emailAccount = HOHSignUpFragment.this.emailAccount.getText().toString();
            String password = HOHSignUpFragment.this.password.getText().toString();
            String block = HOHSignUpFragment.this.block.getText().toString();

            if (!emailAccount.isEmpty() && !password.isEmpty() && !HOHSignUpFragment.this.nameEntered.isEmpty() && !block.isEmpty()) {
              if (SignUp.isLpcEmail(emailAccount)) {
                if (imageUri != null) {
                  try {
                    Integer.parseInt(block);
                    progress.onCreationInProgress();
                    SignUp.assignVariables(getContext(), imageUri);
                    SignUp.createAccount(new HOHAccount(HOHSignUpFragment.this.nameEntered, emailAccount, block), password, "HOH Account", HOHSignUpFragment.this.nameEntered);
                  } catch (NumberFormatException numberFormatException) {
                    Toast.makeText(getContext(), getString(R.string.block_number), Toast.LENGTH_SHORT).show();
                  } 
                  return;
                } 
                Toast.makeText(getContext(), getString(R.string.select_pic), Toast.LENGTH_SHORT).show();
                return;
              } 
              Toast.makeText(getContext(), getString(R.string.use_lpc_email), Toast.LENGTH_SHORT).show();
              return;
            } 
            Toast.makeText(getContext(), getString(R.string.fill_in_all_fields), Toast.LENGTH_SHORT).show();
          }
        });
    return view;
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt1 == PROFILE_PICTURE && paramInt2 == -1) {
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
    emailAccount.setText(email);
    this.password.setText(password);
    this.name.setText(name);
    this.block.setText(block);
    this.container.addView(view);
    super.onConfigurationChanged(paramConfiguration);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    inflater = paramLayoutInflater;
    container = paramViewGroup;
    return initializeView();
  }
  
  public void showIcons() {
    hoh.setVisibility(View.VISIBLE);
    guard.setVisibility(View.VISIBLE);
    student.setVisibility(View.VISIBLE);
    createAccount.setVisibility(View.VISIBLE);
  }
}
