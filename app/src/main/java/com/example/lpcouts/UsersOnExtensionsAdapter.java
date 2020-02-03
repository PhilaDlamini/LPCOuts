package com.example.lpcouts;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class UsersOnExtensionsAdapter extends BaseAdapter {
  private final int TYPE_HEADER = 0;
  private final int TYPE_ITEM = 1;
  private ArrayList<Object> allData;
  private Context context;
  private LayoutInflater inflater;
  StorageReference picsRoot;
  
  public UsersOnExtensionsAdapter(ArrayList<Object> paramArrayList, Context paramContext) {
    this.allData = paramArrayList;
    this.context = paramContext;
    this.inflater = (LayoutInflater)paramContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.picsRoot = FirebaseStorage.getInstance().getReference().child("Profile Pictures");
  }
  
  public int getCount() {
    return allData.size();
  }
  
  public Object getItem(int paramInt) {
    return allData.get(paramInt);
  }
  
  public long getItemId(int paramInt) {
    return paramInt;
  }
  
  public int getItemViewType(int paramInt) {
    return (getItem(paramInt) instanceof String) ? TYPE_HEADER : TYPE_ITEM;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    SpecialExtension specialExtension;
    ViewGroup root;
    TextView reason;
    TextView name;
    TextView returnTime;
    final ImageView profilePic;

    int viewType = getItemViewType(paramInt);
    switch (viewType) {
      case TYPE_ITEM:
        paramView = this.inflater.inflate(R.layout.users_on_extensions, paramViewGroup, false);
        break;

      case TYPE_HEADER:
        paramView = this.inflater.inflate(R.layout.users_out_header, paramViewGroup, false);
        break;
    }

    switch (viewType) {

      case TYPE_ITEM:
        name = paramView.findViewById(R.id.name);
        reason = paramView.findViewById(R.id.reason);//Don't know what this is. Was previously object

        profilePic = (ImageView)paramView.findViewById(R.id.profile_picture);
        returnTime = (TextView)paramView.findViewById(R.id.return_time);
        root = (ViewGroup)paramView.findViewById(R.id.data_root);

        if (UserExtensionActivity.isTheWeekend()) {
          Extension extension = (Extension) getItem(paramInt);
          name.setText(extension.getName());
          returnTime.setText(context.getString(R.string.expires) + " " + extension.getReturnDate() + "\n" + extension.getReturnTime());
          root.setVisibility(View.GONE);
          picsRoot.child(reason.getName()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                public void onSuccess(Uri param1Uri) {
                  Picasso picasso = Picasso.with(context);
                  picasso.load("" + param1Uri).resize(profilePic.getWidth(), profilePic.getHeight()).centerCrop().into(profilePic);
                }
              }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(@NonNull Exception param1Exception) {}
              });
          return paramView;
        }

        specialExtension = (SpecialExtension)getItem(paramInt);
        name.setText(specialExtension.getName());
        returnTime.setText(context.getString(R.string.expires) + " " +
                specialExtension.getReturnTime() + "\n" + context.getString(R.string.today).toLowerCase());
        reason.setVisibility(View.VISIBLE);
        reason.setText(SpecialExtensionApplicationAdapter.getExtensionData(specialExtension));

        picsRoot.child(specialExtension.getName()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
              public void onSuccess(Uri param1Uri) {
                Picasso picasso = Picasso.with(UsersOnExtensionsAdapter.this.context);
                picasso.load("" + param1Uri).resize(profilePic.getWidth(), profilePic.getHeight()).centerCrop().into(profilePic);
              }
            }).addOnFailureListener(new OnFailureListener() {
              public void onFailure(@NonNull Exception param1Exception) {}
            });
        return paramView;

      case TYPE_HEADER:
        ((TextView)paramView.findViewById(R.id.header)).setText((String)getItem(paramInt));
        break;
    }

    return paramView;
  }
  
  public int getViewTypeCount() {
    return 2;
  }
  
  public boolean isEnabled(int paramInt) {
    return (getItemViewType(paramInt) == TYPE_ITEM);
  }
}
