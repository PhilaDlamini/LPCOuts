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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserSignUpFragment extends Fragment {
  private final String FRAGMENT_NAME = "User sign up fragment";
  private final int PROFILE_PICTURE = 30;
  
  EditText block, emailAccount, name, password, room;
  RelativeLayout cameraIcon, root;
  ViewGroup container;
  Button createAccount;
  LinearLayout guard;
  ImageView guardIcon, hohIcon, studentIcon, userProfilePic;
  TextView guardText, hohText, studentText;
  LinearLayout hoh, student;
  private Uri imageUri = null;
  LayoutInflater inflater;
  IconClickListener mClickListener;
  private String nameEntered;
  CreationInProgress progress;


  public void hideIcons() {
    hoh.setVisibility(View.GONE);
    guard.setVisibility(View.GONE);
    createAccount.setVisibility(View.GONE);
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
            UserSignUpFragment.this.hideIcons();
          }
        });

    block.addTextChangedListener(new TextWatcher() {
          public void afterTextChanged(Editable param1Editable) {}
          
          public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
          
          public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
            hideIcons();
          }
        });

    room.addTextChangedListener(new TextWatcher() {
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
  
  public View initializeView() {
    View view;
    if (this.container != null)
      this.container.removeAllViewsInLayout(); 
    if ((getActivity().getResources().getConfiguration()).orientation == 1) {
      view = this.inflater.inflate(R.layout.user_sign_up_fragment, this.container, false);
    } else {
      view = this.inflater.inflate(R.layout.user_sign_up_fragment_land, this.container, false); //huh?
    }

    cameraIcon = (RelativeLayout)view.findViewById(R.id.pick_photo);
    root = (RelativeLayout)view.findViewById(R.id.root);
    createAccount = (Button)view.findViewById(R.id.create_account);
    userProfilePic = (ImageView)view.findViewById(R.id.profile_picture);
    emailAccount = (EditText)view.findViewById(R.id.email);
    password = (EditText)view.findViewById(R.id.password);
    name = (EditText)view.findViewById(R.id.name);
    block = (EditText)view.findViewById(R.id.block);
    room = (EditText)view.findViewById(R.id.room);
    student = (LinearLayout)view.findViewById(R.id.student);
    hoh = (LinearLayout)view.findViewById(R.id.hoh);
    guard = (LinearLayout)view.findViewById(R.id.guard);
    studentIcon = (ImageView)view.findViewById(R.id.student_icon);
    hohIcon = (ImageView)view.findViewById(R.id.hoh_icon);
    guardIcon = (ImageView)view.findViewById(R.id.guard_icon);
    studentText = (TextView)view.findViewById(R.id.student_text);
    hohText = (TextView)view.findViewById(R.id.hoh_text);
    guardText = (TextView)view.findViewById(R.id.guard_text);
    hideWhenType();

    student.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            showIcons();
          }
        });

    hoh.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
           mClickListener.onHOHClick();
          }
        });
    guard.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            mClickListener.onGuardClick();
          }
        });

    root.setOnTouchListener(new View.OnTouchListener() {
          public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
            hideIcons();
            return true;
          }
        });

    cameraIcon.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            hideIcons();
            Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PROFILE_PICTURE);
          }
        });

    createAccount.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            String email = emailAccount.getText().toString().trim();
            String passwordEntered = password.getText().toString().trim();
            String blockEntered = block.getText().toString().trim();
            String roomEntered = room.getText().toString().trim();

            if (!email.isEmpty() && !passwordEntered.isEmpty() && !UserSignUpFragment.this.nameEntered.isEmpty() && !blockEntered.isEmpty() && !roomEntered.isEmpty()) {
              if (SignUp.isLpcEmail(email)) {
                if (UserSignUpFragment.this.imageUri != null) {
                  try {
                    Integer.parseInt(roomEntered);
                    Integer.parseInt(blockEntered);
                    progress.onCreationInProgress();
                    SignUp.assignVariables(getContext(), imageUri);
                    SignUp.createAccount(new User(nameEntered, email, blockEntered, roomEntered), passwordEntered, "User Account",  nameEntered);
                  } catch (NumberFormatException numberFormatException) {
                    Toast.makeText(getContext(), getContext().getString(R.string.block_and_room_number), Toast.LENGTH_SHORT).show();
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
    if (paramInt1 == 30 && paramInt2 == -1) {
      this.imageUri = paramIntent.getData();
      ImageSampler.loadFromUri(getContext(), this.imageUri, this.userProfilePic);
    } 
  }
  
  public void onAttach(Context paramContext) {
    super.onAttach(paramContext);
    mClickListener = (IconClickListener)paramContext;
    progress = (CreationInProgress)paramContext;
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration) {
    String email = emailAccount.getText().toString();
    String password = this.password.getText().toString();
    String name = this.name.getText().toString();
    String block = this.block.getText().toString();
    String room = this.room.getText().toString();
    View view = initializeView();
    emailAccount.setText(email);
    this.password.setText(password);
    this.name.setText(name);
    this.block.setText(block);
    this.room.setText(room);
    this.container.addView(view);
    super.onConfigurationChanged(paramConfiguration);
  }
  
  @Nullable
  public View onCreateView(LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
    container = paramViewGroup;
    inflater = paramLayoutInflater;
    return initializeView();
  }
  
  public void showIcons() {
    hoh.setVisibility(View.VISIBLE);
    guard.setVisibility(View.VISIBLE);
    student.setVisibility(View.VISIBLE);
    createAccount.setVisibility(View.INVISIBLE);
  }
}
