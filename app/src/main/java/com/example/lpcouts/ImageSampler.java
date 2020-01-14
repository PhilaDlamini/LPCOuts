package com.example.lpcouts;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import java.io.BufferedInputStream;

public class ImageSampler {
  private static Context context;
  private static StorageReference picRoot;
  
  public static void assignVariables(Context paramContext) {
    picRoot = FirebaseStorage.getInstance().getReference();
    context = paramContext;
  }
  
  private static int calculateInSampleSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2) {
    int height = paramOptions.outHeight;
    int width = paramOptions.outWidth;
    int i = 1;
    if (height > paramInt1 || width > paramInt2) {
      paramInt1 = Math.round(height / paramInt1);
      paramInt2 = Math.round(width / paramInt2);
      if (paramInt1 >= paramInt2)
        paramInt1 = paramInt2; 
      i = paramInt1;
    } 
    return i;
  }
  
  private static void loadFromFile(String paramString, ImageView paramImageView) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(paramString, options);
    options.inSampleSize = calculateInSampleSize(options, paramImageView.getHeight(), paramImageView.getWidth());
    options.inJustDecodeBounds = false;
    paramImageView.setImageBitmap(BitmapFactory.decodeFile(paramString));
  }
  
  public static void loadFromUri(Context paramContext, Uri paramUri, ImageView paramImageView) {
    try {
      BufferedInputStream bufferedInputStream = new BufferedInputStream(paramContext.getContentResolver().openInputStream(paramUri));
      BitmapFactory.Options options = new BitmapFactory.Options();
      options.inJustDecodeBounds = true;
      BitmapFactory.decodeStream(bufferedInputStream, null, options);
      options.inSampleSize = calculateInSampleSize(options, paramImageView.getHeight(), paramImageView.getWidth());
      options.inJustDecodeBounds = false;
      paramImageView.setImageBitmap(BitmapFactory.decodeStream(new BufferedInputStream(paramContext.getContentResolver().openInputStream(paramUri)), null, options));
      return;
    } catch (Exception exception) {
      exception.printStackTrace();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("");
      stringBuilder.append(exception.getMessage());
      Toast.makeText(paramContext, stringBuilder.toString(), 0).show();
      return;
    } 
  }
  
  public static void loadImage(final ImageView imageView) {
    String str = UserData.getData("Pic path");
    if (str != null) {
      loadFromFile(str, imageView);
      return;
    } 
    picRoot.child("Profile Pictures").child(UserData.getData("Name")).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
          public void onSuccess(Uri param1Uri) {
            Picasso picasso = Picasso.with(context);
            picasso.load("" + param1Uri).resize(imageView.getWidth(), imageView.getHeight()).centerCrop().into(imageView);
          }
        }).addOnFailureListener(new OnFailureListener() {
          public void onFailure(@NonNull Exception param1Exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to load image: ");
            stringBuilder.append(param1Exception.getMessage());
            Toast.makeText(context, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
          }
        });
  }
}
