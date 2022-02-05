package com.lk.bookshoppadminapp.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lk.bookshoppadminapp.Model.Rider;
import com.lk.bookshoppadminapp.R;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class AddNewRiderFragment extends Fragment {

    private int FILE_CHOOSE_ACTIVITY_RESULT_CODE = 1;
    private int FILE_CHOOSE_ACTIVITY_RESULT_CODE_LICENSE= 2;
    private int FILE_CHOOSE_ACTIVITY_RESULT_CODE_VEHICLE= 3;
    private Uri riderPhoto,licPhoto,vehiclePhoto;
    private View view;
    ImageView riderImage, licenseImage, vehicleNoImage;
    ImageView riderImegeSelect, vehicleNoSelect, licenseImageSelect;
    Button saveRider;
    TextView riderName, riderAddress, riderEmail, riderMobile, vehicleNo, licenseNo;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public StorageReference storageRef;

    public AddNewRiderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_new_rider, container, false);

        CollectionReference reference = db.collection("Rider");

        storageRef = FirebaseStorage.getInstance().getReference();

        riderName = view.findViewById(R.id.riderName_text);
        riderAddress = view.findViewById(R.id.riderAddress_text);
        riderEmail = view.findViewById(R.id.riderEmail_text);
        riderMobile = view.findViewById(R.id.riderPhone_text);
        vehicleNo = view.findViewById(R.id.vehicleNo_text);
        licenseNo = view.findViewById(R.id.licenseNo_text);

        riderImage = view.findViewById(R.id.riderImage);
        vehicleNoImage = view.findViewById(R.id.vehicle_image);
        licenseImage = view.findViewById(R.id.license_image);
        riderImegeSelect = view.findViewById(R.id.riderSelectImage_btn);
        vehicleNoSelect = view.findViewById(R.id.vehicleNoImage_btn);
        licenseImageSelect = view.findViewById(R.id.licenseNoImage_btn);
        saveRider = view.findViewById(R.id.saveRider_btn);



        saveRider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rider rider = new Rider();

                rider.setRiderName(riderName.getText().toString());
                rider.setRiderAddress(riderAddress.getText().toString());
                rider.setRiderEmail(riderEmail.getText().toString());
                rider.setRiderMobileNo(riderMobile.getText().toString());
                rider.setLicenseNo(licenseNo.getText().toString());
                rider.setVehicleNo(vehicleNo.getText().toString());

                String riderImagePath = "RiderImage_"+licenseNo.getText().toString()+".png";
                rider.setRiderImagePath(riderImagePath);
                String licenseImagePath = "LicenseImage_"+System.currentTimeMillis()+".png";
                rider.setLicenseNonImagePath(licenseImagePath);
                String vehicleImagePath = "VehicleImage_"+System.currentTimeMillis()+".png";
                rider.setLicenseNonImagePath(vehicleImagePath);

                //Rider Photo
                storageRef.child("RiderImages/"+riderImagePath).
                        putFile(riderPhoto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(view.getContext(),"Rider Photo Uploaded",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(),"Error",Toast.LENGTH_SHORT).show();
                    }
                });

                //License Photo
                storageRef.child("LicenseImage/"+licenseImagePath).
                        putFile(licPhoto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(view.getContext(),"License Photo Uploaded",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(),"error",Toast.LENGTH_SHORT).show();

                    }
                });

                //vehicle no photo
                storageRef.child("VehicleImage/"+licenseImagePath).
                        putFile(vehiclePhoto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(view.getContext(),"Vehicle Photo Uploaded",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(),"error",Toast.LENGTH_SHORT).show();

                    }
                });

                reference.add(rider).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(view.getContext(),"Registered Successfully !", Toast.LENGTH_SHORT).show();
//                        view.getSupportFragmentManager().beginTransaction().replace(R.id.payment_container,
//                                new PaymentMethod()).commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(),"Registered Fail !", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        riderImegeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fileChooser = new Intent();
                fileChooser.setAction(Intent.ACTION_GET_CONTENT);
                fileChooser.setType("image/*");
                startActivityForResult(Intent.createChooser(fileChooser, "Select Rider Photo"), FILE_CHOOSE_ACTIVITY_RESULT_CODE);

                Toast.makeText(view.getContext(), "avaa", Toast.LENGTH_SHORT).show();
            }
        });

        vehicleNoSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fileChooser = new Intent();
                fileChooser.setAction(Intent.ACTION_GET_CONTENT);
                fileChooser.setType("image/*");
                startActivityForResult(Intent.createChooser(fileChooser, "Select Vehicle No Photo"), FILE_CHOOSE_ACTIVITY_RESULT_CODE_VEHICLE);

                Toast.makeText(view.getContext(), "avaa", Toast.LENGTH_SHORT).show();
            }
        });

        licenseImageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fileChooser = new Intent();
                fileChooser.setAction(Intent.ACTION_GET_CONTENT);
                fileChooser.setType("image/*");
                startActivityForResult(Intent.createChooser(fileChooser, "Select License Photo"), FILE_CHOOSE_ACTIVITY_RESULT_CODE_LICENSE);

                Toast.makeText(view.getContext(), "avaa", Toast.LENGTH_SHORT).show();
            }
        });
        return view;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==FILE_CHOOSE_ACTIVITY_RESULT_CODE){
            if(resultCode==RESULT_OK){
                riderPhoto = data.getData();
                Picasso.with(view.getContext()).load(riderPhoto).into(riderImage);

            }else{
                Toast.makeText(view.getContext(),"File Not Selected",Toast.LENGTH_SHORT).show();
            }
        }

        if(requestCode==FILE_CHOOSE_ACTIVITY_RESULT_CODE_LICENSE){
            if(resultCode==RESULT_OK){
                licPhoto = data.getData();
                Picasso.with(view.getContext()).load(licPhoto).into(licenseImage);

            }else{
                Toast.makeText(view.getContext(),"File Not Selected",Toast.LENGTH_SHORT).show();
            }
        }

        if(requestCode==FILE_CHOOSE_ACTIVITY_RESULT_CODE_VEHICLE){
            if(resultCode==RESULT_OK){
                vehiclePhoto = data.getData();
                Picasso.with(view.getContext()).load(vehiclePhoto).into(vehicleNoImage);

            }else{
                Toast.makeText(view.getContext(),"File Not Selected",Toast.LENGTH_SHORT).show();
            }
        }
    }
}