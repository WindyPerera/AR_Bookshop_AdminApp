package com.lk.bookshoppadminapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lk.bookshoppadminapp.Holders.ProductHolder;
import com.lk.bookshoppadminapp.Holders.UpdateHolder;
import com.lk.bookshoppadminapp.Model.Product;
import com.lk.bookshoppadminapp.R;

public class UpdateProductActivity extends AppCompatActivity {
    RecyclerView product_list;
    Query loadProduct;
    Spinner book_category;
    Spinner book_brand;
    Toolbar toolbar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage= FirebaseStorage.getInstance();
    FirestoreRecyclerAdapter<Product, UpdateHolder> fsRecycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Products");

        product_list = findViewById(R.id.up_list);
        product_list.setLayoutManager(new GridLayoutManager(this, 2));
//
//        String[] categorys = {"CR Books", "Exercise Books", "Pencils", "Pens","Glue Bottles","Erasers"};
//        ArrayAdapter category_list = new ArrayAdapter(this, android.R.layout.simple_selectable_list_item, categorys);
//        book_category.setAdapter(category_list);
//
//        String category = book_category.getSelectedItem().toString();
//        String brandList = book_brand.getSelectedItem().toString();
//
//        String[] brand = {"Atlas", "Weerodara", "Maped", "Richard"};
//        ArrayAdapter<String> brand_list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, brand);
//        book_brand.setAdapter(brand_list);

        loadProduct = db.collection("Product");
        FirestoreRecyclerOptions recyclerOptions = new FirestoreRecyclerOptions.
                Builder<Product>().setQuery(loadProduct, Product.class).build();

        fsRecycleAdapter = new FirestoreRecyclerAdapter<Product, UpdateHolder>(recyclerOptions) {

            @Override
            protected void onBindViewHolder(@NonNull UpdateHolder holder, int position, @NonNull Product model) {
                Log.d("uuuuuuuuuuuuuupp",model.getProduct_name());
                holder.product_name.setText(model.getProduct_name());
                holder.product_brand.setText(model.getBrand());
                holder.productID = getSnapshots().getSnapshot(position).getId();

                // Create a storage reference from our app
                StorageReference storageRef = storage.getReference();
//                storageRef.child("ProductImages/" + model.getPro_image()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        // Got the download URL for 'users/me/profile.png'
//                        String s = uri.toString();
//
////                        Glide.with(UpdateProductActivity.this).load(uri).into(holder.productImage);
//                    }
//                });
                holder.updateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(UpdateProductActivity.this,UpdateActivity.class);
                        intent.putExtra("productId",getSnapshots().getSnapshot(position).getId());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public UpdateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.update_item, parent, false);
                return new UpdateHolder(view);
            }


        };
        product_list.setAdapter(fsRecycleAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fsRecycleAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        fsRecycleAdapter.stopListening();
    }
}