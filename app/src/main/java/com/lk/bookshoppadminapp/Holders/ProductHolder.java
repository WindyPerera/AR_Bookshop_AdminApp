package com.lk.bookshoppadminapp.Holders;

import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lk.bookshoppadminapp.Model.Product;
import com.lk.bookshoppadminapp.R;

public class ProductHolder extends RecyclerView.ViewHolder {
    public TextView product_brand, product_name;
    public Product product;
    public String productID;
    public ImageView productImage;
    public Button deleteBtn;
    View itemView;

    FirebaseAuth auth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ProductHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        loadDetails();

    }

    private void loadDetails() {
        product_brand = itemView.findViewById(R.id.brand_text);
        product_name = itemView.findViewById(R.id.name_text);
        deleteBtn = itemView.findViewById(R.id.delete_btn);
        productImage = itemView.findViewById(R.id.pro_image);


    }
}
