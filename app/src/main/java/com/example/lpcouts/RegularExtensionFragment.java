package com.example.lpcouts;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.LinkedHashMap;

public class RegularExtensionFragment extends Fragment {
  ImageView back, getExtension, moreVert, pic;
  TextView returnDate;
  onGetExtensionClicked callback;
  ViewGroup extensionsApproved;

  
  public String getReturnDate() {
    Calendar calendar = Calendar.getInstance();
    LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>();
    linkedHashMap.put(0, "Jan");
    linkedHashMap.put(1, "Feb");
    linkedHashMap.put(2, "Mar");
    linkedHashMap.put(3, "Apr");
    linkedHashMap.put(4, "May");
    linkedHashMap.put(5, "Jun");
    linkedHashMap.put(6, "Jul");
    linkedHashMap.put(7, "Aug");
    linkedHashMap.put(8, "Sep");
    linkedHashMap.put(9, "Oct");
    linkedHashMap.put(10, "Nov");
    linkedHashMap.put(11, "Dec");
    return linkedHashMap.get(calendar.get(Calendar.MONTH)) + " " + calendar.get(Calendar.DAY_OF_MONTH) + 1 + ", " + calendar.get(Calendar.YEAR);
  }
  
  public void onAttach(Context paramContext) {
    super.onAttach(paramContext);
    callback = (onGetExtensionClicked)paramContext;
  }
  
  @Nullable
  public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
    View view = paramLayoutInflater.inflate(R.layout.regular_extension_fragment, paramViewGroup, false);
    back = (ImageView)view.findViewById(R.id.back);
    moreVert = (ImageView)view.findViewById(R.id.more_vert);
    getExtension = (ImageView)view.findViewById(R.id.get_extension);
    returnDate = (TextView)view.findViewById(R.id.date);
    pic = (ImageView)view.findViewById(R.id.profile_picture);
    extensionsApproved = (ViewGroup)view.findViewById(R.id.extension_approved);
    returnDate.setText(getReturnDate());

    ImageSampler.assignVariables(getContext());
    ImageSampler.loadImage(pic);

    this.back.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            Intent intent = new Intent(getContext(), UserMainActivity.class);
            startActivity(intent);
          }
        });

    this.moreVert.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            HOHExtensionsActivity.showPopUpView(getContext(), moreVert);
          }
        });

    this.getExtension.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if (SignOut.canSignOut()) {
              UserData.onRegularExtension(true, new Extension(UserData.getData("Name"), "01:00", RegularExtensionFragment.this.getReturnDate()));
              UserData.saveReturnTime("01:00");

              Animator animator = Animations.getRevealAnimation((View)RegularExtensionFragment.this.extensionsApproved);
              animator.addListener(new Animator.AnimatorListener() {
                    public void onAnimationCancel(Animator param2Animator) {}
                    
                    public void onAnimationEnd(Animator param2Animator) {
                      Intent intent = new Intent(RegularExtensionFragment.this.getContext(), UserMainActivity.class);
                      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                      startActivity(intent);
                    }
                    
                    public void onAnimationRepeat(Animator param2Animator) {}
                    
                    public void onAnimationStart(Animator param2Animator) {}
                  });
              animator.start();
              RegularExtensionFragment.this.extensionsApproved.setVisibility(View.VISIBLE);
              return;
            } 
            Toast.makeText(getContext(), "Cannot get an extension at this point", Toast.LENGTH_SHORT).show();
          }
        });
    return view;
  }
  
   interface onGetExtensionClicked {
    void getExtensionClicked();
  }
}
