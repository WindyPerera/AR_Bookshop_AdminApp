package com.lk.bookshoppadminapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lk.bookshoppadminapp.Model.Admin;
import com.lk.bookshoppadminapp.OptionMenuActivity;
import com.lk.bookshoppadminapp.R;
import com.squareup.picasso.Picasso;

public class AdminProfileActivity extends NavigationMenuActivity {

    ImageView adminImage;
    EditText name, address, mobile, email;
    String adminId;
    Button browPic, editbtn;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        Bundle bundle = getIntent().getExtras();
        adminId = bundle.getString("adminID");

        adminImage = findViewById(R.id.admin_image);
        name = findViewById(R.id.name_field);
        email = findViewById(R.id.email_field);
        mobile = findViewById(R.id.mobile_field);
        browPic = findViewById(R.id.change_image_btn);
        editbtn = findViewById(R.id.edit_btn);

        loadDetails();
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAdmin();
            }
        });
    }

    public void loadDetails() {
        db.collection("Admin").document(adminId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Admin admin = documentSnapshot.toObject(Admin.class);
                name.setText(admin.getAdminName());
                email.setText(admin.getAdminEmail());
                mobile.setText(admin.getAdminMobile());


                StorageReference storageRef = storage.getReference();

                // Create a reference with an initial file path and name

                storageRef.child("AdminImages/" + admin.getImagePath()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        Log.d("HomeAactivity..............", "" + uri);
                        Picasso.with(AdminProfileActivity.this).load(uri).resize(250, 250).into(adminImage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.d("HomeAactivity..............", "" + exception.getMessage());
                        // Handle any errors
                    }
                });
            }
        });
    }

    public void editAdmin(){
        String rename = name.getText().toString();
        String reEmail = email.getText().toString();
        String reMObile = mobile.getText().toString();

        db.collection("Admin").document(adminId).update("adminName",rename,"adminEmail",reEmail,
                "adminMobile",reMObile).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AdminProfileActivity.this, "Update successfully!!!!", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminProfileActivity.this, "Update Errorr!!!!", Toast.LENGTH_SHORT).show();

            }
        });

    }
}