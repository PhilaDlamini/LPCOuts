package com.example.lpcouts;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.LinkedHashMap;

public class RegularExtensionFragment extends Fragment {
  ImageView back, getExtension, moreVert, pic;
  TextView returnDate;
  onGetExtensionClicked callback;
  ViewGroup extensionsApproved;

  
  public String getReturnDate() {
    Calendar calendar = Calendar.getInstance();
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>();
    linkedHashMap.put(Integer.valueOf(0), "Jan");
    linkedHashMap.put(Integer.valueOf(1), "Feb");
    linkedHashMap.put(Integer.valueOf(2), "Mar");
    linkedHashMap.put(Integer.valueOf(3), "Apr");
    linkedHashMap.put(Integer.valueOf(4), "May");
    linkedHashMap.put(Integer.valueOf(5), "Jun");
    linkedHashMap.put(Integer.valueOf(6), "Jul");
    linkedHashMap.put(Integer.valueOf(7), "Aug");
    linkedHashMap.put(Integer.valueOf(8), "Sep");
    linkedHashMap.put(Integer.valueOf(9), "Oct");
    linkedHashMap.put(Integer.valueOf(10), "Nov");
    linkedHashMap.put(Integer.valueOf(11), "Dec");
    return linkedHashMap.get(calendar.get(Calendar.MONTH)) + " " + calendar.get(Calendar.DAY_OF_MONTH) + 1 + ", " + calendar.get(Calendar.YEAR);
  }
  
  public void onAttach(Context paramContext) {
    super.onAttach(paramContext);
    this.callback = (onGetExtensionClicked)paramContext;
  }
  
  @Nullable
  public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
    View view = paramLayoutInflater.inflate(2131558477, paramViewGroup, false);
    this.back = (ImageView)view.findViewById(2131361831);
    this.moreVert = (ImageView)view.findViewById(2131361952);
    this.getExtension = (ImageView)view.findViewById(2131361907);
    this.returnDate = (TextView)view.findViewById(2131361871);
    this.pic = (ImageView)view.findViewById(2131361987);
    this.extensionsApproved = (ViewGroup)view.findViewById(2131361896);
    this.returnDate.setText(getReturnDate());

    ImageSampler.assignVariables(getContext());
    ImageSampler.loadImage(pic);

    this.back.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            Intent intent = new Intent(this.getContext(), UserMainActivity.class);
            this.startActivity(intent);
          }
        });

    this.moreVert.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            HOHExtensionsActivity.showPopUpView(this.getContext(), moreVert);
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
                      intent.addFlags(67108864);
                      RegularExtensionFragment.this.startActivity(intent);
                    }
                    
                    public void onAnimationRepeat(Animator param2Animator) {}
                    
                    public void onAnimationStart(Animator param2Animator) {}
                  });
              animator.start();
              RegularExtensionFragment.this.extensionsApproved.setVisibility(View.VISIBLE);
              return;
            } 
            Toast.makeText(this.getContext(), "Cannot get an extension at this point", Toast.LENGTH_SHORT).show();
          }
        });
    return view;
  }
  
  static interface onGetExtensionClicked {
    void onGetExtensionClicked();
  }
}
