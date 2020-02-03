package com.example.lpcouts;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;

public class Upload extends AsyncTask<Uri, Void, Void> {
    Context context;

    public Upload(Context paramContext) {
        this.context = paramContext;
    }

    protected Void doInBackground(Uri... paramVarArgs) {
        Uri uri = paramVarArgs[0];

        FirebaseStorage.getInstance().getReference().child("Profile Pictures").child(UserData.getData("Name")).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            public void onSuccess(UploadTask.TaskSnapshot param1TaskSnapshot) {
                Log.e("Upload", "File upload successful");
                new Download(context).execute();
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Upload.this.context, "Failed to upload the image ", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}
