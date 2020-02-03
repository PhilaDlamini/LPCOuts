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
    cameraIcon = (RelativeLayout)paramView.findViewById(R.id.pick_photo);
    root = (RelativeLayout)paramView.findViewById(R.id.root);
    createAccount = (Button)paramView.findViewById(R.id.create_account);
    userProfilePic = (ImageView)paramView.findViewById(R.id.user_image);
    emailAccount = (EditText)paramView.findViewById(R.id.email);
    password = (EditText)paramView.findViewById(R.id.password);
    name = (EditText)paramView.findViewById(R.id.name);
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
          public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
          hideIcons();
          }
        });
    this.password.addTextChangedListener(new TextWatcher() {
          public void afterTextChanged(Editable param1Editable) {}
          public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
          public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
            hideIcons();
          }
        });
    this.name.addTextChangedListener(new TextWatcher() {
          public void afterTextChanged(Editable param1Editable) {}
          public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
          public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
           hideIcons();
          }
        });
  }

  //Highlights the guard icon
  public void highlightGuard() {
    studentIcon.setImageResource(R.drawable.student_outline);
    studentText.setTextColor(getResources().getColor(R.color.black));
    hohIcon.setImageResource(R.drawable.hoh);
    hohText.setTextColor(getResources().getColor(R.color.black));
    guardIcon.setImageResource(R.drawable.guard_green);
    guardText.setTextColor(getResources().getColor(R.color.teal_400));
  }
  
  public View initializeView() {
    View view;
    if (container != null)
      container.removeAllViewsInLayout();

    //Inflate the right view depending on orientation
    if ((getActivity().getResources().getConfiguration()).orientation == 1) {
      view = inflater.inflate(R.layout.guard_sign_up_fragment, container, false);
    } else {
      view = inflater.inflate(R.layout.guard_sign_up_fragment_land, container, false);
    }

    findViews(view);
    highlightGuard();
    hideWhenType();

    //When the camera icon is clicked, go back to choose another picture
    cameraIcon.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            UserData.returnToFragment(FRAGMENT_NAME);
            startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), PROFILE_PICTURE);
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
                  SignUp.assignVariables(getContext(), imageUri);
                  SignUp.createAccount(new Guard(nameEntered, email), passwordEntered, "Guard Account", nameEntered);
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
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    inflater = paramLayoutInflater;
    container = paramViewGroup;
    return initializeView();
  }
  
  public void showIcons() {
    hoh.setVisibility(View.VISIBLE);
    guard.setVisibility(View.VISIBLE);
    student.setVisibility(View.VISIBLE);
    createAccount.setVisibility(View.INVISIBLE);
  }
}
