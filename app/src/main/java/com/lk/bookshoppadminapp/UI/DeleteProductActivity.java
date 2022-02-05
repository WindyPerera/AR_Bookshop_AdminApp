package com.lk.bookshoppadminapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.lk.bookshoppadminapp.Model.Product;
import com.lk.bookshoppadminapp.R;

public class DeleteProductActivity extends AppCompatActivity {

    RecyclerView product_list;
    Query loadProduct;
    public ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage  = FirebaseStorage.getInstance();
    FirestoreRecyclerAdapter<Product, ProductHolder> fsRecycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Delete Products");

        loadProduct = db.collection("Product");
        product_list = findViewById(R.id.remove_list);
        product_list.setLayoutManager(new GridLayoutManager(this, 2));

        FirestoreRecyclerOptions recyclerOptions = new FirestoreRecyclerOptions.
                Builder<Product>().setQuery(loadProduct, Product.class).build();

        fsRecycleAdapter = new FirestoreRecyclerAdapter<Product, ProductHolder>(recyclerOptions) {

            @NonNull
            @Override
            public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                return new ProductHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductHolder holder, int position, @NonNull Product model) {
                holder.product_name.setText(model.getProduct_name());
                holder.product_brand.setText(model.getBrand());
                String productID = getSnapshots().getSnapshot(position).getId();

                // Create a storage reference from our app
                StorageReference storageRef = storage.getReference();
                storageRef.child("ProductImages/" + model.getPro_image()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        String s = uri.toString();

                        Glide.with(DeleteProductActivity.this).load(uri).into(holder.productImage);
                    }
                });
                holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(DeleteProductActivity.this);
                        alert.setMessage("Are you sure?")
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {

                                        db.collection("Product").document(productID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(DeleteProductActivity.this,"Delete is Success",Toast.LENGTH_LONG).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("deleteError-------------",e.getMessage());
                                            }
                                        });

                                    }


                                }).setNegativeButton("Cancel", null);

                        AlertDialog alert1 = alert.create();
                        alert1.show();

                    }
                });
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