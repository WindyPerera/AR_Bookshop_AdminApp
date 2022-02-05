package com.lk.bookshoppadminapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.lk.bookshoppadminapp.Model.Product;
import com.lk.bookshoppadminapp.R;

import java.util.ArrayList;
import java.util.List;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

public class ViewProductActivity extends AppCompatActivity {

    Spinner category_list,brand_list,price_list;
    String category_name,brand_name,price;
    Toolbar toolbar;
    TableLayout table;
    DataTable dataTable;
    DataTableHeader header;
    private ArrayList<DataTableRow> rows = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        toolbar = findViewById(R.id.toolbar);
        table = findViewById(R.id.table_layout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View All Product");

        dataTable = findViewById(R.id.data_table);

        db.collection("Product").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<Product> products= queryDocumentSnapshots.toObjects(Product.class);
                header = new DataTableHeader.Builder()
                        .item("No", 5)
                        .item("ProName", 5)
                        .item("ProBrand", 5)
                        .item("ProCategory", 6)
                        .item("Price", 5)
                        .build();

                for(int i=0;i<products.size();i++) {

                    DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(i);
                    String product_Id = doc.getId();
                    Product product = doc.toObject(Product.class);

                    DataTableRow row = new DataTableRow.Builder()
                            .value(String.valueOf(i +1))
                            .value(product.getProduct_name())
                            .value(product.getBrand())
                            .value(product.getCategory())
                            .value("Rs."+String.valueOf(product.getPrice())+"0")
                            .build();
                    rows.add(row);
                    dataTable.setHeader(header);
                    dataTable.setRows(rows);
                    dataTable.inflate(ViewProductActivity.this);
                }
            }
        });

    }
}