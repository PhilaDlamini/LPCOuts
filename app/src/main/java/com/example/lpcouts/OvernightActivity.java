package com.example.lpcouts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    setContentView(R.layout.activity_overnight);
    cross = (ImageView)findViewById(R.id.cross);
    check = (ImageView)findViewById(R.id.check);
    tutor = (EditText)findViewById(R.id.tutor);
    address = (EditText)findViewById(R.id.address);
    year = (EditText)findViewById(R.id.year);
    companion = (EditText)findViewById(R.id.companion);
    hostName = (EditText)findViewById(R.id.host_name);
    hostPhone = (EditText)findViewById(R.id.host_mobile);
    overnightsReference = FirebaseDatabase.getInstance().getReference().child("Overnight applications");

    cross.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
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
