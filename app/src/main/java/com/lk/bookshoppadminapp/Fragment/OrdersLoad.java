package com.lk.bookshoppadminapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.lk.bookshoppadminapp.Holders.OrdersHolder;
import com.lk.bookshoppadminapp.Holders.ProductHolder;
import com.lk.bookshoppadminapp.Model.Invoice;
import com.lk.bookshoppadminapp.Model.Product;
import com.lk.bookshoppadminapp.R;
import com.lk.bookshoppadminapp.UI.ViewOrders;

public class OrdersLoad extends Fragment {
    RecyclerView order_list;
    Query loadOrder;
    View view;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage;
    FirestoreRecyclerAdapter<Invoice, OrdersHolder> fsRecycleAdapter;

    public OrdersLoad() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_orders_load, container, false);

        order_list = view.findViewById(R.id.orders_load_recycle);
        loadOrder = db.collection("Invoice").whereEqualTo("orderedStatus", "pending");
        order_list.setLayoutManager(new LinearLayoutManager(view.getContext()));

        FirestoreRecyclerOptions recyclerOptions = new FirestoreRecyclerOptions.
                Builder<Invoice>().setQuery(loadOrder, Invoice.class).build();

       fsRecycleAdapter = new FirestoreRecyclerAdapter<Invoice , OrdersHolder>(recyclerOptions){

           @NonNull
           @Override
           public OrdersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordered_item, parent, false);
               return new OrdersHolder(view);
           }

           @Override
           protected void onBindViewHolder(@NonNull OrdersHolder holder, int position, @NonNull Invoice model) {
               Log.d("FFFFFFFFFFFFFFFFFFFFF",model.getCustomerId());
               holder.dateText.setText(model.getCurrentDate());
               holder.invoiceID = getSnapshots().getSnapshot(position).getId();
               holder.timeText.setText(model.getCurrentTime());
               holder.orderId.setText(getSnapshots().getSnapshot(position).getId());
               holder.no.setText(String.valueOf(position+1));

           }
       };
        order_list.setAdapter(fsRecycleAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        fsRecycleAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        fsRecycleAdapter.stopListening();
    }
}