package com.lk.bookshoppadminapp.Holders;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lk.bookshoppadminapp.R;
import com.lk.bookshoppadminapp.UI.ViewOrders;

public class OrdersHolder extends RecyclerView.ViewHolder {

    public TextView no,dateText,timeText,orderId;
    public Button viewBtn;
    public String invoiceID;

    public OrdersHolder(@NonNull View itemView) {
        super(itemView);

        no = itemView.findViewById(R.id.no);
        dateText = itemView.findViewById(R.id.date);
        timeText = itemView.findViewById(R.id.time);
        orderId = itemView.findViewById(R.id.ordered_id);

        viewBtn = itemView.findViewById(R.id.view_order);

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewOrders.class);
                intent.putExtra("invoiceID",invoiceID);
                itemView.getContext().startActivity(intent);
            }
        });

    }
}
