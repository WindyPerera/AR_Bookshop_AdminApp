package com.lk.bookshoppadminapp.Holders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lk.bookshoppadminapp.Model.Product;
import com.lk.bookshoppadminapp.R;

public class UpdateHolder extends RecyclerView.ViewHolder {
    View itemView;
    public TextView product_brand, product_name;
    public Product product;
    public String productID;
    public ImageView productImage;
    public Button updateBtn;

    public UpdateHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        loadDetails();
    }

    private void loadDetails() {
        product_brand = itemView.findViewById(R.id.brand_text);
        product_name = itemView.findViewById(R.id.name_text);
        updateBtn = itemView.findViewById(R.id.delete_btn);
        productImage = itemView.findViewById(R.id.pro_image);


    }
}
