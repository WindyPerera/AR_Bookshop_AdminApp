package com.lk.bookshoppadminapp.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lk.bookshoppadminapp.Model.Product;
import com.lk.bookshoppadminapp.R;
import com.lk.bookshoppadminapp.UI.AddProductActivity;
import com.squareup.picasso.Picasso;

public class AddNewProduct extends Fragment {


    private static final int RESULT_OK = -1;
    public int FILE_CHOOSE_ACTIVITY_RESULT_CODE = 2;


    TextView book_name, book_category, book_brand, book_price;
    ImageView book_image;
    Button add_book, select_image;
    Toolbar toolbar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageReference;
    public Uri productUir;
    View view;

    public AddNewProduct() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        storageReference = FirebaseStorage.getInstance().getReference();
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_new_product, container, false);
        toolbar = view.findViewById(R.id.toolbar);

        book_name = view.findViewById(R.id.book_name);
        book_category = view.findViewById(R.id.type_text);
        book_brand = view.findViewById(R.id.brand_text);
        book_price = view.findViewById(R.id.price_text);
        book_image = view.findViewById(R.id.book_image);
        select_image = view.findViewById(R.id.select_btn);
        add_book = view.findViewById(R.id.add_book_btn);


        add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name = book_name.getText().toString();
                String category = book_category.getText().toString();
                String brand = book_brand.getText().toString();
                double price = Double.valueOf(book_price.getText().toString());

//                String nametrime = name.trim();
//                Log.d("name>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", nametrime);
                String imagePath = "ProductImages_"+book_name.getText().toString()+".png";

                Product product = new Product(name, brand, category, price, imagePath);

                storageReference.child("ProductImages/" + imagePath).
                        putFile(productUir).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(view.getContext(), "Photo Uploaded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
//                storageRef.child("RiderImages/" + driverImageUrlPath).putFile(driverphotoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Toast.makeText(RegisterActivity.this, "Driver Photo Uploaded", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(RegisterActivity.this, "Driver photo not uploaded", Toast.LENGTH_SHORT).show();
//                    }
//                });
                db.collection("Product").add(product).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(view.getContext(), "Product Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(view.getContext(), AddProductActivity.class);
                        startActivity(intent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Adding Error", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fileChooser = new Intent();
                fileChooser.setAction(Intent.ACTION_GET_CONTENT);
                fileChooser.setType("image/*");
                startActivityForResult(Intent.createChooser(fileChooser, "Select Product Photo"), FILE_CHOOSE_ACTIVITY_RESULT_CODE);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_CHOOSE_ACTIVITY_RESULT_CODE) {
            if (resultCode == RESULT_OK) {
                productUir = data.getData();
                Log.d("datadatadata.........>>>", String.valueOf(productUir));
                Picasso.with(getContext()).load(productUir).into(book_image);

            } else {
                Toast.makeText(getContext(), "File Not Selected", Toast.LENGTH_SHORT).show();
            }
        }
    }
}   