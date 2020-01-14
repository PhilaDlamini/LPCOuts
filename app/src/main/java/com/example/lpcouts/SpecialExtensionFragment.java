package com.example.lpcouts;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SpecialExtensionFragment extends Fragment {
  EditText address, reason, companion;
  ViewGroup appSent, container;
  ImageView apply, back, moreVert, userProfilePic;
  LayoutInflater inflater;
  DatabaseReference specialExtensions;

  public View initializeView() {
    View view;
    if (container != null)
       container.removeAllViewsInLayout();
    if ((getActivity().getResources().getConfiguration()).orientation == 1) {
      view = inflater.inflate(2131558483, container, false);
    } else {
      view = inflater.inflate(2131558482, container, false);
    }

    userProfilePic = (ImageView)view.findViewById(2131362091);
    back = (ImageView)view.findViewById(2131361831);
    moreVert = (ImageView)view.findViewById(2131361952);
    reason = (EditText)view.findViewById(2131361992);
    apply = (ImageView)view.findViewById(2131361827);
    address = (EditText)view.findViewById(2131361819);
    appSent = (ViewGroup)view.findViewById(2131361826);
    companion = (EditText)view.findViewById(2131361852);

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Special extensions applications");
    specialExtensions = databaseReference.child("BLOCK " + UserData.getData("Block")).child(UserData.getData("Name"));
    ImageSampler.assignVariables(getContext());
    ImageSampler.loadImage(this.userProfilePic);

    apply.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            String reason = SpecialExtensionFragment.this.reason.getText().toString();
            String address = SpecialExtensionFragment.this.address.getText().toString();
            String companion = SpecialExtensionFragment.this.companion.getText().toString();

            if (!reason.isEmpty() && !address.isEmpty() && !companion.isEmpty()) {
              SpecialExtension specialExtension = new SpecialExtension(UserData.getData("Name"), reason, companion, address, "23:00");
              SpecialExtensionFragment.this.specialExtensions.setValue(specialExtension)
                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                    public void onSuccess(Void param2Void) {
                      Animator animator = Animations.getRevealAnimation(SpecialExtensionFragment.this.appSent);
                      animator.addListener(new Animator.AnimatorListener() {
                            public void onAnimationCancel(Animator param3Animator) {}
                            
                            public void onAnimationEnd(Animator param3Animator) {
                              Intent intent = new Intent(getContext(), UserMainActivity.class);
                              intent.addFlags(67108864);
                              startActivity(intent);
                            }
                            
                            public void onAnimationRepeat(Animator param3Animator) {}
                            
                            public void onAnimationStart(Animator param3Animator) {}
                          });
                      animator.start();
                      SpecialExtensionFragment.this.appSent.setVisibility(View.VISIBLE);
                    }
                  }).addOnFailureListener(new OnFailureListener() {
                    public void onFailure(@NonNull Exception param2Exception) {
                      Toast.makeText(SpecialExtensionFragment.this.getContext(), "Could not send the application", Toast.LENGTH_SHORT).show();
                    }
                  });
              return;
            } 
            Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
          }
        });

    back.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            Intent intent = new Intent(getContext(), UserMainActivity.class);
            SpecialExtensionFragment.this.startActivity(intent);
          }
        });

    moreVert.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            HOHExtensionsActivity.showPopUpView(getContext(), moreVert);
          }
        });
    return view;
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration) {
    String reason = this.reason.getText().toString();
    String address = this.address.getText().toString();
    String companion = this.companion.getText().toString();
    View view = initializeView();
    this.reason.setText(reason);
    this.address.setText(address);
    this.companion.setText(companion);
    this.container.addView(view);
    super.onConfigurationChanged(paramConfiguration);
  }
  
  @Nullable
  public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
    this.inflater = paramLayoutInflater;
    this.container = paramViewGroup;
    return initializeView();
  }
}
