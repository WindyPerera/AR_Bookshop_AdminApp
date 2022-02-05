package com.lk.bookshoppadminapp.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lk.bookshoppadminapp.Model.Admin;
import com.lk.bookshoppadminapp.R;
import com.squareup.picasso.Picasso;

public class AdminRegister extends AppCompatActivity {
    private int FILE_CHOOSE_ACTIVITY_RESULT_CODE = 1;
    EditText name, mobile, email, password;
    Button save_admin, selectProfile_btn;
    ImageView adminImage;
    String imagePath;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public StorageReference storageReference;
    Uri adminUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        storageReference = FirebaseStorage.getInstance().getReference();

        name = findViewById(R.id.adminNameText);
        email = findViewById(R.id.adminEmailText);
        mobile = findViewById(R.id.adminMobileText);
        adminImage = findViewById(R.id.adminImage);
//        password = findViewById(R.id.admin_password_text);
        save_admin = findViewById(R.id.saveAdmin_btn);
        selectProfile_btn = findViewById(R.id.camera_btn);

        save_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(TextUtils.isEmpty(name.getText().toString())){
//                    name.setError("Name is Required.");
//                    return;
//                }if(TextUtils.isEmpty(email.getText().toString())){
//                    email.setError("Email is Required.");
//                    return;
//                }if(TextUtils.isEmpty(mobile.getText().toString())){
//                    mobile.setError("Mobile is Required.");
//                    return;
//                }if(mobile.length()<10){
//                    mobile.setError("Mobile No Must be >= 10 Characters");
//                }if(TextUtils.isEmpty(password.getText().toString())){
//                    password.setError("Password is Required.");
//                }if(password.length()<6){
//                    password.setError("Password Must be >= 6 Characters");
//                }
                String adminName = name.getText().toString();
                String adminEmail = email.getText().toString();
                String adminMobile = mobile.getText().toString();
//                String adminPassword = password.getText().toString();

                imagePath = "AdminImage_" + name.getText().toString() + ".png";
                Admin admin = new Admin(adminName, adminEmail, adminMobile, imagePath);
                storageReference.child("AdminImages/" + imagePath).
                        putFile(adminUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(AdminRegister.this, " Photo Uploaded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminRegister.this, " Photo Upload Error", Toast.LENGTH_SHORT).show();
                    }
                });
                db.collection("Admin").add(admin).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent intent = new Intent(AdminRegister.this, AdminLoging.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminRegister.this, "Save Error!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        selectProfile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fileChooser = new Intent();
                fileChooser.setAction(Intent.ACTION_GET_CONTENT);
                fileChooser.setType("image/*");
                startActivityForResult(Intent.createChooser(fileChooser, "Select Your Photo"), FILE_CHOOSE_ACTIVITY_RESULT_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_CHOOSE_ACTIVITY_RESULT_CODE) {
            if (resultCode == RESULT_OK) {
                adminUri = data.getData();
                Picasso.with(AdminRegister.this).load(adminUri).into(adminImage);
                Log.d("URI----------------", String.valueOf(adminUri));

            } else {
                Toast.makeText(this, "File Not Selected", Toast.LENGTH_SHORT).show();
            }
        }
    }
}