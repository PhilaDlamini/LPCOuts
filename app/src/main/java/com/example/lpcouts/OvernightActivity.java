package com.example.lpcouts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OvernightActivity extends AppCompatActivity {
  public final String OVERNIGHTS = "Overnight applications";
  
  EditText address;
  ImageView check, cross;
  EditText hostName, hostPhone, companion, tutor, year;
  DatabaseReference overnightsReference;

  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131558432);
    cross = (ImageView)findViewById(2131361861);
    check = (ImageView)findViewById(2131361845);
    tutor = (EditText)findViewById(2131362085);
    address = (EditText)findViewById(2131361819);
    year = (EditText)findViewById(2131362105);
    companion = (EditText)findViewById(2131361852);
    hostName = (EditText)findViewById(2131361923);
    hostPhone = (EditText)findViewById(2131361922);
    overnightsReference = FirebaseDatabase.getInstance().getReference().child("Overnight applications");

    cross.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            OvernightActivity.this.startActivity(intent);
          }
        });

    check.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            String str1 = tutor.getText().toString();
            String str2 = year.getText().toString();
            String str3 = address.getText().toString();
            String str4 = companion.getText().toString();
            String str5 = hostName.getText().toString();
            String str6 = hostPhone.getText().toString();

            if (!str1.isEmpty() && !str2.isEmpty() && !str3.isEmpty() && !str4.isEmpty() && !str6.isEmpty() && !str5.isEmpty()) {
              OvernightApplication overnightApplication = new OvernightApplication(UserData.getData("Name"), str1, str2, str3, str4, str5, str6);

              overnightsReference.child(UserData.getData("Name")).setValue(overnightApplication)
                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                    public void onSuccess(Void param2Void) {
                      Toast.makeText(OvernightActivity.this, "Your overnight application has been sent. Wait for your HOH", Toast.LENGTH_SHORT)
                              .show(); }
                  }).addOnFailureListener(new OnFailureListener() {
                    public void onFailure(@NonNull Exception param2Exception) {
                      Toast.makeText(OvernightActivity.this, "Failed to send you application. Try again", Toast.LENGTH_SHORT).show();
                    }
                  });
            } 
          }
        });
  }
}
