package com.example.lpcouts;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SpecialExtensionApplicationAdapter extends ArrayAdapter {
    public static final String APPROVED_EXTENSIONS = "Approved special extensions";
    public static final String DISAPPROVED_EXTENSIONS = "DisApproved special extensions";
    private DatabaseReference approvedExtensionsReference;
    Context context;
    private DatabaseReference disApprovedExtensionsReference;
    ArrayList<SpecialExtension> extensions;
    private DatabaseReference extensionsApplicationReference;
    StorageReference picsRoot;

    public SpecialExtensionApplicationAdapter(Context paramContext, ArrayList<SpecialExtension> paramArrayList) {
        super(paramContext, 0, paramArrayList);
        this.context = paramContext;
        this.extensions = paramArrayList;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Special extensions applications");
        this.extensionsApplicationReference = databaseReference.child("BLOCK" + UserData.getData("Block"));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Approved special extensions");
        this.approvedExtensionsReference = databaseReference.child("BLOCK " + UserData.getData("Block"));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("DisApproved special extensions");
        this.disApprovedExtensionsReference = databaseReference.child("BLOCK " + UserData.getData("Block"));

        this.picsRoot = FirebaseStorage.getInstance().getReference().child("Profile Pictures");
    }

    public static String getExtensionData(SpecialExtension specialExtension) {
        return "For " + specialExtension.getReason() + "\n" + specialExtension.getAddress() + "\nWith " + specialExtension.getCompanion();
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        if (paramView == null)
            paramView = LayoutInflater.from(this.context).inflate(2131558452, paramViewGroup, false);

        final ImageView userImage = (ImageView) paramView.findViewById(2131362091);
        TextView textView7 = (TextView) paramView.findViewById(2131362093);
        TextView textView4 = (TextView) paramView.findViewById(2131362030);
        TextView textView5 = (TextView) paramView.findViewById(2131361865);
        TextView textView6 = (TextView) paramView.findViewById(2131361866);
        TextView textView1 = (TextView) paramView.findViewById(2131361867);
        TextView textView2 = (TextView) paramView.findViewById(2131361828);
        TextView textView3 = (TextView) paramView.findViewById(2131361881);

        paramView.findViewById(2131361869).setVisibility(8);

        //Set data
        final SpecialExtension specialExtension = extensions.get(paramInt);
        textView7.setText(specialExtension.getName());
        textView4.setText(context.getString(2131755155) + " " + specialExtension.getReturnTime());
        textView5.setText(specialExtension.getReason().trim());
        textView6.setText(specialExtension.getAddress().trim());
        textView1.setText(context.getString(2131755207) + " " + specialExtension.getCompanion().trim());

        picsRoot.child(specialExtension.getName()).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    public void onSuccess(Uri imageUri) {
                        Picasso picasso = Picasso.with(SpecialExtensionApplicationAdapter.this.context);
                        picasso.load("" + imageUri).resize(userImage.getWidth(), userImage.getHeight()).centerCrop().into(userImage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(@NonNull Exception param1Exception) {
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                SpecialExtensionApplicationAdapter.this.approvedExtensionsReference.child(specialExtension.getName()).setValue(specialExtension).addOnSuccessListener(new OnSuccessListener<Void>() {
                    public void onSuccess(Void param2Void) {
                        Toast.makeText(context, "You approved " + specialExtension.getName() + "'s extension", Toast.LENGTH_SHORT).show();
                        extensionsApplicationReference.child(specialExtension.getName()).setValue(null);
                        extensions.remove(specialExtension);
                        UserData.saveUsersAppliedForSpecialExtension(extensions);
                        notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    public void onFailure(@NonNull Exception param2Exception) {
                        Toast.makeText(context, "Failed to approve their extension", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        textView3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                SpecialExtensionApplicationAdapter.this.disApprovedExtensionsReference.child(specialExtension.getName()).setValue(specialExtension).addOnSuccessListener(new OnSuccessListener<Void>() {
                    public void onSuccess(Void param2Void) {
                        extensionsApplicationReference.setValue(null);
                        Toast.makeText(context, "You disapproved " + specialExtension.getName() + "'s extension approval", Toast.LENGTH_SHORT).show();
                        SpecialExtensionApplicationAdapter.this.extensions.remove(specialExtension);
                        UserData.saveUsersAppliedForSpecialExtension(extensions);
                        notifyDataSetChanged();
                    }
                });
            }
        });
        return paramView;
    }
}
