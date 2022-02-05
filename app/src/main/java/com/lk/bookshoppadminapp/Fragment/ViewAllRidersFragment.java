package com.lk.bookshoppadminapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lk.bookshoppadminapp.Model.Product;
import com.lk.bookshoppadminapp.Model.Rider;
import com.lk.bookshoppadminapp.R;
import com.lk.bookshoppadminapp.UI.ViewProductActivity;

import java.util.List;


public class ViewAllRidersFragment extends Fragment {

    View view;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TableLayout table;


    public ViewAllRidersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_all_riders, container, false);
        table = view.findViewById(R.id.table_layout);

        db.collection("Rider").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Rider> riderList = queryDocumentSnapshots.toObjects(Rider.class);

                for (int i = 0; i < riderList.size(); i++) {
                    DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(i);
                    String riderId = doc.getId();
                    Rider rider = doc.toObject(Rider.class);
                    TableRow row = new TableRow(view.getContext());

//                    TextView riderID = new TextView(view.getContext());
                    TextView riderName = new TextView(view.getContext());
                    TextView riderAddress = new TextView(view.getContext());
                    TextView riderEmail = new TextView(view.getContext());
                    TextView riderMobile = new TextView(view.getContext());

//                    riderID.setText(riderId);
                    riderName.setText(rider.getRiderName());
                    riderAddress.setText(rider.getRiderAddress());
                    riderEmail.setText(rider.getRiderEmail());
                    riderMobile.setText(rider.getRiderMobileNo());

//                    row.addView(riderID);
                    row.addView(riderName);
                    row.addView(riderAddress);
                    row.addView(riderEmail);
                    row.addView(riderMobile);
                    table.addView(row);

                }
            }
        });

        return view;
    }
}