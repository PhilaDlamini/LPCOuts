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

public class GuardSignUpFragment extends Fragment {

  //All the member variables
  public static final String FRAGMENT_NAME = "Guard sign up fragment";
  private final int PROFILE_PICTURE = 30;

  //All the views in the fragment
  EditText block, emailAccount, password, name;
  RelativeLayout cameraIcon, root;
  ViewGroup container;
  Button createAccount;
  LinearLayout hoh;
  LinearLayout guard;
  LinearLayout student;
  LayoutInflater inflater;
  ImageView hohIcon, guardIcon, userProfilePic, studentIcon;
  TextView hohText, guardText, studentText;
  IconClickListener iconClickListener;
  private Uri imageUri = null;
  private String nameEntered;
  CreationInProgress progress;

  private void findViews(View paramView) {

    //Find all the views
    this.cameraIcon = (RelativeLayout)paramView.findViewById(2131361984);
    this.root = (RelativeLayout)paramView.findViewById(2131361999);
    this.createAccount = (Button)paramView.findViewById(2131361859);
    this.userProfilePic = (ImageView)paramView.findViewById(2131362091);
    this.emailAccount = (EditText)paramView.findViewById(2131361886);
    this.password = (EditText)paramView.findViewById(2131361978);
    this.name = (EditText)paramView.findViewById(2131361956);
    this.student = (LinearLayout)paramView.findViewById(2131362049);
    this.hoh = (LinearLayout)paramView.findViewById(2131361917);
    this.guard = (LinearLayout)paramView.findViewById(2131361912);
    this.studentIcon = (ImageView)paramView.findViewById(2131362050);
    this.hohIcon = (ImageView)paramView.findViewById(2131361918);
    this.guardIcon = (ImageView)paramView.findViewById(2131361913);
    this.studentText = (TextView)paramView.findViewById(2131362051);
    this.hohText = (TextView)paramView.findViewById(2131361919);
    this.guardText = (TextView)paramView.findViewById(2131361914);
  }

  //Hide all the other icons
  public void hideIcons() {
    this.student.setVisibility(View.GONE);
    this.hoh.setVisibility(View.GONE);
    this.createAccount.setVisibility(View.VISIBLE);
  }

  //HIde all other icons when we are typing
  public void hideWhenType() {
    this.emailAccount.addTextChangedListener(new TextWatcher() {
          public void afterTextChanged(Editable param1Editable) {}
          public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
          public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) { GuardSignUpFragment.this.hideIcons(); }
        });
    this.password.addTextChangedListener(new TextWatcher() {
          public void afterTextChanged(Editable param1Editable) {}
          public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
          public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) { GuardSignUpFragment.this.hideIcons(); }
        });
    this.name.addTextChangedListener(new TextWatcher() {
          public void afterTextChanged(Editable param1Editable) {}
          public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
          public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) { GuardSignUpFragment.this.hideIcons(); }
        });
  }

  //Higlights the guard icon
  public void highlightGuard() {
    this.studentIcon.setImageResource(2131230940);
    this.studentText.setTextColor(getResources().getColor(2131099681));
    this.hohIcon.setImageResource(2131230869);
    this.hohText.setTextColor(getResources().getColor(2131099681));
    this.guardIcon.setImageResource(2131230867);
    this.guardText.setTextColor(getResources().getColor(2131099803));
  }
  
  public View initializeView() {
    View view;
    if (this.container != null)
      this.container.removeAllViewsInLayout();

    //Inflate the right view depending on orientation
    if ((getActivity().getResources().getConfiguration()).orientation == 1) {
      view = this.inflater.inflate(2131558454, this.container, false);
    } else {
      view = this.inflater.inflate(2131558455, this.container, false);
    }

    findViews(view);
    highlightGuard();
    hideWhenType();

    //When the camera icon is clicked, go back to choose another picture
    cameraIcon.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            UserData.returnToFragment(FRAGMENT_NAME);
            GuardSignUpFragment.this.startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), PROFILE_PICTURE);
            hideWhenType();
          }
        });

    //Hide all icons when the user touches the root
    root.setOnTouchListener(new View.OnTouchListener() {
          public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
            hideIcons();
            return true;
          }
        });

    //Move to student bottom tab
    student.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            iconClickListener.onStudentClick();
          }
        });

    //Create account when the user creates it
    createAccount.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            String email = emailAccount.getText().toString();
            String passwordEntered  = password.getText().toString();

            if (!email.isEmpty() && !passwordEntered.isEmpty() && !nameEntered.isEmpty()) {

              //Check if is lpc email
              if (SignUp.isLpcEmail(email)) {

                //Check if image is there
                if (imageUri != null) {

                  // Call the parent activity (show circular progress)
                  progress.onCreationInProgress();
                  SignUp.assignVariables(this, GuardSignUpFragment.this.imageUri);
                  SignUp.createAccount(new Guard(nameEntered, email), passwordEntered, "Guard Account", nameEntered);
                  return;
                } 
                Toast.makeText(GuardSignUpFragment.this.getContext(), GuardSignUpFragment.this.getString(2131755171), 0).show();
                return;
              } 
              Toast.makeText(GuardSignUpFragment.this.getContext(), GuardSignUpFragment.this.getString(2131755197), 0).show();
              return;
            } 
            Toast.makeText(GuardSignUpFragment.this.getContext(), GuardSignUpFragment.this.getString(2131755103), 0).show();
          }
        });

    //Go to icon sign up when that is clicked
    hoh.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
             iconClickListener.onHOHClick();
          }
        });

    //Show the icons when the guard icon is clicked
    guard.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
           showIcons(); }
        });

    return view;
  }

  //Called after the image is given
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt1 == PROFILE_PICTURE && paramInt2 == -1) {
      imageUri = paramIntent.getData(); //Get the image uri
      ImageSampler.loadFromUri(getContext(), imageUri, userProfilePic);
    } 
  }

  public void onAttach(Context paramContext) {
    super.onAttach(paramContext);
    iconClickListener = (IconClickListener)paramContext;
    progress = (CreationInProgress)paramContext;
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration) {

    //Save the data before orientation change
    String email = emailAccount.getText().toString();
    String passwordEntered = password.getText().toString();
    String nameEntered = name.getText().toString();

    //Initialize view
    View view = initializeView();
    emailAccount.setText(email);
    password.setText(passwordEntered);
    name.setText(nameEntered);
    container.addView(view);
    super.onConfigurationChanged(paramConfiguration);
  }

  //Called to create the view
  @Nullable
  public View onCreateView(LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
    this.inflater = paramLayoutInflater;
    this.container = paramViewGroup;
    return initializeView();
  }
  
  public void showIcons() {
    hoh.setVisibility(View.VISIBLE);
    guard.setVisibility(View.VISIBLE);
    student.setVisibility(View.VISIBLE);
    createAccount.setVisibility(View.INVISIBLE);
  }
}
