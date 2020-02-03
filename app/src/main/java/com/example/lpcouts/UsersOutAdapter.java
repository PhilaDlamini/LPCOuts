package com.example.lpcouts;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Iterator;

public class UsersOutAdapter extends BaseAdapter {
  private final int TYPE_HEADER = 0;
  private final int TYPE_ITEM = 1;
  private final int TYPE_LINE = 2;
  private ArrayList<Object> allData;
  private Context context;
  private LayoutInflater inflater;
  StorageReference picsRoot;
  
  public UsersOutAdapter(ArrayList<Object> paramArrayList, Context paramContext) {
    this.allData = paramArrayList;
    this.context = paramContext;
    this.inflater = (LayoutInflater)paramContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.picsRoot = FirebaseStorage.getInstance().getReference().child("Profile Pictures");
  }
  
  public int getCount() {
    return this.allData.size();
  }
  
  public Object getItem(int paramInt) {
    return this.allData.get(paramInt);
  }
  
  public long getItemId(int paramInt) {
    return paramInt;
  }
  
  public int getItemViewType(int paramInt) {
    return (getItem(paramInt) instanceof Outs) ? TYPE_ITEM : ((getItem(paramInt) instanceof String) ? TYPE_HEADER : TYPE_LINE);
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    Outs outs;
    TextView time;
    final ImageView profilePic;
    ImageView onExtension;
    TextView name;

    int viewType = getItemViewType(paramInt);

    switch (viewType) {
      case TYPE_LINE:
        paramView = this.inflater.inflate(R.layout.users_line, paramViewGroup, false);
        break;
      case TYPE_ITEM:
        paramView = this.inflater.inflate(R.layout.users_out_item, paramViewGroup, false);
        break;
      case TYPE_HEADER:
        paramView = this.inflater.inflate(R.layout.users_out_header, paramViewGroup, false);
        break;
    }

    switch (viewType) {

      case TYPE_ITEM:
        name = (TextView)paramView.findViewById(R.id.name);
        onExtension = (ImageView)paramView.findViewById(R.id.on_extension);
        profilePic = (ImageView)paramView.findViewById(R.id.profile_picture);
        time = (TextView)paramView.findViewById(R.id.time);

        outs = (Outs)getItem(paramInt);

        picsRoot.child(outs.getName()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
              public void onSuccess(Uri param1Uri) {
                Picasso picasso = Picasso.with(UsersOutAdapter.this.context);
                picasso.load("" + param1Uri).resize(profilePic.getWidth(), profilePic.getHeight()).centerCrop().into(profilePic);
              }
            }).addOnFailureListener(new OnFailureListener() {
              public void onFailure(@NonNull Exception param1Exception) {}
            });

        name.setText(outs.getName());
        time.setText(outs.getTime());

        if (isOnExtension(outs.getName())) {
          onExtension.setVisibility(View.VISIBLE);
          return paramView;
        }

        case TYPE_HEADER:
          ((TextView)paramView.findViewById(R.id.header)).setText((String)getItem(paramInt));
        break;
    } 

    return paramView;
  }
  
  public int getViewTypeCount() {
    return 3;
  }
  
  public boolean isEnabled(int paramInt) {
    return (getItemViewType(paramInt) == TYPE_ITEM);
  }
  
  public boolean isOnExtension(String paramString) {
    boolean isOnExtension = false;

    if (UserExtensionActivity.isTheWeekend()) {
      Iterator<Extension> iterator1 = UserData.getUsersOnRegularExtension().iterator();
      while (iterator1.hasNext()) {
        if ((iterator1.next()).getName().trim().equals(paramString))
          isOnExtension = true;
      } 
      return isOnExtension;
    }

    Iterator<SpecialExtension> iterator = UserData.getUsersOnSpecialExtension().iterator();
    while (iterator.hasNext()) {
      if ((iterator.next()).getName().trim().equals(paramString))
        isOnExtension = true;
    } 
    return isOnExtension;
  }
}
