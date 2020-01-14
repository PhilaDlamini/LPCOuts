package com.example.lpcouts;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;

public class SaveImage extends AsyncTask<byte[], Void, Void> {
  Context context;
  
  public SaveImage(Context paramContext) { this.context = paramContext; }
  
  protected Void doInBackground(byte[]... paramVarArgs) {
    File file = Environment.getExternalStorageDirectory();
    file = new File(file, "LPCOUts" + UserData.getData("Name") + ".jpg");
    if (file.exists())
      file.delete(); 
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsoluteFile());
      fileOutputStream.write(paramVarArgs[0]);
      fileOutputStream.close();
      UserData.savePicPath(file.getPath());
      Log.e("SaveImage", "Image saved");
      Toast.makeText(this.context, "Image saved", Toast.LENGTH_SHORT).show();
      this.context.startActivity(new Intent(this.context, MainActivity.class));
    } catch (Exception exception) {
      Log.e("SaveImage", "Error saving image", exception);
    } 
    return null;
  }
}
