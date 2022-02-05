package com.lk.bookshoppadminapp.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lk.bookshoppadminapp.Model.Product;
import com.lk.bookshoppadminapp.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {
    private static final int RESULT_OK = -1;
    public int FILE_CHOOSE_ACTIVITY_RESULT_CODE = 2;

    String productID;
    EditText productName, brandName, catName, price;
    Button imageSearch, updateBtn;
    double upPrice = 0.0;
    ImageView proImege;
    public Uri productUir;
    StorageReference storageRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Bundle bundle = getIntent().getExtras();
        productID = bundle.getString("productId");

        productName = findViewById(R.id.product_name);
        brandName = findViewById(R.id.brand_name);
        catName = findViewById(R.id.catagery);
        price = findViewById(R.id.price_text);
        proImege = findViewById(R.id.pro_image);
        imageSearch = findViewById(R.id.searchImage);
        updateBtn = findViewById(R.id.update_btn);

        loadDetails();
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDetails();
            }
        });
        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fileChooser = new Intent();
                fileChooser.setAction(Intent.ACTION_GET_CONTENT);
                fileChooser.setType("image/*");
                startActivityForResult(Intent.createChooser(fileChooser, "Select Product Photo"), FILE_CHOOSE_ACTIVITY_RESULT_CODE);
            }
        });
    }

    public void loadDetails() {

        db.collection("Product").document(productID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Product product = documentSnapshot.toObject(Product.class);
                productName.setText(product.getProduct_name());
                brandName.setText(product.getBrand());
                catName.setText(product.getCategory());
                price.setText(String.valueOf(product.getPrice()));

//                String imagepath = product.getPro_image();

                StorageReference storageRef = storage.getReference();
//                storageRef.child("ProductImages/" + product.getPro_image()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        // Got the download URL for 'users/me/profile.png'
//                        String s = uri.toString();
//
//                        Glide.with(UpdateActivity.this).load(uri).into(proImege);
//                    }
//                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("errrrrrrrrrr", e.getMessage());
            }
        });
    }

    private void updateDetails() {


        String imagePath = "ProductImages_" + System.currentTimeMillis() + ".png";
        storageRef.child("ProductImages/" + imagePath).
                putFile(productUir).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(UpdateActivity.this, "Photo Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        db.collection("Product").document(productID)
                .update("product_name", productName.getText().toString(),
                        "brand", brandName.getText().toString(),
                        "category", catName.getText().toString(), "price",
                        Double.parseDouble(price.getText().toString()), "pro_image", imagePath).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UpdateActivity.this, "Product Updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateActivity.this, UpdateProductActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateActivity.this, "Updating Error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_CHOOSE_ACTIVITY_RESULT_CODE) {
            if (resultCode == RESULT_OK) {
                productUir = data.getData();
                Log.d("datadatadata.........>>>", String.valueOf(productUir));
                Picasso.with(UpdateActivity.this).load(productUir).into(proImege);

            } else {
                Toast.makeText(UpdateActivity.this, "File Not Selected", Toast.LENGTH_SHORT).show();
            }
        }
    }
}