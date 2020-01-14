package com.example.lpcouts;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

public class Download extends AsyncTask<Void, Void, Void> {

    //The size of one megabyte (used to limit download size of image)
    private final long ONE_MEGABYTE = 1048576L;

    //Context variable
    final Context context;

    //Constructor for the class
    public Download(Context paramContext) {
        this.context = paramContext;
    }

    //Done in the background
    protected Void doInBackground(Void... paramVarArgs) {
        FirebaseStorage.getInstance().getReference().child("Profile Pictures").child(UserData.getData("Name")).getBytes(1048576L)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    public void onSuccess(byte[] param1ArrayOfbyte) {
                      //  Log.e("Download", "Image download successful/ About to save image");
                        //Start async task to save the image
                        SaveImage saveImage = new SaveImage(Download.this.context);
                        saveImage.execute(param1ArrayOfbyte);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    public void onFailure(@NonNull Exception e) {

                        //If there is a failure in downloading the image, show the error message
                        Toast.makeText(Download.this.context, e.getMessage(), Toast.LENGTH_SHORT).show();

                        //Send the user to the main activity
                        Intent intent = new Intent(Download.this.context, MainActivity.class);
                        Download.this.context.startActivity(intent);
            }
        });
        return null;
    }
}
