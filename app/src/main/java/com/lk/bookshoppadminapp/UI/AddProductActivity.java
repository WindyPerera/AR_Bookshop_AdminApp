package com.lk.bookshoppadminapp.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lk.bookshoppadminapp.Fragment.AddNewProduct;
import com.lk.bookshoppadminapp.Model.Brand;
import com.lk.bookshoppadminapp.Model.Product;
import com.lk.bookshoppadminapp.R;
import com.squareup.picasso.Picasso;

public class AddProductActivity extends AppCompatActivity {

    Toolbar toolbar;
    private static final int RESULT_OK = -1;
    public int FILE_CHOOSE_ACTIVITY_RESULT_CODE = 2;


    TextView book_name, book_price;
    ImageView book_image;
    Button add_book,select_image;
    Spinner book_category;
    AutoCompleteTextView book_brand;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageReference;
    public Uri productUir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        storageReference = FirebaseStorage.getInstance().getReference();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Product");

        book_name = findViewById(R.id.book_name);
        book_category = findViewById(R.id.category_text);
        book_brand = findViewById(R.id.brand_text);
        book_price = findViewById(R.id.price_text);
        book_image = findViewById(R.id.book_image);
        select_image = findViewById(R.id.select_btn);
        add_book = findViewById(R.id.add_book_btn);

        String[] categorys = {"CR Books", "Exercise Books", "Pencils", "Pens","Glue Bottles","Erasers"};
        ArrayAdapter category_list = new ArrayAdapter(this, android.R.layout.simple_selectable_list_item, categorys);
        book_category.setAdapter(category_list);


        String[] brand = {"Atlas", "Weerodara", "Maped", "Richard"};
        ArrayAdapter<String> brand_list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, brand);
        book_brand.setAdapter(brand_list);
//        db.collection("Brand").whereEqualTo("brandName",)

//        String brandName = book_brand.getText().toString();
//        Brand brand = new Brand(brandName);


        add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name = book_name.getText().toString();
                String category = book_category.getSelectedItem().toString();
                String brand = book_brand.getText().toString();
                double price = Double.valueOf(book_price.getText().toString());

                String imagePath = "ProductImages_" + System.currentTimeMillis() + ".png";
                Product product = new Product(name, brand, category, price, imagePath);

                storageReference.child("ProductImages/" + imagePath).
                        putFile(productUir).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(AddProductActivity.this, "Photo Uploaded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddProductActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

                db.collection("Product").add(product).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddProductActivity.this, "Product Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddProductActivity.this, AddProductActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddProductActivity.this, "Adding Error", Toast.LENGTH_SHORT).show();
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
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_CHOOSE_ACTIVITY_RESULT_CODE) {
            if (resultCode == RESULT_OK) {
                productUir = data.getData();
                Log.d("datadatadata.........>>>", String.valueOf(productUir));
                Picasso.with(AddProductActivity.this).load(productUir).into(book_image);

            } else {
                Toast.makeText(AddProductActivity.this, "File Not Selected", Toast.LENGTH_SHORT).show();
            }
        }
    }
}